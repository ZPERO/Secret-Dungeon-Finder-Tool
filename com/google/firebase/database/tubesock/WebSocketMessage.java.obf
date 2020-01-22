package com.google.firebase.database.tubesock;

public class WebSocketMessage
{
  private byte[] byteMessage;
  private byte opcode;
  private String stringMessage;
  
  public WebSocketMessage(String paramString)
  {
    stringMessage = paramString;
    opcode = 1;
  }
  
  public WebSocketMessage(byte[] paramArrayOfByte)
  {
    byteMessage = paramArrayOfByte;
    opcode = 2;
  }
  
  public byte[] getBytes()
  {
    return byteMessage;
  }
  
  public String getText()
  {
    return stringMessage;
  }
  
  public boolean isBinary()
  {
    return opcode == 2;
  }
  
  public boolean isText()
  {
    return opcode == 1;
  }
}
