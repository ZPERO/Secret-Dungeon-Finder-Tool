package com.google.firebase.database;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.SnapshotHolder;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.core.utilities.Validation;
import com.google.firebase.database.core.utilities.encoding.CustomClassMapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MutableData
{
  private final SnapshotHolder holder;
  private final Path prefixPath;
  
  private MutableData(SnapshotHolder paramSnapshotHolder, Path paramPath)
  {
    holder = paramSnapshotHolder;
    prefixPath = paramPath;
    ValidationPath.validateWithObject(prefixPath, getValue());
  }
  
  MutableData(Node paramNode)
  {
    this(new SnapshotHolder(paramNode), new Path(""));
  }
  
  public MutableData child(String paramString)
  {
    Validation.validatePathString(paramString);
    return new MutableData(holder, prefixPath.child(new Path(paramString)));
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof MutableData))
    {
      SnapshotHolder localSnapshotHolder = holder;
      paramObject = (MutableData)paramObject;
      if ((localSnapshotHolder.equals(holder)) && (prefixPath.equals(prefixPath))) {
        return true;
      }
    }
    return false;
  }
  
  public Iterable getChildren()
  {
    Node localNode = getNode();
    if ((!localNode.isEmpty()) && (!localNode.isLeafNode())) {
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
            
            public MutableData next()
            {
              NamedNode localNamedNode = (NamedNode)val$iter.next();
              return new MutableData(holder, prefixPath.child(localNamedNode.getName()), null);
            }
            
            public void remove()
            {
              throw new UnsupportedOperationException("remove called on immutable collection");
            }
          };
        }
      };
    }
    new Iterable()
    {
      public Iterator iterator()
      {
        new Iterator()
        {
          public boolean hasNext()
          {
            return false;
          }
          
          public MutableData next()
          {
            throw new NoSuchElementException();
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
    return getNode().getChildCount();
  }
  
  public String getKey()
  {
    if (prefixPath.getBack() != null) {
      return prefixPath.getBack().asString();
    }
    return null;
  }
  
  Node getNode()
  {
    return holder.getNode(prefixPath);
  }
  
  public Object getPriority()
  {
    return getNode().getPriority().getValue();
  }
  
  public Object getValue()
  {
    return getNode().getValue();
  }
  
  public Object getValue(GenericTypeIndicator paramGenericTypeIndicator)
  {
    return CustomClassMapper.convertToCustomClass(getNode().getValue(), paramGenericTypeIndicator);
  }
  
  public Object getValue(Class paramClass)
  {
    return CustomClassMapper.convertToCustomClass(getNode().getValue(), paramClass);
  }
  
  public boolean hasChild(String paramString)
  {
    return getNode().getChild(new Path(paramString)).isEmpty() ^ true;
  }
  
  public boolean hasChildren()
  {
    Node localNode = getNode();
    return (!localNode.isLeafNode()) && (!localNode.isEmpty());
  }
  
  public void setPriority(Object paramObject)
  {
    holder.update(prefixPath, getNode().updatePriority(PriorityUtilities.parsePriority(prefixPath, paramObject)));
  }
  
  public void setValue(Object paramObject)
    throws DatabaseException
  {
    ValidationPath.validateWithObject(prefixPath, paramObject);
    paramObject = CustomClassMapper.convertToPlainJavaTypes(paramObject);
    Validation.validateWritableObject(paramObject);
    holder.update(prefixPath, NodeUtilities.NodeFromJSON(paramObject));
  }
  
  public String toString()
  {
    Object localObject = prefixPath.getFront();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("MutableData { key = ");
    if (localObject != null) {
      localObject = ((ChildKey)localObject).asString();
    } else {
      localObject = "<none>";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(", value = ");
    localStringBuilder.append(holder.getRootNode().getValue(true));
    localStringBuilder.append(" }");
    return localStringBuilder.toString();
  }
}
