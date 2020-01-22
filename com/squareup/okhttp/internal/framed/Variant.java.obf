package com.squareup.okhttp.internal.framed;

import com.squareup.okhttp.Protocol;
import okio.BufferedSink;
import okio.BufferedSource;

public abstract interface Variant
{
  public abstract Protocol getProtocol();
  
  public abstract FrameReader newReader(BufferedSource paramBufferedSource, boolean paramBoolean);
  
  public abstract FrameWriter newWriter(BufferedSink paramBufferedSink, boolean paramBoolean);
}
