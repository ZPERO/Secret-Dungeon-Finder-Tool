package com.squareup.okhttp.internal.framed;

import java.util.Arrays;

public final class Settings
{
  static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
  static final int COUNT = 10;
  static final int CURRENT_CWND = 5;
  static final int DEFAULT_INITIAL_WINDOW_SIZE = 65536;
  static final int DOWNLOAD_BANDWIDTH = 2;
  static final int DOWNLOAD_RETRANS_RATE = 6;
  static final int ENABLE_PUSH = 2;
  static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 1;
  static final int FLOW_CONTROL_OPTIONS = 10;
  static final int FLOW_CONTROL_OPTIONS_DISABLED = 1;
  static final int HEADER_TABLE_SIZE = 1;
  static final int INITIAL_WINDOW_SIZE = 7;
  static final int MAX_CONCURRENT_STREAMS = 4;
  static final int MAX_FRAME_SIZE = 5;
  static final int MAX_HEADER_LIST_SIZE = 6;
  static final int PERSISTED = 2;
  static final int PERSIST_VALUE = 1;
  static final int ROUND_TRIP_TIME = 3;
  static final int UPLOAD_BANDWIDTH = 1;
  private int persistValue;
  private int persisted;
  private int set;
  private final int[] values = new int[10];
  
  public Settings() {}
  
  void clear()
  {
    persisted = 0;
    persistValue = 0;
    set = 0;
    Arrays.fill(values, 0);
  }
  
  int flags(int paramInt)
  {
    int i;
    if (isPersisted(paramInt)) {
      i = 2;
    } else {
      i = 0;
    }
    int j = i;
    if (persistValue(paramInt)) {
      j = i | 0x1;
    }
    return j;
  }
  
  int get(int paramInt)
  {
    return values[paramInt];
  }
  
  int getClientCertificateVectorSize(int paramInt)
  {
    if ((set & 0x100) != 0) {
      paramInt = values[8];
    }
    return paramInt;
  }
  
  int getCurrentCwnd(int paramInt)
  {
    if ((set & 0x20) != 0) {
      paramInt = values[5];
    }
    return paramInt;
  }
  
  int getDownloadBandwidth(int paramInt)
  {
    if ((set & 0x4) != 0) {
      paramInt = values[2];
    }
    return paramInt;
  }
  
  int getDownloadRetransRate(int paramInt)
  {
    if ((set & 0x40) != 0) {
      paramInt = values[6];
    }
    return paramInt;
  }
  
  boolean getEnablePush(boolean paramBoolean)
  {
    int i;
    if ((set & 0x4) != 0) {
      i = values[2];
    } else if (paramBoolean) {
      i = 1;
    } else {
      i = 0;
    }
    return i == 1;
  }
  
  int getHeaderTableSize()
  {
    if ((set & 0x2) != 0) {
      return values[1];
    }
    return -1;
  }
  
  int getInitialWindowSize(int paramInt)
  {
    if ((set & 0x80) != 0) {
      paramInt = values[7];
    }
    return paramInt;
  }
  
  int getMaxConcurrentStreams(int paramInt)
  {
    if ((set & 0x10) != 0) {
      paramInt = values[4];
    }
    return paramInt;
  }
  
  int getMaxFrameSize(int paramInt)
  {
    if ((set & 0x20) != 0) {
      paramInt = values[5];
    }
    return paramInt;
  }
  
  int getMaxHeaderListSize(int paramInt)
  {
    if ((set & 0x40) != 0) {
      paramInt = values[6];
    }
    return paramInt;
  }
  
  int getRoundTripTime(int paramInt)
  {
    if ((set & 0x8) != 0) {
      paramInt = values[3];
    }
    return paramInt;
  }
  
  int getUploadBandwidth(int paramInt)
  {
    if ((set & 0x2) != 0) {
      paramInt = values[1];
    }
    return paramInt;
  }
  
  boolean isFlowControlDisabled()
  {
    int i;
    if ((set & 0x400) != 0) {
      i = values[10];
    } else {
      i = 0;
    }
    return (i & 0x1) != 0;
  }
  
  boolean isPersisted(int paramInt)
  {
    return (1 << paramInt & persisted) != 0;
  }
  
  boolean isSet(int paramInt)
  {
    return (1 << paramInt & set) != 0;
  }
  
  void merge(Settings paramSettings)
  {
    int i = 0;
    while (i < 10)
    {
      if (paramSettings.isSet(i)) {
        set(i, paramSettings.flags(i), paramSettings.get(i));
      }
      i += 1;
    }
  }
  
  boolean persistValue(int paramInt)
  {
    return (1 << paramInt & persistValue) != 0;
  }
  
  Settings set(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= values.length) {
      return this;
    }
    int i = 1 << paramInt1;
    set |= i;
    if ((paramInt2 & 0x1) != 0) {
      persistValue |= i;
    } else {
      persistValue &= i;
    }
    if ((paramInt2 & 0x2) != 0) {
      persisted |= i;
    } else {
      persisted &= i;
    }
    values[paramInt1] = paramInt3;
    return this;
  }
  
  int size()
  {
    return Integer.bitCount(set);
  }
}
