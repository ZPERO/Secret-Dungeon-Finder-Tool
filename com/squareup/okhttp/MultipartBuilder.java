package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okio.BufferedSink;
import okio.ByteString;

public final class MultipartBuilder
{
  public static final MediaType ALTERNATIVE;
  private static final byte[] COLONSPACE = { 58, 32 };
  private static final byte[] CRLF = { 13, 10 };
  private static final byte[] DASHDASH = { 45, 45 };
  public static final MediaType DIGEST;
  public static final MediaType FORM;
  public static final MediaType MIXED = MediaType.parse("multipart/mixed");
  public static final MediaType PARALLEL;
  private final ByteString boundary;
  private final List<RequestBody> partBodies = new ArrayList();
  private final List<Headers> partHeaders = new ArrayList();
  private MediaType type = MIXED;
  
  static
  {
    ALTERNATIVE = MediaType.parse("multipart/alternative");
    DIGEST = MediaType.parse("multipart/digest");
    PARALLEL = MediaType.parse("multipart/parallel");
    FORM = MediaType.parse("multipart/form-data");
  }
  
  public MultipartBuilder()
  {
    this(UUID.randomUUID().toString());
  }
  
  public MultipartBuilder(String paramString)
  {
    boundary = ByteString.encodeUtf8(paramString);
  }
  
  private static StringBuilder appendQuotedString(StringBuilder paramStringBuilder, String paramString)
  {
    paramStringBuilder.append('"');
    int j = paramString.length();
    int i = 0;
    while (i < j)
    {
      char c = paramString.charAt(i);
      if (c != '\n')
      {
        if (c != '\r')
        {
          if (c != '"') {
            paramStringBuilder.append(c);
          } else {
            paramStringBuilder.append("%22");
          }
        }
        else {
          paramStringBuilder.append("%0D");
        }
      }
      else {
        paramStringBuilder.append("%0A");
      }
      i += 1;
    }
    paramStringBuilder.append('"');
    return paramStringBuilder;
  }
  
  public MultipartBuilder addFormDataPart(String paramString1, String paramString2)
  {
    return addFormDataPart(paramString1, null, RequestBody.create(null, paramString2));
  }
  
  public MultipartBuilder addFormDataPart(String paramString1, String paramString2, RequestBody paramRequestBody)
  {
    if (paramString1 != null)
    {
      StringBuilder localStringBuilder = new StringBuilder("form-data; name=");
      appendQuotedString(localStringBuilder, paramString1);
      if (paramString2 != null)
      {
        localStringBuilder.append("; filename=");
        appendQuotedString(localStringBuilder, paramString2);
      }
      return addPart(Headers.of(new String[] { "Content-Disposition", localStringBuilder.toString() }), paramRequestBody);
    }
    throw new NullPointerException("name == null");
  }
  
  public MultipartBuilder addPart(Headers paramHeaders, RequestBody paramRequestBody)
  {
    if (paramRequestBody != null)
    {
      if ((paramHeaders != null) && (paramHeaders.get("Content-Type") != null)) {
        throw new IllegalArgumentException("Unexpected header: Content-Type");
      }
      if ((paramHeaders != null) && (paramHeaders.get("Content-Length") != null)) {
        throw new IllegalArgumentException("Unexpected header: Content-Length");
      }
      partHeaders.add(paramHeaders);
      partBodies.add(paramRequestBody);
      return this;
    }
    throw new NullPointerException("body == null");
  }
  
  public MultipartBuilder addPart(RequestBody paramRequestBody)
  {
    return addPart(null, paramRequestBody);
  }
  
  public RequestBody build()
  {
    if (!partHeaders.isEmpty()) {
      return new MultipartRequestBody(type, boundary, partHeaders, partBodies);
    }
    throw new IllegalStateException("Multipart body must have at least one part.");
  }
  
  public MultipartBuilder type(MediaType paramMediaType)
  {
    if (paramMediaType != null)
    {
      if (paramMediaType.type().equals("multipart"))
      {
        type = paramMediaType;
        return this;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("multipart != ");
      localStringBuilder.append(paramMediaType);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    throw new NullPointerException("type == null");
  }
  
  private static final class MultipartRequestBody
    extends RequestBody
  {
    private final ByteString boundary;
    private long contentLength = -1L;
    private final MediaType contentType;
    private final List<RequestBody> partBodies;
    private final List<Headers> partHeaders;
    
    public MultipartRequestBody(MediaType paramMediaType, ByteString paramByteString, List paramList1, List paramList2)
    {
      if (paramMediaType != null)
      {
        boundary = paramByteString;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramMediaType);
        localStringBuilder.append("; boundary=");
        localStringBuilder.append(paramByteString.utf8());
        contentType = MediaType.parse(localStringBuilder.toString());
        partHeaders = Util.immutableList(paramList1);
        partBodies = Util.immutableList(paramList2);
        return;
      }
      throw new NullPointerException("type == null");
    }
    
    private long writeOrCountBytes(BufferedSink paramBufferedSink, boolean paramBoolean)
      throws IOException
    {
      throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a4 = a3\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n");
    }
    
    public long contentLength()
      throws IOException
    {
      long l = contentLength;
      if (l != -1L) {
        return l;
      }
      l = writeOrCountBytes(null, true);
      contentLength = l;
      return l;
    }
    
    public MediaType contentType()
    {
      return contentType;
    }
    
    public void writeTo(BufferedSink paramBufferedSink)
      throws IOException
    {
      writeOrCountBytes(paramBufferedSink, false);
    }
  }
}
