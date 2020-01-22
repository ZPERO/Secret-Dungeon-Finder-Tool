package androidx.core.os;

import android.os.Build.VERSION;
import android.os.LocaleList;
import java.util.Locale;

public final class LocaleListCompat
{
  private static final LocaleListCompat sEmptyLocaleList = create(new Locale[0]);
  private LocaleListInterface mImpl;
  
  private LocaleListCompat(LocaleListInterface paramLocaleListInterface)
  {
    mImpl = paramLocaleListInterface;
  }
  
  public static LocaleListCompat create(Locale... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return wrap(new LocaleList(paramVarArgs));
    }
    return new LocaleListCompat(new LocaleListCompatWrapper(paramVarArgs));
  }
  
  static Locale forLanguageTagCompat(String paramString)
  {
    if (paramString.contains("-"))
    {
      localObject = paramString.split("-", -1);
      if (localObject.length > 2) {
        return new Locale(localObject[0], localObject[1], localObject[2]);
      }
      if (localObject.length > 1) {
        return new Locale(localObject[0], localObject[1]);
      }
      if (localObject.length == 1) {
        return new Locale(localObject[0]);
      }
    }
    else
    {
      if (!paramString.contains("_")) {
        break label194;
      }
      localObject = paramString.split("_", -1);
      if (localObject.length > 2) {
        return new Locale(localObject[0], localObject[1], localObject[2]);
      }
      if (localObject.length > 1) {
        return new Locale(localObject[0], localObject[1]);
      }
      if (localObject.length == 1) {
        return new Locale(localObject[0]);
      }
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Can not parse language tag: [");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("]");
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
    label194:
    return new Locale(paramString);
  }
  
  public static LocaleListCompat forLanguageTags(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()))
    {
      String[] arrayOfString = paramString.split(",", -1);
      Locale[] arrayOfLocale = new Locale[arrayOfString.length];
      int i = 0;
      while (i < arrayOfLocale.length)
      {
        if (Build.VERSION.SDK_INT >= 21) {
          paramString = Locale.forLanguageTag(arrayOfString[i]);
        } else {
          paramString = forLanguageTagCompat(arrayOfString[i]);
        }
        arrayOfLocale[i] = paramString;
        i += 1;
      }
      return create(arrayOfLocale);
    }
    return getEmptyLocaleList();
  }
  
  public static LocaleListCompat getAdjustedDefault()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return wrap(LocaleList.getAdjustedDefault());
    }
    return create(new Locale[] { Locale.getDefault() });
  }
  
  public static LocaleListCompat getDefault()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return wrap(LocaleList.getDefault());
    }
    return create(new Locale[] { Locale.getDefault() });
  }
  
  public static LocaleListCompat getEmptyLocaleList()
  {
    return sEmptyLocaleList;
  }
  
  public static LocaleListCompat wrap(LocaleList paramLocaleList)
  {
    return new LocaleListCompat(new LocaleListPlatformWrapper(paramLocaleList));
  }
  
  public static LocaleListCompat wrap(Object paramObject)
  {
    return wrap((LocaleList)paramObject);
  }
  
  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof LocaleListCompat)) && (mImpl.equals(mImpl));
  }
  
  public Locale getFirstMatch(String[] paramArrayOfString)
  {
    return mImpl.getFirstMatch(paramArrayOfString);
  }
  
  public Locale getSongs(int paramInt)
  {
    return mImpl.getList(paramInt);
  }
  
  public int hashCode()
  {
    return mImpl.hashCode();
  }
  
  public int indexOf(Locale paramLocale)
  {
    return mImpl.indexOf(paramLocale);
  }
  
  public boolean isEmpty()
  {
    return mImpl.isEmpty();
  }
  
  public int size()
  {
    return mImpl.size();
  }
  
  public String toLanguageTags()
  {
    return mImpl.toLanguageTags();
  }
  
  public String toString()
  {
    return mImpl.toString();
  }
  
  public Object unwrap()
  {
    return mImpl.getLocaleList();
  }
}
