package com.google.firebase.database.core;

import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.ChildrenNode.ChildVisitor;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ServerValues
{
  public static final String NAME_SUBKEY_SERVERVALUE = ".sv";
  
  public ServerValues() {}
  
  public static Map generateServerValues(Clock paramClock)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("timestamp", Long.valueOf(paramClock.millis()));
    return localHashMap;
  }
  
  public static Object resolveDeferredValue(Object paramObject, Map paramMap)
  {
    Object localObject1 = paramObject;
    if ((paramObject instanceof Map))
    {
      Object localObject2 = (Map)paramObject;
      localObject1 = paramObject;
      if (((Map)localObject2).containsKey(".sv"))
      {
        localObject2 = (String)((Map)localObject2).get(".sv");
        localObject1 = paramObject;
        if (paramMap.containsKey(localObject2)) {
          localObject1 = paramMap.get(localObject2);
        }
      }
    }
    return localObject1;
  }
  
  public static CompoundWrite resolveDeferredValueMerge(CompoundWrite paramCompoundWrite, Map paramMap)
  {
    Object localObject = CompoundWrite.emptyWrite();
    Iterator localIterator = paramCompoundWrite.iterator();
    for (paramCompoundWrite = (CompoundWrite)localObject; localIterator.hasNext(); paramCompoundWrite = paramCompoundWrite.addWrite((Path)((Map.Entry)localObject).getKey(), resolveDeferredValueSnapshot((Node)((Map.Entry)localObject).getValue(), paramMap))) {
      localObject = (Map.Entry)localIterator.next();
    }
    return paramCompoundWrite;
  }
  
  public static Node resolveDeferredValueSnapshot(Node paramNode, Map paramMap)
  {
    Object localObject3 = paramNode.getPriority().getValue();
    Object localObject1 = localObject3;
    Object localObject2 = localObject1;
    if ((localObject3 instanceof Map))
    {
      localObject3 = (Map)localObject3;
      localObject2 = localObject1;
      if (((Map)localObject3).containsKey(".sv")) {
        localObject2 = paramMap.get((String)((Map)localObject3).get(".sv"));
      }
    }
    localObject1 = PriorityUtilities.parsePriority(localObject2);
    if (paramNode.isLeafNode())
    {
      paramMap = resolveDeferredValue(paramNode.getValue(), paramMap);
      if ((paramMap.equals(paramNode.getValue())) && (localObject1.equals(paramNode.getPriority()))) {
        return paramNode;
      }
      return NodeUtilities.NodeFromJSON(paramMap, (Node)localObject1);
    }
    if (paramNode.isEmpty()) {
      return paramNode;
    }
    paramNode = (ChildrenNode)paramNode;
    localObject2 = new SnapshotHolder(paramNode);
    paramNode.forEachChild(new ChildrenNode.ChildVisitor()
    {
      public void visitChild(ChildKey paramAnonymousChildKey, Node paramAnonymousNode)
      {
        Node localNode = ServerValues.resolveDeferredValueSnapshot(paramAnonymousNode, ServerValues.this);
        if (localNode != paramAnonymousNode) {
          val$holder.update(new Path(paramAnonymousChildKey.asString()), localNode);
        }
      }
    });
    if (!((SnapshotHolder)localObject2).getRootNode().getPriority().equals(localObject1)) {
      return ((SnapshotHolder)localObject2).getRootNode().updatePriority((Node)localObject1);
    }
    return ((SnapshotHolder)localObject2).getRootNode();
  }
  
  public static SparseSnapshotTree resolveDeferredValueTree(SparseSnapshotTree paramSparseSnapshotTree, final Map paramMap)
  {
    SparseSnapshotTree localSparseSnapshotTree = new SparseSnapshotTree();
    paramSparseSnapshotTree.forEachTree(new Path(""), new SparseSnapshotTree.SparseSnapshotTreeVisitor()
    {
      public void visitTree(Path paramAnonymousPath, Node paramAnonymousNode)
      {
        remember(paramAnonymousPath, ServerValues.resolveDeferredValueSnapshot(paramAnonymousNode, paramMap));
      }
    });
    return localSparseSnapshotTree;
  }
}
