package com.squareup.okhttp.internal.framed;

import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http2
  implements Variant
{
  private static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
  static final byte FLAG_ACK = 1;
  static final byte FLAG_COMPRESSED = 32;
  static final byte FLAG_END_HEADERS = 4;
  static final byte FLAG_END_PUSH_PROMISE = 4;
  static final byte FLAG_END_STREAM = 1;
  static final byte FLAG_NONE = 0;
  static final byte FLAG_PADDED = 8;
  static final byte FLAG_PRIORITY = 32;
  static final int INITIAL_MAX_FRAME_SIZE = 16384;
  static final byte TYPE_CONTINUATION = 9;
  static final byte TYPE_DATA = 0;
  static final byte TYPE_GOAWAY = 7;
  static final byte TYPE_HEADERS = 1;
  static final byte TYPE_PING = 6;
  static final byte TYPE_PRIORITY = 2;
  static final byte TYPE_PUSH_PROMISE = 5;
  static final byte TYPE_RST_STREAM = 3;
  static final byte TYPE_SETTINGS = 4;
  static final byte TYPE_WINDOW_UPDATE = 8;
  private static final Logger logger = Logger.getLogger(FrameLogger.class.getName());
  
  public Http2() {}
  
  private static IllegalArgumentException illegalArgument(String paramString, Object... paramVarArgs)
  {
    throw new IllegalArgumentException(String.format(paramString, paramVarArgs));
  }
  
  private static IOException ioException(String paramString, Object... paramVarArgs)
    throws IOException
  {
    throw new IOException(String.format(paramString, paramVarArgs));
  }
  
  private static int lengthWithoutPadding(int paramInt, byte paramByte, short paramShort)
    throws IOException
  {
    short s = paramInt;
    if ((paramByte & 0x8) != 0) {
      s = paramInt - 1;
    }
    if (paramShort <= s) {
      return (short)(s - paramShort);
    }
    throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", new Object[] { Short.valueOf(paramShort), Integer.valueOf(s) });
  }
  
  private static int readMedium(BufferedSource paramBufferedSource)
    throws IOException
  {
    int i = paramBufferedSource.readByte();
    int j = paramBufferedSource.readByte();
    return paramBufferedSource.readByte() & 0xFF | (i & 0xFF) << 16 | (j & 0xFF) << 8;
  }
  
  private static void writeMedium(BufferedSink paramBufferedSink, int paramInt)
    throws IOException
  {
    paramBufferedSink.writeByte(paramInt >>> 16 & 0xFF);
    paramBufferedSink.writeByte(paramInt >>> 8 & 0xFF);
    paramBufferedSink.writeByte(paramInt & 0xFF);
  }
  
  public Protocol getProtocol()
  {
    return Protocol.HTTP_2;
  }
  
  public FrameReader newReader(BufferedSource paramBufferedSource, boolean paramBoolean)
  {
    return new Reader(paramBufferedSource, 4096, paramBoolean);
  }
  
  public FrameWriter newWriter(BufferedSink paramBufferedSink, boolean paramBoolean)
  {
    return new Writer(paramBufferedSink, paramBoolean);
  }
  
  static final class ContinuationSource
    implements Source
  {
    byte flags;
    int left;
    int length;
    short padding;
    private final BufferedSource source;
    int streamId;
    
    public ContinuationSource(BufferedSource paramBufferedSource)
    {
      source = paramBufferedSource;
    }
    
    private void readContinuationHeader()
      throws IOException
    {
      int i = streamId;
      int j = Http2.readMedium(source);
      left = j;
      length = j;
      byte b = (byte)(source.readByte() & 0xFF);
      flags = ((byte)(source.readByte() & 0xFF));
      if (Http2.logger.isLoggable(Level.FINE)) {
        Http2.logger.fine(Http2.FrameLogger.formatHeader(true, streamId, length, b, flags));
      }
      streamId = (source.readInt() & 0x7FFFFFFF);
      if (b == 9)
      {
        if (streamId == i) {
          return;
        }
        throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
      }
      throw Http2.ioException("%s != TYPE_CONTINUATION", new Object[] { Byte.valueOf(b) });
    }
    
    public void close()
      throws IOException
    {}
    
    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      int i;
      for (;;)
      {
        i = left;
        if (i != 0) {
          break;
        }
        source.skip(padding);
        padding = 0;
        if ((flags & 0x4) != 0) {
          return -1L;
        }
        readContinuationHeader();
      }
      paramLong = source.read(paramBuffer, Math.min(paramLong, i));
      if (paramLong == -1L) {
        return -1L;
      }
      left = ((int)(left - paramLong));
      return paramLong;
    }
    
    public Timeout timeout()
    {
      return source.timeout();
    }
  }
  
  static final class FrameLogger
  {
    private static final String[] BINARY;
    private static final String[] FLAGS;
    private static final String[] TYPES;
    
    static
    {
      int k = 0;
      TYPES = new String[] { "DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION" };
      FLAGS = new String[64];
      BINARY = new String['?'];
      int i = 0;
      for (;;)
      {
        localObject1 = BINARY;
        if (i >= localObject1.length) {
          break;
        }
        localObject1[i] = String.format("%8s", new Object[] { Integer.toBinaryString(i) }).replace(' ', '0');
        i += 1;
      }
      Object localObject2 = FLAGS;
      localObject2[0] = "";
      localObject2[1] = "END_STREAM";
      Object localObject1 = new int[1];
      localObject1[0] = 1;
      localObject2[8] = "PADDED";
      int j = localObject1.length;
      i = 0;
      Object localObject3;
      while (i < j)
      {
        m = localObject1[i];
        localObject2 = FLAGS;
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append(FLAGS[m]);
        ((StringBuilder)localObject3).append("|PADDED");
        localObject2[(m | 0x8)] = ((StringBuilder)localObject3).toString();
        i += 1;
      }
      localObject2 = FLAGS;
      localObject2[4] = "END_HEADERS";
      localObject2[32] = "PRIORITY";
      localObject2[36] = "END_HEADERS|PRIORITY";
      localObject2 = new int[3];
      Object tmp263_261 = localObject2;
      tmp263_261[0] = 4;
      Object tmp267_263 = tmp263_261;
      tmp267_263[1] = 32;
      Object tmp272_267 = tmp267_263;
      tmp272_267[2] = 36;
      tmp272_267;
      int m = localObject2.length;
      i = 0;
      for (;;)
      {
        j = k;
        if (i >= m) {
          break;
        }
        int n = localObject2[i];
        int i1 = localObject1.length;
        j = 0;
        while (j < i1)
        {
          int i2 = localObject1[j];
          localObject3 = FLAGS;
          int i3 = i2 | n;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(FLAGS[i2]);
          localStringBuilder.append('|');
          localStringBuilder.append(FLAGS[n]);
          localObject3[i3] = localStringBuilder.toString();
          localObject3 = FLAGS;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append(FLAGS[i2]);
          localStringBuilder.append('|');
          localStringBuilder.append(FLAGS[n]);
          localStringBuilder.append("|PADDED");
          localObject3[(i3 | 0x8)] = localStringBuilder.toString();
          j += 1;
        }
        i += 1;
      }
      for (;;)
      {
        localObject1 = FLAGS;
        if (j >= localObject1.length) {
          break;
        }
        if (localObject1[j] == null) {
          localObject1[j] = BINARY[j];
        }
        j += 1;
      }
    }
    
    FrameLogger() {}
    
    static String formatFlags(byte paramByte1, byte paramByte2)
    {
      if (paramByte2 == 0) {
        return "";
      }
      if ((paramByte1 != 2) && (paramByte1 != 3)) {
        if ((paramByte1 != 4) && (paramByte1 != 6))
        {
          if ((paramByte1 != 7) && (paramByte1 != 8))
          {
            Object localObject1 = FLAGS;
            if (paramByte2 < localObject1.length) {
              localObject1 = localObject1[paramByte2];
            } else {
              localObject1 = BINARY[paramByte2];
            }
            if ((paramByte1 == 5) && ((paramByte2 & 0x4) != 0)) {
              return ((String)localObject1).replace("HEADERS", "PUSH_PROMISE");
            }
            localObject2 = localObject1;
            if (paramByte1 == 0)
            {
              localObject2 = localObject1;
              if ((paramByte2 & 0x20) == 0) {
                return localObject2;
              }
              localObject2 = ((String)localObject1).replace("PRIORITY", "COMPRESSED");
            }
            return localObject2;
          }
        }
        else
        {
          if (paramByte2 == 1) {
            return "ACK";
          }
          return BINARY[paramByte2];
        }
      }
      Object localObject2 = BINARY[paramByte2];
      return localObject2;
    }
    
    static String formatHeader(boolean paramBoolean, int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
    {
      Object localObject = TYPES;
      if (paramByte1 < localObject.length) {
        localObject = localObject[paramByte1];
      } else {
        localObject = String.format("0x%02x", new Object[] { Byte.valueOf(paramByte1) });
      }
      String str2 = formatFlags(paramByte1, paramByte2);
      String str1;
      if (paramBoolean) {
        str1 = "<<";
      } else {
        str1 = ">>";
      }
      return String.format("%s 0x%08x %5d %-13s %s", new Object[] { str1, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), localObject, str2 });
    }
  }
  
  static final class Reader
    implements FrameReader
  {
    private final boolean client;
    private final Http2.ContinuationSource continuation;
    final Hpack.Reader hpackReader;
    private final BufferedSource source;
    
    Reader(BufferedSource paramBufferedSource, int paramInt, boolean paramBoolean)
    {
      source = paramBufferedSource;
      client = paramBoolean;
      continuation = new Http2.ContinuationSource(source);
      hpackReader = new Hpack.Reader(paramInt, continuation);
    }
    
    private void readData(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      int i = 1;
      short s = 0;
      boolean bool;
      if ((paramByte & 0x1) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      if ((paramByte & 0x20) == 0) {
        i = 0;
      }
      if (i == 0)
      {
        if ((paramByte & 0x8) != 0) {
          s = (short)(source.readByte() & 0xFF);
        }
        paramInt1 = Http2.lengthWithoutPadding(paramInt1, paramByte, s);
        paramHandler.data(bool, paramInt2, source, paramInt1);
        source.skip(s);
        return;
      }
      throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
    }
    
    private void readGoAway(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt1 >= 8)
      {
        if (paramInt2 == 0)
        {
          paramByte = source.readInt();
          paramInt2 = source.readInt();
          paramInt1 -= 8;
          ErrorCode localErrorCode = ErrorCode.fromHttp2(paramInt2);
          if (localErrorCode != null)
          {
            ByteString localByteString = ByteString.EMPTY;
            if (paramInt1 > 0) {
              localByteString = source.readByteString(paramInt1);
            }
            paramHandler.goAway(paramByte, localErrorCode, localByteString);
            return;
          }
          throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", new Object[] { Integer.valueOf(paramInt2) });
        }
        throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
      }
      throw Http2.ioException("TYPE_GOAWAY length < 8: %s", new Object[] { Integer.valueOf(paramInt1) });
    }
    
    private List readHeaderBlock(int paramInt1, short paramShort, byte paramByte, int paramInt2)
      throws IOException
    {
      Http2.ContinuationSource localContinuationSource = continuation;
      left = paramInt1;
      length = paramInt1;
      padding = paramShort;
      flags = paramByte;
      streamId = paramInt2;
      hpackReader.readHeaders();
      return hpackReader.getAndResetHeaderList();
    }
    
    private void readHeaders(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      short s = 0;
      if (paramInt2 != 0)
      {
        boolean bool;
        if ((paramByte & 0x1) != 0) {
          bool = true;
        } else {
          bool = false;
        }
        if ((paramByte & 0x8) != 0) {
          s = (short)(source.readByte() & 0xFF);
        }
        int i = paramInt1;
        if ((paramByte & 0x20) != 0)
        {
          readPriority(paramHandler, paramInt2);
          i = paramInt1 - 5;
        }
        paramHandler.headers(false, bool, paramInt2, -1, readHeaderBlock(Http2.lengthWithoutPadding(i, paramByte, s), s, paramByte, paramInt2), HeadersMode.HTTP_20_HEADERS);
        return;
      }
      throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
    }
    
    private void readPing(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      boolean bool = false;
      if (paramInt1 == 8)
      {
        if (paramInt2 == 0)
        {
          paramInt1 = source.readInt();
          paramInt2 = source.readInt();
          if ((paramByte & 0x1) != 0) {
            bool = true;
          }
          paramHandler.ping(bool, paramInt1, paramInt2);
          return;
        }
        throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
      }
      throw Http2.ioException("TYPE_PING length != 8: %s", new Object[] { Integer.valueOf(paramInt1) });
    }
    
    private void readPriority(FrameReader.Handler paramHandler, int paramInt)
      throws IOException
    {
      int i = source.readInt();
      boolean bool;
      if ((0x80000000 & i) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      paramHandler.priority(paramInt, i & 0x7FFFFFFF, (source.readByte() & 0xFF) + 1, bool);
    }
    
    private void readPriority(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt1 == 5)
      {
        if (paramInt2 != 0)
        {
          readPriority(paramHandler, paramInt2);
          return;
        }
        throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
      }
      throw Http2.ioException("TYPE_PRIORITY length: %d != 5", new Object[] { Integer.valueOf(paramInt1) });
    }
    
    private void readPushPromise(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      short s = 0;
      if (paramInt2 != 0)
      {
        if ((paramByte & 0x8) != 0) {
          s = (short)(source.readByte() & 0xFF);
        }
        paramHandler.pushPromise(paramInt2, source.readInt() & 0x7FFFFFFF, readHeaderBlock(Http2.lengthWithoutPadding(paramInt1 - 4, paramByte, s), s, paramByte, paramInt2));
        return;
      }
      throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
    }
    
    private void readRstStream(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt1 == 4)
      {
        if (paramInt2 != 0)
        {
          paramInt1 = source.readInt();
          ErrorCode localErrorCode = ErrorCode.fromHttp2(paramInt1);
          if (localErrorCode != null)
          {
            paramHandler.rstStream(paramInt2, localErrorCode);
            return;
          }
          throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", new Object[] { Integer.valueOf(paramInt1) });
        }
        throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
      }
      throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", new Object[] { Integer.valueOf(paramInt1) });
    }
    
    private void readSettings(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt2 == 0)
      {
        if ((paramByte & 0x1) != 0)
        {
          if (paramInt1 == 0)
          {
            paramHandler.ackSettings();
            return;
          }
          throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
        }
        if (paramInt1 % 6 == 0)
        {
          Settings localSettings = new Settings();
          paramInt2 = 0;
          while (paramInt2 < paramInt1)
          {
            byte b1 = source.readShort();
            byte b2 = b1;
            int i = source.readInt();
            paramByte = b2;
            switch (b1)
            {
            default: 
              throw Http2.ioException("PROTOCOL_ERROR invalid settings id: %s", new Object[] { Short.valueOf(b1) });
            case 5: 
              if ((i >= 16384) && (i <= 16777215)) {
                paramByte = b2;
              } else {
                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", new Object[] { Integer.valueOf(i) });
              }
              break;
            case 4: 
              paramByte = 7;
              if (i < 0) {
                throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
              }
              break;
            case 3: 
              paramByte = 4;
              break;
            case 2: 
              paramByte = b2;
              if (i != 0) {
                if (i == 1) {
                  paramByte = b2;
                } else {
                  throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                }
              }
              break;
            }
            localSettings.set(paramByte, 0, i);
            paramInt2 += 6;
          }
          paramHandler.settings(false, localSettings);
          if (localSettings.getHeaderTableSize() >= 0) {
            hpackReader.headerTableSizeSetting(localSettings.getHeaderTableSize());
          }
        }
        else
        {
          throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", new Object[] { Integer.valueOf(paramInt1) });
        }
      }
      else
      {
        paramHandler = Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
        throw paramHandler;
      }
    }
    
    private void readWindowUpdate(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt1 == 4)
      {
        long l = source.readInt() & 0x7FFFFFFF;
        if (l != 0L)
        {
          paramHandler.windowUpdate(paramInt2, l);
          return;
        }
        throw Http2.ioException("windowSizeIncrement was 0", new Object[] { Long.valueOf(l) });
      }
      throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", new Object[] { Integer.valueOf(paramInt1) });
    }
    
    public void close()
      throws IOException
    {
      source.close();
    }
    
    public boolean nextFrame(FrameReader.Handler paramHandler)
      throws IOException
    {
      BufferedSource localBufferedSource = source;
      try
      {
        localBufferedSource.require(9L);
        int i = Http2.readMedium(source);
        if ((i >= 0) && (i <= 16384))
        {
          byte b1 = (byte)(source.readByte() & 0xFF);
          byte b2 = (byte)(source.readByte() & 0xFF);
          int j = source.readInt() & 0x7FFFFFFF;
          if (Http2.logger.isLoggable(Level.FINE)) {
            Http2.logger.fine(Http2.FrameLogger.formatHeader(true, j, i, b1, b2));
          }
          switch (b1)
          {
          default: 
            source.skip(i);
            return true;
          case 8: 
            readWindowUpdate(paramHandler, i, b2, j);
            return true;
          case 7: 
            readGoAway(paramHandler, i, b2, j);
            return true;
          case 6: 
            readPing(paramHandler, i, b2, j);
            return true;
          case 5: 
            readPushPromise(paramHandler, i, b2, j);
            return true;
          case 4: 
            readSettings(paramHandler, i, b2, j);
            return true;
          case 3: 
            readRstStream(paramHandler, i, b2, j);
            return true;
          case 2: 
            readPriority(paramHandler, i, b2, j);
            return true;
          case 1: 
            readHeaders(paramHandler, i, b2, j);
            return true;
          }
          readData(paramHandler, i, b2, j);
          return true;
        }
        throw Http2.ioException("FRAME_SIZE_ERROR: %s", new Object[] { Integer.valueOf(i) });
      }
      catch (IOException paramHandler) {}
      return false;
    }
    
    public void readConnectionPreface()
      throws IOException
    {
      if (client) {
        return;
      }
      ByteString localByteString = source.readByteString(Http2.CONNECTION_PREFACE.size());
      if (Http2.logger.isLoggable(Level.FINE)) {
        Http2.logger.fine(String.format("<< CONNECTION %s", new Object[] { localByteString.hex() }));
      }
      if (Http2.CONNECTION_PREFACE.equals(localByteString)) {
        return;
      }
      throw Http2.ioException("Expected a connection header but was %s", new Object[] { localByteString.utf8() });
    }
  }
  
  static final class Writer
    implements FrameWriter
  {
    private final boolean client;
    private boolean closed;
    private final Buffer hpackBuffer;
    private final Hpack.Writer hpackWriter;
    private int maxFrameSize;
    private final BufferedSink sink;
    
    Writer(BufferedSink paramBufferedSink, boolean paramBoolean)
    {
      sink = paramBufferedSink;
      client = paramBoolean;
      hpackBuffer = new Buffer();
      hpackWriter = new Hpack.Writer(hpackBuffer);
      maxFrameSize = 16384;
    }
    
    private void writeContinuationFrames(int paramInt, long paramLong)
      throws IOException
    {
      while (paramLong > 0L)
      {
        int i = (int)Math.min(maxFrameSize, paramLong);
        long l = i;
        paramLong -= l;
        byte b;
        if (paramLong == 0L) {
          b = 4;
        } else {
          b = 0;
        }
        frameHeader(paramInt, i, (byte)9, b);
        sink.write(hpackBuffer, l);
      }
    }
    
    public void ackSettings(Settings paramSettings)
      throws IOException
    {
      try
      {
        if (!closed)
        {
          maxFrameSize = paramSettings.getMaxFrameSize(maxFrameSize);
          frameHeader(0, 0, (byte)4, (byte)1);
          sink.flush();
          return;
        }
        throw new IOException("closed");
      }
      catch (Throwable paramSettings)
      {
        throw paramSettings;
      }
    }
    
    public void close()
      throws IOException
    {
      try
      {
        closed = true;
        sink.close();
        return;
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    public void connectionPreface()
      throws IOException
    {
      try
      {
        if (!closed)
        {
          boolean bool = client;
          if (!bool) {
            return;
          }
          if (Http2.logger.isLoggable(Level.FINE)) {
            Http2.logger.fine(String.format(">> CONNECTION %s", new Object[] { Http2.CONNECTION_PREFACE.hex() }));
          }
          sink.write(Http2.CONNECTION_PREFACE.toByteArray());
          sink.flush();
          return;
        }
        throw new IOException("closed");
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    public void data(boolean paramBoolean, int paramInt1, Buffer paramBuffer, int paramInt2)
      throws IOException
    {
      try
      {
        if (!closed)
        {
          byte b = 0;
          if (paramBoolean) {
            b = (byte)1;
          }
          dataFrame(paramInt1, b, paramBuffer, paramInt2);
          return;
        }
        throw new IOException("closed");
      }
      catch (Throwable paramBuffer)
      {
        throw paramBuffer;
      }
    }
    
    void dataFrame(int paramInt1, byte paramByte, Buffer paramBuffer, int paramInt2)
      throws IOException
    {
      frameHeader(paramInt1, paramInt2, (byte)0, paramByte);
      if (paramInt2 > 0) {
        sink.write(paramBuffer, paramInt2);
      }
    }
    
    public void flush()
      throws IOException
    {
      try
      {
        if (!closed)
        {
          sink.flush();
          return;
        }
        throw new IOException("closed");
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
    
    void frameHeader(int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
      throws IOException
    {
      if (Http2.logger.isLoggable(Level.FINE)) {
        Http2.logger.fine(Http2.FrameLogger.formatHeader(false, paramInt1, paramInt2, paramByte1, paramByte2));
      }
      int i = maxFrameSize;
      if (paramInt2 <= i)
      {
        if ((0x80000000 & paramInt1) == 0)
        {
          Http2.writeMedium(sink, paramInt2);
          sink.writeByte(paramByte1 & 0xFF);
          sink.writeByte(paramByte2 & 0xFF);
          sink.writeInt(paramInt1 & 0x7FFFFFFF);
          return;
        }
        throw Http2.illegalArgument("reserved bit set: %s", new Object[] { Integer.valueOf(paramInt1) });
      }
      throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt2) });
    }
    
    public void goAway(int paramInt, ErrorCode paramErrorCode, byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        if (!closed)
        {
          if (httpCode != -1)
          {
            frameHeader(0, paramArrayOfByte.length + 8, (byte)7, (byte)0);
            sink.writeInt(paramInt);
            sink.writeInt(httpCode);
            if (paramArrayOfByte.length > 0) {
              sink.write(paramArrayOfByte);
            }
            sink.flush();
            return;
          }
          throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
        }
        throw new IOException("closed");
      }
      catch (Throwable paramErrorCode)
      {
        throw paramErrorCode;
      }
    }
    
    public void headers(int paramInt, List paramList)
      throws IOException
    {
      try
      {
        if (!closed)
        {
          headers(false, paramInt, paramList);
          return;
        }
        throw new IOException("closed");
      }
      catch (Throwable paramList)
      {
        throw paramList;
      }
    }
    
    void headers(boolean paramBoolean, int paramInt, List paramList)
      throws IOException
    {
      if (!closed)
      {
        hpackWriter.writeHeaders(paramList);
        long l1 = hpackBuffer.size();
        int i = (int)Math.min(maxFrameSize, l1);
        long l2 = i;
        byte b1;
        if (l1 == l2) {
          b1 = 4;
        } else {
          b1 = 0;
        }
        byte b2 = b1;
        if (paramBoolean) {
          b2 = (byte)(b1 | 0x1);
        }
        frameHeader(paramInt, i, (byte)1, b2);
        sink.write(hpackBuffer, l2);
        if (l1 > l2) {
          writeContinuationFrames(paramInt, l1 - l2);
        }
      }
      else
      {
        throw new IOException("closed");
      }
    }
    
    public int maxDataLength()
    {
      return maxFrameSize;
    }
    
    public void ping(boolean paramBoolean, int paramInt1, int paramInt2)
      throws IOException
    {
      for (;;)
      {
        try
        {
          if (!closed)
          {
            if (paramBoolean)
            {
              b = 1;
              frameHeader(0, 8, (byte)6, b);
              sink.writeInt(paramInt1);
              sink.writeInt(paramInt2);
              sink.flush();
            }
          }
          else {
            throw new IOException("closed");
          }
        }
        catch (Throwable localThrowable)
        {
          throw localThrowable;
        }
        byte b = 0;
      }
    }
    
    public void pushPromise(int paramInt1, int paramInt2, List paramList)
      throws IOException
    {
      for (;;)
      {
        try
        {
          if (!closed)
          {
            hpackWriter.writeHeaders(paramList);
            long l1 = hpackBuffer.size();
            int i = (int)Math.min(maxFrameSize - 4, l1);
            long l2 = i;
            if (l1 == l2)
            {
              b = 4;
              frameHeader(paramInt1, i + 4, (byte)5, b);
              sink.writeInt(paramInt2 & 0x7FFFFFFF);
              sink.write(hpackBuffer, l2);
              if (l1 > l2) {
                writeContinuationFrames(paramInt1, l1 - l2);
              }
            }
          }
          else
          {
            throw new IOException("closed");
          }
        }
        catch (Throwable paramList)
        {
          throw paramList;
        }
        byte b = 0;
      }
    }
    
    public void rstStream(int paramInt, ErrorCode paramErrorCode)
      throws IOException
    {
      try
      {
        if (!closed)
        {
          if (httpCode != -1)
          {
            frameHeader(paramInt, 4, (byte)3, (byte)0);
            sink.writeInt(httpCode);
            sink.flush();
            return;
          }
          throw new IllegalArgumentException();
        }
        throw new IOException("closed");
      }
      catch (Throwable paramErrorCode)
      {
        throw paramErrorCode;
      }
    }
    
    public void settings(Settings paramSettings)
      throws IOException
    {
      for (;;)
      {
        int j;
        int i;
        try
        {
          if (!closed)
          {
            j = paramSettings.size();
            i = 0;
            frameHeader(0, j * 6, (byte)4, (byte)0);
            if (i < 10)
            {
              if (!paramSettings.isSet(i))
              {
                break label129;
                sink.writeShort(j);
                sink.writeInt(paramSettings.get(i));
              }
            }
            else {
              sink.flush();
            }
          }
          else
          {
            throw new IOException("closed");
          }
        }
        catch (Throwable paramSettings)
        {
          throw paramSettings;
        }
        if (i == 4)
        {
          j = 3;
        }
        else if (i == 7)
        {
          j = 4;
        }
        else
        {
          j = i;
          continue;
          label129:
          i += 1;
        }
      }
    }
    
    public void synReply(boolean paramBoolean, int paramInt, List paramList)
      throws IOException
    {
      try
      {
        if (!closed)
        {
          headers(paramBoolean, paramInt, paramList);
          return;
        }
        throw new IOException("closed");
      }
      catch (Throwable paramList)
      {
        throw paramList;
      }
    }
    
    public void synStream(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, List paramList)
      throws IOException
    {
      if (!paramBoolean2) {}
      try
      {
        if (!closed)
        {
          headers(paramBoolean1, paramInt1, paramList);
          return;
        }
        throw new IOException("closed");
      }
      catch (Throwable paramList)
      {
        for (;;) {}
      }
      throw new UnsupportedOperationException();
      throw paramList;
    }
    
    public void windowUpdate(int paramInt, long paramLong)
      throws IOException
    {
      try
      {
        if (!closed)
        {
          if ((paramLong != 0L) && (paramLong <= 2147483647L))
          {
            frameHeader(paramInt, 4, (byte)8, (byte)0);
            sink.writeInt((int)paramLong);
            sink.flush();
            return;
          }
          throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", new Object[] { Long.valueOf(paramLong) });
        }
        throw new IOException("closed");
      }
      catch (Throwable localThrowable)
      {
        throw localThrowable;
      }
    }
  }
}
