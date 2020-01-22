package com.google.firebase.database.tubesock;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

class MessageBuilderFactory
{
  MessageBuilderFactory() {}
  
  static Builder builder(byte paramByte)
  {
    if (paramByte == 2) {
      return new BinaryBuilder();
    }
    return new TextBuilder();
  }
  
  static class BinaryBuilder
    implements MessageBuilderFactory.Builder
  {
    private int pendingByteCount = 0;
    private List<byte[]> pendingBytes = new ArrayList();
    
    BinaryBuilder() {}
    
    public boolean appendBytes(byte[] paramArrayOfByte)
    {
      pendingBytes.add(paramArrayOfByte);
      pendingByteCount += paramArrayOfByte.length;
      return true;
    }
    
    public WebSocketMessage toMessage()
    {
      byte[] arrayOfByte1 = new byte[pendingByteCount];
      int i = 0;
      int j = 0;
      while (i < pendingBytes.size())
      {
        byte[] arrayOfByte2 = (byte[])pendingBytes.get(i);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, j, arrayOfByte2.length);
        j += arrayOfByte2.length;
        i += 1;
      }
      return new WebSocketMessage(arrayOfByte1);
    }
  }
  
  static abstract interface Builder
  {
    public abstract boolean appendBytes(byte[] paramArrayOfByte);
    
    public abstract WebSocketMessage toMessage();
  }
  
  static class TextBuilder
    implements MessageBuilderFactory.Builder
  {
    private static ThreadLocal<CharsetDecoder> localDecoder = new ThreadLocal()
    {
      protected CharsetDecoder initialValue()
      {
        CharsetDecoder localCharsetDecoder = Charset.forName("UTF8").newDecoder();
        localCharsetDecoder.onMalformedInput(CodingErrorAction.REPORT);
        localCharsetDecoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        return localCharsetDecoder;
      }
    };
    private static ThreadLocal<CharsetEncoder> localEncoder = new ThreadLocal()
    {
      protected CharsetEncoder initialValue()
      {
        CharsetEncoder localCharsetEncoder = Charset.forName("UTF8").newEncoder();
        localCharsetEncoder.onMalformedInput(CodingErrorAction.REPORT);
        localCharsetEncoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        return localCharsetEncoder;
      }
    };
    private StringBuilder builder = new StringBuilder();
    private ByteBuffer carryOver;
    
    TextBuilder() {}
    
    private String decodeString(byte[] paramArrayOfByte)
    {
      try
      {
        paramArrayOfByte = ByteBuffer.wrap(paramArrayOfByte);
        Object localObject = localDecoder;
        localObject = ((ThreadLocal)localObject).get();
        localObject = (CharsetDecoder)localObject;
        paramArrayOfByte = ((CharsetDecoder)localObject).decode(paramArrayOfByte).toString();
        return paramArrayOfByte;
      }
      catch (CharacterCodingException paramArrayOfByte)
      {
        for (;;) {}
      }
      return null;
    }
    
    private String decodeStringStreaming(byte[] paramArrayOfByte)
    {
      try
      {
        Object localObject2 = getBuffer(paramArrayOfByte);
        int i = ((ByteBuffer)localObject2).remaining();
        float f1 = i;
        paramArrayOfByte = localDecoder;
        paramArrayOfByte = paramArrayOfByte.get();
        paramArrayOfByte = (CharsetDecoder)paramArrayOfByte;
        float f2 = paramArrayOfByte.averageCharsPerByte();
        i = (int)(f1 * f2);
        paramArrayOfByte = CharBuffer.allocate(i);
        for (;;)
        {
          Object localObject1 = localDecoder;
          localObject1 = ((ThreadLocal)localObject1).get();
          localObject1 = (CharsetDecoder)localObject1;
          localObject1 = ((CharsetDecoder)localObject1).decode((ByteBuffer)localObject2, paramArrayOfByte, false);
          boolean bool = ((CoderResult)localObject1).isError();
          if (bool) {
            return null;
          }
          bool = ((CoderResult)localObject1).isUnderflow();
          if (bool)
          {
            i = ((ByteBuffer)localObject2).remaining();
            if (i > 0) {
              carryOver = ((ByteBuffer)localObject2);
            }
            localObject1 = CharBuffer.wrap(paramArrayOfByte);
            localObject2 = localEncoder;
            localObject2 = ((ThreadLocal)localObject2).get();
            localObject2 = (CharsetEncoder)localObject2;
            ((CharsetEncoder)localObject2).encode((CharBuffer)localObject1);
            paramArrayOfByte.flip();
            paramArrayOfByte = paramArrayOfByte.toString();
            return paramArrayOfByte;
          }
          bool = ((CoderResult)localObject1).isOverflow();
          if (bool)
          {
            i = i * 2 + 1;
            localObject1 = CharBuffer.allocate(i);
            paramArrayOfByte.flip();
            ((CharBuffer)localObject1).put(paramArrayOfByte);
            paramArrayOfByte = (byte[])localObject1;
          }
        }
        return null;
      }
      catch (CharacterCodingException paramArrayOfByte) {}
    }
    
    private ByteBuffer getBuffer(byte[] paramArrayOfByte)
    {
      ByteBuffer localByteBuffer = carryOver;
      if (localByteBuffer != null)
      {
        localByteBuffer = ByteBuffer.allocate(paramArrayOfByte.length + localByteBuffer.remaining());
        localByteBuffer.put(carryOver);
        carryOver = null;
        localByteBuffer.put(paramArrayOfByte);
        localByteBuffer.flip();
        return localByteBuffer;
      }
      return ByteBuffer.wrap(paramArrayOfByte);
    }
    
    public boolean appendBytes(byte[] paramArrayOfByte)
    {
      paramArrayOfByte = decodeString(paramArrayOfByte);
      if (paramArrayOfByte != null)
      {
        builder.append(paramArrayOfByte);
        return true;
      }
      return false;
    }
    
    public WebSocketMessage toMessage()
    {
      if (carryOver != null) {
        return null;
      }
      return new WebSocketMessage(builder.toString());
    }
  }
}
