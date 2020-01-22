package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.JavaVersion;
import com.google.gson.internal.PreJava9DateFormatProvider;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DateTypeAdapter
  extends TypeAdapter<Date>
{
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
  {
    public TypeAdapter create(Gson paramAnonymousGson, TypeToken paramAnonymousTypeToken)
    {
      if (paramAnonymousTypeToken.getRawType() == Date.class) {
        return new DateTypeAdapter();
      }
      return null;
    }
  };
  private final List<DateFormat> dateFormats = new ArrayList();
  
  public DateTypeAdapter()
  {
    dateFormats.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
    if (!Locale.getDefault().equals(Locale.US)) {
      dateFormats.add(DateFormat.getDateTimeInstance(2, 2));
    }
    if (JavaVersion.isJava9OrLater()) {
      dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(2, 2));
    }
  }
  
  /* Error */
  private Date deserializeToDate(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 26	com/google/gson/internal/bind/DateTypeAdapter:dateFormats	Ljava/util/List;
    //   6: invokeinterface 75 1 0
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface 80 1 0
    //   18: ifeq +23 -> 41
    //   21: aload_2
    //   22: invokeinterface 84 1 0
    //   27: checkcast 34	java/text/DateFormat
    //   30: astore_3
    //   31: aload_3
    //   32: aload_1
    //   33: invokevirtual 87	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   36: astore_3
    //   37: aload_0
    //   38: monitorexit
    //   39: aload_3
    //   40: areturn
    //   41: aload_1
    //   42: new 89	java/text/ParsePosition
    //   45: dup
    //   46: iconst_0
    //   47: invokespecial 92	java/text/ParsePosition:<init>	(I)V
    //   50: invokestatic 97	com/google/gson/internal/bind/util/ISO8601Utils:parse	(Ljava/lang/String;Ljava/text/ParsePosition;)Ljava/util/Date;
    //   53: astore_2
    //   54: aload_0
    //   55: monitorexit
    //   56: aload_2
    //   57: areturn
    //   58: astore_2
    //   59: new 99	com/google/gson/JsonSyntaxException
    //   62: dup
    //   63: aload_1
    //   64: aload_2
    //   65: invokespecial 102	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   68: athrow
    //   69: astore_1
    //   70: aload_0
    //   71: monitorexit
    //   72: aload_1
    //   73: athrow
    //   74: astore_3
    //   75: goto -63 -> 12
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	78	0	this	DateTypeAdapter
    //   0	78	1	paramString	String
    //   11	46	2	localObject1	Object
    //   58	7	2	localParseException1	java.text.ParseException
    //   30	10	3	localObject2	Object
    //   74	1	3	localParseException2	java.text.ParseException
    // Exception table:
    //   from	to	target	type
    //   41	54	58	java/text/ParseException
    //   2	12	69	java/lang/Throwable
    //   12	31	69	java/lang/Throwable
    //   31	37	69	java/lang/Throwable
    //   41	54	69	java/lang/Throwable
    //   59	69	69	java/lang/Throwable
    //   31	37	74	java/text/ParseException
  }
  
  public Date read(JsonReader paramJsonReader)
    throws IOException
  {
    if (paramJsonReader.peek() == JsonToken.NULL)
    {
      paramJsonReader.nextNull();
      return null;
    }
    return deserializeToDate(paramJsonReader.nextString());
  }
  
  public void write(JsonWriter paramJsonWriter, Date paramDate)
    throws IOException
  {
    if (paramDate == null) {}
    try
    {
      paramJsonWriter.nullValue();
      return;
    }
    catch (Throwable paramJsonWriter)
    {
      throw paramJsonWriter;
    }
    paramJsonWriter.value(((DateFormat)dateFormats.get(0)).format(paramDate));
  }
}
