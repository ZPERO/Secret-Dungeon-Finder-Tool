package androidx.core.text;

import android.text.SpannableStringBuilder;
import java.util.Locale;

public final class BidiFormatter
{
  private static final int DEFAULT_FLAGS = 2;
  static final BidiFormatter DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
  static final BidiFormatter DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
  static final TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
  private static final int DIR_LTR = -1;
  private static final int DIR_RTL = 1;
  private static final int DIR_UNKNOWN = 0;
  private static final String EMPTY_STRING = "";
  private static final int FLAG_STEREO_RESET = 2;
  private static final char LRE = '?';
  private static final char LRM = '?';
  private static final String LRM_STRING = Character.toString('?');
  private static final char PDF = '?';
  private static final char RLE = '?';
  private static final char RLM = '?';
  private static final String RLM_STRING = Character.toString('?');
  private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
  private final int mFlags;
  private final boolean mIsRtlContext;
  
  BidiFormatter(boolean paramBoolean, int paramInt, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    mIsRtlContext = paramBoolean;
    mFlags = paramInt;
    mDefaultTextDirectionHeuristicCompat = paramTextDirectionHeuristicCompat;
  }
  
  private static int getEntryDir(CharSequence paramCharSequence)
  {
    return new DirectionalityEstimator(paramCharSequence, false).getEntryDir();
  }
  
  private static int getExitDir(CharSequence paramCharSequence)
  {
    return new DirectionalityEstimator(paramCharSequence, false).getExitDir();
  }
  
  public static BidiFormatter getInstance()
  {
    return new Builder().build();
  }
  
  public static BidiFormatter getInstance(Locale paramLocale)
  {
    return new Builder(paramLocale).build();
  }
  
  public static BidiFormatter getInstance(boolean paramBoolean)
  {
    return new Builder(paramBoolean).build();
  }
  
  static boolean isRtlLocale(Locale paramLocale)
  {
    return TextUtilsCompat.getLayoutDirectionFromLocale(paramLocale) == 1;
  }
  
  private String markAfter(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    if ((!mIsRtlContext) && ((bool) || (getExitDir(paramCharSequence) == 1))) {
      return LRM_STRING;
    }
    if ((mIsRtlContext) && ((!bool) || (getExitDir(paramCharSequence) == -1))) {
      return RLM_STRING;
    }
    return "";
  }
  
  private String markBefore(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    if ((!mIsRtlContext) && ((bool) || (getEntryDir(paramCharSequence) == 1))) {
      return LRM_STRING;
    }
    if ((mIsRtlContext) && ((!bool) || (getEntryDir(paramCharSequence) == -1))) {
      return RLM_STRING;
    }
    return "";
  }
  
  public boolean getStereoReset()
  {
    return (mFlags & 0x2) != 0;
  }
  
  public boolean isRtl(CharSequence paramCharSequence)
  {
    return mDefaultTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
  }
  
  public boolean isRtl(String paramString)
  {
    return isRtl(paramString);
  }
  
  public boolean isRtlContext()
  {
    return mIsRtlContext;
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence)
  {
    return unicodeWrap(paramCharSequence, mDefaultTextDirectionHeuristicCompat, true);
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    return unicodeWrap(paramCharSequence, paramTextDirectionHeuristicCompat, true);
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat, boolean paramBoolean)
  {
    if (paramCharSequence == null) {
      return null;
    }
    boolean bool = paramTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    if ((getStereoReset()) && (paramBoolean))
    {
      if (bool) {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.RTL;
      } else {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.LTR;
      }
      localSpannableStringBuilder.append(markBefore(paramCharSequence, paramTextDirectionHeuristicCompat));
    }
    if (bool != mIsRtlContext)
    {
      char c;
      if (bool) {
        c = '?';
      } else {
        c = '?';
      }
      localSpannableStringBuilder.append(c);
      localSpannableStringBuilder.append(paramCharSequence);
      localSpannableStringBuilder.append('?');
    }
    else
    {
      localSpannableStringBuilder.append(paramCharSequence);
    }
    if (paramBoolean)
    {
      if (bool) {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.RTL;
      } else {
        paramTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.LTR;
      }
      localSpannableStringBuilder.append(markAfter(paramCharSequence, paramTextDirectionHeuristicCompat));
    }
    return localSpannableStringBuilder;
  }
  
  public CharSequence unicodeWrap(CharSequence paramCharSequence, boolean paramBoolean)
  {
    return unicodeWrap(paramCharSequence, mDefaultTextDirectionHeuristicCompat, paramBoolean);
  }
  
  public String unicodeWrap(String paramString)
  {
    return unicodeWrap(paramString, mDefaultTextDirectionHeuristicCompat, true);
  }
  
  public String unicodeWrap(String paramString, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
  {
    return unicodeWrap(paramString, paramTextDirectionHeuristicCompat, true);
  }
  
  public String unicodeWrap(String paramString, TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat, boolean paramBoolean)
  {
    if (paramString == null) {
      return null;
    }
    return unicodeWrap(paramString, paramTextDirectionHeuristicCompat, paramBoolean).toString();
  }
  
  public String unicodeWrap(String paramString, boolean paramBoolean)
  {
    return unicodeWrap(paramString, mDefaultTextDirectionHeuristicCompat, paramBoolean);
  }
  
  public static final class Builder
  {
    private int mFlags;
    private boolean mIsRtlContext;
    private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;
    
    public Builder()
    {
      initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
    }
    
    public Builder(Locale paramLocale)
    {
      initialize(BidiFormatter.isRtlLocale(paramLocale));
    }
    
    public Builder(boolean paramBoolean)
    {
      initialize(paramBoolean);
    }
    
    private static BidiFormatter getDefaultInstanceFromContext(boolean paramBoolean)
    {
      if (paramBoolean) {
        return BidiFormatter.DEFAULT_RTL_INSTANCE;
      }
      return BidiFormatter.DEFAULT_LTR_INSTANCE;
    }
    
    private void initialize(boolean paramBoolean)
    {
      mIsRtlContext = paramBoolean;
      mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
      mFlags = 2;
    }
    
    public BidiFormatter build()
    {
      if ((mFlags == 2) && (mTextDirectionHeuristicCompat == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC)) {
        return getDefaultInstanceFromContext(mIsRtlContext);
      }
      return new BidiFormatter(mIsRtlContext, mFlags, mTextDirectionHeuristicCompat);
    }
    
    public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat paramTextDirectionHeuristicCompat)
    {
      mTextDirectionHeuristicCompat = paramTextDirectionHeuristicCompat;
      return this;
    }
    
    public Builder stereoReset(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        mFlags |= 0x2;
        return this;
      }
      mFlags &= 0xFFFFFFFD;
      return this;
    }
  }
  
  private static class DirectionalityEstimator
  {
    private static final byte[] DIR_TYPE_CACHE = new byte['?'];
    private static final int DIR_TYPE_CACHE_SIZE = 1792;
    private int charIndex;
    private final boolean isHtml;
    private char lastChar;
    private final int length;
    private final CharSequence text;
    
    static
    {
      int i = 0;
      while (i < 1792)
      {
        DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
        i += 1;
      }
    }
    
    DirectionalityEstimator(CharSequence paramCharSequence, boolean paramBoolean)
    {
      text = paramCharSequence;
      isHtml = paramBoolean;
      length = paramCharSequence.length();
    }
    
    private static byte getCachedDirectionality(char paramChar)
    {
      if (paramChar < '?') {
        return DIR_TYPE_CACHE[paramChar];
      }
      return Character.getDirectionality(paramChar);
    }
    
    private byte skipEntityBackward()
    {
      int i = charIndex;
      int j;
      do
      {
        j = charIndex;
        if (j <= 0) {
          break;
        }
        CharSequence localCharSequence = text;
        j -= 1;
        charIndex = j;
        lastChar = localCharSequence.charAt(j);
        j = lastChar;
        if (j == 38) {
          return 12;
        }
      } while (j != 59);
      charIndex = i;
      lastChar = ';';
      return 13;
    }
    
    private byte skipEntityForward()
    {
      char c;
      do
      {
        int i = charIndex;
        if (i >= length) {
          break;
        }
        CharSequence localCharSequence = text;
        charIndex = (i + 1);
        c = localCharSequence.charAt(i);
        lastChar = c;
      } while (c != ';');
      return 12;
    }
    
    private byte skipTagBackward()
    {
      int j = charIndex;
      CharSequence localCharSequence;
      do
      {
        k = charIndex;
        if (k <= 0) {
          break;
        }
        localCharSequence = text;
        k -= 1;
        charIndex = k;
        lastChar = localCharSequence.charAt(k);
        k = lastChar;
        if (k == 60) {
          return 12;
        }
        if (k == 62) {
          break;
        }
      } while ((k != 34) && (k != 39));
      int k = lastChar;
      for (;;)
      {
        int m = charIndex;
        if (m <= 0) {
          break;
        }
        localCharSequence = text;
        m -= 1;
        charIndex = m;
        int i = localCharSequence.charAt(m);
        lastChar = i;
        if (i == k) {
          break;
        }
      }
      charIndex = j;
      lastChar = '>';
      return 13;
    }
    
    private byte skipTagForward()
    {
      int j = charIndex;
      CharSequence localCharSequence;
      do
      {
        k = charIndex;
        if (k >= length) {
          break;
        }
        localCharSequence = text;
        charIndex = (k + 1);
        lastChar = localCharSequence.charAt(k);
        k = lastChar;
        if (k == 62) {
          return 12;
        }
      } while ((k != 34) && (k != 39));
      int k = lastChar;
      for (;;)
      {
        int m = charIndex;
        if (m >= length) {
          break;
        }
        localCharSequence = text;
        charIndex = (m + 1);
        int i = localCharSequence.charAt(m);
        lastChar = i;
        if (i == k) {
          break;
        }
      }
      charIndex = j;
      lastChar = '<';
      return 13;
    }
    
    byte dirTypeBackward()
    {
      lastChar = text.charAt(charIndex - 1);
      int i;
      if (Character.isLowSurrogate(lastChar))
      {
        i = Character.codePointBefore(text, charIndex);
        charIndex -= Character.charCount(i);
        return Character.getDirectionality(i);
      }
      charIndex -= 1;
      byte b2 = getCachedDirectionality(lastChar);
      byte b1 = b2;
      if (isHtml)
      {
        i = lastChar;
        if (i == 62) {
          return skipTagBackward();
        }
        b1 = b2;
        if (i == 59) {
          b1 = skipEntityBackward();
        }
      }
      return b1;
    }
    
    byte dirTypeForward()
    {
      lastChar = text.charAt(charIndex);
      int i;
      if (Character.isHighSurrogate(lastChar))
      {
        i = Character.codePointAt(text, charIndex);
        charIndex += Character.charCount(i);
        return Character.getDirectionality(i);
      }
      charIndex += 1;
      byte b2 = getCachedDirectionality(lastChar);
      byte b1 = b2;
      if (isHtml)
      {
        i = lastChar;
        if (i == 60) {
          return skipTagForward();
        }
        b1 = b2;
        if (i == 38) {
          b1 = skipEntityForward();
        }
      }
      return b1;
    }
    
    int getEntryDir()
    {
      charIndex = 0;
      int k = 0;
      int j = 0;
      int i = 0;
      while ((charIndex < length) && (k == 0))
      {
        int m = dirTypeForward();
        if (m != 0)
        {
          if ((m != 1) && (m != 2))
          {
            if (m == 9) {}
          }
          else {
            switch (m)
            {
            default: 
              break;
            case 18: 
              i -= 1;
              j = 0;
              break;
            case 16: 
            case 17: 
              i += 1;
              j = 1;
              break;
            case 14: 
            case 15: 
              i += 1;
              j = -1;
              continue;
              if (i != 0) {
                break label153;
              }
              return 1;
            }
          }
        }
        else
        {
          if (i == 0) {
            return -1;
          }
          label153:
          k = i;
        }
      }
      if (k == 0) {
        return 0;
      }
      if (j != 0) {
        return j;
      }
      while (charIndex > 0) {
        switch (dirTypeBackward())
        {
        default: 
          break;
        case 18: 
          i += 1;
          break;
        case 16: 
        case 17: 
          if (k == i) {
            return 1;
          }
        case 14: 
        case 15: 
          if (k == i) {
            return -1;
          }
          i -= 1;
        }
      }
      return 0;
    }
    
    int getExitDir()
    {
      charIndex = length;
      int j = 0;
      int i = 0;
      while (charIndex > 0)
      {
        int k = dirTypeBackward();
        if (k != 0)
        {
          if ((k != 1) && (k != 2))
          {
            if (k == 9) {}
          }
          else {
            switch (k)
            {
            default: 
              if (j != 0) {
                continue;
              }
              break;
            case 18: 
              i += 1;
              break;
            case 16: 
            case 17: 
              if (j == i) {
                return 1;
              }
            case 14: 
            case 15: 
              if (j == i) {
                return -1;
              }
              i -= 1;
              continue;
              if (i == 0) {
                return 1;
              }
              if (j != 0) {
                continue;
              }
              break;
            }
          }
        }
        else
        {
          if (i == 0) {
            return -1;
          }
          if (j == 0) {
            j = i;
          }
        }
      }
      return 0;
    }
  }
}
