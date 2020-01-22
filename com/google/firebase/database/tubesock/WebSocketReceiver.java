package com.google.firebase.database.tubesock;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;

class WebSocketReceiver
{
  private WebSocketEventHandler eventHandler = null;
  private DataInputStream input = null;
  private byte[] inputHeader = new byte[112];
  private MessageBuilderFactory.Builder pendingBuilder;
  private volatile boolean stop = false;
  private WebSocket websocket = null;
  
  WebSocketReceiver(WebSocket paramWebSocket)
  {
    websocket = paramWebSocket;
  }
  
  private void appendBytes(boolean paramBoolean, byte paramByte, byte[] paramArrayOfByte)
  {
    if (paramByte == 9)
    {
      if (paramBoolean)
      {
        handlePing(paramArrayOfByte);
        return;
      }
      throw new WebSocketException("PING must not fragment across frames");
    }
    if ((pendingBuilder != null) && (paramByte != 0)) {
      throw new WebSocketException("Failed to continue outstanding frame");
    }
    if ((pendingBuilder == null) && (paramByte == 0)) {
      throw new WebSocketException("Received continuing frame, but there's nothing to continue");
    }
    if (pendingBuilder == null) {
      pendingBuilder = MessageBuilderFactory.builder(paramByte);
    }
    if (pendingBuilder.appendBytes(paramArrayOfByte))
    {
      if (paramBoolean)
      {
        paramArrayOfByte = pendingBuilder.toMessage();
        pendingBuilder = null;
        if (paramArrayOfByte != null)
        {
          eventHandler.onMessage(paramArrayOfByte);
          return;
        }
        throw new WebSocketException("Failed to decode whole message");
      }
    }
    else {
      throw new WebSocketException("Failed to decode frame");
    }
  }
  
  private void handleError(WebSocketException paramWebSocketException)
  {
    stopit();
    websocket.handleReceiverError(paramWebSocketException);
  }
  
  private void handlePing(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length <= 125)
    {
      websocket.pong(paramArrayOfByte);
      return;
    }
    throw new WebSocketException("PING frame too long");
  }
  
  private long parseLong(byte[] paramArrayOfByte, int paramInt)
  {
    return (paramArrayOfByte[(paramInt + 0)] << 56) + ((paramArrayOfByte[(paramInt + 1)] & 0xFF) << 48) + ((paramArrayOfByte[(paramInt + 2)] & 0xFF) << 40) + ((paramArrayOfByte[(paramInt + 3)] & 0xFF) << 32) + ((paramArrayOfByte[(paramInt + 4)] & 0xFF) << 24) + ((paramArrayOfByte[(paramInt + 5)] & 0xFF) << 16) + ((paramArrayOfByte[(paramInt + 6)] & 0xFF) << 8) + ((paramArrayOfByte[(paramInt + 7)] & 0xFF) << 0);
  }
  
  private int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    input.readFully(paramArrayOfByte, paramInt1, paramInt2);
    return paramInt2;
  }
  
  void encode()
  {
    eventHandler = websocket.getEventHandler();
    for (;;)
    {
      Object localObject;
      if (!stop) {
        localObject = inputHeader;
      }
      try
      {
        int i = read((byte[])localObject, 0, 1);
        int j = i + 0;
        boolean bool;
        if ((inputHeader[0] & 0x80) != 0) {
          bool = true;
        } else {
          bool = false;
        }
        if ((inputHeader[0] & 0x70) != 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i == 0)
        {
          byte b = (byte)(inputHeader[0] & 0xF);
          localObject = inputHeader;
          i = read((byte[])localObject, j, 1);
          i = j + i;
          j = inputHeader[1];
          long l = 0L;
          if (j < 126)
          {
            l = j;
          }
          else if (j == 126)
          {
            localObject = inputHeader;
            read((byte[])localObject, i, 2);
            l = (inputHeader[2] & 0xFF) << 8 | inputHeader[3] & 0xFF;
          }
          else if (j == 127)
          {
            localObject = inputHeader;
            j = read((byte[])localObject, i, 8);
            localObject = inputHeader;
            l = parseLong((byte[])localObject, i + j - 8);
          }
          i = (int)l;
          localObject = new byte[i];
          read((byte[])localObject, 0, i);
          if (b == 8)
          {
            localObject = websocket;
            ((WebSocket)localObject).onCloseOpReceived();
            continue;
          }
          if (b == 10) {
            continue;
          }
          if ((b != 1) && (b != 2) && (b != 9) && (b != 0))
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("Unsupported opcode: ");
            ((StringBuilder)localObject).append(b);
            localObject = new WebSocketException(((StringBuilder)localObject).toString());
            throw ((Throwable)localObject);
          }
          appendBytes(bool, b, (byte[])localObject);
          continue;
        }
        localObject = new WebSocketException("Invalid frame received");
        throw ((Throwable)localObject);
      }
      catch (WebSocketException localWebSocketException)
      {
        handleError(localWebSocketException);
      }
      catch (IOException localIOException)
      {
        handleError(new WebSocketException("IO Error", localIOException));
        continue;
        continue;
        return;
      }
      catch (SocketTimeoutException localSocketTimeoutException)
      {
        for (;;) {}
      }
    }
  }
  
  boolean isRunning()
  {
    return stop ^ true;
  }
  
  void setInput(DataInputStream paramDataInputStream)
  {
    input = paramDataInputStream;
  }
  
  void stopit()
  {
    stop = true;
  }
}
