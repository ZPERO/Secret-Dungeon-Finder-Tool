package androidx.media;

import android.media.AudioAttributes;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AudioAttributesCompat
  implements VersionedParcelable
{
  static final String AUDIO_ATTRIBUTES_CONTENT_TYPE = "androidx.media.audio_attrs.CONTENT_TYPE";
  static final String AUDIO_ATTRIBUTES_FLAGS = "androidx.media.audio_attrs.FLAGS";
  static final String AUDIO_ATTRIBUTES_FRAMEWORKS = "androidx.media.audio_attrs.FRAMEWORKS";
  static final String AUDIO_ATTRIBUTES_LEGACY_STREAM_TYPE = "androidx.media.audio_attrs.LEGACY_STREAM_TYPE";
  static final String AUDIO_ATTRIBUTES_USAGE = "androidx.media.audio_attrs.USAGE";
  public static final int CONTENT_TYPE_MOVIE = 3;
  public static final int CONTENT_TYPE_MUSIC = 2;
  public static final int CONTENT_TYPE_SONIFICATION = 4;
  public static final int CONTENT_TYPE_SPEECH = 1;
  public static final int CONTENT_TYPE_UNKNOWN = 0;
  static final int FLAG_ALL = 1023;
  static final int FLAG_ALL_PUBLIC = 273;
  public static final int FLAG_AUDIBILITY_ENFORCED = 1;
  static final int FLAG_BEACON = 8;
  static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;
  static final int FLAG_BYPASS_MUTE = 128;
  static final int FLAG_DEEP_BUFFER = 512;
  public static final int FLAG_HW_AV_SYNC = 16;
  static final int FLAG_HW_HOTWORD = 32;
  static final int FLAG_LOW_LATENCY = 256;
  static final int FLAG_SCO = 4;
  static final int FLAG_SECURE = 2;
  static final int INVALID_STREAM_TYPE = -1;
  private static final String PAGE_KEY = "AudioAttributesCompat";
  private static final int[] SDK_USAGES = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16 };
  private static final int SUPPRESSIBLE_CALL = 2;
  private static final int SUPPRESSIBLE_NOTIFICATION = 1;
  private static final SparseIntArray SUPPRESSIBLE_USAGES = new SparseIntArray();
  public static final int USAGE_ALARM = 4;
  public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
  public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
  public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
  public static final int USAGE_ASSISTANT = 16;
  public static final int USAGE_GAME = 14;
  public static final int USAGE_MEDIA = 1;
  public static final int USAGE_NOTIFICATION = 5;
  public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
  public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
  public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
  public static final int USAGE_NOTIFICATION_EVENT = 10;
  public static final int USAGE_NOTIFICATION_RINGTONE = 6;
  public static final int USAGE_UNKNOWN = 0;
  private static final int USAGE_VIRTUAL_SOURCE = 15;
  public static final int USAGE_VOICE_COMMUNICATION = 2;
  public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
  static boolean sForceLegacyBehavior;
  AudioAttributesImpl mImpl;
  
  static
  {
    SUPPRESSIBLE_USAGES.put(5, 1);
    SUPPRESSIBLE_USAGES.put(6, 2);
    SUPPRESSIBLE_USAGES.put(7, 2);
    SUPPRESSIBLE_USAGES.put(8, 1);
    SUPPRESSIBLE_USAGES.put(9, 1);
    SUPPRESSIBLE_USAGES.put(10, 1);
  }
  
  AudioAttributesCompat() {}
  
  AudioAttributesCompat(AudioAttributesImpl paramAudioAttributesImpl)
  {
    mImpl = paramAudioAttributesImpl;
  }
  
  public static AudioAttributesCompat fromBundle(Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      paramBundle = AudioAttributesImplApi21.fromBundle(paramBundle);
    } else {
      paramBundle = AudioAttributesImplBase.fromBundle(paramBundle);
    }
    if (paramBundle == null) {
      return null;
    }
    return new AudioAttributesCompat(paramBundle);
  }
  
  public static void setForceLegacyBehavior(boolean paramBoolean)
  {
    sForceLegacyBehavior = paramBoolean;
  }
  
  static int toVolumeStreamType(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    if ((paramInt1 & 0x1) == 1)
    {
      if (paramBoolean) {
        return 1;
      }
      return 7;
    }
    int i = 0;
    if ((paramInt1 & 0x4) == 4)
    {
      if (paramBoolean) {
        return 0;
      }
      return 6;
    }
    paramInt1 = i;
    switch (paramInt2)
    {
    default: 
      break;
    case 15: 
      if (paramBoolean) {
        break label224;
      }
      return 3;
    case 13: 
      return 1;
    case 11: 
      return 10;
    case 6: 
      return 2;
    case 5: 
    case 7: 
    case 8: 
    case 9: 
    case 10: 
      return 5;
    case 4: 
      return 4;
    case 3: 
      if (paramBoolean) {
        return 0;
      }
      paramInt1 = 8;
    case 2: 
      return paramInt1;
    case 1: 
    case 12: 
    case 14: 
    case 16: 
      return 3;
    }
    if (paramBoolean)
    {
      return Integer.MIN_VALUE;
      label224:
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unknown usage value ");
      localStringBuilder.append(paramInt2);
      localStringBuilder.append(" in audio attributes");
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    return 3;
  }
  
  static int toVolumeStreamType(boolean paramBoolean, AudioAttributesCompat paramAudioAttributesCompat)
  {
    return toVolumeStreamType(paramBoolean, paramAudioAttributesCompat.getFlags(), paramAudioAttributesCompat.getUsage());
  }
  
  static int usageForStreamType(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 9: 
      return 0;
    case 10: 
      return 11;
    case 8: 
      return 3;
    case 6: 
      return 2;
    case 5: 
      return 5;
    case 4: 
      return 4;
    case 3: 
      return 1;
    case 2: 
      return 6;
    case 1: 
    case 7: 
      return 13;
    }
    return 2;
  }
  
  static String usageToString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 15: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("unknown usage ");
      localStringBuilder.append(paramInt);
      return localStringBuilder.toString();
    case 16: 
      return "USAGE_ASSISTANT";
    case 14: 
      return "USAGE_GAME";
    case 13: 
      return "USAGE_ASSISTANCE_SONIFICATION";
    case 12: 
      return "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
    case 11: 
      return "USAGE_ASSISTANCE_ACCESSIBILITY";
    case 10: 
      return "USAGE_NOTIFICATION_EVENT";
    case 9: 
      return "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
    case 8: 
      return "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
    case 7: 
      return "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
    case 6: 
      return "USAGE_NOTIFICATION_RINGTONE";
    case 5: 
      return "USAGE_NOTIFICATION";
    case 4: 
      return "USAGE_ALARM";
    case 3: 
      return "USAGE_VOICE_COMMUNICATION_SIGNALLING";
    case 2: 
      return "USAGE_VOICE_COMMUNICATION";
    case 1: 
      return "USAGE_MEDIA";
    }
    return "USAGE_UNKNOWN";
  }
  
  public static AudioAttributesCompat wrap(Object paramObject)
  {
    if ((Build.VERSION.SDK_INT >= 21) && (!sForceLegacyBehavior))
    {
      paramObject = new AudioAttributesImplApi21((AudioAttributes)paramObject);
      AudioAttributesCompat localAudioAttributesCompat = new AudioAttributesCompat();
      mImpl = paramObject;
      return localAudioAttributesCompat;
    }
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AudioAttributesCompat)) {
      return false;
    }
    paramObject = (AudioAttributesCompat)paramObject;
    AudioAttributesImpl localAudioAttributesImpl = mImpl;
    if (localAudioAttributesImpl == null)
    {
      if (mImpl == null) {
        return true;
      }
    }
    else {
      return localAudioAttributesImpl.equals(mImpl);
    }
    return false;
  }
  
  public int getContentType()
  {
    return mImpl.getContentType();
  }
  
  public int getFlags()
  {
    return mImpl.getFlags();
  }
  
  public int getLegacyStreamType()
  {
    return mImpl.getLegacyStreamType();
  }
  
  int getRawLegacyStreamType()
  {
    return mImpl.getRawLegacyStreamType();
  }
  
  public int getUsage()
  {
    return mImpl.getUsage();
  }
  
  public int getVolumeControlStream()
  {
    return mImpl.getVolumeControlStream();
  }
  
  public int hashCode()
  {
    return mImpl.hashCode();
  }
  
  public Bundle toBundle()
  {
    return mImpl.toBundle();
  }
  
  public String toString()
  {
    return mImpl.toString();
  }
  
  public Object unwrap()
  {
    return mImpl.getAudioAttributes();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AttributeContentType {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AttributeUsage {}
  
  static abstract class AudioManagerHidden
  {
    public static final int STREAM_ACCESSIBILITY = 10;
    public static final int STREAM_BLUETOOTH_SCO = 6;
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    public static final int STREAM_TTS = 9;
    
    private AudioManagerHidden() {}
  }
  
  public static class Builder
  {
    private int mContentType = 0;
    private int mFlags = 0;
    private int mLegacyStream = -1;
    private int mUsage = 0;
    
    public Builder() {}
    
    public Builder(AudioAttributesCompat paramAudioAttributesCompat)
    {
      mUsage = paramAudioAttributesCompat.getUsage();
      mContentType = paramAudioAttributesCompat.getContentType();
      mFlags = paramAudioAttributesCompat.getFlags();
      mLegacyStream = paramAudioAttributesCompat.getRawLegacyStreamType();
    }
    
    public AudioAttributesCompat build()
    {
      throw new Runtime("d2j fail translate: java.lang.RuntimeException: fail exe a4 = a3\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:274)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:163)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer$LiveA.onUseLocal(UnSSATransformer.java:552)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer$LiveA.onUseLocal(UnSSATransformer.java:1)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:166)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:331)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:387)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:90)\n\t... 17 more\n");
    }
    
    public Builder setContentType(int paramInt)
    {
      if ((paramInt != 0) && (paramInt != 1) && (paramInt != 2) && (paramInt != 3) && (paramInt != 4))
      {
        mUsage = 0;
        return this;
      }
      mContentType = paramInt;
      return this;
    }
    
    public Builder setFlags(int paramInt)
    {
      mFlags = (paramInt & 0x3FF | mFlags);
      return this;
    }
    
    Builder setInternalLegacyStreamType(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Invalid stream type ");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(" for AudioAttributesCompat");
        Log.e("AudioAttributesCompat", localStringBuilder.toString());
        break;
      case 10: 
        mContentType = 1;
        break;
      case 9: 
        mContentType = 4;
        break;
      case 8: 
        mContentType = 4;
        break;
      case 7: 
        mFlags = (0x1 | mFlags);
        break;
      case 6: 
        mContentType = 1;
        mFlags |= 0x4;
        break;
      case 5: 
        mContentType = 4;
        break;
      case 4: 
        mContentType = 4;
        break;
      case 3: 
        mContentType = 2;
        break;
      case 2: 
        mContentType = 4;
        break;
      case 1: 
        mContentType = 4;
        break;
      }
      mContentType = 1;
      mUsage = AudioAttributesCompat.usageForStreamType(paramInt);
      return this;
    }
    
    public Builder setLegacyStreamType(int paramInt)
    {
      if (paramInt != 10)
      {
        mLegacyStream = paramInt;
        Builder localBuilder = this;
        if (Build.VERSION.SDK_INT >= 21) {
          localBuilder = setInternalLegacyStreamType(paramInt);
        }
        return localBuilder;
      }
      throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
    }
    
    public Builder setUsage(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        mUsage = 0;
        return this;
      case 16: 
        if ((!AudioAttributesCompat.sForceLegacyBehavior) && (Build.VERSION.SDK_INT > 25))
        {
          mUsage = paramInt;
          return this;
        }
        mUsage = 12;
        return this;
      }
      mUsage = paramInt;
      return this;
    }
  }
}
