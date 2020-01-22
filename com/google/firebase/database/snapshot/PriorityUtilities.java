package com.google.firebase.database.snapshot;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.core.Path;

public class PriorityUtilities
{
  public PriorityUtilities() {}
  
  public static Node NullPriority()
  {
    return EmptyNode.Empty();
  }
  
  public static boolean isValidPriority(Node paramNode)
  {
    return (paramNode.getPriority().isEmpty()) && ((paramNode.isEmpty()) || ((paramNode instanceof DoubleNode)) || ((paramNode instanceof StringNode)) || ((paramNode instanceof DeferredValueNode)));
  }
  
  public static Node parsePriority(Path paramPath, Object paramObject)
  {
    Object localObject = NodeUtilities.NodeFromJSON(paramObject);
    paramObject = localObject;
    if ((localObject instanceof LongNode)) {
      paramObject = new DoubleNode(Double.valueOf(((Long)((Node)localObject).getValue()).longValue()), NullPriority());
    }
    if (!isValidPriority((Node)paramObject))
    {
      paramObject = new StringBuilder();
      if (paramPath != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Path '");
        ((StringBuilder)localObject).append(paramPath);
        ((StringBuilder)localObject).append("'");
        paramPath = ((StringBuilder)localObject).toString();
      }
      else
      {
        paramPath = "Node";
      }
      paramObject.append(paramPath);
      paramObject.append(" contains invalid priority: Must be a string, double, ServerValue, or null");
      throw new DatabaseException(paramObject.toString());
    }
    return (Node)paramObject;
  }
  
  public static Node parsePriority(Object paramObject)
  {
    return parsePriority(null, paramObject);
  }
}
