package com.google.firebase.database;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DatabaseError
{
  public static final int DATA_STALE = -1;
  public static final int DISCONNECTED = -4;
  public static final int EXPIRED_TOKEN = -6;
  public static final int INVALID_TOKEN = -7;
  public static final int MAX_RETRIES = -8;
  public static final int NETWORK_ERROR = -24;
  public static final int OPERATION_FAILED = -2;
  public static final int OVERRIDDEN_BY_SET = -9;
  public static final int PERMISSION_DENIED = -3;
  public static final int UNAVAILABLE = -10;
  public static final int UNKNOWN_ERROR = -999;
  public static final int USER_CODE_EXCEPTION = -11;
  public static final int WRITE_CANCELED = -25;
  private static final Map<String, Integer> errorCodes;
  private static final Map<Integer, String> errorReasons = new HashMap();
  private final int code;
  private final String details;
  private final String message;
  
  static
  {
    Object localObject1 = errorReasons;
    Integer localInteger1 = Integer.valueOf(-1);
    ((Map)localObject1).put(localInteger1, "The transaction needs to be run again with current data");
    Object localObject2 = errorReasons;
    localObject1 = Integer.valueOf(-2);
    ((Map)localObject2).put(localObject1, "The server indicated that this operation failed");
    Object localObject3 = errorReasons;
    localObject2 = Integer.valueOf(-3);
    ((Map)localObject3).put(localObject2, "This client does not have permission to perform this operation");
    Object localObject4 = errorReasons;
    localObject3 = Integer.valueOf(-4);
    ((Map)localObject4).put(localObject3, "The operation had to be aborted due to a network disconnect");
    Object localObject5 = errorReasons;
    localObject4 = Integer.valueOf(-6);
    ((Map)localObject5).put(localObject4, "The supplied auth token has expired");
    Map localMap = errorReasons;
    localObject5 = Integer.valueOf(-7);
    localMap.put(localObject5, "The supplied auth token was invalid");
    localMap = errorReasons;
    Integer localInteger2 = Integer.valueOf(-8);
    localMap.put(localInteger2, "The transaction had too many retries");
    errorReasons.put(Integer.valueOf(-9), "The transaction was overridden by a subsequent set");
    errorReasons.put(Integer.valueOf(-10), "The service is unavailable");
    errorReasons.put(Integer.valueOf(-11), "User code called from the Firebase Database runloop threw an exception:\n");
    errorReasons.put(Integer.valueOf(-24), "The operation could not be performed due to a network error");
    errorReasons.put(Integer.valueOf(-25), "The write was canceled by the user.");
    errorReasons.put(Integer.valueOf(64537), "An unknown error occurred");
    errorCodes = new HashMap();
    errorCodes.put("datastale", localInteger1);
    errorCodes.put("failure", localObject1);
    errorCodes.put("permission_denied", localObject2);
    errorCodes.put("disconnected", localObject3);
    errorCodes.put("expired_token", localObject4);
    errorCodes.put("invalid_token", localObject5);
    errorCodes.put("maxretries", localInteger2);
    errorCodes.put("overriddenbyset", Integer.valueOf(-9));
    errorCodes.put("unavailable", Integer.valueOf(-10));
    errorCodes.put("network_error", Integer.valueOf(-24));
    errorCodes.put("write_canceled", Integer.valueOf(-25));
  }
  
  private DatabaseError(int paramInt, String paramString)
  {
    this(paramInt, paramString, null);
  }
  
  private DatabaseError(int paramInt, String paramString1, String paramString2)
  {
    code = paramInt;
    message = paramString1;
    paramString1 = paramString2;
    if (paramString2 == null) {
      paramString1 = "";
    }
    details = paramString1;
  }
  
  public static DatabaseError fromCode(int paramInt)
  {
    if (errorReasons.containsKey(Integer.valueOf(paramInt))) {
      return new DatabaseError(paramInt, (String)errorReasons.get(Integer.valueOf(paramInt)), null);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid Firebase Database error code: ");
    localStringBuilder.append(paramInt);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public static DatabaseError fromException(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    paramThrowable = new StringBuilder();
    paramThrowable.append((String)errorReasons.get(Integer.valueOf(-11)));
    paramThrowable.append(localStringWriter.toString());
    return new DatabaseError(-11, paramThrowable.toString());
  }
  
  public static DatabaseError fromStatus(String paramString)
  {
    return fromStatus(paramString, null);
  }
  
  public static DatabaseError fromStatus(String paramString1, String paramString2)
  {
    return fromStatus(paramString1, paramString2, null);
  }
  
  public static DatabaseError fromStatus(String paramString1, String paramString2, String paramString3)
  {
    Object localObject = (Integer)errorCodes.get(paramString1.toLowerCase());
    paramString1 = (String)localObject;
    if (localObject == null) {
      paramString1 = Integer.valueOf(64537);
    }
    localObject = paramString2;
    if (paramString2 == null) {
      localObject = (String)errorReasons.get(paramString1);
    }
    return new DatabaseError(paramString1.intValue(), (String)localObject, paramString3);
  }
  
  public int getCode()
  {
    return code;
  }
  
  public String getDetails()
  {
    return details;
  }
  
  public String getMessage()
  {
    return message;
  }
  
  public DatabaseException toException()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Firebase Database error: ");
    localStringBuilder.append(message);
    return new DatabaseException(localStringBuilder.toString());
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DatabaseError: ");
    localStringBuilder.append(message);
    return localStringBuilder.toString();
  }
}
