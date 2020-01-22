package com.google.firebase.package_9;

import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.io.IOException;
import java.util.Map;

final class zzba
{
  private final zzaw zzaj;
  private int zzdl = 0;
  private final Map<Integer, TaskCompletionSource<Void>> zzdm = new ArrayMap();
  
  zzba(zzaw paramZzaw)
  {
    zzaj = paramZzaw;
  }
  
  /* Error */
  private final boolean processMessage(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 25	com/google/firebase/package_9/zzba:zzaj	Lcom/google/firebase/package_9/zzaw;
    //   6: astore_3
    //   7: aload_3
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 25	com/google/firebase/package_9/zzba:zzaj	Lcom/google/firebase/package_9/zzaw;
    //   13: invokevirtual 36	com/google/firebase/package_9/zzaw:zzak	()Ljava/lang/String;
    //   16: astore 4
    //   18: aload_1
    //   19: invokestatic 42	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   22: astore_2
    //   23: aload_2
    //   24: invokevirtual 46	java/lang/String:length	()I
    //   27: ifeq +13 -> 40
    //   30: ldc 48
    //   32: aload_2
    //   33: invokevirtual 52	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   36: astore_2
    //   37: goto +13 -> 50
    //   40: new 38	java/lang/String
    //   43: dup
    //   44: ldc 48
    //   46: invokespecial 55	java/lang/String:<init>	(Ljava/lang/String;)V
    //   49: astore_2
    //   50: aload 4
    //   52: aload_2
    //   53: invokevirtual 58	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   56: ifeq +59 -> 115
    //   59: aload_1
    //   60: invokestatic 42	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   63: astore_1
    //   64: aload_1
    //   65: invokevirtual 46	java/lang/String:length	()I
    //   68: ifeq +13 -> 81
    //   71: ldc 48
    //   73: aload_1
    //   74: invokevirtual 52	java/lang/String:concat	(Ljava/lang/String;)Ljava/lang/String;
    //   77: astore_1
    //   78: goto +13 -> 91
    //   81: new 38	java/lang/String
    //   84: dup
    //   85: ldc 48
    //   87: invokespecial 55	java/lang/String:<init>	(Ljava/lang/String;)V
    //   90: astore_1
    //   91: aload 4
    //   93: aload_1
    //   94: invokevirtual 46	java/lang/String:length	()I
    //   97: invokevirtual 62	java/lang/String:substring	(I)Ljava/lang/String;
    //   100: astore_1
    //   101: aload_0
    //   102: getfield 25	com/google/firebase/package_9/zzba:zzaj	Lcom/google/firebase/package_9/zzaw;
    //   105: aload_1
    //   106: invokevirtual 65	com/google/firebase/package_9/zzaw:setCurrentTheme	(Ljava/lang/String;)V
    //   109: aload_3
    //   110: monitorexit
    //   111: aload_0
    //   112: monitorexit
    //   113: iconst_1
    //   114: ireturn
    //   115: aload_3
    //   116: monitorexit
    //   117: aload_0
    //   118: monitorexit
    //   119: iconst_0
    //   120: ireturn
    //   121: astore_1
    //   122: aload_3
    //   123: monitorexit
    //   124: aload_1
    //   125: athrow
    //   126: astore_1
    //   127: aload_0
    //   128: monitorexit
    //   129: aload_1
    //   130: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	131	0	this	zzba
    //   0	131	1	paramString	String
    //   22	31	2	str1	String
    //   6	117	3	localZzaw	zzaw
    //   16	76	4	str2	String
    // Exception table:
    //   from	to	target	type
    //   9	37	121	java/lang/Throwable
    //   40	50	121	java/lang/Throwable
    //   50	78	121	java/lang/Throwable
    //   81	91	121	java/lang/Throwable
    //   91	111	121	java/lang/Throwable
    //   115	117	121	java/lang/Throwable
    //   122	124	121	java/lang/Throwable
    //   2	9	126	java/lang/Throwable
    //   124	126	126	java/lang/Throwable
  }
  
  private static boolean put(FirebaseInstanceId paramFirebaseInstanceId, String paramString)
  {
    Object localObject = paramString.split("!");
    if (localObject.length == 2)
    {
      paramString = localObject[0];
      localObject = localObject[1];
      int i = -1;
      try
      {
        int j = paramString.hashCode();
        boolean bool;
        if (j != 83)
        {
          if (j == 85)
          {
            bool = paramString.equals("U");
            if (bool) {
              i = 1;
            }
          }
        }
        else
        {
          bool = paramString.equals("S");
          if (bool) {
            i = 0;
          }
        }
        if (i != 0)
        {
          if (i != 1) {
            return true;
          }
          paramFirebaseInstanceId.createNote((String)localObject);
          bool = FirebaseInstanceId.get();
          if (bool)
          {
            Log.d("FirebaseInstanceId", "unsubscribe operation succeeded");
            return true;
          }
        }
        else
        {
          paramFirebaseInstanceId.sign((String)localObject);
          bool = FirebaseInstanceId.get();
          if (bool)
          {
            Log.d("FirebaseInstanceId", "subscribe operation succeeded");
            return true;
          }
        }
      }
      catch (IOException paramFirebaseInstanceId)
      {
        paramFirebaseInstanceId = String.valueOf(paramFirebaseInstanceId.getMessage());
        if (paramFirebaseInstanceId.length() != 0) {
          paramFirebaseInstanceId = "Topic sync failed: ".concat(paramFirebaseInstanceId);
        } else {
          paramFirebaseInstanceId = new String("Topic sync failed: ");
        }
        Log.e("FirebaseInstanceId", paramFirebaseInstanceId);
        return false;
      }
    }
    return true;
  }
  
  private final String zzar()
  {
    Object localObject = zzaj;
    try
    {
      String str = zzaj.zzak();
      if (!TextUtils.isEmpty(str))
      {
        localObject = str.split(",");
        if ((localObject.length > 1) && (!TextUtils.isEmpty(localObject[1]))) {
          return localObject[1];
        }
      }
      return null;
    }
    catch (Throwable localThrowable)
    {
      throw localThrowable;
    }
  }
  
  /* Error */
  final boolean doRun(FirebaseInstanceId paramFirebaseInstanceId)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 129	com/google/firebase/package_9/zzba:zzar	()Ljava/lang/String;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnonnull +21 -> 29
    //   11: invokestatic 95	com/google/firebase/package_9/FirebaseInstanceId:get	()Z
    //   14: ifeq +11 -> 25
    //   17: ldc 97
    //   19: ldc -125
    //   21: invokestatic 105	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   24: pop
    //   25: aload_0
    //   26: monitorexit
    //   27: iconst_1
    //   28: ireturn
    //   29: aload_0
    //   30: monitorexit
    //   31: aload_1
    //   32: aload_2
    //   33: invokestatic 133	com/google/firebase/package_9/zzba:put	(Lcom/google/firebase/package_9/FirebaseInstanceId;Ljava/lang/String;)Z
    //   36: ifne +5 -> 41
    //   39: iconst_0
    //   40: ireturn
    //   41: aload_0
    //   42: monitorenter
    //   43: aload_0
    //   44: getfield 23	com/google/firebase/package_9/zzba:zzdm	Ljava/util/Map;
    //   47: aload_0
    //   48: getfield 18	com/google/firebase/package_9/zzba:zzdl	I
    //   51: invokestatic 138	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   54: invokeinterface 144 2 0
    //   59: checkcast 146	com/google/android/android/tasks/TaskCompletionSource
    //   62: astore_3
    //   63: aload_0
    //   64: aload_2
    //   65: invokespecial 148	com/google/firebase/package_9/zzba:processMessage	(Ljava/lang/String;)Z
    //   68: pop
    //   69: aload_0
    //   70: aload_0
    //   71: getfield 18	com/google/firebase/package_9/zzba:zzdl	I
    //   74: iconst_1
    //   75: iadd
    //   76: putfield 18	com/google/firebase/package_9/zzba:zzdl	I
    //   79: aload_0
    //   80: monitorexit
    //   81: aload_3
    //   82: ifnull -82 -> 0
    //   85: aload_3
    //   86: aconst_null
    //   87: invokevirtual 152	com/google/android/android/tasks/TaskCompletionSource:setResult	(Ljava/lang/Object;)V
    //   90: goto -90 -> 0
    //   93: astore_1
    //   94: aload_0
    //   95: monitorexit
    //   96: aload_1
    //   97: athrow
    //   98: astore_1
    //   99: aload_0
    //   100: monitorexit
    //   101: goto +3 -> 104
    //   104: aload_1
    //   105: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	106	0	this	zzba
    //   0	106	1	paramFirebaseInstanceId	FirebaseInstanceId
    //   6	59	2	str	String
    //   62	24	3	localTaskCompletionSource	com.google.android.android.tasks.TaskCompletionSource
    // Exception table:
    //   from	to	target	type
    //   43	81	93	java/lang/Throwable
    //   94	96	93	java/lang/Throwable
    //   2	7	98	java/lang/Throwable
    //   11	25	98	java/lang/Throwable
    //   25	27	98	java/lang/Throwable
    //   29	31	98	java/lang/Throwable
    //   99	101	98	java/lang/Throwable
  }
  
  /* Error */
  final com.google.android.android.tasks.Task execute(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: astore 6
    //   5: aload_0
    //   6: getfield 25	com/google/firebase/package_9/zzba:zzaj	Lcom/google/firebase/package_9/zzaw;
    //   9: astore 8
    //   11: aload_0
    //   12: astore 5
    //   14: aload 5
    //   16: astore 6
    //   18: aload 8
    //   20: monitorenter
    //   21: aload 5
    //   23: getfield 25	com/google/firebase/package_9/zzba:zzaj	Lcom/google/firebase/package_9/zzaw;
    //   26: invokevirtual 36	com/google/firebase/package_9/zzaw:zzak	()Ljava/lang/String;
    //   29: astore 7
    //   31: aload 5
    //   33: getfield 25	com/google/firebase/package_9/zzba:zzaj	Lcom/google/firebase/package_9/zzaw;
    //   36: astore 6
    //   38: new 156	java/lang/StringBuilder
    //   41: dup
    //   42: aload 7
    //   44: invokestatic 42	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   47: invokevirtual 46	java/lang/String:length	()I
    //   50: iconst_1
    //   51: iadd
    //   52: aload_1
    //   53: invokestatic 42	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   56: invokevirtual 46	java/lang/String:length	()I
    //   59: iadd
    //   60: invokespecial 159	java/lang/StringBuilder:<init>	(I)V
    //   63: astore 9
    //   65: aload 9
    //   67: aload 7
    //   69: invokevirtual 163	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: pop
    //   73: aload 9
    //   75: ldc 48
    //   77: invokevirtual 163	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload 9
    //   83: aload_1
    //   84: invokevirtual 163	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: aload 6
    //   90: aload 9
    //   92: invokevirtual 166	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   95: invokevirtual 65	com/google/firebase/package_9/zzaw:setCurrentTheme	(Ljava/lang/String;)V
    //   98: aload 8
    //   100: monitorexit
    //   101: aload 5
    //   103: astore 6
    //   105: new 146	com/google/android/android/tasks/TaskCompletionSource
    //   108: dup
    //   109: invokespecial 167	com/google/android/android/tasks/TaskCompletionSource:<init>	()V
    //   112: astore_1
    //   113: aload 5
    //   115: astore 6
    //   117: aload 5
    //   119: getfield 23	com/google/firebase/package_9/zzba:zzdm	Ljava/util/Map;
    //   122: astore 8
    //   124: aload 5
    //   126: astore 6
    //   128: aload 7
    //   130: invokestatic 125	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   133: istore 4
    //   135: iload 4
    //   137: ifeq +8 -> 145
    //   140: iconst_0
    //   141: istore_2
    //   142: goto +20 -> 162
    //   145: aload 5
    //   147: astore 6
    //   149: aload 7
    //   151: ldc 48
    //   153: invokevirtual 75	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   156: arraylength
    //   157: istore_2
    //   158: iload_2
    //   159: iconst_1
    //   160: isub
    //   161: istore_2
    //   162: aload 5
    //   164: astore 6
    //   166: aload 5
    //   168: getfield 18	com/google/firebase/package_9/zzba:zzdl	I
    //   171: istore_3
    //   172: aload 5
    //   174: astore 6
    //   176: aload 8
    //   178: iload_3
    //   179: iload_2
    //   180: iadd
    //   181: invokestatic 138	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   184: aload_1
    //   185: invokeinterface 170 3 0
    //   190: pop
    //   191: aload 5
    //   193: astore 6
    //   195: aload_1
    //   196: invokevirtual 174	com/google/android/android/tasks/TaskCompletionSource:getTask	()Lcom/google/android/android/tasks/Task;
    //   199: astore_1
    //   200: aload 5
    //   202: monitorexit
    //   203: aload_1
    //   204: areturn
    //   205: astore_1
    //   206: aload 8
    //   208: monitorexit
    //   209: aload 5
    //   211: astore 6
    //   213: aload_1
    //   214: athrow
    //   215: astore_1
    //   216: aload 6
    //   218: monitorexit
    //   219: aload_1
    //   220: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	221	0	this	zzba
    //   0	221	1	paramString	String
    //   141	40	2	i	int
    //   171	10	3	j	int
    //   133	3	4	bool	boolean
    //   12	198	5	localZzba	zzba
    //   3	214	6	localObject1	Object
    //   29	121	7	str	String
    //   9	198	8	localObject2	Object
    //   63	28	9	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   21	101	205	java/lang/Throwable
    //   206	209	205	java/lang/Throwable
    //   5	11	215	java/lang/Throwable
    //   18	21	215	java/lang/Throwable
    //   105	113	215	java/lang/Throwable
    //   117	124	215	java/lang/Throwable
    //   128	135	215	java/lang/Throwable
    //   149	158	215	java/lang/Throwable
    //   166	172	215	java/lang/Throwable
    //   176	191	215	java/lang/Throwable
    //   195	200	215	java/lang/Throwable
    //   213	215	215	java/lang/Throwable
  }
  
  /* Error */
  final boolean zzaq()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 129	com/google/firebase/package_9/zzba:zzar	()Ljava/lang/String;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnull +9 -> 17
    //   11: iconst_1
    //   12: istore_1
    //   13: aload_0
    //   14: monitorexit
    //   15: iload_1
    //   16: ireturn
    //   17: iconst_0
    //   18: istore_1
    //   19: goto -6 -> 13
    //   22: astore_2
    //   23: aload_0
    //   24: monitorexit
    //   25: goto +3 -> 28
    //   28: aload_2
    //   29: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	30	0	this	zzba
    //   12	7	1	bool	boolean
    //   6	2	2	str	String
    //   22	7	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   2	7	22	java/lang/Throwable
  }
}
