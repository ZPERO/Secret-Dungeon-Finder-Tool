package com.airbnb.lottie;

import android.util.Log;
import androidx.collection.ArraySet;
import androidx.core.util.Pair;
import com.airbnb.lottie.utils.MeanCalculator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PerformanceTracker
{
  private boolean enabled = false;
  private final Comparator<Pair<String, Float>> floatComparator = new Comparator()
  {
    public int compare(Pair paramAnonymousPair1, Pair paramAnonymousPair2)
    {
      float f1 = ((Float)second).floatValue();
      float f2 = ((Float)second).floatValue();
      if (f2 > f1) {
        return 1;
      }
      if (f1 > f2) {
        return -1;
      }
      return 0;
    }
  };
  private final Set<FrameListener> frameListeners = new ArraySet();
  private final Map<String, MeanCalculator> layerRenderTimes = new HashMap();
  
  public PerformanceTracker() {}
  
  public void addFrameListener(FrameListener paramFrameListener)
  {
    frameListeners.add(paramFrameListener);
  }
  
  public void clearRenderTimes()
  {
    layerRenderTimes.clear();
  }
  
  public List getSortedRenderTimes()
  {
    if (!enabled) {
      return Collections.emptyList();
    }
    ArrayList localArrayList = new ArrayList(layerRenderTimes.size());
    Iterator localIterator = layerRenderTimes.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localArrayList.add(new Pair(localEntry.getKey(), Float.valueOf(((MeanCalculator)localEntry.getValue()).getMean())));
    }
    Collections.sort(localArrayList, floatComparator);
    return localArrayList;
  }
  
  public void logRenderTimes()
  {
    if (!enabled) {
      return;
    }
    List localList = getSortedRenderTimes();
    Log.d("LOTTIE", "Render times:");
    int i = 0;
    while (i < localList.size())
    {
      Pair localPair = (Pair)localList.get(i);
      Log.d("LOTTIE", String.format("\t\t%30s:%.2f", new Object[] { first, second }));
      i += 1;
    }
  }
  
  public void recordRenderTime(String paramString, float paramFloat)
  {
    if (!enabled) {
      return;
    }
    MeanCalculator localMeanCalculator2 = (MeanCalculator)layerRenderTimes.get(paramString);
    MeanCalculator localMeanCalculator1 = localMeanCalculator2;
    if (localMeanCalculator2 == null)
    {
      localMeanCalculator1 = new MeanCalculator();
      layerRenderTimes.put(paramString, localMeanCalculator1);
    }
    localMeanCalculator1.set(paramFloat);
    if (paramString.equals("__container"))
    {
      paramString = frameListeners.iterator();
      while (paramString.hasNext()) {
        ((FrameListener)paramString.next()).onFrameRendered(paramFloat);
      }
    }
  }
  
  public void removeFrameListener(FrameListener paramFrameListener)
  {
    frameListeners.remove(paramFrameListener);
  }
  
  void setEnabled(boolean paramBoolean)
  {
    enabled = paramBoolean;
  }
  
  public static abstract interface FrameListener
  {
    public abstract void onFrameRendered(float paramFloat);
  }
}