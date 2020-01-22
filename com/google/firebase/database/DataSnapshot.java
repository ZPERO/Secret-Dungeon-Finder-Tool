package com.google.firebase.database;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Iterator;

public class DataSnapshot
{
  private final IndexedNode node;
  private final DatabaseReference query;
  
  DataSnapshot(DatabaseReference paramDatabaseReference, IndexedNode paramIndexedNode)
  {
    node = paramIndexedNode;
    query = paramDatabaseReference;
  }
  
  public DataSnapshot child(String paramString)
  {
    return new DataSnapshot(query.child(paramString), IndexedNode.from(node.getNode().getChild(new Path(paramString))));
  }
  
  public boolean exists()
  {
    return node.getNode().isEmpty() ^ true;
  }
  
  public Iterable getChildren()
  {
    new Iterable()
    {
      public Iterator iterator()
      {
        new Iterator()
        {
          public boolean hasNext()
          {
            return val$iter.hasNext();
          }
          
          public DataSnapshot next()
          {
            NamedNode localNamedNode = (NamedNode)val$iter.next();
            return new DataSnapshot(query.child(localNamedNode.getName().asString()), IndexedNode.from(localNamedNode.getNode()));
          }
          
          public void remove()
          {
            throw new UnsupportedOperationException("remove called on immutable collection");
          }
        };
      }
    };
  }
  
  public long getChildrenCount()
  {
    return node.getNode().getChildCount();
  }
  
  public String getKey()
  {
    return query.getKey();
  }
  
  public Object getPriority()
  {
    Object localObject = node.getNode().getPriority().getValue();
    if ((localObject instanceof Long)) {
      return Double.valueOf(((Long)localObject).longValue());
    }
    return localObject;
  }
  
  public DatabaseReference getRef()
  {
    return query;
  }
  
  public Object getValue()
  {
    return node.getNode().getValue();
  }
  
  public Object getValue(GenericTypeIndicator paramGenericTypeIndicator)
  {
    return CustomClassMapper.convertToCustomClass(node.getNode().getValue(), paramGenericTypeIndicator);
  }
  
  public Object getValue(Class paramClass)
  {
    return CustomClassMapper.convertToCustomClass(node.getNode().getValue(), paramClass);
  }
  
  public Object getValue(boolean paramBoolean)
  {
    return node.getNode().getValue(paramBoolean);
  }
  
  public boolean hasChild(String paramString)
  {
    if (query.getParent() == null) {
      Validation.validateRootPathString(paramString);
    } else {
      Validation.validatePathString(paramString);
    }
    return node.getNode().getChild(new Path(paramString)).isEmpty() ^ true;
  }
  
  public boolean hasChildren()
  {
    return node.getNode().getChildCount() > 0;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DataSnapshot { key = ");
    localStringBuilder.append(query.getKey());
    localStringBuilder.append(", value = ");
    localStringBuilder.append(node.getNode().getValue(true));
    localStringBuilder.append(" }");
    return localStringBuilder.toString();
  }
}
