package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Path
  implements Iterable<ChildKey>, Comparable<Path>
{
  private static final Path EMPTY_PATH = new Path("");
  private final int data;
  private final ChildKey[] pieces;
  private final int start;
  
  public Path(String paramString)
  {
    paramString = paramString.split("/", -1);
    int m = paramString.length;
    int i = 0;
    int k;
    for (int j = 0; i < m; j = k)
    {
      k = j;
      if (paramString[i].length() > 0) {
        k = j + 1;
      }
      i += 1;
    }
    pieces = new ChildKey[j];
    m = paramString.length;
    i = 0;
    for (j = 0; i < m; j = k)
    {
      String str = paramString[i];
      k = j;
      if (str.length() > 0)
      {
        pieces[j] = ChildKey.fromString(str);
        k = j + 1;
      }
      i += 1;
    }
    start = 0;
    data = pieces.length;
  }
  
  public Path(List paramList)
  {
    pieces = new ChildKey[paramList.size()];
    Iterator localIterator = paramList.iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      pieces[i] = ChildKey.fromString(str);
      i += 1;
    }
    start = 0;
    data = paramList.size();
  }
  
  public Path(ChildKey... paramVarArgs)
  {
    pieces = ((ChildKey[])Arrays.copyOf(paramVarArgs, paramVarArgs.length));
    int i = 0;
    start = 0;
    data = paramVarArgs.length;
    int j = paramVarArgs.length;
    while (i < j)
    {
      ChildKey localChildKey = paramVarArgs[i];
      i += 1;
    }
  }
  
  private Path(ChildKey[] paramArrayOfChildKey, int paramInt1, int paramInt2)
  {
    pieces = paramArrayOfChildKey;
    start = paramInt1;
    data = paramInt2;
  }
  
  public static Path getEmptyPath()
  {
    return EMPTY_PATH;
  }
  
  public static Path getRelative(Path paramPath1, Path paramPath2)
  {
    Object localObject = paramPath1.getFront();
    ChildKey localChildKey = paramPath2.getFront();
    if (localObject == null) {
      return paramPath2;
    }
    if (((ChildKey)localObject).equals(localChildKey)) {
      return getRelative(paramPath1.popFront(), paramPath2.popFront());
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("INTERNAL ERROR: ");
    ((StringBuilder)localObject).append(paramPath2);
    ((StringBuilder)localObject).append(" is not contained in ");
    ((StringBuilder)localObject).append(paramPath1);
    throw new DatabaseException(((StringBuilder)localObject).toString());
  }
  
  public List asList()
  {
    ArrayList localArrayList = new ArrayList(size());
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(((ChildKey)localIterator.next()).asString());
    }
    return localArrayList;
  }
  
  public Path child(Path paramPath)
  {
    int i = size() + paramPath.size();
    ChildKey[] arrayOfChildKey = new ChildKey[i];
    System.arraycopy(pieces, start, arrayOfChildKey, 0, size());
    System.arraycopy(pieces, start, arrayOfChildKey, size(), paramPath.size());
    return new Path(arrayOfChildKey, 0, i);
  }
  
  public Path child(ChildKey paramChildKey)
  {
    int i = size();
    int j = i + 1;
    ChildKey[] arrayOfChildKey = new ChildKey[j];
    System.arraycopy(pieces, start, arrayOfChildKey, 0, i);
    arrayOfChildKey[i] = paramChildKey;
    return new Path(arrayOfChildKey, 0, j);
  }
  
  public int compareTo(Path paramPath)
  {
    int j = start;
    int i = start;
    while ((j < data) && (i < data))
    {
      int k = pieces[j].compareTo(pieces[i]);
      if (k != 0) {
        return k;
      }
      j += 1;
      i += 1;
    }
    if ((j == data) && (i == data)) {
      return 0;
    }
    if (j == data) {
      return -1;
    }
    return 1;
  }
  
  public boolean contains(Path paramPath)
  {
    if (size() > paramPath.size()) {
      return false;
    }
    int j = start;
    int i = start;
    while (j < data)
    {
      if (!pieces[j].equals(pieces[i])) {
        return false;
      }
      j += 1;
      i += 1;
    }
    return true;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Path)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    paramObject = (Path)paramObject;
    if (size() != paramObject.size()) {
      return false;
    }
    int j = start;
    int i = start;
    while ((j < data) && (i < data))
    {
      if (!pieces[j].equals(pieces[i])) {
        return false;
      }
      j += 1;
      i += 1;
    }
    return true;
  }
  
  public ChildKey getBack()
  {
    if (!isEmpty()) {
      return pieces[(data - 1)];
    }
    return null;
  }
  
  public ChildKey getFront()
  {
    if (isEmpty()) {
      return null;
    }
    return pieces[start];
  }
  
  public Path getParent()
  {
    if (isEmpty()) {
      return null;
    }
    return new Path(pieces, start, data - 1);
  }
  
  public int hashCode()
  {
    int i = start;
    int j = 0;
    while (i < data)
    {
      j = j * 37 + pieces[i].hashCode();
      i += 1;
    }
    return j;
  }
  
  public boolean isEmpty()
  {
    return start >= data;
  }
  
  public Iterator iterator()
  {
    new Iterator()
    {
      int offset = start;
      
      public boolean hasNext()
      {
        return offset < data;
      }
      
      public ChildKey next()
      {
        if (hasNext())
        {
          Object localObject = pieces;
          int i = offset;
          localObject = localObject[i];
          offset = (i + 1);
          return localObject;
        }
        throw new NoSuchElementException("No more elements.");
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Can't remove component from immutable Path!");
      }
    };
  }
  
  public Path popFront()
  {
    int j = start;
    int i = j;
    if (!isEmpty()) {
      i = j + 1;
    }
    return new Path(pieces, i, data);
  }
  
  public int size()
  {
    return data - start;
  }
  
  public String toString()
  {
    if (isEmpty()) {
      return "/";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    int i = start;
    while (i < data)
    {
      localStringBuilder.append("/");
      localStringBuilder.append(pieces[i].asString());
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  public String wireFormat()
  {
    if (isEmpty()) {
      return "/";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    int i = start;
    while (i < data)
    {
      if (i > start) {
        localStringBuilder.append("/");
      }
      localStringBuilder.append(pieces[i].asString());
      i += 1;
    }
    return localStringBuilder.toString();
  }
}
