package com.google.firebase.database.core;

import com.google.firebase.database.snapshot.Node;

public class UserWriteRecord
{
  private final CompoundWrite merge;
  private final Node overwrite;
  private final Path path;
  private final boolean visible;
  private final long writeId;
  
  public UserWriteRecord(long paramLong, Path paramPath, CompoundWrite paramCompoundWrite)
  {
    writeId = paramLong;
    path = paramPath;
    overwrite = null;
    merge = paramCompoundWrite;
    visible = true;
  }
  
  public UserWriteRecord(long paramLong, Path paramPath, Node paramNode, boolean paramBoolean)
  {
    writeId = paramLong;
    path = paramPath;
    overwrite = paramNode;
    merge = null;
    visible = paramBoolean;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject != null)
    {
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      paramObject = (UserWriteRecord)paramObject;
      if (writeId != writeId) {
        return false;
      }
      if (!path.equals(path)) {
        return false;
      }
      if (visible != visible) {
        return false;
      }
      Object localObject = overwrite;
      if (localObject != null) {
        if (!localObject.equals(overwrite)) {
          break label136;
        }
      } else if (overwrite != null) {
        return false;
      }
      localObject = merge;
      paramObject = merge;
      if (localObject != null)
      {
        if (((CompoundWrite)localObject).equals(paramObject)) {
          return true;
        }
      }
      else {
        return paramObject == null;
      }
    }
    label136:
    return false;
  }
  
  public CompoundWrite getMerge()
  {
    CompoundWrite localCompoundWrite = merge;
    if (localCompoundWrite != null) {
      return localCompoundWrite;
    }
    throw new IllegalArgumentException("Can't access merge when write is an overwrite!");
  }
  
  public Node getOverwrite()
  {
    Node localNode = overwrite;
    if (localNode != null) {
      return localNode;
    }
    throw new IllegalArgumentException("Can't access overwrite when write is a merge!");
  }
  
  public Path getPath()
  {
    return path;
  }
  
  public long getWriteId()
  {
    return writeId;
  }
  
  public int hashCode()
  {
    int k = Long.valueOf(writeId).hashCode();
    int m = Boolean.valueOf(visible).hashCode();
    int n = path.hashCode();
    Object localObject = overwrite;
    int j = 0;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    localObject = merge;
    if (localObject != null) {
      j = ((CompoundWrite)localObject).hashCode();
    }
    return (((k * 31 + m) * 31 + n) * 31 + i) * 31 + j;
  }
  
  public boolean isMerge()
  {
    return merge != null;
  }
  
  public boolean isOverwrite()
  {
    return overwrite != null;
  }
  
  public boolean isVisible()
  {
    return visible;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("UserWriteRecord{id=");
    localStringBuilder.append(writeId);
    localStringBuilder.append(" path=");
    localStringBuilder.append(path);
    localStringBuilder.append(" visible=");
    localStringBuilder.append(visible);
    localStringBuilder.append(" overwrite=");
    localStringBuilder.append(overwrite);
    localStringBuilder.append(" merge=");
    localStringBuilder.append(merge);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
