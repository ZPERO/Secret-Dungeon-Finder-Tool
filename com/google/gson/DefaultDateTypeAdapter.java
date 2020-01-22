package com.google.gson;

import com.google.gson.internal.JavaVersion;
import com.google.gson.internal.PreJava9DateFormatProvider;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class DefaultDateTypeAdapter
  extends TypeAdapter<java.util.Date>
{
  private static final String SIMPLE_NAME = "DefaultDateTypeAdapter";
  private final List<DateFormat> dateFormats = new ArrayList();
  private final Class<? extends java.util.Date> dateType;
  
  public DefaultDateTypeAdapter(int paramInt1, int paramInt2)
  {
    this(java.util.Date.class, paramInt1, paramInt2);
  }
  
  DefaultDateTypeAdapter(Class paramClass)
  {
    dateType = verifyDateType(paramClass);
    dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
    if (!Locale.getDefault().equals(Locale.US)) {
      dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
    }
    if (JavaVersion.isJava9OrLater()) {
      dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
    }
  }
  
  DefaultDateTypeAdapter(Class paramClass, int paramInt)
  {
    dateType = verifyDateType(paramClass);
    dateFormats.add(DateFormat.getDateInstance(paramInt, Locale.US));
    if (!Locale.getDefault().equals(Locale.US)) {
      dateFormats.add(DateFormat.getDateInstance(paramInt));
    }
    if (JavaVersion.isJava9OrLater()) {
      dateFormats.add(PreJava9DateFormatProvider.getUSDateFormat(paramInt));
    }
  }
  
  public DefaultDateTypeAdapter(Class paramClass, int paramInt1, int paramInt2)
  {
    dateType = verifyDateType(paramClass);
    dateFormats.add(DateFormat.getDateTimeInstance(paramInt1, paramInt2, Locale.US));
    if (!Locale.getDefault().equals(Locale.US)) {
      dateFormats.add(DateFormat.getDateTimeInstance(paramInt1, paramInt2));
    }
    if (JavaVersion.isJava9OrLater()) {
      dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(paramInt1, paramInt2));
    }
  }
  
  DefaultDateTypeAdapter(Class paramClass, String paramString)
  {
    dateType = verifyDateType(paramClass);
    dateFormats.add(new SimpleDateFormat(paramString, Locale.US));
    if (!Locale.getDefault().equals(Locale.US)) {
      dateFormats.add(new SimpleDateFormat(paramString));
    }
  }
  
  /* Error */
  private java.util.Date deserializeToDate(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 32	com/google/gson/DefaultDateTypeAdapter:dateFormats	Ljava/util/List;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 32	com/google/gson/DefaultDateTypeAdapter:dateFormats	Ljava/util/List;
    //   11: invokeinterface 107 1 0
    //   16: astore_3
    //   17: aload_3
    //   18: invokeinterface 112 1 0
    //   23: ifeq +27 -> 50
    //   26: aload_3
    //   27: invokeinterface 116 1 0
    //   32: checkcast 46	java/text/DateFormat
    //   35: astore 4
    //   37: aload 4
    //   39: aload_1
    //   40: invokevirtual 119	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   43: astore 4
    //   45: aload_2
    //   46: monitorexit
    //   47: aload 4
    //   49: areturn
    //   50: aload_1
    //   51: new 121	java/text/ParsePosition
    //   54: dup
    //   55: iconst_0
    //   56: invokespecial 124	java/text/ParsePosition:<init>	(I)V
    //   59: invokestatic 129	com/google/gson/internal/bind/util/ISO8601Utils:parse	(Ljava/lang/String;Ljava/text/ParsePosition;)Ljava/util/Date;
    //   62: astore_3
    //   63: aload_2
    //   64: monitorexit
    //   65: aload_3
    //   66: areturn
    //   67: astore_3
    //   68: new 131	com/google/gson/JsonSyntaxException
    //   71: dup
    //   72: aload_1
    //   73: aload_3
    //   74: invokespecial 134	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   77: athrow
    //   78: astore_1
    //   79: aload_2
    //   80: monitorexit
    //   81: aload_1
    //   82: athrow
    //   83: astore 4
    //   85: goto -68 -> 17
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	88	0	this	DefaultDateTypeAdapter
    //   0	88	1	paramString	String
    //   4	76	2	localList	List
    //   16	50	3	localObject1	Object
    //   67	7	3	localParseException1	java.text.ParseException
    //   35	13	4	localObject2	Object
    //   83	1	4	localParseException2	java.text.ParseException
    // Exception table:
    //   from	to	target	type
    //   50	63	67	java/text/ParseException
    //   7	17	78	java/lang/Throwable
    //   17	37	78	java/lang/Throwable
    //   37	45	78	java/lang/Throwable
    //   45	47	78	java/lang/Throwable
    //   50	63	78	java/lang/Throwable
    //   63	65	78	java/lang/Throwable
    //   68	78	78	java/lang/Throwable
    //   79	81	78	java/lang/Throwable
    //   37	45	83	java/text/ParseException
  }
  
  private static Class verifyDateType(Class paramClass)
  {
    if ((paramClass != java.util.Date.class) && (paramClass != java.sql.Date.class))
    {
      if (paramClass == Timestamp.class) {
        return paramClass;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Date type must be one of ");
      localStringBuilder.append(java.util.Date.class);
      localStringBuilder.append(", ");
      localStringBuilder.append(Timestamp.class);
      localStringBuilder.append(", or ");
      localStringBuilder.append(java.sql.Date.class);
      localStringBuilder.append(" but was ");
      localStringBuilder.append(paramClass);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    return paramClass;
  }
  
  public java.util.Date read(JsonReader paramJsonReader)
    throws IOException
  {
    if (paramJsonReader.peek() == JsonToken.NULL)
    {
      paramJsonReader.nextNull();
      return null;
    }
    paramJsonReader = deserializeToDate(paramJsonReader.nextString());
    Class localClass = dateType;
    if (localClass == java.util.Date.class) {
      return paramJsonReader;
    }
    if (localClass == Timestamp.class) {
      return new Timestamp(paramJsonReader.getTime());
    }
    if (localClass == java.sql.Date.class) {
      return new java.sql.Date(paramJsonReader.getTime());
    }
    throw new AssertionError();
  }
  
  public String toString()
  {
    DateFormat localDateFormat = (DateFormat)dateFormats.get(0);
    if ((localDateFormat instanceof SimpleDateFormat))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("DefaultDateTypeAdapter(");
      localStringBuilder.append(((SimpleDateFormat)localDateFormat).toPattern());
      localStringBuilder.append(')');
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DefaultDateTypeAdapter(");
    localStringBuilder.append(localDateFormat.getClass().getSimpleName());
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
  
  public void write(JsonWriter paramJsonWriter, java.util.Date paramDate)
    throws IOException
  {
    if (paramDate == null)
    {
      paramJsonWriter.nullValue();
      return;
    }
    List localList = dateFormats;
    try
    {
      paramJsonWriter.value(((DateFormat)dateFormats.get(0)).format(paramDate));
      return;
    }
    catch (Throwable paramJsonWriter)
    {
      throw paramJsonWriter;
    }
  }
}
