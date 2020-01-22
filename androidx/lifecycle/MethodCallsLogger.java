package androidx.lifecycle;

import java.util.HashMap;
import java.util.Map;

public class MethodCallsLogger
{
  private Map<String, Integer> mCalledMethods = new HashMap();
  
  public MethodCallsLogger() {}
  
  public boolean approveCall(String paramString, int paramInt)
  {
    Integer localInteger = (Integer)mCalledMethods.get(paramString);
    int j = 0;
    int i;
    if (localInteger != null) {
      i = localInteger.intValue();
    } else {
      i = 0;
    }
    if ((i & paramInt) != 0) {
      j = 1;
    }
    mCalledMethods.put(paramString, Integer.valueOf(paramInt | i));
    return j ^ 0x1;
  }
}
