package com.google.firebase.database.snapshot;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeUtilities
{
  public NodeUtilities() {}
  
  public static Node NodeFromJSON(Object paramObject)
    throws DatabaseException
  {
    return NodeFromJSON(paramObject, PriorityUtilities.NullPriority());
  }
  
  public static Node NodeFromJSON(Object paramObject, Node paramNode)
    throws DatabaseException
  {
    Object localObject1 = paramObject;
    Node localNode1 = paramNode;
    if ((paramObject instanceof Map)) {}
    try
    {
      Object localObject2 = (Map)paramObject;
      boolean bool = ((Map)localObject2).containsKey(".priority");
      if (bool) {
        paramNode = PriorityUtilities.parsePriority(((Map)localObject2).get(".priority"));
      }
      bool = ((Map)localObject2).containsKey(".value");
      localObject1 = paramObject;
      localNode1 = paramNode;
      if (bool)
      {
        localObject1 = ((Map)localObject2).get(".value");
        localNode1 = paramNode;
      }
      if (localObject1 == null)
      {
        paramObject = EmptyNode.Empty();
        return paramObject;
      }
      if ((localObject1 instanceof String))
      {
        paramObject = new StringNode((String)localObject1, localNode1);
        return paramObject;
      }
      if ((localObject1 instanceof Long))
      {
        paramObject = new LongNode((Long)localObject1, localNode1);
        return paramObject;
      }
      if ((localObject1 instanceof Integer))
      {
        i = ((Integer)localObject1).intValue();
        long l = i;
        paramObject = new LongNode(Long.valueOf(l), localNode1);
        return paramObject;
      }
      if ((localObject1 instanceof Double))
      {
        paramObject = new DoubleNode((Double)localObject1, localNode1);
        return paramObject;
      }
      if ((localObject1 instanceof Boolean))
      {
        paramObject = new BooleanNode((Boolean)localObject1, localNode1);
        return paramObject;
      }
      if ((!(localObject1 instanceof Map)) && (!(localObject1 instanceof List)))
      {
        paramObject = new StringBuilder();
        paramObject.append("Failed to parse node with class ");
        paramObject.append(localObject1.getClass().toString());
        paramObject = new DatabaseException(paramObject.toString());
        throw paramObject;
      }
      if ((localObject1 instanceof Map))
      {
        localObject1 = (Map)localObject1;
        bool = ((Map)localObject1).containsKey(".sv");
        if (bool)
        {
          paramObject = new DeferredValueNode((Map)localObject1, localNode1);
          return paramObject;
        }
        paramNode = new HashMap(((Map)localObject1).size());
        localObject2 = ((Map)localObject1).keySet().iterator();
        for (;;)
        {
          bool = ((Iterator)localObject2).hasNext();
          paramObject = paramNode;
          if (!bool) {
            break;
          }
          paramObject = (String)((Iterator)localObject2).next();
          bool = paramObject.startsWith(".");
          if (!bool)
          {
            Node localNode2 = NodeFromJSON(((Map)localObject1).get(paramObject));
            bool = localNode2.isEmpty();
            if (!bool) {
              paramNode.put(ChildKey.fromString(paramObject), localNode2);
            }
          }
        }
      }
      localObject1 = (List)localObject1;
      paramNode = new HashMap(((List)localObject1).size());
      int i = 0;
      for (;;)
      {
        int j = ((List)localObject1).size();
        paramObject = paramNode;
        if (i >= j) {
          break;
        }
        paramObject = new StringBuilder();
        paramObject.append("");
        paramObject.append(i);
        paramObject = paramObject.toString();
        localObject2 = NodeFromJSON(((List)localObject1).get(i));
        bool = ((Node)localObject2).isEmpty();
        if (!bool) {
          paramNode.put(ChildKey.fromString(paramObject), localObject2);
        }
        i += 1;
      }
      bool = paramObject.isEmpty();
      if (bool)
      {
        paramObject = EmptyNode.Empty();
        return paramObject;
      }
      paramNode = ChildrenNode.NAME_ONLY_COMPARATOR;
      paramObject = ImmutableSortedMap.Builder.fromMap(paramObject, paramNode);
      paramObject = new ChildrenNode(paramObject, localNode1);
      return paramObject;
    }
    catch (ClassCastException paramObject)
    {
      paramObject = new DatabaseException("Failed to parse node", paramObject);
      throw paramObject;
    }
  }
  
  public static int nameAndPriorityCompare(ChildKey paramChildKey1, Node paramNode1, ChildKey paramChildKey2, Node paramNode2)
  {
    int i = paramNode1.compareTo(paramNode2);
    if (i != 0) {
      return i;
    }
    return paramChildKey1.compareTo(paramChildKey2);
  }
}
