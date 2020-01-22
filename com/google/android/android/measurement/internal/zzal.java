package com.google.android.android.measurement.internal;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;

public final class zzal
  extends Log
{
  private final zzam zzalq = new zzam(this, getContext(), "google_app_measurement_local.db");
  private boolean zzalr;
  
  zzal(zzbt paramZzbt)
  {
    super(paramZzbt);
  }
  
  /* Error */
  private final boolean delete(int paramInt, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 42	com/google/android/android/measurement/internal/zzco:zzgb	()V
    //   4: aload_0
    //   5: invokevirtual 45	com/google/android/android/measurement/internal/zzco:zzaf	()V
    //   8: aload_0
    //   9: getfield 47	com/google/android/android/measurement/internal/zzal:zzalr	Z
    //   12: ifeq +5 -> 17
    //   15: iconst_0
    //   16: ireturn
    //   17: new 49	android/content/ContentValues
    //   20: dup
    //   21: invokespecial 51	android/content/ContentValues:<init>	()V
    //   24: astore 15
    //   26: aload 15
    //   28: ldc 53
    //   30: iload_1
    //   31: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   34: invokevirtual 63	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   37: aload 15
    //   39: ldc 65
    //   41: aload_2
    //   42: invokevirtual 68	android/content/ContentValues:put	(Ljava/lang/String;[B)V
    //   45: iconst_0
    //   46: istore_1
    //   47: iconst_5
    //   48: istore_3
    //   49: iload_1
    //   50: iconst_5
    //   51: if_icmpge +616 -> 667
    //   54: aconst_null
    //   55: astore 10
    //   57: aconst_null
    //   58: astore 12
    //   60: aconst_null
    //   61: astore 14
    //   63: aconst_null
    //   64: astore 11
    //   66: aload_0
    //   67: invokespecial 72	com/google/android/android/measurement/internal/zzal:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   70: astore 13
    //   72: aload 13
    //   74: astore_2
    //   75: aload 13
    //   77: ifnonnull +27 -> 104
    //   80: aload 14
    //   82: astore 10
    //   84: aload_2
    //   85: astore 11
    //   87: aload_0
    //   88: iconst_1
    //   89: putfield 47	com/google/android/android/measurement/internal/zzal:zzalr	Z
    //   92: aload 13
    //   94: ifnull +606 -> 700
    //   97: aload 13
    //   99: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   102: iconst_0
    //   103: ireturn
    //   104: aload 13
    //   106: invokevirtual 82	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   109: lconst_0
    //   110: lstore 7
    //   112: aload 13
    //   114: ldc 84
    //   116: aconst_null
    //   117: invokevirtual 88	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   120: astore 10
    //   122: aload 10
    //   124: astore 12
    //   126: lload 7
    //   128: lstore 5
    //   130: aload 10
    //   132: ifnull +62 -> 194
    //   135: aload 10
    //   137: invokeinterface 94 1 0
    //   142: istore 9
    //   144: lload 7
    //   146: lstore 5
    //   148: iload 9
    //   150: ifeq +44 -> 194
    //   153: aload 10
    //   155: iconst_0
    //   156: invokeinterface 98 2 0
    //   161: lstore 5
    //   163: goto +31 -> 194
    //   166: astore 10
    //   168: aload_2
    //   169: astore 11
    //   171: aload 12
    //   173: astore_2
    //   174: goto +464 -> 638
    //   177: astore 10
    //   179: aload 12
    //   181: astore_2
    //   182: goto +187 -> 369
    //   185: astore 13
    //   187: aload 10
    //   189: astore 12
    //   191: goto +363 -> 554
    //   194: lload 5
    //   196: ldc2_w 99
    //   199: lcmp
    //   200: iflt +99 -> 299
    //   203: aload_0
    //   204: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   207: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   210: ldc 112
    //   212: invokevirtual 118	com/google/android/android/measurement/internal/zzar:zzbx	(Ljava/lang/String;)V
    //   215: ldc2_w 99
    //   218: lload 5
    //   220: lsub
    //   221: lconst_1
    //   222: ladd
    //   223: lstore 5
    //   225: lload 5
    //   227: invokestatic 124	java/lang/Long:toString	(J)Ljava/lang/String;
    //   230: astore 11
    //   232: aload 13
    //   234: ldc 126
    //   236: ldc -128
    //   238: iconst_1
    //   239: anewarray 130	java/lang/String
    //   242: dup
    //   243: iconst_0
    //   244: aload 11
    //   246: aastore
    //   247: invokevirtual 133	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   250: istore 4
    //   252: iload 4
    //   254: i2l
    //   255: lstore 7
    //   257: lload 7
    //   259: lload 5
    //   261: lcmp
    //   262: ifeq +37 -> 299
    //   265: aload_0
    //   266: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   269: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   272: astore 11
    //   274: aload 11
    //   276: ldc -121
    //   278: lload 5
    //   280: invokestatic 138	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   283: lload 7
    //   285: invokestatic 138	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   288: lload 5
    //   290: lload 7
    //   292: lsub
    //   293: invokestatic 138	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   296: invokevirtual 142	com/google/android/android/measurement/internal/zzar:append	(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
    //   299: aload 13
    //   301: ldc 126
    //   303: aconst_null
    //   304: aload 15
    //   306: invokevirtual 146	android/database/sqlite/SQLiteDatabase:insertOrThrow	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   309: pop2
    //   310: aload 13
    //   312: invokevirtual 149	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   315: aload 13
    //   317: invokevirtual 152	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   320: aload 10
    //   322: ifnull +10 -> 332
    //   325: aload 10
    //   327: invokeinterface 153 1 0
    //   332: aload 13
    //   334: ifnull +8 -> 342
    //   337: aload 13
    //   339: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   342: iconst_1
    //   343: ireturn
    //   344: aload 10
    //   346: astore 12
    //   348: goto +146 -> 494
    //   351: astore 10
    //   353: aconst_null
    //   354: astore 12
    //   356: aload_2
    //   357: astore 11
    //   359: aload 12
    //   361: astore_2
    //   362: goto +276 -> 638
    //   365: astore 10
    //   367: aconst_null
    //   368: astore_2
    //   369: aload 13
    //   371: astore 11
    //   373: goto +28 -> 401
    //   376: aconst_null
    //   377: astore 12
    //   379: goto +115 -> 494
    //   382: astore 13
    //   384: goto +170 -> 554
    //   387: astore 10
    //   389: aconst_null
    //   390: astore 11
    //   392: aconst_null
    //   393: astore_2
    //   394: goto +244 -> 638
    //   397: astore 10
    //   399: aconst_null
    //   400: astore_2
    //   401: aload 11
    //   403: ifnull +26 -> 429
    //   406: aload 11
    //   408: checkcast 79	android/database/sqlite/SQLiteDatabase
    //   411: invokevirtual 156	android/database/sqlite/SQLiteDatabase:inTransaction	()Z
    //   414: istore 9
    //   416: iload 9
    //   418: ifeq +11 -> 429
    //   421: aload 11
    //   423: checkcast 79	android/database/sqlite/SQLiteDatabase
    //   426: invokevirtual 152	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   429: aload_0
    //   430: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   433: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   436: ldc -98
    //   438: aload 10
    //   440: invokevirtual 161	com/google/android/android/measurement/internal/zzar:append	(Ljava/lang/String;Ljava/lang/Object;)V
    //   443: aload_0
    //   444: iconst_1
    //   445: putfield 47	com/google/android/android/measurement/internal/zzal:zzalr	Z
    //   448: aload_2
    //   449: ifnull +12 -> 461
    //   452: aload_2
    //   453: checkcast 90	android/database/Cursor
    //   456: invokeinterface 153 1 0
    //   461: iload_3
    //   462: istore 4
    //   464: aload 11
    //   466: ifnull +153 -> 619
    //   469: aload 11
    //   471: checkcast 79	android/database/sqlite/SQLiteDatabase
    //   474: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   477: iload_3
    //   478: istore 4
    //   480: goto +139 -> 619
    //   483: astore 10
    //   485: goto +153 -> 638
    //   488: aconst_null
    //   489: astore_2
    //   490: aload 10
    //   492: astore 12
    //   494: iload_3
    //   495: i2l
    //   496: lstore 5
    //   498: aload 12
    //   500: astore 10
    //   502: aload_2
    //   503: astore 11
    //   505: lload 5
    //   507: invokestatic 167	android/os/SystemClock:sleep	(J)V
    //   510: iload_3
    //   511: bipush 20
    //   513: iadd
    //   514: istore_3
    //   515: aload 12
    //   517: ifnull +13 -> 530
    //   520: aload 12
    //   522: checkcast 90	android/database/Cursor
    //   525: invokeinterface 153 1 0
    //   530: iload_3
    //   531: istore 4
    //   533: aload_2
    //   534: ifnull +85 -> 619
    //   537: aload_2
    //   538: checkcast 79	android/database/sqlite/SQLiteDatabase
    //   541: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   544: iload_3
    //   545: istore 4
    //   547: goto +72 -> 619
    //   550: astore 13
    //   552: aconst_null
    //   553: astore_2
    //   554: aload 12
    //   556: astore 10
    //   558: aload_2
    //   559: astore 11
    //   561: aload_0
    //   562: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   565: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   568: ldc -98
    //   570: aload 13
    //   572: invokevirtual 161	com/google/android/android/measurement/internal/zzar:append	(Ljava/lang/String;Ljava/lang/Object;)V
    //   575: aload 12
    //   577: astore 10
    //   579: aload_2
    //   580: astore 11
    //   582: aload_0
    //   583: iconst_1
    //   584: putfield 47	com/google/android/android/measurement/internal/zzal:zzalr	Z
    //   587: aload 12
    //   589: ifnull +13 -> 602
    //   592: aload 12
    //   594: checkcast 90	android/database/Cursor
    //   597: invokeinterface 153 1 0
    //   602: iload_3
    //   603: istore 4
    //   605: aload_2
    //   606: ifnull +13 -> 619
    //   609: aload_2
    //   610: checkcast 79	android/database/sqlite/SQLiteDatabase
    //   613: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   616: iload_3
    //   617: istore 4
    //   619: iload_1
    //   620: iconst_1
    //   621: iadd
    //   622: istore_1
    //   623: iload 4
    //   625: istore_3
    //   626: goto -577 -> 49
    //   629: astore 12
    //   631: aload 10
    //   633: astore_2
    //   634: aload 12
    //   636: astore 10
    //   638: aload_2
    //   639: ifnull +12 -> 651
    //   642: aload_2
    //   643: checkcast 90	android/database/Cursor
    //   646: invokeinterface 153 1 0
    //   651: aload 11
    //   653: ifnull +11 -> 664
    //   656: aload 11
    //   658: checkcast 79	android/database/sqlite/SQLiteDatabase
    //   661: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   664: aload 10
    //   666: athrow
    //   667: aload_0
    //   668: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   671: invokevirtual 170	com/google/android/android/measurement/internal/zzap:zzjg	()Lcom/google/android/android/measurement/internal/zzar;
    //   674: ldc -84
    //   676: invokevirtual 118	com/google/android/android/measurement/internal/zzar:zzbx	(Ljava/lang/String;)V
    //   679: iconst_0
    //   680: ireturn
    //   681: astore_2
    //   682: goto -194 -> 488
    //   685: astore 10
    //   687: goto -311 -> 376
    //   690: astore 11
    //   692: goto -348 -> 344
    //   695: astore 11
    //   697: goto -353 -> 344
    //   700: iconst_0
    //   701: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	702	0	this	zzal
    //   0	702	1	paramInt	int
    //   0	702	2	paramArrayOfByte	byte[]
    //   48	578	3	i	int
    //   250	374	4	j	int
    //   128	378	5	l1	long
    //   110	181	7	l2	long
    //   142	275	9	bool	boolean
    //   55	99	10	localObject1	Object
    //   166	1	10	localThrowable1	Throwable
    //   177	168	10	localSQLiteException1	SQLiteException
    //   351	1	10	localThrowable2	Throwable
    //   365	1	10	localSQLiteException2	SQLiteException
    //   387	1	10	localThrowable3	Throwable
    //   397	42	10	localSQLiteException3	SQLiteException
    //   483	8	10	localThrowable4	Throwable
    //   500	165	10	localObject2	Object
    //   685	1	10	localSQLiteDatabaseLockedException1	android.database.sqlite.SQLiteDatabaseLockedException
    //   64	593	11	localObject3	Object
    //   690	1	11	localSQLiteDatabaseLockedException2	android.database.sqlite.SQLiteDatabaseLockedException
    //   695	1	11	localSQLiteDatabaseLockedException3	android.database.sqlite.SQLiteDatabaseLockedException
    //   58	535	12	localObject4	Object
    //   629	6	12	localThrowable5	Throwable
    //   70	43	13	localSQLiteDatabase	SQLiteDatabase
    //   185	185	13	localSQLiteFullException1	android.database.sqlite.SQLiteFullException
    //   382	1	13	localSQLiteFullException2	android.database.sqlite.SQLiteFullException
    //   550	21	13	localSQLiteFullException3	android.database.sqlite.SQLiteFullException
    //   61	20	14	localObject5	Object
    //   24	281	15	localContentValues	android.content.ContentValues
    // Exception table:
    //   from	to	target	type
    //   135	144	166	java/lang/Throwable
    //   153	163	166	java/lang/Throwable
    //   203	215	166	java/lang/Throwable
    //   225	232	166	java/lang/Throwable
    //   232	252	166	java/lang/Throwable
    //   265	274	166	java/lang/Throwable
    //   274	299	166	java/lang/Throwable
    //   299	320	166	java/lang/Throwable
    //   135	144	177	android/database/sqlite/SQLiteException
    //   153	163	177	android/database/sqlite/SQLiteException
    //   203	215	177	android/database/sqlite/SQLiteException
    //   225	232	177	android/database/sqlite/SQLiteException
    //   232	252	177	android/database/sqlite/SQLiteException
    //   265	274	177	android/database/sqlite/SQLiteException
    //   274	299	177	android/database/sqlite/SQLiteException
    //   299	320	177	android/database/sqlite/SQLiteException
    //   135	144	185	android/database/sqlite/SQLiteFullException
    //   153	163	185	android/database/sqlite/SQLiteFullException
    //   203	215	185	android/database/sqlite/SQLiteFullException
    //   225	232	185	android/database/sqlite/SQLiteFullException
    //   232	252	185	android/database/sqlite/SQLiteFullException
    //   265	274	185	android/database/sqlite/SQLiteFullException
    //   274	299	185	android/database/sqlite/SQLiteFullException
    //   299	320	185	android/database/sqlite/SQLiteFullException
    //   104	109	351	java/lang/Throwable
    //   112	122	351	java/lang/Throwable
    //   104	109	365	android/database/sqlite/SQLiteException
    //   112	122	365	android/database/sqlite/SQLiteException
    //   104	109	382	android/database/sqlite/SQLiteFullException
    //   112	122	382	android/database/sqlite/SQLiteFullException
    //   66	72	387	java/lang/Throwable
    //   66	72	397	android/database/sqlite/SQLiteException
    //   406	416	483	java/lang/Throwable
    //   421	429	483	java/lang/Throwable
    //   429	448	483	java/lang/Throwable
    //   66	72	550	android/database/sqlite/SQLiteFullException
    //   87	92	629	java/lang/Throwable
    //   505	510	629	java/lang/Throwable
    //   561	575	629	java/lang/Throwable
    //   582	587	629	java/lang/Throwable
    //   66	72	681	android/database/sqlite/SQLiteDatabaseLockedException
    //   104	109	685	android/database/sqlite/SQLiteDatabaseLockedException
    //   112	122	685	android/database/sqlite/SQLiteDatabaseLockedException
    //   135	144	690	android/database/sqlite/SQLiteDatabaseLockedException
    //   153	163	690	android/database/sqlite/SQLiteDatabaseLockedException
    //   203	215	695	android/database/sqlite/SQLiteDatabaseLockedException
    //   225	232	695	android/database/sqlite/SQLiteDatabaseLockedException
    //   232	252	695	android/database/sqlite/SQLiteDatabaseLockedException
    //   265	274	695	android/database/sqlite/SQLiteDatabaseLockedException
    //   274	299	695	android/database/sqlite/SQLiteDatabaseLockedException
    //   299	320	695	android/database/sqlite/SQLiteDatabaseLockedException
  }
  
  private final SQLiteDatabase getWritableDatabase()
    throws SQLiteException
  {
    if (zzalr) {
      return null;
    }
    SQLiteDatabase localSQLiteDatabase = zzalq.getWritableDatabase();
    if (localSQLiteDatabase == null)
    {
      zzalr = true;
      return null;
    }
    return localSQLiteDatabase;
  }
  
  /* Error */
  public final java.util.List doInBackground(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 45	com/google/android/android/measurement/internal/zzco:zzaf	()V
    //   4: aload_0
    //   5: invokevirtual 42	com/google/android/android/measurement/internal/zzco:zzgb	()V
    //   8: aload_0
    //   9: getfield 47	com/google/android/android/measurement/internal/zzal:zzalr	Z
    //   12: ifeq +5 -> 17
    //   15: aconst_null
    //   16: areturn
    //   17: new 180	java/util/ArrayList
    //   20: dup
    //   21: invokespecial 181	java/util/ArrayList:<init>	()V
    //   24: astore 17
    //   26: aload_0
    //   27: invokevirtual 20	com/google/android/android/measurement/internal/zzco:getContext	()Landroid/content/Context;
    //   30: ldc 22
    //   32: invokevirtual 187	android/content/Context:getDatabasePath	(Ljava/lang/String;)Ljava/io/File;
    //   35: invokevirtual 192	java/io/File:exists	()Z
    //   38: ifne +6 -> 44
    //   41: aload 17
    //   43: areturn
    //   44: iconst_0
    //   45: istore_1
    //   46: iconst_5
    //   47: istore_2
    //   48: aconst_null
    //   49: astore 12
    //   51: iload_1
    //   52: iconst_5
    //   53: if_icmpge +1239 -> 1292
    //   56: aload_0
    //   57: invokespecial 72	com/google/android/android/measurement/internal/zzal:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   60: astore 13
    //   62: aload 13
    //   64: astore 10
    //   66: aload 10
    //   68: astore 11
    //   70: aload 10
    //   72: ifnonnull +24 -> 96
    //   75: aload 11
    //   77: astore 13
    //   79: aload_0
    //   80: iconst_1
    //   81: putfield 47	com/google/android/android/measurement/internal/zzal:zzalr	Z
    //   84: aload 10
    //   86: ifnull +1291 -> 1377
    //   89: aload 10
    //   91: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   94: aconst_null
    //   95: areturn
    //   96: aload 10
    //   98: invokevirtual 82	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   101: bipush 100
    //   103: invokestatic 195	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   106: astore 12
    //   108: aload 10
    //   110: astore 11
    //   112: aload 10
    //   114: ldc 126
    //   116: iconst_3
    //   117: anewarray 130	java/lang/String
    //   120: dup
    //   121: iconst_0
    //   122: ldc -59
    //   124: aastore
    //   125: dup
    //   126: iconst_1
    //   127: ldc 53
    //   129: aastore
    //   130: dup
    //   131: iconst_2
    //   132: ldc 65
    //   134: aastore
    //   135: aconst_null
    //   136: aconst_null
    //   137: aconst_null
    //   138: aconst_null
    //   139: ldc -57
    //   141: aload 12
    //   143: invokevirtual 203	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   146: astore 12
    //   148: aload 12
    //   150: astore 13
    //   152: ldc2_w 204
    //   155: lstore 5
    //   157: aload 11
    //   159: astore 15
    //   161: aload 13
    //   163: astore 14
    //   165: aload 12
    //   167: invokeinterface 208 1 0
    //   172: istore 9
    //   174: iload 9
    //   176: ifeq +587 -> 763
    //   179: aload 11
    //   181: astore 15
    //   183: aload 13
    //   185: astore 14
    //   187: aload 12
    //   189: iconst_0
    //   190: invokeinterface 98 2 0
    //   195: lstore 7
    //   197: aload 11
    //   199: astore 15
    //   201: aload 13
    //   203: astore 14
    //   205: aload 12
    //   207: iconst_1
    //   208: invokeinterface 212 2 0
    //   213: istore_3
    //   214: aload 11
    //   216: astore 15
    //   218: aload 13
    //   220: astore 14
    //   222: aload 12
    //   224: iconst_2
    //   225: invokeinterface 216 2 0
    //   230: astore 19
    //   232: iload_3
    //   233: ifne +166 -> 399
    //   236: aload 11
    //   238: astore 15
    //   240: aload 13
    //   242: astore 14
    //   244: invokestatic 222	android/os/Parcel:obtain	()Landroid/os/Parcel;
    //   247: astore 16
    //   249: aload 19
    //   251: arraylength
    //   252: istore_3
    //   253: aload 16
    //   255: aload 19
    //   257: iconst_0
    //   258: iload_3
    //   259: invokevirtual 226	android/os/Parcel:unmarshall	([BII)V
    //   262: aload 16
    //   264: iconst_0
    //   265: invokevirtual 230	android/os/Parcel:setDataPosition	(I)V
    //   268: getstatic 236	com/google/android/android/measurement/internal/zzad:CREATOR	Landroid/os/Parcelable$Creator;
    //   271: astore 14
    //   273: aload 14
    //   275: aload 16
    //   277: invokeinterface 242 2 0
    //   282: astore 14
    //   284: aload 14
    //   286: checkcast 232	com/google/android/android/measurement/internal/zzad
    //   289: astore 18
    //   291: aload 11
    //   293: astore 15
    //   295: aload 13
    //   297: astore 14
    //   299: aload 16
    //   301: invokevirtual 245	android/os/Parcel:recycle	()V
    //   304: lload 7
    //   306: lstore 5
    //   308: aload 18
    //   310: ifnull -153 -> 157
    //   313: aload 11
    //   315: astore 15
    //   317: aload 13
    //   319: astore 14
    //   321: aload 17
    //   323: aload 18
    //   325: invokeinterface 251 2 0
    //   330: pop
    //   331: lload 7
    //   333: lstore 5
    //   335: goto -178 -> 157
    //   338: astore 18
    //   340: goto +35 -> 375
    //   343: aload_0
    //   344: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   347: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   350: ldc -3
    //   352: invokevirtual 118	com/google/android/android/measurement/internal/zzar:zzbx	(Ljava/lang/String;)V
    //   355: aload 11
    //   357: astore 15
    //   359: aload 13
    //   361: astore 14
    //   363: aload 16
    //   365: invokevirtual 245	android/os/Parcel:recycle	()V
    //   368: lload 7
    //   370: lstore 5
    //   372: goto -215 -> 157
    //   375: aload 11
    //   377: astore 15
    //   379: aload 13
    //   381: astore 14
    //   383: aload 16
    //   385: invokevirtual 245	android/os/Parcel:recycle	()V
    //   388: aload 11
    //   390: astore 15
    //   392: aload 13
    //   394: astore 14
    //   396: aload 18
    //   398: athrow
    //   399: iload_3
    //   400: iconst_1
    //   401: if_icmpne +166 -> 567
    //   404: aload 11
    //   406: astore 15
    //   408: aload 13
    //   410: astore 14
    //   412: invokestatic 222	android/os/Parcel:obtain	()Landroid/os/Parcel;
    //   415: astore 18
    //   417: aload 19
    //   419: arraylength
    //   420: istore_3
    //   421: aload 18
    //   423: aload 19
    //   425: iconst_0
    //   426: iload_3
    //   427: invokevirtual 226	android/os/Parcel:unmarshall	([BII)V
    //   430: aload 18
    //   432: iconst_0
    //   433: invokevirtual 230	android/os/Parcel:setDataPosition	(I)V
    //   436: getstatic 256	com/google/android/android/measurement/internal/zzfh:CREATOR	Landroid/os/Parcelable$Creator;
    //   439: astore 14
    //   441: aload 14
    //   443: aload 18
    //   445: invokeinterface 242 2 0
    //   450: astore 14
    //   452: aload 14
    //   454: checkcast 255	com/google/android/android/measurement/internal/zzfh
    //   457: astore 16
    //   459: aload 11
    //   461: astore 15
    //   463: aload 13
    //   465: astore 14
    //   467: aload 18
    //   469: invokevirtual 245	android/os/Parcel:recycle	()V
    //   472: goto +37 -> 509
    //   475: astore 16
    //   477: goto +66 -> 543
    //   480: aload_0
    //   481: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   484: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   487: ldc_w 258
    //   490: invokevirtual 118	com/google/android/android/measurement/internal/zzar:zzbx	(Ljava/lang/String;)V
    //   493: aload 11
    //   495: astore 15
    //   497: aload 13
    //   499: astore 14
    //   501: aload 18
    //   503: invokevirtual 245	android/os/Parcel:recycle	()V
    //   506: aconst_null
    //   507: astore 16
    //   509: lload 7
    //   511: lstore 5
    //   513: aload 16
    //   515: ifnull -358 -> 157
    //   518: aload 11
    //   520: astore 15
    //   522: aload 13
    //   524: astore 14
    //   526: aload 17
    //   528: aload 16
    //   530: invokeinterface 251 2 0
    //   535: pop
    //   536: lload 7
    //   538: lstore 5
    //   540: goto -383 -> 157
    //   543: aload 11
    //   545: astore 15
    //   547: aload 13
    //   549: astore 14
    //   551: aload 18
    //   553: invokevirtual 245	android/os/Parcel:recycle	()V
    //   556: aload 11
    //   558: astore 15
    //   560: aload 13
    //   562: astore 14
    //   564: aload 16
    //   566: athrow
    //   567: iload_3
    //   568: iconst_2
    //   569: if_icmpne +166 -> 735
    //   572: aload 11
    //   574: astore 15
    //   576: aload 13
    //   578: astore 14
    //   580: invokestatic 222	android/os/Parcel:obtain	()Landroid/os/Parcel;
    //   583: astore 18
    //   585: aload 19
    //   587: arraylength
    //   588: istore_3
    //   589: aload 18
    //   591: aload 19
    //   593: iconst_0
    //   594: iload_3
    //   595: invokevirtual 226	android/os/Parcel:unmarshall	([BII)V
    //   598: aload 18
    //   600: iconst_0
    //   601: invokevirtual 230	android/os/Parcel:setDataPosition	(I)V
    //   604: getstatic 261	com/google/android/android/measurement/internal/ComponentInfo:CREATOR	Landroid/os/Parcelable$Creator;
    //   607: astore 14
    //   609: aload 14
    //   611: aload 18
    //   613: invokeinterface 242 2 0
    //   618: astore 14
    //   620: aload 14
    //   622: checkcast 260	com/google/android/android/measurement/internal/ComponentInfo
    //   625: astore 16
    //   627: aload 11
    //   629: astore 15
    //   631: aload 13
    //   633: astore 14
    //   635: aload 18
    //   637: invokevirtual 245	android/os/Parcel:recycle	()V
    //   640: goto +37 -> 677
    //   643: astore 16
    //   645: goto +66 -> 711
    //   648: aload_0
    //   649: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   652: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   655: ldc_w 258
    //   658: invokevirtual 118	com/google/android/android/measurement/internal/zzar:zzbx	(Ljava/lang/String;)V
    //   661: aload 11
    //   663: astore 15
    //   665: aload 13
    //   667: astore 14
    //   669: aload 18
    //   671: invokevirtual 245	android/os/Parcel:recycle	()V
    //   674: aconst_null
    //   675: astore 16
    //   677: lload 7
    //   679: lstore 5
    //   681: aload 16
    //   683: ifnull -526 -> 157
    //   686: aload 11
    //   688: astore 15
    //   690: aload 13
    //   692: astore 14
    //   694: aload 17
    //   696: aload 16
    //   698: invokeinterface 251 2 0
    //   703: pop
    //   704: lload 7
    //   706: lstore 5
    //   708: goto -551 -> 157
    //   711: aload 11
    //   713: astore 15
    //   715: aload 13
    //   717: astore 14
    //   719: aload 18
    //   721: invokevirtual 245	android/os/Parcel:recycle	()V
    //   724: aload 11
    //   726: astore 15
    //   728: aload 13
    //   730: astore 14
    //   732: aload 16
    //   734: athrow
    //   735: aload 11
    //   737: astore 15
    //   739: aload 13
    //   741: astore 14
    //   743: aload_0
    //   744: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   747: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   750: ldc_w 263
    //   753: invokevirtual 118	com/google/android/android/measurement/internal/zzar:zzbx	(Ljava/lang/String;)V
    //   756: lload 7
    //   758: lstore 5
    //   760: goto -603 -> 157
    //   763: aload 11
    //   765: astore 15
    //   767: aload 13
    //   769: astore 14
    //   771: lload 5
    //   773: invokestatic 124	java/lang/Long:toString	(J)Ljava/lang/String;
    //   776: astore 16
    //   778: aload 11
    //   780: astore 15
    //   782: aload 13
    //   784: astore 14
    //   786: aload 10
    //   788: ldc 126
    //   790: ldc_w 265
    //   793: iconst_1
    //   794: anewarray 130	java/lang/String
    //   797: dup
    //   798: iconst_0
    //   799: aload 16
    //   801: aastore
    //   802: invokevirtual 133	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   805: istore_3
    //   806: aload 11
    //   808: astore 15
    //   810: aload 13
    //   812: astore 14
    //   814: aload 17
    //   816: invokeinterface 269 1 0
    //   821: istore 4
    //   823: iload_3
    //   824: iload 4
    //   826: if_icmpge +24 -> 850
    //   829: aload 11
    //   831: astore 15
    //   833: aload 13
    //   835: astore 14
    //   837: aload_0
    //   838: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   841: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   844: ldc_w 271
    //   847: invokevirtual 118	com/google/android/android/measurement/internal/zzar:zzbx	(Ljava/lang/String;)V
    //   850: aload 11
    //   852: astore 15
    //   854: aload 13
    //   856: astore 14
    //   858: aload 10
    //   860: invokevirtual 149	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   863: aload 11
    //   865: astore 15
    //   867: aload 13
    //   869: astore 14
    //   871: aload 10
    //   873: invokevirtual 152	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   876: aload 12
    //   878: ifnull +10 -> 888
    //   881: aload 12
    //   883: invokeinterface 153 1 0
    //   888: aload 10
    //   890: ifnull +489 -> 1379
    //   893: aload 10
    //   895: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   898: aload 17
    //   900: areturn
    //   901: astore 10
    //   903: goto +101 -> 1004
    //   906: aload 12
    //   908: astore 11
    //   910: goto +216 -> 1126
    //   913: astore 14
    //   915: aload 10
    //   917: astore 11
    //   919: aload 12
    //   921: astore 10
    //   923: goto +259 -> 1182
    //   926: astore 12
    //   928: aload 11
    //   930: astore 10
    //   932: goto +58 -> 990
    //   935: astore 10
    //   937: goto +64 -> 1001
    //   940: astore 14
    //   942: goto +33 -> 975
    //   945: astore 12
    //   947: goto +43 -> 990
    //   950: astore 12
    //   952: aload 10
    //   954: astore 11
    //   956: aload 12
    //   958: astore 10
    //   960: goto +41 -> 1001
    //   963: aload 13
    //   965: astore 10
    //   967: aconst_null
    //   968: astore 11
    //   970: goto +156 -> 1126
    //   973: astore 14
    //   975: aload 13
    //   977: astore 11
    //   979: aconst_null
    //   980: astore 10
    //   982: goto +200 -> 1182
    //   985: astore 12
    //   987: aconst_null
    //   988: astore 10
    //   990: aconst_null
    //   991: astore 11
    //   993: goto +274 -> 1267
    //   996: astore 10
    //   998: aconst_null
    //   999: astore 11
    //   1001: aconst_null
    //   1002: astore 13
    //   1004: aload 11
    //   1006: ifnull +36 -> 1042
    //   1009: aload 11
    //   1011: astore 15
    //   1013: aload 13
    //   1015: astore 14
    //   1017: aload 11
    //   1019: invokevirtual 156	android/database/sqlite/SQLiteDatabase:inTransaction	()Z
    //   1022: istore 9
    //   1024: iload 9
    //   1026: ifeq +16 -> 1042
    //   1029: aload 11
    //   1031: astore 15
    //   1033: aload 13
    //   1035: astore 14
    //   1037: aload 11
    //   1039: invokevirtual 152	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1042: aload 11
    //   1044: astore 15
    //   1046: aload 13
    //   1048: astore 14
    //   1050: aload_0
    //   1051: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   1054: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   1057: ldc_w 273
    //   1060: aload 10
    //   1062: invokevirtual 161	com/google/android/android/measurement/internal/zzar:append	(Ljava/lang/String;Ljava/lang/Object;)V
    //   1065: aload 11
    //   1067: astore 15
    //   1069: aload 13
    //   1071: astore 14
    //   1073: aload_0
    //   1074: iconst_1
    //   1075: putfield 47	com/google/android/android/measurement/internal/zzal:zzalr	Z
    //   1078: aload 13
    //   1080: ifnull +10 -> 1090
    //   1083: aload 13
    //   1085: invokeinterface 153 1 0
    //   1090: iload_2
    //   1091: istore_3
    //   1092: aload 11
    //   1094: ifnull +150 -> 1244
    //   1097: aload 11
    //   1099: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   1102: iload_2
    //   1103: istore_3
    //   1104: goto +140 -> 1244
    //   1107: astore 12
    //   1109: aload 15
    //   1111: astore 10
    //   1113: aload 14
    //   1115: astore 11
    //   1117: goto +150 -> 1267
    //   1120: aconst_null
    //   1121: astore 11
    //   1123: aconst_null
    //   1124: astore 10
    //   1126: iload_2
    //   1127: i2l
    //   1128: lstore 5
    //   1130: lload 5
    //   1132: invokestatic 167	android/os/SystemClock:sleep	(J)V
    //   1135: iload_2
    //   1136: bipush 20
    //   1138: iadd
    //   1139: istore_2
    //   1140: aload 11
    //   1142: ifnull +10 -> 1152
    //   1145: aload 11
    //   1147: invokeinterface 153 1 0
    //   1152: iload_2
    //   1153: istore_3
    //   1154: aload 10
    //   1156: ifnull +88 -> 1244
    //   1159: aload 10
    //   1161: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   1164: iload_2
    //   1165: istore_3
    //   1166: goto +78 -> 1244
    //   1169: astore 12
    //   1171: goto +96 -> 1267
    //   1174: astore 14
    //   1176: aconst_null
    //   1177: astore 10
    //   1179: aconst_null
    //   1180: astore 11
    //   1182: aload 10
    //   1184: astore 12
    //   1186: aload 11
    //   1188: astore 13
    //   1190: aload_0
    //   1191: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   1194: invokevirtual 110	com/google/android/android/measurement/internal/zzap:zzjd	()Lcom/google/android/android/measurement/internal/zzar;
    //   1197: ldc_w 273
    //   1200: aload 14
    //   1202: invokevirtual 161	com/google/android/android/measurement/internal/zzar:append	(Ljava/lang/String;Ljava/lang/Object;)V
    //   1205: aload 10
    //   1207: astore 12
    //   1209: aload 11
    //   1211: astore 13
    //   1213: aload_0
    //   1214: iconst_1
    //   1215: putfield 47	com/google/android/android/measurement/internal/zzal:zzalr	Z
    //   1218: aload 10
    //   1220: ifnull +10 -> 1230
    //   1223: aload 10
    //   1225: invokeinterface 153 1 0
    //   1230: iload_2
    //   1231: istore_3
    //   1232: aload 11
    //   1234: ifnull +10 -> 1244
    //   1237: aload 11
    //   1239: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   1242: iload_2
    //   1243: istore_3
    //   1244: iload_1
    //   1245: iconst_1
    //   1246: iadd
    //   1247: istore_1
    //   1248: iload_3
    //   1249: istore_2
    //   1250: goto -1202 -> 48
    //   1253: astore 10
    //   1255: aload 12
    //   1257: astore 11
    //   1259: aload 10
    //   1261: astore 12
    //   1263: aload 13
    //   1265: astore 10
    //   1267: aload 11
    //   1269: ifnull +10 -> 1279
    //   1272: aload 11
    //   1274: invokeinterface 153 1 0
    //   1279: aload 10
    //   1281: ifnull +8 -> 1289
    //   1284: aload 10
    //   1286: invokevirtual 77	android/database/sqlite/SQLiteClosable:close	()V
    //   1289: aload 12
    //   1291: athrow
    //   1292: aload_0
    //   1293: invokevirtual 104	com/google/android/android/measurement/internal/zzco:zzgo	()Lcom/google/android/android/measurement/internal/zzap;
    //   1296: invokevirtual 170	com/google/android/android/measurement/internal/zzap:zzjg	()Lcom/google/android/android/measurement/internal/zzar;
    //   1299: ldc_w 275
    //   1302: invokevirtual 118	com/google/android/android/measurement/internal/zzar:zzbx	(Ljava/lang/String;)V
    //   1305: aconst_null
    //   1306: areturn
    //   1307: astore 10
    //   1309: goto -189 -> 1120
    //   1312: astore 10
    //   1314: goto -351 -> 963
    //   1317: astore 10
    //   1319: goto -356 -> 963
    //   1322: astore 11
    //   1324: goto -418 -> 906
    //   1327: astore 14
    //   1329: goto -986 -> 343
    //   1332: astore 11
    //   1334: goto -428 -> 906
    //   1337: astore 11
    //   1339: goto -433 -> 906
    //   1342: astore 11
    //   1344: goto -438 -> 906
    //   1347: astore 14
    //   1349: goto -869 -> 480
    //   1352: astore 11
    //   1354: goto -448 -> 906
    //   1357: astore 11
    //   1359: goto -453 -> 906
    //   1362: astore 14
    //   1364: goto -716 -> 648
    //   1367: astore 11
    //   1369: goto -463 -> 906
    //   1372: astore 11
    //   1374: goto -468 -> 906
    //   1377: aconst_null
    //   1378: areturn
    //   1379: aload 17
    //   1381: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1382	0	this	zzal
    //   0	1382	1	paramInt	int
    //   47	1203	2	i	int
    //   213	1036	3	j	int
    //   821	6	4	k	int
    //   155	976	5	l1	long
    //   195	562	7	l2	long
    //   172	853	9	bool	boolean
    //   64	830	10	localObject1	Object
    //   901	15	10	localSQLiteException1	SQLiteException
    //   921	10	10	localObject2	Object
    //   935	18	10	localSQLiteException2	SQLiteException
    //   958	31	10	localObject3	Object
    //   996	65	10	localSQLiteException3	SQLiteException
    //   1111	113	10	localObject4	Object
    //   1253	7	10	localThrowable1	Throwable
    //   1265	20	10	localObject5	Object
    //   1307	1	10	localSQLiteDatabaseLockedException1	android.database.sqlite.SQLiteDatabaseLockedException
    //   1312	1	10	localSQLiteDatabaseLockedException2	android.database.sqlite.SQLiteDatabaseLockedException
    //   1317	1	10	localSQLiteDatabaseLockedException3	android.database.sqlite.SQLiteDatabaseLockedException
    //   68	1205	11	localObject6	Object
    //   1322	1	11	localSQLiteDatabaseLockedException4	android.database.sqlite.SQLiteDatabaseLockedException
    //   1332	1	11	localSQLiteDatabaseLockedException5	android.database.sqlite.SQLiteDatabaseLockedException
    //   1337	1	11	localSQLiteDatabaseLockedException6	android.database.sqlite.SQLiteDatabaseLockedException
    //   1342	1	11	localSQLiteDatabaseLockedException7	android.database.sqlite.SQLiteDatabaseLockedException
    //   1352	1	11	localSQLiteDatabaseLockedException8	android.database.sqlite.SQLiteDatabaseLockedException
    //   1357	1	11	localSQLiteDatabaseLockedException9	android.database.sqlite.SQLiteDatabaseLockedException
    //   1367	1	11	localSQLiteDatabaseLockedException10	android.database.sqlite.SQLiteDatabaseLockedException
    //   1372	1	11	localSQLiteDatabaseLockedException11	android.database.sqlite.SQLiteDatabaseLockedException
    //   49	871	12	localObject7	Object
    //   926	1	12	localThrowable2	Throwable
    //   945	1	12	localThrowable3	Throwable
    //   950	7	12	localSQLiteException4	SQLiteException
    //   985	1	12	localThrowable4	Throwable
    //   1107	1	12	localThrowable5	Throwable
    //   1169	1	12	localThrowable6	Throwable
    //   1184	106	12	localObject8	Object
    //   60	1204	13	localObject9	Object
    //   163	707	14	localObject10	Object
    //   913	1	14	localSQLiteFullException1	android.database.sqlite.SQLiteFullException
    //   940	1	14	localSQLiteFullException2	android.database.sqlite.SQLiteFullException
    //   973	1	14	localSQLiteFullException3	android.database.sqlite.SQLiteFullException
    //   1015	99	14	localObject11	Object
    //   1174	27	14	localSQLiteFullException4	android.database.sqlite.SQLiteFullException
    //   1327	1	14	localParseException1	com.google.android.android.common.internal.safeparcel.SafeParcelReader.ParseException
    //   1347	1	14	localParseException2	com.google.android.android.common.internal.safeparcel.SafeParcelReader.ParseException
    //   1362	1	14	localParseException3	com.google.android.android.common.internal.safeparcel.SafeParcelReader.ParseException
    //   159	951	15	localObject12	Object
    //   247	211	16	localObject13	Object
    //   475	1	16	localThrowable7	Throwable
    //   507	119	16	localObject14	Object
    //   643	1	16	localThrowable8	Throwable
    //   675	125	16	localObject15	Object
    //   24	1356	17	localArrayList	java.util.ArrayList
    //   289	35	18	localZzad	zzad
    //   338	59	18	localThrowable9	Throwable
    //   415	305	18	localParcel	Parcel
    //   230	362	19	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   249	253	338	java/lang/Throwable
    //   253	268	338	java/lang/Throwable
    //   268	273	338	java/lang/Throwable
    //   273	284	338	java/lang/Throwable
    //   284	291	338	java/lang/Throwable
    //   343	355	338	java/lang/Throwable
    //   417	421	475	java/lang/Throwable
    //   421	436	475	java/lang/Throwable
    //   436	441	475	java/lang/Throwable
    //   441	452	475	java/lang/Throwable
    //   452	459	475	java/lang/Throwable
    //   480	493	475	java/lang/Throwable
    //   585	589	643	java/lang/Throwable
    //   589	604	643	java/lang/Throwable
    //   604	609	643	java/lang/Throwable
    //   609	620	643	java/lang/Throwable
    //   620	627	643	java/lang/Throwable
    //   648	661	643	java/lang/Throwable
    //   165	174	901	android/database/sqlite/SQLiteException
    //   187	197	901	android/database/sqlite/SQLiteException
    //   205	214	901	android/database/sqlite/SQLiteException
    //   222	232	901	android/database/sqlite/SQLiteException
    //   244	249	901	android/database/sqlite/SQLiteException
    //   299	304	901	android/database/sqlite/SQLiteException
    //   321	331	901	android/database/sqlite/SQLiteException
    //   363	368	901	android/database/sqlite/SQLiteException
    //   383	388	901	android/database/sqlite/SQLiteException
    //   396	399	901	android/database/sqlite/SQLiteException
    //   412	417	901	android/database/sqlite/SQLiteException
    //   467	472	901	android/database/sqlite/SQLiteException
    //   501	506	901	android/database/sqlite/SQLiteException
    //   526	536	901	android/database/sqlite/SQLiteException
    //   551	556	901	android/database/sqlite/SQLiteException
    //   564	567	901	android/database/sqlite/SQLiteException
    //   580	585	901	android/database/sqlite/SQLiteException
    //   635	640	901	android/database/sqlite/SQLiteException
    //   669	674	901	android/database/sqlite/SQLiteException
    //   694	704	901	android/database/sqlite/SQLiteException
    //   719	724	901	android/database/sqlite/SQLiteException
    //   732	735	901	android/database/sqlite/SQLiteException
    //   743	756	901	android/database/sqlite/SQLiteException
    //   771	778	901	android/database/sqlite/SQLiteException
    //   786	806	901	android/database/sqlite/SQLiteException
    //   814	823	901	android/database/sqlite/SQLiteException
    //   837	850	901	android/database/sqlite/SQLiteException
    //   858	863	901	android/database/sqlite/SQLiteException
    //   871	876	901	android/database/sqlite/SQLiteException
    //   165	174	913	android/database/sqlite/SQLiteFullException
    //   187	197	913	android/database/sqlite/SQLiteFullException
    //   205	214	913	android/database/sqlite/SQLiteFullException
    //   222	232	913	android/database/sqlite/SQLiteFullException
    //   244	249	913	android/database/sqlite/SQLiteFullException
    //   299	304	913	android/database/sqlite/SQLiteFullException
    //   321	331	913	android/database/sqlite/SQLiteFullException
    //   363	368	913	android/database/sqlite/SQLiteFullException
    //   383	388	913	android/database/sqlite/SQLiteFullException
    //   396	399	913	android/database/sqlite/SQLiteFullException
    //   412	417	913	android/database/sqlite/SQLiteFullException
    //   467	472	913	android/database/sqlite/SQLiteFullException
    //   501	506	913	android/database/sqlite/SQLiteFullException
    //   526	536	913	android/database/sqlite/SQLiteFullException
    //   551	556	913	android/database/sqlite/SQLiteFullException
    //   564	567	913	android/database/sqlite/SQLiteFullException
    //   580	585	913	android/database/sqlite/SQLiteFullException
    //   635	640	913	android/database/sqlite/SQLiteFullException
    //   669	674	913	android/database/sqlite/SQLiteFullException
    //   694	704	913	android/database/sqlite/SQLiteFullException
    //   719	724	913	android/database/sqlite/SQLiteFullException
    //   732	735	913	android/database/sqlite/SQLiteFullException
    //   743	756	913	android/database/sqlite/SQLiteFullException
    //   771	778	913	android/database/sqlite/SQLiteFullException
    //   786	806	913	android/database/sqlite/SQLiteFullException
    //   814	823	913	android/database/sqlite/SQLiteFullException
    //   837	850	913	android/database/sqlite/SQLiteFullException
    //   858	863	913	android/database/sqlite/SQLiteFullException
    //   871	876	913	android/database/sqlite/SQLiteFullException
    //   112	148	926	java/lang/Throwable
    //   112	148	935	android/database/sqlite/SQLiteException
    //   112	148	940	android/database/sqlite/SQLiteFullException
    //   96	101	945	java/lang/Throwable
    //   101	108	945	java/lang/Throwable
    //   96	101	950	android/database/sqlite/SQLiteException
    //   101	108	950	android/database/sqlite/SQLiteException
    //   96	101	973	android/database/sqlite/SQLiteFullException
    //   101	108	973	android/database/sqlite/SQLiteFullException
    //   56	62	985	java/lang/Throwable
    //   56	62	996	android/database/sqlite/SQLiteException
    //   165	174	1107	java/lang/Throwable
    //   187	197	1107	java/lang/Throwable
    //   205	214	1107	java/lang/Throwable
    //   222	232	1107	java/lang/Throwable
    //   244	249	1107	java/lang/Throwable
    //   299	304	1107	java/lang/Throwable
    //   321	331	1107	java/lang/Throwable
    //   363	368	1107	java/lang/Throwable
    //   383	388	1107	java/lang/Throwable
    //   396	399	1107	java/lang/Throwable
    //   412	417	1107	java/lang/Throwable
    //   467	472	1107	java/lang/Throwable
    //   501	506	1107	java/lang/Throwable
    //   526	536	1107	java/lang/Throwable
    //   551	556	1107	java/lang/Throwable
    //   564	567	1107	java/lang/Throwable
    //   580	585	1107	java/lang/Throwable
    //   635	640	1107	java/lang/Throwable
    //   669	674	1107	java/lang/Throwable
    //   694	704	1107	java/lang/Throwable
    //   719	724	1107	java/lang/Throwable
    //   732	735	1107	java/lang/Throwable
    //   743	756	1107	java/lang/Throwable
    //   771	778	1107	java/lang/Throwable
    //   786	806	1107	java/lang/Throwable
    //   814	823	1107	java/lang/Throwable
    //   837	850	1107	java/lang/Throwable
    //   858	863	1107	java/lang/Throwable
    //   871	876	1107	java/lang/Throwable
    //   1017	1024	1107	java/lang/Throwable
    //   1037	1042	1107	java/lang/Throwable
    //   1050	1065	1107	java/lang/Throwable
    //   1073	1078	1107	java/lang/Throwable
    //   1130	1135	1169	java/lang/Throwable
    //   56	62	1174	android/database/sqlite/SQLiteFullException
    //   79	84	1253	java/lang/Throwable
    //   1190	1205	1253	java/lang/Throwable
    //   1213	1218	1253	java/lang/Throwable
    //   56	62	1307	android/database/sqlite/SQLiteDatabaseLockedException
    //   96	101	1312	android/database/sqlite/SQLiteDatabaseLockedException
    //   101	108	1312	android/database/sqlite/SQLiteDatabaseLockedException
    //   112	148	1317	android/database/sqlite/SQLiteDatabaseLockedException
    //   165	174	1322	android/database/sqlite/SQLiteDatabaseLockedException
    //   187	197	1322	android/database/sqlite/SQLiteDatabaseLockedException
    //   205	214	1322	android/database/sqlite/SQLiteDatabaseLockedException
    //   222	232	1322	android/database/sqlite/SQLiteDatabaseLockedException
    //   244	249	1322	android/database/sqlite/SQLiteDatabaseLockedException
    //   253	268	1327	com/google/android/android/common/internal/safeparcel/SafeParcelReader$ParseException
    //   273	284	1327	com/google/android/android/common/internal/safeparcel/SafeParcelReader$ParseException
    //   299	304	1332	android/database/sqlite/SQLiteDatabaseLockedException
    //   321	331	1332	android/database/sqlite/SQLiteDatabaseLockedException
    //   363	368	1337	android/database/sqlite/SQLiteDatabaseLockedException
    //   383	388	1337	android/database/sqlite/SQLiteDatabaseLockedException
    //   396	399	1337	android/database/sqlite/SQLiteDatabaseLockedException
    //   412	417	1342	android/database/sqlite/SQLiteDatabaseLockedException
    //   421	436	1347	com/google/android/android/common/internal/safeparcel/SafeParcelReader$ParseException
    //   441	452	1347	com/google/android/android/common/internal/safeparcel/SafeParcelReader$ParseException
    //   467	472	1352	android/database/sqlite/SQLiteDatabaseLockedException
    //   501	506	1357	android/database/sqlite/SQLiteDatabaseLockedException
    //   526	536	1357	android/database/sqlite/SQLiteDatabaseLockedException
    //   551	556	1357	android/database/sqlite/SQLiteDatabaseLockedException
    //   564	567	1357	android/database/sqlite/SQLiteDatabaseLockedException
    //   580	585	1357	android/database/sqlite/SQLiteDatabaseLockedException
    //   589	604	1362	com/google/android/android/common/internal/safeparcel/SafeParcelReader$ParseException
    //   609	620	1362	com/google/android/android/common/internal/safeparcel/SafeParcelReader$ParseException
    //   635	640	1367	android/database/sqlite/SQLiteDatabaseLockedException
    //   669	674	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   694	704	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   719	724	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   732	735	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   743	756	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   771	778	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   786	806	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   814	823	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   837	850	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   858	863	1372	android/database/sqlite/SQLiteDatabaseLockedException
    //   871	876	1372	android/database/sqlite/SQLiteDatabaseLockedException
  }
  
  public final void resetAnalyticsData()
  {
    zzgb();
    zzaf();
    try
    {
      int i = getWritableDatabase().delete("messages", null, null);
      i += 0;
      if (i > 0)
      {
        zzgo().zzjl().append("Reset local analytics data. records", Integer.valueOf(i));
        return;
      }
    }
    catch (SQLiteException localSQLiteException)
    {
      zzgo().zzjd().append("Error resetting local analytics data. error", localSQLiteException);
    }
  }
  
  public final boolean rmdir(zzad paramZzad)
  {
    Parcel localParcel = Parcel.obtain();
    paramZzad.writeToParcel(localParcel, 0);
    paramZzad = localParcel.marshall();
    localParcel.recycle();
    if (paramZzad.length > 131072)
    {
      zzgo().zzjg().zzbx("Event is too long for local database. Sending event directly to service");
      return false;
    }
    return delete(0, paramZzad);
  }
  
  public final boolean rmdir(zzfh paramZzfh)
  {
    Parcel localParcel = Parcel.obtain();
    paramZzfh.writeToParcel(localParcel, 0);
    paramZzfh = localParcel.marshall();
    localParcel.recycle();
    if (paramZzfh.length > 131072)
    {
      zzgo().zzjg().zzbx("User property too long for local database. Sending directly to service");
      return false;
    }
    return delete(1, paramZzfh);
  }
  
  public final boolean updateRows(ComponentInfo paramComponentInfo)
  {
    zzgm();
    paramComponentInfo = zzfk.marshall(paramComponentInfo);
    if (paramComponentInfo.length > 131072)
    {
      zzgo().zzjg().zzbx("Conditional user property too long for local database. Sending directly to service");
      return false;
    }
    return delete(2, paramComponentInfo);
  }
  
  protected final boolean zzgt()
  {
    return false;
  }
}
