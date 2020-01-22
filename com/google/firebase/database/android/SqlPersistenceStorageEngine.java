package com.google.firebase.database.android;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.database.core.persistence.PersistenceStorageEngine;
import com.google.firebase.database.core.persistence.PruneForest;
import com.google.firebase.database.core.persistence.TrackedQuery;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.core.utilities.NodeSizeEstimator;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.util.JsonMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SqlPersistenceStorageEngine
  implements PersistenceStorageEngine
{
  private static final int CHILDREN_NODE_SPLIT_SIZE_THRESHOLD = 16384;
  private static final String CREATE_SERVER_CACHE = "CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);";
  private static final String CREATE_TRACKED_KEYS = "CREATE TABLE trackedKeys (id INTEGER, key TEXT);";
  private static final String CREATE_TRACKED_QUERIES = "CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);";
  private static final String CREATE_WRITES = "CREATE TABLE writes (id INTEGER, path TEXT, type TEXT, part INTEGER, node BLOB, UNIQUE (id, part));";
  private static final String FIRST_PART_KEY = ".part-0000";
  private static final String LOGGER_COMPONENT = "Persistence";
  private static final String PART_KEY_FORMAT = ".part-%04d";
  private static final String PART_KEY_PREFIX = ".part-";
  private static final String PATH_COLUMN_NAME = "path";
  private static final String ROW_ID_COLUMN_NAME = "rowid";
  private static final int ROW_SPLIT_SIZE = 262144;
  private static final String SERVER_CACHE_TABLE = "serverCache";
  private static final String TRACKED_KEYS_ID_COLUMN_NAME = "id";
  private static final String TRACKED_KEYS_KEY_COLUMN_NAME = "key";
  private static final String TRACKED_KEYS_TABLE = "trackedKeys";
  private static final String TRACKED_QUERY_ACTIVE_COLUMN_NAME = "active";
  private static final String TRACKED_QUERY_COMPLETE_COLUMN_NAME = "complete";
  private static final String TRACKED_QUERY_ID_COLUMN_NAME = "id";
  private static final String TRACKED_QUERY_LAST_USE_COLUMN_NAME = "lastUse";
  private static final String TRACKED_QUERY_PARAMS_COLUMN_NAME = "queryParams";
  private static final String TRACKED_QUERY_PATH_COLUMN_NAME = "path";
  private static final String TRACKED_QUERY_TABLE = "trackedQueries";
  private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
  private static final String VALUE_COLUMN_NAME = "value";
  private static final String WRITES_TABLE = "writes";
  private static final String WRITE_ID_COLUMN_NAME = "id";
  private static final String WRITE_NODE_COLUMN_NAME = "node";
  private static final String WRITE_PART_COLUMN_NAME = "part";
  private static final String WRITE_TYPE_COLUMN_NAME = "type";
  private static final String WRITE_TYPE_MERGE = "m";
  private static final String WRITE_TYPE_OVERWRITE = "o";
  private final SQLiteDatabase database;
  private boolean insideTransaction;
  private final LogWrapper logger;
  private long transactionStart = 0L;
  
  public SqlPersistenceStorageEngine(android.content.Context paramContext, com.google.firebase.database.core.Context paramContext1, String paramString)
  {
    try
    {
      paramString = URLEncoder.encode(paramString, "utf-8");
      logger = paramContext1.getLogger("Persistence");
      database = openDatabase(paramContext, paramString);
      return;
    }
    catch (IOException paramContext)
    {
      throw new RuntimeException(paramContext);
    }
  }
  
  private static String buildAncestorWhereClause(Path paramPath, String[] paramArrayOfString)
  {
    int i = 0;
    StringBuilder localStringBuilder = new StringBuilder("(");
    while (!paramPath.isEmpty())
    {
      localStringBuilder.append("path");
      localStringBuilder.append(" = ? OR ");
      paramArrayOfString[i] = pathToKey(paramPath);
      paramPath = paramPath.getParent();
      i += 1;
    }
    localStringBuilder.append("path");
    localStringBuilder.append(" = ?)");
    paramArrayOfString[i] = pathToKey(Path.getEmptyPath());
    return localStringBuilder.toString();
  }
  
  private String commaSeparatedList(Collection paramCollection)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    paramCollection = paramCollection.iterator();
    int i = 1;
    while (paramCollection.hasNext())
    {
      long l = ((Long)paramCollection.next()).longValue();
      if (i == 0) {
        localStringBuilder.append(",");
      }
      i = 0;
      localStringBuilder.append(l);
    }
    return localStringBuilder.toString();
  }
  
  private Node deserializeNode(byte[] paramArrayOfByte)
  {
    Object localObject = UTF8_CHARSET;
    try
    {
      localObject = NodeUtilities.NodeFromJSON(JsonMapper.parseJsonValue(new String(paramArrayOfByte, (Charset)localObject)));
      return localObject;
    }
    catch (IOException localIOException)
    {
      paramArrayOfByte = new String(paramArrayOfByte, UTF8_CHARSET);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Could not deserialize node: ");
      localStringBuilder.append(paramArrayOfByte);
      throw new RuntimeException(localStringBuilder.toString(), localIOException);
    }
  }
  
  private byte[] joinBytes(List paramList)
  {
    Object localObject = paramList.iterator();
    int i = 0;
    while (((Iterator)localObject).hasNext()) {
      i += ((byte[])((Iterator)localObject).next()).length;
    }
    localObject = new byte[i];
    paramList = paramList.iterator();
    i = 0;
    while (paramList.hasNext())
    {
      byte[] arrayOfByte = (byte[])paramList.next();
      System.arraycopy(arrayOfByte, 0, localObject, i, arrayOfByte.length);
      i += arrayOfByte.length;
    }
    return localObject;
  }
  
  private Node loadNested(Path paramPath)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a21 = a20\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
  }
  
  private Cursor loadNestedQuery(Path paramPath, String[] paramArrayOfString)
  {
    String str1 = pathToKey(paramPath);
    String str2 = pathPrefixStartToPrefixEnd(str1);
    String[] arrayOfString = new String[paramPath.size() + 3];
    String str3 = buildAncestorWhereClause(paramPath, arrayOfString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str3);
    localStringBuilder.append(" OR (path > ? AND path < ?)");
    str3 = localStringBuilder.toString();
    arrayOfString[(paramPath.size() + 1)] = str1;
    arrayOfString[(paramPath.size() + 2)] = str2;
    return database.query("serverCache", paramArrayOfString, str3, arrayOfString, null, null, "path");
  }
  
  private SQLiteDatabase openDatabase(android.content.Context paramContext, String paramString)
  {
    paramContext = new PersistentCacheOpenHelper(paramContext, paramString);
    try
    {
      paramContext = paramContext.getWritableDatabase();
      paramContext.rawQuery("PRAGMA locking_mode = EXCLUSIVE", null).close();
      paramContext.beginTransaction();
      paramContext.endTransaction();
      return paramContext;
    }
    catch (SQLiteException paramContext)
    {
      if ((paramContext instanceof SQLiteDatabaseLockedException)) {
        throw new DatabaseException("Failed to gain exclusive lock to Firebase Database's offline persistence. This generally means you are using Firebase Database from multiple processes in your app. Keep in mind that multi-process Android apps execute the code in your Application class in all processes, so you may need to avoid initializing FirebaseDatabase in your Application class. If you are intentionally using Firebase Database from multiple processes, you can only enable offline persistence (i.e. call setPersistenceEnabled(true)) in one of them.", paramContext);
      }
      throw paramContext;
    }
  }
  
  private String partKey(Path paramPath, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(pathToKey(paramPath));
    localStringBuilder.append(String.format(".part-%04d", new Object[] { Integer.valueOf(paramInt) }));
    return localStringBuilder.toString();
  }
  
  private static String pathPrefixStartToPrefixEnd(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString.substring(0, paramString.length() - 1));
    localStringBuilder.append('0');
    return localStringBuilder.toString();
  }
  
  private static String pathToKey(Path paramPath)
  {
    if (paramPath.isEmpty()) {
      return "/";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramPath.toString());
    localStringBuilder.append("/");
    return localStringBuilder.toString();
  }
  
  private void pruneTreeRecursive(Path paramPath1, final Path paramPath2, ImmutableTree paramImmutableTree1, final ImmutableTree paramImmutableTree2, PruneForest paramPruneForest, final List paramList)
  {
    if (paramImmutableTree1.getValue() != null)
    {
      int i = ((Integer)paramPruneForest.foldKeptNodes(Integer.valueOf(0), new ImmutableTree.TreeVisitor()
      {
        public Integer onNodeValue(Path paramAnonymousPath, Void paramAnonymousVoid, Integer paramAnonymousInteger)
        {
          int i;
          if (paramImmutableTree2.readLong(paramAnonymousPath) == null) {
            i = paramAnonymousInteger.intValue() + 1;
          } else {
            i = paramAnonymousInteger.intValue();
          }
          return Integer.valueOf(i);
        }
      })).intValue();
      if (i > 0)
      {
        paramPath1 = paramPath1.child(paramPath2);
        if (logger.logsDebug()) {
          logger.debug(String.format("Need to rewrite %d nodes below path %s", new Object[] { Integer.valueOf(i), paramPath1 }), new Object[0]);
        }
        paramPruneForest.foldKeptNodes(null, new ImmutableTree.TreeVisitor()
        {
          public Void onNodeValue(Path paramAnonymousPath, Void paramAnonymousVoid1, Void paramAnonymousVoid2)
          {
            if (paramImmutableTree2.readLong(paramAnonymousPath) == null) {
              paramList.add(new Pair(paramPath2.child(paramAnonymousPath), val$currentNode.getChild(paramAnonymousPath)));
            }
            return null;
          }
        });
      }
    }
    else
    {
      paramImmutableTree1 = paramImmutableTree1.getChildren().iterator();
      while (paramImmutableTree1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramImmutableTree1.next();
        ChildKey localChildKey = (ChildKey)localEntry.getKey();
        PruneForest localPruneForest = paramPruneForest.child((ChildKey)localEntry.getKey());
        pruneTreeRecursive(paramPath1, paramPath2.child(localChildKey), (ImmutableTree)localEntry.getValue(), paramImmutableTree2.getChild(localChildKey), localPruneForest, paramList);
      }
    }
  }
  
  private int removeNested(String paramString, Path paramPath)
  {
    paramPath = pathToKey(paramPath);
    String str = pathPrefixStartToPrefixEnd(paramPath);
    return database.delete(paramString, "path >= ? AND path < ?", new String[] { paramPath, str });
  }
  
  private int saveNested(Path paramPath, Node paramNode)
  {
    long l = NodeSizeEstimator.estimateSerializedNodeSize(paramNode);
    if (((paramNode instanceof ChildrenNode)) && (l > 16384L))
    {
      boolean bool = logger.logsDebug();
      int i = 0;
      if (bool) {
        logger.debug(String.format("Node estimated serialized size at path %s of %d bytes exceeds limit of %d bytes. Splitting up.", new Object[] { paramPath, Long.valueOf(l), Integer.valueOf(16384) }), new Object[0]);
      }
      Iterator localIterator = paramNode.iterator();
      while (localIterator.hasNext())
      {
        NamedNode localNamedNode = (NamedNode)localIterator.next();
        i += saveNested(paramPath.child(localNamedNode.getName()), localNamedNode.getNode());
      }
      int j = i;
      if (!paramNode.getPriority().isEmpty())
      {
        saveNode(paramPath.child(ChildKey.getPriorityKey()), paramNode.getPriority());
        j = i + 1;
      }
      saveNode(paramPath, EmptyNode.Empty());
      return j + 1;
    }
    saveNode(paramPath, paramNode);
    return 1;
  }
  
  private void saveNode(Path paramPath, Node paramNode)
  {
    paramNode = serializeObject(paramNode.getValue(true));
    if (paramNode.length >= 262144)
    {
      paramNode = splitBytes(paramNode, 262144);
      boolean bool = logger.logsDebug();
      int j = 0;
      int i = j;
      if (bool)
      {
        localObject = logger;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Saving huge leaf node with ");
        localStringBuilder.append(paramNode.size());
        localStringBuilder.append(" parts.");
        ((LogWrapper)localObject).debug(localStringBuilder.toString(), new Object[0]);
        i = j;
      }
      while (i < paramNode.size())
      {
        localObject = new ContentValues();
        ((ContentValues)localObject).put("path", partKey(paramPath, i));
        ((ContentValues)localObject).put("value", (byte[])paramNode.get(i));
        database.insertWithOnConflict("serverCache", null, (ContentValues)localObject, 5);
        i += 1;
      }
    }
    Object localObject = new ContentValues();
    ((ContentValues)localObject).put("path", pathToKey(paramPath));
    ((ContentValues)localObject).put("value", paramNode);
    database.insertWithOnConflict("serverCache", null, (ContentValues)localObject, 5);
  }
  
  private void saveWrite(Path paramPath, long paramLong, String paramString, byte[] paramArrayOfByte)
  {
    verifyInsideTransaction();
    Object localObject = database;
    int i = 0;
    ((SQLiteDatabase)localObject).delete("writes", "id = ?", new String[] { String.valueOf(paramLong) });
    if (paramArrayOfByte.length >= 262144)
    {
      paramArrayOfByte = splitBytes(paramArrayOfByte, 262144);
      while (i < paramArrayOfByte.size())
      {
        localObject = new ContentValues();
        ((ContentValues)localObject).put("id", Long.valueOf(paramLong));
        ((ContentValues)localObject).put("path", pathToKey(paramPath));
        ((ContentValues)localObject).put("type", paramString);
        ((ContentValues)localObject).put("part", Integer.valueOf(i));
        ((ContentValues)localObject).put("node", (byte[])paramArrayOfByte.get(i));
        database.insertWithOnConflict("writes", null, (ContentValues)localObject, 5);
        i += 1;
      }
    }
    localObject = new ContentValues();
    ((ContentValues)localObject).put("id", Long.valueOf(paramLong));
    ((ContentValues)localObject).put("path", pathToKey(paramPath));
    ((ContentValues)localObject).put("type", paramString);
    ((ContentValues)localObject).put("part", null);
    ((ContentValues)localObject).put("node", paramArrayOfByte);
    database.insertWithOnConflict("writes", null, (ContentValues)localObject, 5);
  }
  
  private byte[] serializeObject(Object paramObject)
  {
    try
    {
      paramObject = JsonMapper.serializeJsonValue(paramObject);
      Charset localCharset = UTF8_CHARSET;
      paramObject = paramObject.getBytes(localCharset);
      return paramObject;
    }
    catch (IOException paramObject)
    {
      throw new RuntimeException("Could not serialize leaf node", paramObject);
    }
  }
  
  private static List splitBytes(byte[] paramArrayOfByte, int paramInt)
  {
    int j = (paramArrayOfByte.length - 1) / paramInt + 1;
    ArrayList localArrayList = new ArrayList(j);
    int i = 0;
    while (i < j)
    {
      int m = paramArrayOfByte.length;
      int k = i * paramInt;
      m = Math.min(paramInt, m - k);
      byte[] arrayOfByte = new byte[m];
      System.arraycopy(paramArrayOfByte, k, arrayOfByte, 0, m);
      localArrayList.add(arrayOfByte);
      i += 1;
    }
    return localArrayList;
  }
  
  private int splitNodeRunLength(Path paramPath, List paramList, int paramInt)
  {
    int i = paramInt + 1;
    String str = pathToKey(paramPath);
    if (((String)paramList.get(paramInt)).startsWith(str))
    {
      while ((i < paramList.size()) && (((String)paramList.get(i)).equals(partKey(paramPath, i - paramInt)))) {
        i += 1;
      }
      if (i < paramList.size())
      {
        paramPath = (String)paramList.get(i);
        paramList = new StringBuilder();
        paramList.append(str);
        paramList.append(".part-");
        if (paramPath.startsWith(paramList.toString())) {
          throw new IllegalStateException("Run did not finish with all parts");
        }
      }
      return i - paramInt;
    }
    paramPath = new IllegalStateException("Extracting split nodes needs to start with path prefix");
    throw paramPath;
  }
  
  private void updateServerCache(Path paramPath, Node paramNode, boolean paramBoolean)
  {
    long l1 = System.currentTimeMillis();
    int k;
    int m;
    if (!paramBoolean)
    {
      k = removeNested("serverCache", paramPath);
      m = saveNested(paramPath, paramNode);
    }
    else
    {
      paramNode = paramNode.iterator();
      int j = 0;
      int i = 0;
      for (;;)
      {
        k = j;
        m = i;
        if (!paramNode.hasNext()) {
          break;
        }
        NamedNode localNamedNode = (NamedNode)paramNode.next();
        j += removeNested("serverCache", paramPath.child(localNamedNode.getName()));
        i += saveNested(paramPath.child(localNamedNode.getName()), localNamedNode.getNode());
      }
    }
    long l2 = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Persisted a total of %d rows and deleted %d rows for a set at %s in %dms", new Object[] { Integer.valueOf(m), Integer.valueOf(k), paramPath.toString(), Long.valueOf(l2 - l1) }), new Object[0]);
    }
  }
  
  private void verifyInsideTransaction()
  {
    Utilities.hardAssert(insideTransaction, "Transaction expected to already be in progress.");
  }
  
  public void beginTransaction()
  {
    Utilities.hardAssert(insideTransaction ^ true, "runInTransaction called when an existing transaction is already in progress.");
    if (logger.logsDebug()) {
      logger.debug("Starting transaction.", new Object[0]);
    }
    database.beginTransaction();
    insideTransaction = true;
    transactionStart = System.currentTimeMillis();
  }
  
  public void close()
  {
    database.close();
  }
  
  public void deleteTrackedQuery(long paramLong)
  {
    verifyInsideTransaction();
    String str = String.valueOf(paramLong);
    database.delete("trackedQueries", "id = ?", new String[] { str });
    database.delete("trackedKeys", "id = ?", new String[] { str });
  }
  
  public void endTransaction()
  {
    database.endTransaction();
    insideTransaction = false;
    long l1 = System.currentTimeMillis();
    long l2 = transactionStart;
    if (logger.logsDebug()) {
      logger.debug(String.format("Transaction completed. Elapsed: %dms", new Object[] { Long.valueOf(l1 - l2) }), new Object[0]);
    }
  }
  
  public List loadTrackedQueries()
  {
    long l1 = System.currentTimeMillis();
    Cursor localCursor = database.query("trackedQueries", new String[] { "id", "path", "queryParams", "lastUse", "complete", "active" }, null, null, null, null, "id");
    ArrayList localArrayList = new ArrayList();
    try
    {
      for (;;)
      {
        bool1 = localCursor.moveToNext();
        if (bool1)
        {
          l2 = localCursor.getLong(0);
          Object localObject1 = new Path(localCursor.getString(1));
          Object localObject2 = localCursor.getString(2);
          try
          {
            localObject2 = JsonMapper.parseJson((String)localObject2);
            localObject1 = QuerySpec.fromPathAndQueryObject((Path)localObject1, (Map)localObject2);
            long l3 = localCursor.getLong(3);
            int i = localCursor.getInt(4);
            if (i != 0) {
              bool1 = true;
            } else {
              bool1 = false;
            }
            i = localCursor.getInt(5);
            boolean bool2;
            if (i != 0) {
              bool2 = true;
            } else {
              bool2 = false;
            }
            localArrayList.add(new TrackedQuery(l2, (QuerySpec)localObject1, l3, bool1, bool2));
          }
          catch (IOException localIOException)
          {
            throw new RuntimeException(localIOException);
          }
        }
      }
      long l2 = System.currentTimeMillis();
      boolean bool1 = logger.logsDebug();
      if (bool1) {
        logger.debug(String.format("Loaded %d tracked queries in %dms", new Object[] { Integer.valueOf(localIOException.size()), Long.valueOf(l2 - l1) }), new Object[0]);
      }
      localCursor.close();
      return localIOException;
    }
    catch (Throwable localThrowable)
    {
      localCursor.close();
      throw localThrowable;
    }
  }
  
  public Set loadTrackedQueryKeys(long paramLong)
  {
    return loadTrackedQueryKeys(Collections.singleton(Long.valueOf(paramLong)));
  }
  
  public Set loadTrackedQueryKeys(Set paramSet)
  {
    long l1 = System.currentTimeMillis();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("id IN (");
    ((StringBuilder)localObject).append(commaSeparatedList(paramSet));
    ((StringBuilder)localObject).append(")");
    localObject = ((StringBuilder)localObject).toString();
    localObject = database.query(true, "trackedKeys", new String[] { "key" }, (String)localObject, null, null, null, null, null);
    HashSet localHashSet = new HashSet();
    try
    {
      for (;;)
      {
        bool = ((Cursor)localObject).moveToNext();
        if (!bool) {
          break;
        }
        localHashSet.add(ChildKey.fromString(((Cursor)localObject).getString(0)));
      }
      long l2 = System.currentTimeMillis();
      boolean bool = logger.logsDebug();
      if (bool) {
        logger.debug(String.format("Loaded %d tracked queries keys for tracked queries %s in %dms", new Object[] { Integer.valueOf(localHashSet.size()), paramSet.toString(), Long.valueOf(l2 - l1) }), new Object[0]);
      }
      ((Cursor)localObject).close();
      return localHashSet;
    }
    catch (Throwable paramSet)
    {
      ((Cursor)localObject).close();
      throw paramSet;
    }
  }
  
  public List loadUserWrites()
  {
    long l1 = System.currentTimeMillis();
    Cursor localCursor = database.query("writes", new String[] { "id", "path", "type", "part", "node" }, null, null, null, null, "id, part");
    ArrayList localArrayList = new ArrayList();
    try
    {
      Object localObject2;
      String str;
      for (;;)
      {
        bool = localCursor.moveToNext();
        if (!bool) {
          break label372;
        }
        l2 = localCursor.getLong(0);
        localObject2 = new Path(localCursor.getString(1));
        str = localCursor.getString(2);
        bool = localCursor.isNull(3);
        if (bool)
        {
          localObject1 = localCursor.getBlob(4);
        }
        else
        {
          localObject1 = new ArrayList();
          long l3;
          do
          {
            ((List)localObject1).add(localCursor.getBlob(4));
            bool = localCursor.moveToNext();
            if (!bool) {
              break;
            }
            l3 = localCursor.getLong(0);
          } while (l3 == l2);
          localCursor.moveToPrevious();
          localObject1 = joinBytes((List)localObject1);
        }
        Charset localCharset = UTF8_CHARSET;
        localObject1 = JsonMapper.parseJsonValue(new String((byte[])localObject1, localCharset));
        bool = "o".equals(str);
        if (bool)
        {
          localObject1 = NodeUtilities.NodeFromJSON(localObject1);
          localObject1 = new UserWriteRecord(l2, (Path)localObject2, (Node)localObject1, true);
        }
        else
        {
          bool = "m".equals(str);
          if (!bool) {
            break;
          }
          localObject1 = (Map)localObject1;
          localObject1 = CompoundWrite.fromValue((Map)localObject1);
          localObject1 = new UserWriteRecord(l2, (Path)localObject2, (CompoundWrite)localObject1);
        }
        localArrayList.add(localObject1);
      }
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Got invalid write type: ");
      ((StringBuilder)localObject1).append(str);
      localObject1 = new IllegalStateException(((StringBuilder)localObject1).toString());
      throw ((Throwable)localObject1);
      label372:
      long l2 = System.currentTimeMillis();
      localObject1 = logger;
      boolean bool = ((LogWrapper)localObject1).logsDebug();
      if (bool)
      {
        localObject1 = logger;
        int i = localArrayList.size();
        localObject2 = String.format("Loaded %d writes in %dms", new Object[] { Integer.valueOf(i), Long.valueOf(l2 - l1) });
        ((LogWrapper)localObject1).debug((String)localObject2, new Object[0]);
      }
      localCursor.close();
      return localArrayList;
    }
    catch (Throwable localThrowable) {}catch (IOException localIOException)
    {
      throw new RuntimeException("Failed to load writes", localIOException);
    }
    localCursor.close();
    throw localIOException;
  }
  
  public void mergeIntoServerCache(Path paramPath, CompoundWrite paramCompoundWrite)
  {
    verifyInsideTransaction();
    long l1 = System.currentTimeMillis();
    paramCompoundWrite = paramCompoundWrite.iterator();
    int j = 0;
    int i = 0;
    while (paramCompoundWrite.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramCompoundWrite.next();
      j += removeNested("serverCache", paramPath.child((Path)localEntry.getKey()));
      i += saveNested(paramPath.child((Path)localEntry.getKey()), (Node)localEntry.getValue());
    }
    long l2 = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Persisted a total of %d rows and deleted %d rows for a merge at %s in %dms", new Object[] { Integer.valueOf(i), Integer.valueOf(j), paramPath.toString(), Long.valueOf(l2 - l1) }), new Object[0]);
    }
  }
  
  public void mergeIntoServerCache(Path paramPath, Node paramNode)
  {
    verifyInsideTransaction();
    updateServerCache(paramPath, paramNode, true);
  }
  
  public void overwriteServerCache(Path paramPath, Node paramNode)
  {
    verifyInsideTransaction();
    updateServerCache(paramPath, paramNode, false);
  }
  
  public void pruneCache(Path paramPath, PruneForest paramPruneForest)
  {
    if (!paramPruneForest.prunesAnything()) {
      return;
    }
    verifyInsideTransaction();
    long l1 = System.currentTimeMillis();
    Object localObject3 = loadNestedQuery(paramPath, new String[] { "rowid", "path" });
    Object localObject2 = new ImmutableTree(null);
    Object localObject1 = new ImmutableTree(null);
    while (((Cursor)localObject3).moveToNext())
    {
      l2 = ((Cursor)localObject3).getLong(0);
      Path localPath = new Path(((Cursor)localObject3).getString(1));
      Object localObject4;
      StringBuilder localStringBuilder;
      if (!paramPath.contains(localPath))
      {
        localObject4 = logger;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("We are pruning at ");
        localStringBuilder.append(paramPath);
        localStringBuilder.append(" but we have data stored higher up at ");
        localStringBuilder.append(localPath);
        localStringBuilder.append(". Ignoring.");
        ((LogWrapper)localObject4).warn(localStringBuilder.toString());
      }
      else
      {
        localObject4 = Path.getRelative(paramPath, localPath);
        if (paramPruneForest.shouldPruneUnkeptDescendants((Path)localObject4))
        {
          localObject2 = ((ImmutableTree)localObject2).getKey((Path)localObject4, Long.valueOf(l2));
        }
        else if (paramPruneForest.shouldKeep((Path)localObject4))
        {
          localObject1 = ((ImmutableTree)localObject1).getKey((Path)localObject4, Long.valueOf(l2));
        }
        else
        {
          localObject4 = logger;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("We are pruning at ");
          localStringBuilder.append(paramPath);
          localStringBuilder.append(" and have data at ");
          localStringBuilder.append(localPath);
          localStringBuilder.append(" that isn't marked for pruning or keeping. Ignoring.");
          ((LogWrapper)localObject4).warn(localStringBuilder.toString());
        }
      }
    }
    int i;
    int j;
    if (!((ImmutableTree)localObject2).isEmpty())
    {
      localObject3 = new ArrayList();
      pruneTreeRecursive(paramPath, Path.getEmptyPath(), (ImmutableTree)localObject2, (ImmutableTree)localObject1, paramPruneForest, (List)localObject3);
      paramPruneForest = ((ImmutableTree)localObject2).values();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("rowid IN (");
      ((StringBuilder)localObject1).append(commaSeparatedList(paramPruneForest));
      ((StringBuilder)localObject1).append(")");
      localObject1 = ((StringBuilder)localObject1).toString();
      database.delete("serverCache", (String)localObject1, null);
      localObject1 = ((List)localObject3).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Pair)((Iterator)localObject1).next();
        saveNested(paramPath.child((Path)((Pair)localObject2).getFirst()), (Node)((Pair)localObject2).getSecond());
      }
      i = paramPruneForest.size();
      j = ((List)localObject3).size();
    }
    else
    {
      i = 0;
      j = 0;
    }
    long l2 = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Pruned %d rows with %d nodes resaved in %dms", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Long.valueOf(l2 - l1) }), new Object[0]);
    }
  }
  
  public void purgeCache()
  {
    verifyInsideTransaction();
    database.delete("serverCache", null, null);
    database.delete("writes", null, null);
    database.delete("trackedQueries", null, null);
    database.delete("trackedKeys", null, null);
  }
  
  public void removeAllUserWrites()
  {
    verifyInsideTransaction();
    long l1 = System.currentTimeMillis();
    int i = database.delete("writes", null, null);
    long l2 = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Deleted %d (all) write(s) in %dms", new Object[] { Integer.valueOf(i), Long.valueOf(l2 - l1) }), new Object[0]);
    }
  }
  
  public void removeUserWrite(long paramLong)
  {
    verifyInsideTransaction();
    long l1 = System.currentTimeMillis();
    int i = database.delete("writes", "id = ?", new String[] { String.valueOf(paramLong) });
    long l2 = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Deleted %d write(s) with writeId %d in %dms", new Object[] { Integer.valueOf(i), Long.valueOf(paramLong), Long.valueOf(l2 - l1) }), new Object[0]);
    }
  }
  
  public void resetPreviouslyActiveTrackedQueries(long paramLong)
  {
    verifyInsideTransaction();
    long l = System.currentTimeMillis();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("active", Boolean.valueOf(false));
    localContentValues.put("lastUse", Long.valueOf(paramLong));
    database.updateWithOnConflict("trackedQueries", localContentValues, "active = 1", new String[0], 5);
    paramLong = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Reset active tracked queries in %dms", new Object[] { Long.valueOf(paramLong - l) }), new Object[0]);
    }
  }
  
  public void saveTrackedQuery(TrackedQuery paramTrackedQuery)
  {
    verifyInsideTransaction();
    long l1 = System.currentTimeMillis();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("id", Long.valueOf(zoom));
    localContentValues.put("path", pathToKey(querySpec.getPath()));
    localContentValues.put("queryParams", querySpec.getParams().toJSON());
    localContentValues.put("lastUse", Long.valueOf(lastUse));
    localContentValues.put("complete", Boolean.valueOf(complete));
    localContentValues.put("active", Boolean.valueOf(active));
    database.insertWithOnConflict("trackedQueries", null, localContentValues, 5);
    long l2 = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Saved new tracked query in %dms", new Object[] { Long.valueOf(l2 - l1) }), new Object[0]);
    }
  }
  
  public void saveTrackedQueryKeys(long paramLong, Set paramSet)
  {
    verifyInsideTransaction();
    long l1 = System.currentTimeMillis();
    database.delete("trackedKeys", "id = ?", new String[] { String.valueOf(paramLong) });
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      ChildKey localChildKey = (ChildKey)localIterator.next();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Long.valueOf(paramLong));
      localContentValues.put("key", localChildKey.asString());
      database.insertWithOnConflict("trackedKeys", null, localContentValues, 5);
    }
    long l2 = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Set %d tracked query keys for tracked query %d in %dms", new Object[] { Integer.valueOf(paramSet.size()), Long.valueOf(paramLong), Long.valueOf(l2 - l1) }), new Object[0]);
    }
  }
  
  public void saveUserMerge(Path paramPath, CompoundWrite paramCompoundWrite, long paramLong)
  {
    verifyInsideTransaction();
    long l = System.currentTimeMillis();
    saveWrite(paramPath, paramLong, "m", serializeObject(paramCompoundWrite.getValue(true)));
    paramLong = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Persisted user merge in %dms", new Object[] { Long.valueOf(paramLong - l) }), new Object[0]);
    }
  }
  
  public void saveUserOverwrite(Path paramPath, Node paramNode, long paramLong)
  {
    verifyInsideTransaction();
    long l = System.currentTimeMillis();
    saveWrite(paramPath, paramLong, "o", serializeObject(paramNode.getValue(true)));
    paramLong = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Persisted user overwrite in %dms", new Object[] { Long.valueOf(paramLong - l) }), new Object[0]);
    }
  }
  
  public Node serverCache(Path paramPath)
  {
    return loadNested(paramPath);
  }
  
  public long serverCacheEstimatedSizeInBytes()
  {
    Object localObject = String.format("SELECT sum(length(%s) + length(%s)) FROM %s", new Object[] { "value", "path", "serverCache" });
    localObject = database.rawQuery((String)localObject, null);
    try
    {
      boolean bool = ((Cursor)localObject).moveToFirst();
      if (bool)
      {
        long l = ((Cursor)localObject).getLong(0);
        ((Cursor)localObject).close();
        return l;
      }
      throw new IllegalStateException("Couldn't read database result!");
    }
    catch (Throwable localThrowable)
    {
      ((Cursor)localObject).close();
      throw localThrowable;
    }
  }
  
  public void setTransactionSuccessful()
  {
    database.setTransactionSuccessful();
  }
  
  public void updateTrackedQueryKeys(long paramLong, Set paramSet1, Set paramSet2)
  {
    verifyInsideTransaction();
    long l1 = System.currentTimeMillis();
    Iterator localIterator = paramSet2.iterator();
    ChildKey localChildKey;
    while (localIterator.hasNext())
    {
      localChildKey = (ChildKey)localIterator.next();
      database.delete("trackedKeys", "id = ? AND key = ?", new String[] { String.valueOf(paramLong), localChildKey.asString() });
    }
    localIterator = paramSet1.iterator();
    while (localIterator.hasNext())
    {
      localChildKey = (ChildKey)localIterator.next();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Long.valueOf(paramLong));
      localContentValues.put("key", localChildKey.asString());
      database.insertWithOnConflict("trackedKeys", null, localContentValues, 5);
    }
    long l2 = System.currentTimeMillis();
    if (logger.logsDebug()) {
      logger.debug(String.format("Updated tracked query keys (%d added, %d removed) for tracked query id %d in %dms", new Object[] { Integer.valueOf(paramSet1.size()), Integer.valueOf(paramSet2.size()), Long.valueOf(paramLong), Long.valueOf(l2 - l1) }), new Object[0]);
    }
  }
  
  private static class PersistentCacheOpenHelper
    extends SQLiteOpenHelper
  {
    private static final int DATABASE_VERSION = 2;
    
    public PersistentCacheOpenHelper(android.content.Context paramContext, String paramString)
    {
      super(paramString, null, 2);
    }
    
    private void dropTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("DROP TABLE IF EXISTS ");
      localStringBuilder.append(paramString);
      paramSQLiteDatabase.execSQL(localStringBuilder.toString());
    }
    
    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);");
      paramSQLiteDatabase.execSQL("CREATE TABLE writes (id INTEGER, path TEXT, type TEXT, part INTEGER, node BLOB, UNIQUE (id, part));");
      paramSQLiteDatabase.execSQL("CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);");
      paramSQLiteDatabase.execSQL("CREATE TABLE trackedKeys (id INTEGER, key TEXT);");
    }
    
    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      if (paramInt1 <= 1)
      {
        dropTable(paramSQLiteDatabase, "serverCache");
        paramSQLiteDatabase.execSQL("CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);");
        dropTable(paramSQLiteDatabase, "complete");
        paramSQLiteDatabase.execSQL("CREATE TABLE trackedKeys (id INTEGER, key TEXT);");
        paramSQLiteDatabase.execSQL("CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);");
        return;
      }
      paramSQLiteDatabase = new StringBuilder();
      paramSQLiteDatabase.append("We don't handle upgrading to ");
      paramSQLiteDatabase.append(paramInt2);
      throw new AssertionError(paramSQLiteDatabase.toString());
    }
  }
}
