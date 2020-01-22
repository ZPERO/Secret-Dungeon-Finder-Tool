package com.squareup.okhttp.internal.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import okio.Okio;
import okio.Sink;
import okio.Source;

public abstract interface FileSystem
{
  public static final FileSystem SYSTEM = new FileSystem()
  {
    public Sink appendingSink(File paramAnonymousFile)
      throws FileNotFoundException
    {
      try
      {
        Sink localSink = Okio.appendingSink(paramAnonymousFile);
        return localSink;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        for (;;) {}
      }
      paramAnonymousFile.getParentFile().mkdirs();
      return Okio.appendingSink(paramAnonymousFile);
    }
    
    public void delete(File paramAnonymousFile)
      throws IOException
    {
      if (!paramAnonymousFile.delete())
      {
        if (!paramAnonymousFile.exists()) {
          return;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("failed to delete ");
        localStringBuilder.append(paramAnonymousFile);
        throw new IOException(localStringBuilder.toString());
      }
    }
    
    public void deleteContents(File paramAnonymousFile)
      throws IOException
    {
      Object localObject = paramAnonymousFile.listFiles();
      if (localObject != null)
      {
        int j = localObject.length;
        int i = 0;
        for (;;)
        {
          if (i >= j) {
            return;
          }
          paramAnonymousFile = localObject[i];
          if (paramAnonymousFile.isDirectory()) {
            deleteContents(paramAnonymousFile);
          }
          if (!paramAnonymousFile.delete()) {
            break;
          }
          i += 1;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("failed to delete ");
        ((StringBuilder)localObject).append(paramAnonymousFile);
        throw new IOException(((StringBuilder)localObject).toString());
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("not a readable directory: ");
      ((StringBuilder)localObject).append(paramAnonymousFile);
      paramAnonymousFile = new IOException(((StringBuilder)localObject).toString());
      throw paramAnonymousFile;
    }
    
    public boolean exists(File paramAnonymousFile)
      throws IOException
    {
      return paramAnonymousFile.exists();
    }
    
    public void rename(File paramAnonymousFile1, File paramAnonymousFile2)
      throws IOException
    {
      delete(paramAnonymousFile2);
      if (paramAnonymousFile1.renameTo(paramAnonymousFile2)) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("failed to rename ");
      localStringBuilder.append(paramAnonymousFile1);
      localStringBuilder.append(" to ");
      localStringBuilder.append(paramAnonymousFile2);
      throw new IOException(localStringBuilder.toString());
    }
    
    public Sink sink(File paramAnonymousFile)
      throws FileNotFoundException
    {
      try
      {
        Sink localSink = Okio.sink(paramAnonymousFile);
        return localSink;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        for (;;) {}
      }
      paramAnonymousFile.getParentFile().mkdirs();
      return Okio.sink(paramAnonymousFile);
    }
    
    public long size(File paramAnonymousFile)
    {
      return paramAnonymousFile.length();
    }
    
    public Source source(File paramAnonymousFile)
      throws FileNotFoundException
    {
      return Okio.source(paramAnonymousFile);
    }
  };
  
  public abstract Sink appendingSink(File paramFile)
    throws FileNotFoundException;
  
  public abstract void delete(File paramFile)
    throws IOException;
  
  public abstract void deleteContents(File paramFile)
    throws IOException;
  
  public abstract boolean exists(File paramFile)
    throws IOException;
  
  public abstract void rename(File paramFile1, File paramFile2)
    throws IOException;
  
  public abstract Sink sink(File paramFile)
    throws FileNotFoundException;
  
  public abstract long size(File paramFile);
  
  public abstract Source source(File paramFile)
    throws FileNotFoundException;
}
