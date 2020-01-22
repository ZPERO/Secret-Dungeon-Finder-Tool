package androidx.lifecycle;

import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap.IteratorWithAdditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

public class LifecycleRegistry
  extends Lifecycle
{
  private int mAddingObserverCounter = 0;
  private boolean mHandlingEvent = false;
  private final WeakReference<LifecycleOwner> mLifecycleOwner;
  private boolean mNewEventOccurred = false;
  private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap = new FastSafeIterableMap();
  private ArrayList<Lifecycle.State> mParentStates = new ArrayList();
  private Lifecycle.State mState;
  
  public LifecycleRegistry(LifecycleOwner paramLifecycleOwner)
  {
    mLifecycleOwner = new WeakReference(paramLifecycleOwner);
    mState = Lifecycle.State.INITIALIZED;
  }
  
  private void backwardPass(LifecycleOwner paramLifecycleOwner)
  {
    Iterator localIterator = mObserverMap.descendingIterator();
    while ((localIterator.hasNext()) && (!mNewEventOccurred))
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      ObserverWithState localObserverWithState = (ObserverWithState)localEntry.getValue();
      while ((mState.compareTo(mState) > 0) && (!mNewEventOccurred) && (mObserverMap.contains(localEntry.getKey())))
      {
        Lifecycle.Event localEvent = downEvent(mState);
        pushParentState(getStateAfter(localEvent));
        localObserverWithState.dispatchEvent(paramLifecycleOwner, localEvent);
        popParentState();
      }
    }
  }
  
  private Lifecycle.State calculateTargetState(LifecycleObserver paramLifecycleObserver)
  {
    paramLifecycleObserver = mObserverMap.ceil(paramLifecycleObserver);
    Object localObject = null;
    if (paramLifecycleObserver != null) {
      paramLifecycleObserver = getValuemState;
    } else {
      paramLifecycleObserver = null;
    }
    if (!mParentStates.isEmpty())
    {
      localObject = mParentStates;
      localObject = (Lifecycle.State)((ArrayList)localObject).get(((ArrayList)localObject).size() - 1);
    }
    return print(print(mState, paramLifecycleObserver), (Lifecycle.State)localObject);
  }
  
  private static Lifecycle.Event downEvent(Lifecycle.State paramState)
  {
    int i = 1.$SwitchMap$androidx$lifecycle$Lifecycle$State[paramState.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4)
          {
            if (i != 5)
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Unexpected state value ");
              localStringBuilder.append(paramState);
              throw new IllegalArgumentException(localStringBuilder.toString());
            }
            throw new IllegalArgumentException();
          }
          return Lifecycle.Event.ON_PAUSE;
        }
        return Lifecycle.Event.ON_STOP;
      }
      return Lifecycle.Event.ON_DESTROY;
    }
    throw new IllegalArgumentException();
  }
  
  private void forwardPass(LifecycleOwner paramLifecycleOwner)
  {
    SafeIterableMap.IteratorWithAdditions localIteratorWithAdditions = mObserverMap.iteratorWithAdditions();
    while ((localIteratorWithAdditions.hasNext()) && (!mNewEventOccurred))
    {
      Map.Entry localEntry = (Map.Entry)localIteratorWithAdditions.next();
      ObserverWithState localObserverWithState = (ObserverWithState)localEntry.getValue();
      while ((mState.compareTo(mState) < 0) && (!mNewEventOccurred) && (mObserverMap.contains(localEntry.getKey())))
      {
        pushParentState(mState);
        localObserverWithState.dispatchEvent(paramLifecycleOwner, upEvent(mState));
        popParentState();
      }
    }
  }
  
  static Lifecycle.State getStateAfter(Lifecycle.Event paramEvent)
  {
    switch (1.$SwitchMap$androidx$lifecycle$Lifecycle$Event[paramEvent.ordinal()])
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected event value ");
      localStringBuilder.append(paramEvent);
      throw new IllegalArgumentException(localStringBuilder.toString());
    case 6: 
      return Lifecycle.State.DESTROYED;
    case 5: 
      return Lifecycle.State.RESUMED;
    case 3: 
    case 4: 
      return Lifecycle.State.STARTED;
    }
    return Lifecycle.State.CREATED;
  }
  
  private boolean isSynced()
  {
    if (mObserverMap.size() == 0) {
      return true;
    }
    Lifecycle.State localState1 = mObserverMap.eldest().getValue()).mState;
    Lifecycle.State localState2 = mObserverMap.newest().getValue()).mState;
    return (localState1 == localState2) && (mState == localState2);
  }
  
  private void moveToState(Lifecycle.State paramState)
  {
    if (mState == paramState) {
      return;
    }
    mState = paramState;
    if ((!mHandlingEvent) && (mAddingObserverCounter == 0))
    {
      mHandlingEvent = true;
      sync();
      mHandlingEvent = false;
      return;
    }
    mNewEventOccurred = true;
  }
  
  private void popParentState()
  {
    ArrayList localArrayList = mParentStates;
    localArrayList.remove(localArrayList.size() - 1);
  }
  
  static Lifecycle.State print(Lifecycle.State paramState1, Lifecycle.State paramState2)
  {
    if ((paramState2 != null) && (paramState2.compareTo(paramState1) < 0)) {
      return paramState2;
    }
    return paramState1;
  }
  
  private void pushParentState(Lifecycle.State paramState)
  {
    mParentStates.add(paramState);
  }
  
  private void sync()
  {
    Object localObject = (LifecycleOwner)mLifecycleOwner.get();
    if (localObject != null)
    {
      while (!isSynced())
      {
        mNewEventOccurred = false;
        if (mState.compareTo(mObserverMap.eldest().getValue()).mState) < 0) {
          backwardPass((LifecycleOwner)localObject);
        }
        Map.Entry localEntry = mObserverMap.newest();
        if ((!mNewEventOccurred) && (localEntry != null) && (mState.compareTo(getValuemState) > 0)) {
          forwardPass((LifecycleOwner)localObject);
        }
      }
      mNewEventOccurred = false;
      return;
    }
    localObject = new IllegalStateException("LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state.");
    throw ((Throwable)localObject);
  }
  
  private static Lifecycle.Event upEvent(Lifecycle.State paramState)
  {
    int i = 1.$SwitchMap$androidx$lifecycle$Lifecycle$State[paramState.ordinal()];
    if (i != 1) {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4)
          {
            if (i != 5)
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Unexpected state value ");
              localStringBuilder.append(paramState);
              throw new IllegalArgumentException(localStringBuilder.toString());
            }
          }
          else {
            throw new IllegalArgumentException();
          }
        }
        else {
          return Lifecycle.Event.ON_RESUME;
        }
      }
      else {
        return Lifecycle.Event.ON_START;
      }
    }
    return Lifecycle.Event.ON_CREATE;
  }
  
  public void addObserver(LifecycleObserver paramLifecycleObserver)
  {
    if (mState == Lifecycle.State.DESTROYED) {
      localState = Lifecycle.State.DESTROYED;
    } else {
      localState = Lifecycle.State.INITIALIZED;
    }
    ObserverWithState localObserverWithState = new ObserverWithState(paramLifecycleObserver, localState);
    if ((ObserverWithState)mObserverMap.putIfAbsent(paramLifecycleObserver, localObserverWithState) != null) {
      return;
    }
    LifecycleOwner localLifecycleOwner = (LifecycleOwner)mLifecycleOwner.get();
    if (localLifecycleOwner == null) {
      return;
    }
    int i;
    if ((mAddingObserverCounter == 0) && (!mHandlingEvent)) {
      i = 0;
    } else {
      i = 1;
    }
    Lifecycle.State localState = calculateTargetState(paramLifecycleObserver);
    mAddingObserverCounter += 1;
    while ((mState.compareTo(localState) < 0) && (mObserverMap.contains(paramLifecycleObserver)))
    {
      pushParentState(mState);
      localObserverWithState.dispatchEvent(localLifecycleOwner, upEvent(mState));
      popParentState();
      localState = calculateTargetState(paramLifecycleObserver);
    }
    if (i == 0) {
      sync();
    }
    mAddingObserverCounter -= 1;
  }
  
  public Lifecycle.State getCurrentState()
  {
    return mState;
  }
  
  public int getObserverCount()
  {
    return mObserverMap.size();
  }
  
  public void handleLifecycleEvent(Lifecycle.Event paramEvent)
  {
    moveToState(getStateAfter(paramEvent));
  }
  
  public void markState(Lifecycle.State paramState)
  {
    setCurrentState(paramState);
  }
  
  public void removeObserver(LifecycleObserver paramLifecycleObserver)
  {
    mObserverMap.remove(paramLifecycleObserver);
  }
  
  public void setCurrentState(Lifecycle.State paramState)
  {
    moveToState(paramState);
  }
  
  static class ObserverWithState
  {
    LifecycleEventObserver mLifecycleObserver;
    Lifecycle.State mState;
    
    ObserverWithState(LifecycleObserver paramLifecycleObserver, Lifecycle.State paramState)
    {
      mLifecycleObserver = Lifecycling.lifecycleEventObserver(paramLifecycleObserver);
      mState = paramState;
    }
    
    void dispatchEvent(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent)
    {
      Lifecycle.State localState = LifecycleRegistry.getStateAfter(paramEvent);
      mState = LifecycleRegistry.print(mState, localState);
      mLifecycleObserver.onStateChanged(paramLifecycleOwner, paramEvent);
      mState = localState;
    }
  }
}
