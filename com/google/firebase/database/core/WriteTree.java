package com.google.firebase.database.core;

import com.google.firebase.database.core.utilities.Predicate;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class WriteTree
{
  private static final Predicate<UserWriteRecord> DEFAULT_FILTER = new Predicate()
  {
    public boolean evaluate(UserWriteRecord paramAnonymousUserWriteRecord)
    {
      return paramAnonymousUserWriteRecord.isVisible();
    }
  };
  private List<UserWriteRecord> allWrites = new ArrayList();
  private Long lastWriteId = Long.valueOf(-1L);
  private CompoundWrite visibleWrites = CompoundWrite.emptyWrite();
  
  public WriteTree() {}
  
  private static CompoundWrite layerTree(List paramList, Predicate paramPredicate, Path paramPath)
  {
    Object localObject = CompoundWrite.emptyWrite();
    Iterator localIterator = paramList.iterator();
    paramList = (List)localObject;
    while (localIterator.hasNext())
    {
      localObject = (UserWriteRecord)localIterator.next();
      if (paramPredicate.evaluate(localObject))
      {
        Path localPath = ((UserWriteRecord)localObject).getPath();
        if (((UserWriteRecord)localObject).isOverwrite())
        {
          if (paramPath.contains(localPath)) {
            paramList = paramList.addWrite(Path.getRelative(paramPath, localPath), ((UserWriteRecord)localObject).getOverwrite());
          } else if (localPath.contains(paramPath)) {
            paramList = paramList.addWrite(Path.getEmptyPath(), ((UserWriteRecord)localObject).getOverwrite().getChild(Path.getRelative(localPath, paramPath)));
          }
        }
        else if (paramPath.contains(localPath))
        {
          paramList = paramList.addWrites(Path.getRelative(paramPath, localPath), ((UserWriteRecord)localObject).getMerge());
        }
        else if (localPath.contains(paramPath))
        {
          localPath = Path.getRelative(localPath, paramPath);
          if (localPath.isEmpty())
          {
            paramList = paramList.addWrites(Path.getEmptyPath(), ((UserWriteRecord)localObject).getMerge());
          }
          else
          {
            localObject = ((UserWriteRecord)localObject).getMerge().getCompleteNode(localPath);
            if (localObject != null) {
              paramList = paramList.addWrite(Path.getEmptyPath(), (Node)localObject);
            }
          }
        }
      }
    }
    return paramList;
  }
  
  private boolean recordContainsPath(UserWriteRecord paramUserWriteRecord, Path paramPath)
  {
    if (paramUserWriteRecord.isOverwrite()) {
      return paramUserWriteRecord.getPath().contains(paramPath);
    }
    Iterator localIterator = paramUserWriteRecord.getMerge().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (paramUserWriteRecord.getPath().child((Path)localEntry.getKey()).contains(paramPath)) {
        return true;
      }
    }
    return false;
  }
  
  private void resetTree()
  {
    visibleWrites = layerTree(allWrites, DEFAULT_FILTER, Path.getEmptyPath());
    if (allWrites.size() > 0)
    {
      List localList = allWrites;
      lastWriteId = Long.valueOf(((UserWriteRecord)localList.get(localList.size() - 1)).getWriteId());
      return;
    }
    lastWriteId = Long.valueOf(-1L);
  }
  
  public void addMerge(Path paramPath, CompoundWrite paramCompoundWrite, Long paramLong)
  {
    allWrites.add(new UserWriteRecord(paramLong.longValue(), paramPath, paramCompoundWrite));
    visibleWrites = visibleWrites.addWrites(paramPath, paramCompoundWrite);
    lastWriteId = paramLong;
  }
  
  public void addOverwrite(Path paramPath, Node paramNode, Long paramLong, boolean paramBoolean)
  {
    allWrites.add(new UserWriteRecord(paramLong.longValue(), paramPath, paramNode, paramBoolean));
    if (paramBoolean) {
      visibleWrites = visibleWrites.addWrite(paramPath, paramNode);
    }
    lastWriteId = paramLong;
  }
  
  public Node calcCompleteChild(Path paramPath, ChildKey paramChildKey, CacheNode paramCacheNode)
  {
    paramPath = paramPath.child(paramChildKey);
    Node localNode = visibleWrites.getCompleteNode(paramPath);
    if (localNode != null) {
      return localNode;
    }
    if (paramCacheNode.isCompleteForChild(paramChildKey)) {
      return visibleWrites.childCompoundWrite(paramPath).apply(paramCacheNode.getNode().getImmediateChild(paramChildKey));
    }
    return null;
  }
  
  public Node calcCompleteEventCache(Path paramPath, Node paramNode)
  {
    return calcCompleteEventCache(paramPath, paramNode, new ArrayList());
  }
  
  public Node calcCompleteEventCache(Path paramPath, Node paramNode, List paramList)
  {
    return calcCompleteEventCache(paramPath, paramNode, paramList, false);
  }
  
  public Node calcCompleteEventCache(final Path paramPath, Node paramNode, final List paramList, final boolean paramBoolean)
  {
    if ((paramList.isEmpty()) && (!paramBoolean))
    {
      paramList = visibleWrites.getCompleteNode(paramPath);
      if (paramList != null) {
        return paramList;
      }
      paramPath = visibleWrites.childCompoundWrite(paramPath);
      if (paramPath.isEmpty()) {
        return (Node)paramNode;
      }
      if ((paramNode == null) && (!paramPath.hasCompleteWrite(Path.getEmptyPath()))) {
        return null;
      }
      if (paramNode == null) {
        paramNode = EmptyNode.Empty();
      }
      return paramPath.apply((Node)paramNode);
    }
    CompoundWrite localCompoundWrite = visibleWrites.childCompoundWrite(paramPath);
    if ((!paramBoolean) && (localCompoundWrite.isEmpty())) {
      return (Node)paramNode;
    }
    if ((!paramBoolean) && (paramNode == null) && (!localCompoundWrite.hasCompleteWrite(Path.getEmptyPath()))) {
      return null;
    }
    paramList = new Predicate()
    {
      public boolean evaluate(UserWriteRecord paramAnonymousUserWriteRecord)
      {
        return ((paramAnonymousUserWriteRecord.isVisible()) || (paramBoolean)) && (!paramList.contains(Long.valueOf(paramAnonymousUserWriteRecord.getWriteId()))) && ((paramAnonymousUserWriteRecord.getPath().contains(paramPath)) || (paramPath.contains(paramAnonymousUserWriteRecord.getPath())));
      }
    };
    paramPath = layerTree(allWrites, paramList, paramPath);
    if (paramNode == null) {
      paramNode = EmptyNode.Empty();
    }
    return paramPath.apply((Node)paramNode);
  }
  
  public Node calcCompleteEventChildren(Path paramPath, Node paramNode)
  {
    Object localObject2 = EmptyNode.Empty();
    Object localObject1 = localObject2;
    Object localObject3 = visibleWrites.getCompleteNode(paramPath);
    if (localObject3 != null)
    {
      if (!((Node)localObject3).isLeafNode())
      {
        paramPath = ((Iterable)localObject3).iterator();
        while (paramPath.hasNext())
        {
          localObject2 = (NamedNode)paramPath.next();
          paramNode = ((NamedNode)localObject2).getName();
          localObject2 = ((NamedNode)localObject2).getNode();
          localObject1 = ((Node)localObject1).updateImmediateChild(paramNode, (Node)localObject2);
        }
      }
      return localObject2;
    }
    paramPath = visibleWrites.childCompoundWrite(paramPath);
    paramNode = paramNode.iterator();
    while (paramNode.hasNext())
    {
      localObject3 = (NamedNode)paramNode.next();
      localObject2 = paramPath.childCompoundWrite(new Path(new ChildKey[] { ((NamedNode)localObject3).getName() })).apply(((NamedNode)localObject3).getNode());
      localObject3 = ((NamedNode)localObject3).getName();
      localObject1 = ((Node)localObject1).updateImmediateChild((ChildKey)localObject3, (Node)localObject2);
    }
    paramPath = paramPath.getCompleteChildren().iterator();
    while (paramPath.hasNext())
    {
      localObject2 = (NamedNode)paramPath.next();
      paramNode = ((NamedNode)localObject2).getName();
      localObject2 = ((NamedNode)localObject2).getNode();
      localObject1 = ((Node)localObject1).updateImmediateChild(paramNode, (Node)localObject2);
    }
    return (Node)localObject1;
    return (Node)localObject1;
  }
  
  public Node calcEventCacheAfterServerOverwrite(Path paramPath1, Path paramPath2, Node paramNode1, Node paramNode2)
  {
    paramPath1 = paramPath1.child(paramPath2);
    if (visibleWrites.hasCompleteWrite(paramPath1)) {
      return null;
    }
    paramPath1 = visibleWrites.childCompoundWrite(paramPath1);
    if (paramPath1.isEmpty()) {
      return paramNode2.getChild(paramPath2);
    }
    return paramPath1.apply(paramNode2.getChild(paramPath2));
  }
  
  public NamedNode calcNextNodeAfterPost(Path paramPath, Node paramNode, NamedNode paramNamedNode, boolean paramBoolean, Index paramIndex)
  {
    CompoundWrite localCompoundWrite = visibleWrites.childCompoundWrite(paramPath);
    Object localObject2 = localCompoundWrite.getCompleteNode(Path.getEmptyPath());
    paramPath = (Path)localObject2;
    Object localObject1 = null;
    if (localObject2 == null)
    {
      if (paramNode != null) {
        paramPath = localCompoundWrite.apply(paramNode);
      }
    }
    else
    {
      localObject2 = paramPath.iterator();
      for (paramPath = localObject1; ((Iterator)localObject2).hasNext(); paramPath = paramNode)
      {
        label56:
        paramNode = (NamedNode)((Iterator)localObject2).next();
        if ((paramIndex.compare(paramNode, paramNamedNode, paramBoolean) <= 0) || ((paramPath != null) && (paramIndex.compare(paramNode, paramPath, paramBoolean) >= 0))) {
          break label56;
        }
      }
    }
    return null;
    return paramPath;
  }
  
  public WriteTreeRef childWrites(Path paramPath)
  {
    return new WriteTreeRef(paramPath, this);
  }
  
  public Node getCompleteWriteData(Path paramPath)
  {
    return visibleWrites.getCompleteNode(paramPath);
  }
  
  public UserWriteRecord getWrite(long paramLong)
  {
    Iterator localIterator = allWrites.iterator();
    while (localIterator.hasNext())
    {
      UserWriteRecord localUserWriteRecord = (UserWriteRecord)localIterator.next();
      if (localUserWriteRecord.getWriteId() == paramLong) {
        return localUserWriteRecord;
      }
    }
    return null;
  }
  
  public List purgeAllWrites()
  {
    ArrayList localArrayList = new ArrayList(allWrites);
    visibleWrites = CompoundWrite.emptyWrite();
    allWrites = new ArrayList();
    return localArrayList;
  }
  
  public boolean removeWrite(long paramLong)
  {
    Object localObject = allWrites.iterator();
    int i = 0;
    while (((Iterator)localObject).hasNext())
    {
      localUserWriteRecord = (UserWriteRecord)((Iterator)localObject).next();
      if (localUserWriteRecord.getWriteId() == paramLong) {
        break label58;
      }
      i += 1;
    }
    UserWriteRecord localUserWriteRecord = null;
    label58:
    allWrites.remove(localUserWriteRecord);
    boolean bool1 = localUserWriteRecord.isVisible();
    int j = allWrites.size() - 1;
    int m;
    for (int k = 0; (bool1) && (j >= 0); k = m)
    {
      localObject = (UserWriteRecord)allWrites.get(j);
      boolean bool2 = bool1;
      m = k;
      if (((UserWriteRecord)localObject).isVisible()) {
        if ((j >= i) && (recordContainsPath((UserWriteRecord)localObject, localUserWriteRecord.getPath())))
        {
          bool2 = false;
          m = k;
        }
        else
        {
          bool2 = bool1;
          m = k;
          if (localUserWriteRecord.getPath().contains(((UserWriteRecord)localObject).getPath()))
          {
            m = 1;
            bool2 = bool1;
          }
        }
      }
      j -= 1;
      bool1 = bool2;
    }
    if (!bool1) {
      return false;
    }
    if (k != 0)
    {
      resetTree();
      return true;
    }
    if (localUserWriteRecord.isOverwrite())
    {
      visibleWrites = visibleWrites.removeWrite(localUserWriteRecord.getPath());
      return true;
    }
    localObject = localUserWriteRecord.getMerge().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Path localPath = (Path)((Map.Entry)((Iterator)localObject).next()).getKey();
      visibleWrites = visibleWrites.removeWrite(localUserWriteRecord.getPath().child(localPath));
    }
    return true;
  }
  
  public Node shadowingWrite(Path paramPath)
  {
    return visibleWrites.getCompleteNode(paramPath);
  }
}
