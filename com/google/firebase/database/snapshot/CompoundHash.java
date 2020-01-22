package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.NodeSizeEstimator;
import com.google.firebase.database.core.utilities.Utilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class CompoundHash
{
  private final List<String> hashes;
  private final List<Path> posts;
  
  private CompoundHash(List paramList1, List paramList2)
  {
    if (paramList1.size() == paramList2.size() - 1)
    {
      posts = paramList1;
      hashes = paramList2;
      return;
    }
    throw new IllegalArgumentException("Number of posts need to be n-1 for n hashes in CompoundHash");
  }
  
  public static CompoundHash fromNode(Node paramNode)
  {
    return fromNode(paramNode, new SimpleSizeSplitStrategy(paramNode));
  }
  
  public static CompoundHash fromNode(Node paramNode, SplitStrategy paramSplitStrategy)
  {
    if (paramNode.isEmpty()) {
      return new CompoundHash(Collections.emptyList(), Collections.singletonList(""));
    }
    paramSplitStrategy = new CompoundHashBuilder(paramSplitStrategy);
    processNode(paramNode, paramSplitStrategy);
    paramSplitStrategy.finishHashing();
    return new CompoundHash(currentPaths, currentHashes);
  }
  
  private static void processNode(Node paramNode, CompoundHashBuilder paramCompoundHashBuilder)
  {
    if (paramNode.isLeafNode())
    {
      paramCompoundHashBuilder.processLeaf((LeafNode)paramNode);
      return;
    }
    if (!paramNode.isEmpty())
    {
      if ((paramNode instanceof ChildrenNode))
      {
        ((ChildrenNode)paramNode).forEachChild(new ChildrenNode.ChildVisitor()
        {
          public void visitChild(ChildKey paramAnonymousChildKey, Node paramAnonymousNode)
          {
            CompoundHash.CompoundHashBuilder.access$400(CompoundHash.this, paramAnonymousChildKey);
            CompoundHash.processNode(paramAnonymousNode, CompoundHash.this);
            CompoundHash.CompoundHashBuilder.access$600(CompoundHash.this);
          }
        }, true);
        return;
      }
      paramCompoundHashBuilder = new StringBuilder();
      paramCompoundHashBuilder.append("Expected children node, but got: ");
      paramCompoundHashBuilder.append(paramNode);
      throw new IllegalStateException(paramCompoundHashBuilder.toString());
    }
    throw new IllegalArgumentException("Can't calculate hash on empty node!");
  }
  
  public List getHashes()
  {
    return Collections.unmodifiableList(hashes);
  }
  
  public List getPosts()
  {
    return Collections.unmodifiableList(posts);
  }
  
  static class CompoundHashBuilder
  {
    private final List<String> currentHashes = new ArrayList();
    private Stack<ChildKey> currentPath = new Stack();
    private int currentPathDepth;
    private final List<Path> currentPaths = new ArrayList();
    private int lastLeafDepth = -1;
    private boolean needsComma = true;
    private StringBuilder optHashValueBuilder = null;
    private final CompoundHash.SplitStrategy splitStrategy;
    
    public CompoundHashBuilder(CompoundHash.SplitStrategy paramSplitStrategy)
    {
      splitStrategy = paramSplitStrategy;
    }
    
    private void appendKey(StringBuilder paramStringBuilder, ChildKey paramChildKey)
    {
      paramStringBuilder.append(Utilities.stringHashV2Representation(paramChildKey.asString()));
    }
    
    private Path currentPath(int paramInt)
    {
      ChildKey[] arrayOfChildKey = new ChildKey[paramInt];
      int i = 0;
      while (i < paramInt)
      {
        arrayOfChildKey[i] = ((ChildKey)currentPath.get(i));
        i += 1;
      }
      return new Path(arrayOfChildKey);
    }
    
    private void endChild()
    {
      currentPathDepth -= 1;
      if (buildingRange()) {
        optHashValueBuilder.append(")");
      }
      needsComma = true;
    }
    
    private void endRange()
    {
      Utilities.hardAssert(buildingRange(), "Can't end range without starting a range!");
      int i = 0;
      while (i < currentPathDepth)
      {
        optHashValueBuilder.append(")");
        i += 1;
      }
      optHashValueBuilder.append(")");
      Path localPath = currentPath(lastLeafDepth);
      String str = Utilities.sha1HexDigest(optHashValueBuilder.toString());
      currentHashes.add(str);
      currentPaths.add(localPath);
      optHashValueBuilder = null;
    }
    
    private void ensureRange()
    {
      if (!buildingRange())
      {
        optHashValueBuilder = new StringBuilder();
        optHashValueBuilder.append("(");
        Iterator localIterator = currentPath(currentPathDepth).iterator();
        while (localIterator.hasNext())
        {
          ChildKey localChildKey = (ChildKey)localIterator.next();
          appendKey(optHashValueBuilder, localChildKey);
          optHashValueBuilder.append(":(");
        }
        needsComma = false;
      }
    }
    
    private void finishHashing()
    {
      boolean bool;
      if (currentPathDepth == 0) {
        bool = true;
      } else {
        bool = false;
      }
      Utilities.hardAssert(bool, "Can't finish hashing in the middle processing a child");
      if (buildingRange()) {
        endRange();
      }
      currentHashes.add("");
    }
    
    private void processLeaf(LeafNode paramLeafNode)
    {
      ensureRange();
      lastLeafDepth = currentPathDepth;
      optHashValueBuilder.append(paramLeafNode.getHashRepresentation(Node.HashVersion.BITWISE_OR));
      needsComma = true;
      if (splitStrategy.shouldSplit(this)) {
        endRange();
      }
    }
    
    private void startChild(ChildKey paramChildKey)
    {
      ensureRange();
      if (needsComma) {
        optHashValueBuilder.append(",");
      }
      appendKey(optHashValueBuilder, paramChildKey);
      optHashValueBuilder.append(":(");
      if (currentPathDepth == currentPath.size()) {
        currentPath.add(paramChildKey);
      } else {
        currentPath.set(currentPathDepth, paramChildKey);
      }
      currentPathDepth += 1;
      needsComma = false;
    }
    
    public boolean buildingRange()
    {
      return optHashValueBuilder != null;
    }
    
    public int currentHashLength()
    {
      return optHashValueBuilder.length();
    }
    
    public Path currentPath()
    {
      return currentPath(currentPathDepth);
    }
  }
  
  private static class SimpleSizeSplitStrategy
    implements CompoundHash.SplitStrategy
  {
    private final long splitThreshold;
    
    public SimpleSizeSplitStrategy(Node paramNode)
    {
      splitThreshold = Math.max(512L, Math.sqrt(NodeSizeEstimator.estimateSerializedNodeSize(paramNode) * 100L));
    }
    
    public boolean shouldSplit(CompoundHash.CompoundHashBuilder paramCompoundHashBuilder)
    {
      return (paramCompoundHashBuilder.currentHashLength() > splitThreshold) && ((paramCompoundHashBuilder.currentPath().isEmpty()) || (!paramCompoundHashBuilder.currentPath().getBack().equals(ChildKey.getPriorityKey())));
    }
  }
  
  public static abstract interface SplitStrategy
  {
    public abstract boolean shouldSplit(CompoundHash.CompoundHashBuilder paramCompoundHashBuilder);
  }
}
