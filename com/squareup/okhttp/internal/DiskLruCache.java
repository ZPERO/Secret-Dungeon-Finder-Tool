package com.squareup.okhttp.internal;

import com.squareup.okhttp.internal.io.FileSystem;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class DiskLruCache
  implements Closeable
{
  static final long ANY_SEQUENCE_NUMBER = -1L;
  private static final String CLEAN = "CLEAN";
  private static final String DIRTY = "DIRTY";
  static final String JOURNAL_FILE = "journal";
  static final String JOURNAL_FILE_BACKUP = "journal.bkp";
  static final String JOURNAL_FILE_TEMP = "journal.tmp";
  static final Pattern LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
  static final String MAGIC = "libcore.io.DiskLruCache";
  private static final Sink NULL_SINK = new Sink()
  {
    public void close()
      throws IOException
    {}
    
    public void flush()
      throws IOException
    {}
    
    public Timeout timeout()
    {
      return Timeout.NONE;
    }
    
    public void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
      throws IOException
    {
      paramAnonymousBuffer.skip(paramAnonymousLong);
    }
  };
  private static final String READ = "READ";
  private static final String REMOVE = "REMOVE";
  static final String VERSION_1 = "1";
  private final int appVersion;
  private final Runnable cleanupRunnable = new Runnable()
  {
    public void run()
    {
      DiskLruCache localDiskLruCache1 = DiskLruCache.this;
      for (;;)
      {
        try
        {
          if (!initialized)
          {
            i = 1;
            if ((i | closed) != 0) {
              return;
            }
            DiskLruCache localDiskLruCache2 = DiskLruCache.this;
            try
            {
              localDiskLruCache2.trimToSize();
              localDiskLruCache2 = DiskLruCache.this;
              boolean bool = localDiskLruCache2.journalRebuildRequired();
              if (bool)
              {
                localDiskLruCache2 = DiskLruCache.this;
                localDiskLruCache2.rebuildJournal();
                localDiskLruCache2 = DiskLruCache.this;
                DiskLruCache.access$502(localDiskLruCache2, 0);
              }
              return;
            }
            catch (IOException localIOException)
            {
              throw new RuntimeException(localIOException);
            }
          }
          int i = 0;
        }
        catch (Throwable localThrowable)
        {
          throw localThrowable;
        }
      }
    }
  };
  private boolean closed;
  private final File directory;
  private final Executor executor;
  private final FileSystem fileSystem;
  private boolean hasJournalErrors;
  private boolean initialized;
  private final File journalFile;
  private final File journalFileBackup;
  private final File journalFileTmp;
  private BufferedSink journalWriter;
  private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75F, true);
  private long maxSize;
  private long nextSequenceNumber = 0L;
  private int redundantOpCount;
  private long size = 0L;
  private final int valueCount;
  
  DiskLruCache(FileSystem paramFileSystem, File paramFile, int paramInt1, int paramInt2, long paramLong, Executor paramExecutor)
  {
    fileSystem = paramFileSystem;
    directory = paramFile;
    appVersion = paramInt1;
    journalFile = new File(paramFile, "journal");
    journalFileTmp = new File(paramFile, "journal.tmp");
    journalFileBackup = new File(paramFile, "journal.bkp");
    valueCount = paramInt2;
    maxSize = paramLong;
    executor = paramExecutor;
  }
  
  private void checkNotClosed()
  {
    try
    {
      boolean bool = isClosed();
      if (!bool) {
        return;
      }
      throw new IllegalStateException("cache is closed");
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  private void completeEdit(Editor paramEditor, boolean paramBoolean)
    throws IOException
  {
    for (;;)
    {
      int j;
      try
      {
        Entry localEntry = entry;
        if (currentEditor == paramEditor)
        {
          int k = 0;
          j = k;
          if (paramBoolean)
          {
            j = k;
            if (!readable)
            {
              int i = 0;
              j = k;
              if (i < valueCount)
              {
                if (written[i] != 0)
                {
                  if (!fileSystem.exists(dirtyFiles[i]))
                  {
                    paramEditor.abort();
                    return;
                  }
                  i += 1;
                  continue;
                }
                paramEditor.abort();
                paramEditor = new StringBuilder();
                paramEditor.append("Newly created entry didn't create value for index ");
                paramEditor.append(i);
                throw new IllegalStateException(paramEditor.toString());
              }
            }
          }
          long l1;
          if (j < valueCount)
          {
            paramEditor = dirtyFiles[j];
            if (paramBoolean)
            {
              if (fileSystem.exists(paramEditor))
              {
                File localFile = cleanFiles[j];
                fileSystem.rename(paramEditor, localFile);
                l1 = lengths[j];
                long l2 = fileSystem.size(localFile);
                lengths[j] = l2;
                size = (size - l1 + l2);
              }
            }
            else {
              fileSystem.delete(paramEditor);
            }
          }
          else
          {
            redundantOpCount += 1;
            Entry.access$902(localEntry, null);
            if ((readable | paramBoolean))
            {
              Entry.access$802(localEntry, true);
              journalWriter.writeUtf8("CLEAN").writeByte(32);
              journalWriter.writeUtf8(key);
              localEntry.writeLengths(journalWriter);
              journalWriter.writeByte(10);
              if (paramBoolean)
              {
                l1 = nextSequenceNumber;
                nextSequenceNumber = (1L + l1);
                Entry.access$1602(localEntry, l1);
              }
            }
            else
            {
              lruEntries.remove(key);
              journalWriter.writeUtf8("REMOVE").writeByte(32);
              journalWriter.writeUtf8(key);
              journalWriter.writeByte(10);
            }
            journalWriter.flush();
            if ((size > maxSize) || (journalRebuildRequired())) {
              executor.execute(cleanupRunnable);
            }
          }
        }
        else
        {
          throw new IllegalStateException();
        }
      }
      catch (Throwable paramEditor)
      {
        throw paramEditor;
      }
      j += 1;
    }
  }
  
  public static DiskLruCache create(FileSystem paramFileSystem, File paramFile, int paramInt1, int paramInt2, long paramLong)
  {
    if (paramLong > 0L)
    {
      if (paramInt2 > 0) {
        return new DiskLruCache(paramFileSystem, paramFile, paramInt1, paramInt2, paramLong, new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
      }
      throw new IllegalArgumentException("valueCount <= 0");
    }
    throw new IllegalArgumentException("maxSize <= 0");
  }
  
  private Editor edit(String paramString, long paramLong)
    throws IOException
  {
    try
    {
      initialize();
      checkNotClosed();
      validateKey(paramString);
      Entry localEntry = (Entry)lruEntries.get(paramString);
      if (paramLong != -1L) {
        if (localEntry != null)
        {
          long l = sequenceNumber;
          if (l == paramLong) {}
        }
        else
        {
          return null;
        }
      }
      if (localEntry != null)
      {
        localObject = currentEditor;
        if (localObject != null) {
          return null;
        }
      }
      journalWriter.writeUtf8("DIRTY").writeByte(32).writeUtf8(paramString).writeByte(10);
      journalWriter.flush();
      boolean bool = hasJournalErrors;
      if (bool) {
        return null;
      }
      Object localObject = localEntry;
      if (localEntry == null)
      {
        localObject = new Entry(paramString, null);
        lruEntries.put(paramString, localObject);
      }
      paramString = new Editor((Entry)localObject, null);
      Entry.access$902((Entry)localObject, paramString);
      return paramString;
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
  
  private boolean journalRebuildRequired()
  {
    int i = redundantOpCount;
    return (i >= 2000) && (i >= lruEntries.size());
  }
  
  private BufferedSink newJournalWriter()
    throws FileNotFoundException
  {
    Okio.buffer(new FaultHidingSink(fileSystem.appendingSink(journalFile))
    {
      protected void onException(IOException paramAnonymousIOException)
      {
        DiskLruCache.access$602(DiskLruCache.this, true);
      }
    });
  }
  
  private void processJournal()
    throws IOException
  {
    fileSystem.delete(journalFileTmp);
    Iterator localIterator = lruEntries.values().iterator();
    while (localIterator.hasNext())
    {
      Entry localEntry = (Entry)localIterator.next();
      Editor localEditor = currentEditor;
      int j = 0;
      int i = 0;
      if (localEditor == null)
      {
        while (i < valueCount)
        {
          size += lengths[i];
          i += 1;
        }
      }
      else
      {
        Entry.access$902(localEntry, null);
        i = j;
        while (i < valueCount)
        {
          fileSystem.delete(cleanFiles[i]);
          fileSystem.delete(dirtyFiles[i]);
          i += 1;
        }
        localIterator.remove();
      }
    }
  }
  
  private void readJournal()
    throws IOException
  {
    localBufferedSource = Okio.buffer(fileSystem.source(journalFile));
    for (;;)
    {
      try
      {
        str1 = localBufferedSource.readUtf8LineStrict();
        str2 = localBufferedSource.readUtf8LineStrict();
        localObject = localBufferedSource.readUtf8LineStrict();
        str3 = localBufferedSource.readUtf8LineStrict();
        str4 = localBufferedSource.readUtf8LineStrict();
        bool = "libcore.io.DiskLruCache".equals(str1);
        if (bool)
        {
          bool = "1".equals(str2);
          if (bool)
          {
            bool = Integer.toString(appVersion).equals(localObject);
            if (bool)
            {
              bool = Integer.toString(valueCount).equals(str3);
              if (bool)
              {
                bool = "".equals(str4);
                if (bool) {
                  i = 0;
                }
              }
            }
          }
        }
      }
      catch (Throwable localThrowable)
      {
        String str1;
        String str2;
        Object localObject;
        String str3;
        String str4;
        boolean bool;
        int i;
        int j;
        Util.closeQuietly(localBufferedSource);
        throw localThrowable;
      }
      try
      {
        readJournalLine(localBufferedSource.readUtf8LineStrict());
        i += 1;
      }
      catch (EOFException localEOFException) {}
    }
    j = lruEntries.size();
    redundantOpCount = (i - j);
    bool = localBufferedSource.exhausted();
    if (!bool) {
      rebuildJournal();
    } else {
      journalWriter = newJournalWriter();
    }
    Util.closeQuietly(localBufferedSource);
    return;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("unexpected journal header: [");
    ((StringBuilder)localObject).append(str1);
    ((StringBuilder)localObject).append(", ");
    ((StringBuilder)localObject).append(str2);
    ((StringBuilder)localObject).append(", ");
    ((StringBuilder)localObject).append(str3);
    ((StringBuilder)localObject).append(", ");
    ((StringBuilder)localObject).append(str4);
    ((StringBuilder)localObject).append("]");
    throw new IOException(((StringBuilder)localObject).toString());
  }
  
  private void readJournalLine(String paramString)
    throws IOException
  {
    int i = paramString.indexOf(' ');
    if (i != -1)
    {
      int j = i + 1;
      int k = paramString.indexOf(' ', j);
      if (k == -1)
      {
        localObject3 = paramString.substring(j);
        localObject2 = localObject3;
        localObject1 = localObject2;
        if (i == 6)
        {
          localObject1 = localObject2;
          if (paramString.startsWith("REMOVE")) {
            lruEntries.remove(localObject3);
          }
        }
      }
      else
      {
        localObject1 = paramString.substring(j, k);
      }
      Object localObject3 = (Entry)lruEntries.get(localObject1);
      Object localObject2 = localObject3;
      if (localObject3 == null)
      {
        localObject2 = new Entry((String)localObject1, null);
        lruEntries.put(localObject1, localObject2);
      }
      if ((k != -1) && (i == 5) && (paramString.startsWith("CLEAN")))
      {
        paramString = paramString.substring(k + 1).split(" ");
        Entry.access$802((Entry)localObject2, true);
        Entry.access$902((Entry)localObject2, null);
        ((Entry)localObject2).setLengths(paramString);
        return;
      }
      if ((k == -1) && (i == 5) && (paramString.startsWith("DIRTY")))
      {
        Entry.access$902((Entry)localObject2, new Editor((Entry)localObject2, null));
        return;
      }
      if ((k == -1) && (i == 4) && (paramString.startsWith("READ"))) {
        return;
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("unexpected journal line: ");
      ((StringBuilder)localObject1).append(paramString);
      throw new IOException(((StringBuilder)localObject1).toString());
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("unexpected journal line: ");
    ((StringBuilder)localObject1).append(paramString);
    throw new IOException(((StringBuilder)localObject1).toString());
  }
  
  /* Error */
  private void rebuildJournal()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: astore_1
    //   4: aload_0
    //   5: getfield 290	com/squareup/okhttp/internal/DiskLruCache:journalWriter	Lokio/BufferedSink;
    //   8: astore_3
    //   9: aload_0
    //   10: astore_2
    //   11: aload_3
    //   12: ifnull +18 -> 30
    //   15: aload_2
    //   16: astore_1
    //   17: aload_2
    //   18: getfield 290	com/squareup/okhttp/internal/DiskLruCache:journalWriter	Lokio/BufferedSink;
    //   21: astore_3
    //   22: aload_2
    //   23: astore_1
    //   24: aload_3
    //   25: invokeinterface 509 1 0
    //   30: aload_0
    //   31: astore_2
    //   32: aload_2
    //   33: astore_1
    //   34: aload_2
    //   35: getfield 125	com/squareup/okhttp/internal/DiskLruCache:fileSystem	Lcom/squareup/okhttp/internal/io/FileSystem;
    //   38: aload_2
    //   39: getfield 138	com/squareup/okhttp/internal/DiskLruCache:journalFileTmp	Ljava/io/File;
    //   42: invokeinterface 512 2 0
    //   47: invokestatic 406	okio/Okio:buffer	(Lokio/Sink;)Lokio/BufferedSink;
    //   50: astore_3
    //   51: aload_3
    //   52: ldc 51
    //   54: invokeinterface 296 2 0
    //   59: bipush 10
    //   61: invokeinterface 300 2 0
    //   66: pop
    //   67: aload_3
    //   68: ldc 60
    //   70: invokeinterface 296 2 0
    //   75: bipush 10
    //   77: invokeinterface 300 2 0
    //   82: pop
    //   83: aload_3
    //   84: aload_2
    //   85: getfield 129	com/squareup/okhttp/internal/DiskLruCache:appVersion	I
    //   88: i2l
    //   89: invokeinterface 516 3 0
    //   94: bipush 10
    //   96: invokeinterface 300 2 0
    //   101: pop
    //   102: aload_3
    //   103: aload_2
    //   104: getfield 142	com/squareup/okhttp/internal/DiskLruCache:valueCount	I
    //   107: i2l
    //   108: invokeinterface 516 3 0
    //   113: bipush 10
    //   115: invokeinterface 300 2 0
    //   120: pop
    //   121: aload_3
    //   122: bipush 10
    //   124: invokeinterface 300 2 0
    //   129: pop
    //   130: aload_2
    //   131: getfield 116	com/squareup/okhttp/internal/DiskLruCache:lruEntries	Ljava/util/LinkedHashMap;
    //   134: invokevirtual 411	java/util/LinkedHashMap:values	()Ljava/util/Collection;
    //   137: invokeinterface 417 1 0
    //   142: astore_1
    //   143: aload_1
    //   144: invokeinterface 422 1 0
    //   149: ifeq +108 -> 257
    //   152: aload_1
    //   153: invokeinterface 426 1 0
    //   158: checkcast 21	com/squareup/okhttp/internal/DiskLruCache$Entry
    //   161: astore 4
    //   163: aload 4
    //   165: invokestatic 226	com/squareup/okhttp/internal/DiskLruCache$Entry:access$900	(Lcom/squareup/okhttp/internal/DiskLruCache$Entry;)Lcom/squareup/okhttp/internal/DiskLruCache$Editor;
    //   168: ifnull +43 -> 211
    //   171: aload_3
    //   172: ldc 37
    //   174: invokeinterface 296 2 0
    //   179: bipush 32
    //   181: invokeinterface 300 2 0
    //   186: pop
    //   187: aload_3
    //   188: aload 4
    //   190: invokestatic 304	com/squareup/okhttp/internal/DiskLruCache$Entry:access$1500	(Lcom/squareup/okhttp/internal/DiskLruCache$Entry;)Ljava/lang/String;
    //   193: invokeinterface 296 2 0
    //   198: pop
    //   199: aload_3
    //   200: bipush 10
    //   202: invokeinterface 300 2 0
    //   207: pop
    //   208: goto -65 -> 143
    //   211: aload_3
    //   212: ldc 35
    //   214: invokeinterface 296 2 0
    //   219: bipush 32
    //   221: invokeinterface 300 2 0
    //   226: pop
    //   227: aload_3
    //   228: aload 4
    //   230: invokestatic 304	com/squareup/okhttp/internal/DiskLruCache$Entry:access$1500	(Lcom/squareup/okhttp/internal/DiskLruCache$Entry;)Ljava/lang/String;
    //   233: invokeinterface 296 2 0
    //   238: pop
    //   239: aload 4
    //   241: aload_3
    //   242: invokevirtual 308	com/squareup/okhttp/internal/DiskLruCache$Entry:writeLengths	(Lokio/BufferedSink;)V
    //   245: aload_3
    //   246: bipush 10
    //   248: invokeinterface 300 2 0
    //   253: pop
    //   254: goto -111 -> 143
    //   257: aload_2
    //   258: astore_1
    //   259: aload_3
    //   260: invokeinterface 509 1 0
    //   265: aload_2
    //   266: astore_1
    //   267: aload_2
    //   268: getfield 125	com/squareup/okhttp/internal/DiskLruCache:fileSystem	Lcom/squareup/okhttp/internal/io/FileSystem;
    //   271: aload_2
    //   272: getfield 136	com/squareup/okhttp/internal/DiskLruCache:journalFile	Ljava/io/File;
    //   275: invokeinterface 243 2 0
    //   280: ifeq +22 -> 302
    //   283: aload_2
    //   284: astore_1
    //   285: aload_2
    //   286: getfield 125	com/squareup/okhttp/internal/DiskLruCache:fileSystem	Lcom/squareup/okhttp/internal/io/FileSystem;
    //   289: aload_2
    //   290: getfield 136	com/squareup/okhttp/internal/DiskLruCache:journalFile	Ljava/io/File;
    //   293: aload_2
    //   294: getfield 140	com/squareup/okhttp/internal/DiskLruCache:journalFileBackup	Ljava/io/File;
    //   297: invokeinterface 269 3 0
    //   302: aload_2
    //   303: astore_1
    //   304: aload_2
    //   305: getfield 125	com/squareup/okhttp/internal/DiskLruCache:fileSystem	Lcom/squareup/okhttp/internal/io/FileSystem;
    //   308: aload_2
    //   309: getfield 138	com/squareup/okhttp/internal/DiskLruCache:journalFileTmp	Ljava/io/File;
    //   312: aload_2
    //   313: getfield 136	com/squareup/okhttp/internal/DiskLruCache:journalFile	Ljava/io/File;
    //   316: invokeinterface 269 3 0
    //   321: aload_2
    //   322: astore_1
    //   323: aload_2
    //   324: getfield 125	com/squareup/okhttp/internal/DiskLruCache:fileSystem	Lcom/squareup/okhttp/internal/io/FileSystem;
    //   327: aload_2
    //   328: getfield 140	com/squareup/okhttp/internal/DiskLruCache:journalFileBackup	Ljava/io/File;
    //   331: invokeinterface 280 2 0
    //   336: aload_2
    //   337: astore_1
    //   338: aload_2
    //   339: aload_2
    //   340: invokespecial 464	com/squareup/okhttp/internal/DiskLruCache:newJournalWriter	()Lokio/BufferedSink;
    //   343: putfield 290	com/squareup/okhttp/internal/DiskLruCache:journalWriter	Lokio/BufferedSink;
    //   346: aload_2
    //   347: astore_1
    //   348: aload_2
    //   349: iconst_0
    //   350: putfield 205	com/squareup/okhttp/internal/DiskLruCache:hasJournalErrors	Z
    //   353: aload_2
    //   354: monitorexit
    //   355: return
    //   356: astore 4
    //   358: aload_2
    //   359: astore_1
    //   360: aload_3
    //   361: invokeinterface 509 1 0
    //   366: aload_2
    //   367: astore_1
    //   368: aload 4
    //   370: athrow
    //   371: astore_2
    //   372: aload_1
    //   373: monitorexit
    //   374: goto +3 -> 377
    //   377: aload_2
    //   378: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	379	0	this	DiskLruCache
    //   3	370	1	localObject	Object
    //   10	357	2	localDiskLruCache	DiskLruCache
    //   371	7	2	localThrowable1	Throwable
    //   8	353	3	localBufferedSink	BufferedSink
    //   161	79	4	localEntry	Entry
    //   356	13	4	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   51	143	356	java/lang/Throwable
    //   143	208	356	java/lang/Throwable
    //   211	254	356	java/lang/Throwable
    //   4	9	371	java/lang/Throwable
    //   17	22	371	java/lang/Throwable
    //   24	30	371	java/lang/Throwable
    //   34	51	371	java/lang/Throwable
    //   259	265	371	java/lang/Throwable
    //   267	283	371	java/lang/Throwable
    //   285	302	371	java/lang/Throwable
    //   304	321	371	java/lang/Throwable
    //   323	336	371	java/lang/Throwable
    //   338	346	371	java/lang/Throwable
    //   348	353	371	java/lang/Throwable
    //   360	366	371	java/lang/Throwable
    //   368	371	371	java/lang/Throwable
  }
  
  private boolean removeEntry(Entry paramEntry)
    throws IOException
  {
    if (currentEditor != null) {
      Editor.access$1902(currentEditor, true);
    }
    int i = 0;
    while (i < valueCount)
    {
      fileSystem.delete(cleanFiles[i]);
      size -= lengths[i];
      lengths[i] = 0L;
      i += 1;
    }
    redundantOpCount += 1;
    journalWriter.writeUtf8("REMOVE").writeByte(32).writeUtf8(key).writeByte(10);
    lruEntries.remove(key);
    if (journalRebuildRequired()) {
      executor.execute(cleanupRunnable);
    }
    return true;
  }
  
  private void trimToSize()
    throws IOException
  {
    while (size > maxSize) {
      removeEntry((Entry)lruEntries.values().iterator().next());
    }
  }
  
  private void validateKey(String paramString)
  {
    if (LEGAL_KEY_PATTERN.matcher(paramString).matches()) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("keys must match regex [a-z0-9_-]{1,120}: \"");
    localStringBuilder.append(paramString);
    localStringBuilder.append("\"");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void close()
    throws IOException
  {
    for (;;)
    {
      int i;
      try
      {
        if ((initialized) && (!closed))
        {
          Entry[] arrayOfEntry = (Entry[])lruEntries.values().toArray(new Entry[lruEntries.size()]);
          int j = arrayOfEntry.length;
          i = 0;
          if (i < j)
          {
            Entry localEntry = arrayOfEntry[i];
            if (currentEditor != null) {
              currentEditor.abort();
            }
          }
          else
          {
            trimToSize();
            journalWriter.close();
            journalWriter = null;
            closed = true;
          }
        }
        else
        {
          closed = true;
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
      i += 1;
    }
  }
  
  public void delete()
    throws IOException
  {
    close();
    fileSystem.deleteContents(directory);
  }
  
  public Editor edit(String paramString)
    throws IOException
  {
    return edit(paramString, -1L);
  }
  
  public void evictAll()
    throws IOException
  {
    try
    {
      initialize();
      Entry[] arrayOfEntry = (Entry[])lruEntries.values().toArray(new Entry[lruEntries.size()]);
      int j = arrayOfEntry.length;
      int i = 0;
      while (i < j)
      {
        removeEntry(arrayOfEntry[i]);
        i += 1;
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void flush()
    throws IOException
  {
    try
    {
      boolean bool = initialized;
      if (!bool) {
        return;
      }
      checkNotClosed();
      trimToSize();
      journalWriter.flush();
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public Snapshot get(String paramString)
    throws IOException
  {
    try
    {
      initialize();
      checkNotClosed();
      validateKey(paramString);
      Object localObject = (Entry)lruEntries.get(paramString);
      if ((localObject != null) && (readable))
      {
        localObject = ((Entry)localObject).snapshot();
        if (localObject == null) {
          return null;
        }
        redundantOpCount += 1;
        journalWriter.writeUtf8("READ").writeByte(32).writeUtf8(paramString).writeByte(10);
        if (journalRebuildRequired()) {
          executor.execute(cleanupRunnable);
        }
        return localObject;
      }
      return null;
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
  
  public File getDirectory()
  {
    return directory;
  }
  
  public long getMaxSize()
  {
    try
    {
      long l = maxSize;
      return l;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public void initialize()
    throws IOException
  {
    try
    {
      boolean bool = initialized;
      if (bool) {
        return;
      }
      if (fileSystem.exists(journalFileBackup)) {
        if (fileSystem.exists(journalFile)) {
          fileSystem.delete(journalFileBackup);
        } else {
          fileSystem.rename(journalFileBackup, journalFile);
        }
      }
      bool = fileSystem.exists(journalFile);
      if (bool) {
        try
        {
          readJournal();
          processJournal();
          initialized = true;
          return;
        }
        catch (IOException localIOException)
        {
          Platform localPlatform = Platform.get();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("DiskLruCache ");
          localStringBuilder.append(directory);
          localStringBuilder.append(" is corrupt: ");
          localStringBuilder.append(localIOException.getMessage());
          localStringBuilder.append(", removing");
          localPlatform.logW(localStringBuilder.toString());
          delete();
          closed = false;
        }
      }
      rebuildJournal();
      initialized = true;
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public boolean isClosed()
  {
    try
    {
      boolean bool = closed;
      return bool;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public boolean remove(String paramString)
    throws IOException
  {
    try
    {
      initialize();
      checkNotClosed();
      validateKey(paramString);
      paramString = (Entry)lruEntries.get(paramString);
      if (paramString == null) {
        return false;
      }
      boolean bool = removeEntry(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      throw paramString;
    }
  }
  
  public void setMaxSize(long paramLong)
  {
    try
    {
      maxSize = paramLong;
      if (initialized) {
        executor.execute(cleanupRunnable);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public long size()
    throws IOException
  {
    try
    {
      initialize();
      long l = size;
      return l;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public Iterator snapshots()
    throws IOException
  {
    try
    {
      initialize();
      Iterator local3 = new Iterator()
      {
        final Iterator<DiskLruCache.Entry> delegate = new ArrayList(lruEntries.values()).iterator();
        DiskLruCache.Snapshot nextSnapshot;
        DiskLruCache.Snapshot removeSnapshot;
        
        public boolean hasNext()
        {
          if (nextSnapshot != null) {
            return true;
          }
          DiskLruCache localDiskLruCache = DiskLruCache.this;
          try
          {
            if (closed) {
              return false;
            }
            while (delegate.hasNext())
            {
              DiskLruCache.Snapshot localSnapshot = ((DiskLruCache.Entry)delegate.next()).snapshot();
              if (localSnapshot != null)
              {
                nextSnapshot = localSnapshot;
                return true;
              }
            }
            return false;
          }
          catch (Throwable localThrowable)
          {
            throw localThrowable;
          }
        }
        
        public DiskLruCache.Snapshot next()
        {
          if (hasNext())
          {
            removeSnapshot = nextSnapshot;
            nextSnapshot = null;
            return removeSnapshot;
          }
          throw new NoSuchElementException();
        }
        
        public void remove()
        {
          DiskLruCache.Snapshot localSnapshot = removeSnapshot;
          DiskLruCache localDiskLruCache;
          if (localSnapshot != null) {
            localDiskLruCache = DiskLruCache.this;
          }
          try
          {
            try
            {
              localDiskLruCache.remove(DiskLruCache.Snapshot.access$2100(localSnapshot));
            }
            catch (Throwable localThrowable)
            {
              removeSnapshot = null;
              throw localThrowable;
            }
          }
          catch (IOException localIOException)
          {
            for (;;) {}
          }
          removeSnapshot = null;
          return;
          throw new IllegalStateException("remove() before next()");
        }
      };
      return local3;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  public final class Editor
  {
    private boolean committed;
    private final DiskLruCache.Entry entry;
    private boolean hasErrors;
    private final boolean[] written;
    
    private Editor(DiskLruCache.Entry paramEntry)
    {
      entry = paramEntry;
      if (DiskLruCache.Entry.access$800(paramEntry)) {
        this$1 = null;
      } else {
        this$1 = new boolean[valueCount];
      }
      written = DiskLruCache.this;
    }
    
    public void abort()
      throws IOException
    {
      DiskLruCache localDiskLruCache = DiskLruCache.this;
      try
      {
        DiskLruCache.this.completeEdit(this, false);
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    /* Error */
    public void abortUnlessCommitted()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 22	com/squareup/okhttp/internal/DiskLruCache$Editor:this$0	Lcom/squareup/okhttp/internal/DiskLruCache;
      //   4: astore_2
      //   5: aload_2
      //   6: monitorenter
      //   7: aload_0
      //   8: getfield 64	com/squareup/okhttp/internal/DiskLruCache$Editor:committed	Z
      //   11: istore_1
      //   12: iload_1
      //   13: ifne +14 -> 27
      //   16: aload_0
      //   17: getfield 22	com/squareup/okhttp/internal/DiskLruCache$Editor:this$0	Lcom/squareup/okhttp/internal/DiskLruCache;
      //   20: astore_3
      //   21: aload_3
      //   22: aload_0
      //   23: iconst_0
      //   24: invokestatic 60	com/squareup/okhttp/internal/DiskLruCache:access$2600	(Lcom/squareup/okhttp/internal/DiskLruCache;Lcom/squareup/okhttp/internal/DiskLruCache$Editor;Z)V
      //   27: aload_2
      //   28: monitorexit
      //   29: return
      //   30: astore_3
      //   31: aload_2
      //   32: monitorexit
      //   33: aload_3
      //   34: athrow
      //   35: astore_3
      //   36: goto -9 -> 27
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	39	0	this	Editor
      //   11	2	1	bool	boolean
      //   4	28	2	localDiskLruCache1	DiskLruCache
      //   20	2	3	localDiskLruCache2	DiskLruCache
      //   30	4	3	localThrowable	Throwable
      //   35	1	3	localIOException	IOException
      // Exception table:
      //   from	to	target	type
      //   7	12	30	java/lang/Throwable
      //   21	27	30	java/lang/Throwable
      //   27	29	30	java/lang/Throwable
      //   31	33	30	java/lang/Throwable
      //   21	27	35	java/io/IOException
    }
    
    public void commit()
      throws IOException
    {
      DiskLruCache localDiskLruCache = DiskLruCache.this;
      try
      {
        if (hasErrors)
        {
          DiskLruCache.this.completeEdit(this, false);
          DiskLruCache.this.removeEntry(entry);
        }
        else
        {
          DiskLruCache.this.completeEdit(this, true);
        }
        committed = true;
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    public Sink newSink(int paramInt)
      throws IOException
    {
      localDiskLruCache1 = DiskLruCache.this;
      try
      {
        if (DiskLruCache.Entry.access$900(entry) == this)
        {
          if (!DiskLruCache.Entry.access$800(entry)) {
            written[paramInt] = true;
          }
          localObject = DiskLruCache.Entry.access$1400(entry)[paramInt];
          localDiskLruCache2 = DiskLruCache.this;
        }
      }
      catch (Throwable localThrowable)
      {
        Object localObject;
        DiskLruCache localDiskLruCache2;
        label77:
        throw localThrowable;
      }
      try
      {
        localObject = fileSystem.sink((File)localObject);
        localObject = new FaultHidingSink((Sink)localObject)
        {
          protected void onException(IOException paramAnonymousIOException)
          {
            paramAnonymousIOException = DiskLruCache.this;
            try
            {
              DiskLruCache.Editor.access$1902(DiskLruCache.Editor.this, true);
              return;
            }
            catch (Throwable localThrowable)
            {
              throw localThrowable;
            }
          }
        };
        return localObject;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        break label77;
      }
      localObject = DiskLruCache.NULL_SINK;
      return localObject;
      throw new IllegalStateException();
    }
    
    public Source newSource(int paramInt)
      throws IOException
    {
      localDiskLruCache = DiskLruCache.this;
      try
      {
        if (DiskLruCache.Entry.access$900(entry) == this)
        {
          if (!DiskLruCache.Entry.access$800(entry)) {
            return null;
          }
          localObject1 = DiskLruCache.this;
        }
      }
      catch (Throwable localThrowable)
      {
        Object localObject1;
        Object localObject2;
        label74:
        throw localThrowable;
      }
      try
      {
        localObject1 = fileSystem;
        localObject2 = entry;
        localObject2 = DiskLruCache.Entry.access$1300((DiskLruCache.Entry)localObject2);
        localObject2 = localObject2[paramInt];
        localObject1 = ((FileSystem)localObject1).source((File)localObject2);
        return localObject1;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        break label74;
      }
      return null;
      throw new IllegalStateException();
    }
  }
  
  private final class Entry
  {
    private final File[] cleanFiles;
    private DiskLruCache.Editor currentEditor;
    private final File[] dirtyFiles;
    private final String key;
    private final long[] lengths;
    private boolean readable;
    private long sequenceNumber;
    
    private Entry(String paramString)
    {
      key = paramString;
      lengths = new long[valueCount];
      cleanFiles = new File[valueCount];
      dirtyFiles = new File[valueCount];
      paramString = new StringBuilder(paramString);
      paramString.append('.');
      int j = paramString.length();
      int i = 0;
      while (i < valueCount)
      {
        paramString.append(i);
        cleanFiles[i] = new File(directory, paramString.toString());
        paramString.append(".tmp");
        dirtyFiles[i] = new File(directory, paramString.toString());
        paramString.setLength(j);
        i += 1;
      }
    }
    
    private IOException invalidLengths(String[] paramArrayOfString)
      throws IOException
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("unexpected journal line: ");
      localStringBuilder.append(Arrays.toString(paramArrayOfString));
      throw new IOException(localStringBuilder.toString());
    }
    
    private void setLengths(String[] paramArrayOfString)
      throws IOException
    {
      int i;
      if (paramArrayOfString.length == valueCount) {
        i = 0;
      }
      for (;;)
      {
        long[] arrayOfLong;
        String str;
        if (i < paramArrayOfString.length)
        {
          arrayOfLong = lengths;
          str = paramArrayOfString[i];
        }
        try
        {
          long l = Long.parseLong(str);
          arrayOfLong[i] = l;
          i += 1;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          for (;;) {}
        }
      }
      return;
      throw invalidLengths(paramArrayOfString);
      paramArrayOfString = invalidLengths(paramArrayOfString);
      throw paramArrayOfString;
    }
    
    DiskLruCache.Snapshot snapshot()
    {
      if (Thread.holdsLock(DiskLruCache.this))
      {
        localObject1 = new Source[valueCount];
        Object localObject2 = (long[])lengths.clone();
        j = 0;
        i = 0;
        Object localObject3 = DiskLruCache.this;
        for (;;)
        {
          try
          {
            int k = valueCount;
            if (i < k)
            {
              localObject3 = DiskLruCache.this;
              localObject3 = fileSystem;
              localObject4 = cleanFiles[i];
              localObject3 = ((FileSystem)localObject3).source((File)localObject4);
              localObject1[i] = localObject3;
              i += 1;
              break;
            }
            localObject3 = DiskLruCache.this;
            Object localObject4 = key;
            long l = sequenceNumber;
            localObject2 = new DiskLruCache.Snapshot((DiskLruCache)localObject3, (String)localObject4, l, (Source[])localObject1, (long[])localObject2, null);
            return localObject2;
          }
          catch (FileNotFoundException localFileNotFoundException)
          {
            i = j;
            continue;
          }
          if ((i >= valueCount) || (localObject1[i] == null)) {
            continue;
          }
          Util.closeQuietly(localObject1[i]);
          i += 1;
        }
        return null;
      }
      Object localObject1 = new AssertionError();
      throw ((Throwable)localObject1);
    }
    
    void writeLengths(BufferedSink paramBufferedSink)
      throws IOException
    {
      long[] arrayOfLong = lengths;
      int j = arrayOfLong.length;
      int i = 0;
      while (i < j)
      {
        long l = arrayOfLong[i];
        paramBufferedSink.writeByte(32).writeDecimalLong(l);
        i += 1;
      }
    }
  }
  
  public final class Snapshot
    implements Closeable
  {
    private final String key;
    private final long[] lengths;
    private final long sequenceNumber;
    private final Source[] sources;
    
    private Snapshot(String paramString, long paramLong, Source[] paramArrayOfSource, long[] paramArrayOfLong)
    {
      key = paramString;
      sequenceNumber = paramLong;
      sources = paramArrayOfSource;
      lengths = paramArrayOfLong;
    }
    
    public void close()
    {
      Source[] arrayOfSource = sources;
      int j = arrayOfSource.length;
      int i = 0;
      while (i < j)
      {
        Util.closeQuietly(arrayOfSource[i]);
        i += 1;
      }
    }
    
    public DiskLruCache.Editor edit()
      throws IOException
    {
      return DiskLruCache.this.edit(key, sequenceNumber);
    }
    
    public long getLength(int paramInt)
    {
      return lengths[paramInt];
    }
    
    public Source getSource(int paramInt)
    {
      return sources[paramInt];
    }
    
    public String key()
    {
      return key;
    }
  }
}
