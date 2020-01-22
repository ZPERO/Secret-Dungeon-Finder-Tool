package androidx.recyclerview.widget;

import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SimplePool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AdapterHelper
  implements OpReorderer.Callback
{
  private static final boolean DEBUG = false;
  static final int POSITION_TYPE_INVISIBLE = 0;
  static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
  private static final String TAG = "AHT";
  final Callback mCallback;
  final boolean mDisableRecycler;
  private int mExistingUpdateTypes = 0;
  Runnable mOnItemProcessedCallback;
  final OpReorderer mOpReorderer;
  final ArrayList<UpdateOp> mPendingUpdates = new ArrayList();
  final ArrayList<UpdateOp> mPostponedList = new ArrayList();
  private Pools.Pool<UpdateOp> mUpdateOpPool = new Pools.SimplePool(30);
  
  AdapterHelper(Callback paramCallback)
  {
    this(paramCallback, false);
  }
  
  AdapterHelper(Callback paramCallback, boolean paramBoolean)
  {
    mCallback = paramCallback;
    mDisableRecycler = paramBoolean;
    mOpReorderer = new OpReorderer(this);
  }
  
  private void applyAdd(UpdateOp paramUpdateOp)
  {
    postponeAndUpdateViewHolders(paramUpdateOp);
  }
  
  private void applyMove(UpdateOp paramUpdateOp)
  {
    postponeAndUpdateViewHolders(paramUpdateOp);
  }
  
  private void applyRemove(UpdateOp paramUpdateOp)
  {
    int i2 = positionStart;
    int m = positionStart + itemCount;
    int i = positionStart;
    int n = 0;
    int j = -1;
    while (i < m)
    {
      int k;
      if ((mCallback.findViewHolder(i) == null) && (!canFindInPreLayout(i)))
      {
        if (j == 1)
        {
          postponeAndUpdateViewHolders(obtainUpdateOp(2, i2, n, null));
          k = 1;
        }
        else
        {
          k = 0;
        }
        j = 0;
      }
      else
      {
        if (j == 0)
        {
          dispatchAndUpdateViewHolders(obtainUpdateOp(2, i2, n, null));
          j = 1;
        }
        else
        {
          j = 0;
        }
        int i1 = 1;
        k = j;
        j = i1;
      }
      if (k != 0)
      {
        k = i - n;
        m -= n;
        i = 1;
      }
      else
      {
        n += 1;
        k = i;
        i = n;
      }
      k += 1;
      n = i;
      i = k;
    }
    UpdateOp localUpdateOp = paramUpdateOp;
    if (n != itemCount)
    {
      recycleUpdateOp(paramUpdateOp);
      localUpdateOp = obtainUpdateOp(2, i2, n, null);
    }
    if (j == 0)
    {
      dispatchAndUpdateViewHolders(localUpdateOp);
      return;
    }
    postponeAndUpdateViewHolders(localUpdateOp);
  }
  
  private void applyUpdate(UpdateOp paramUpdateOp)
  {
    int i3 = positionStart;
    int i4 = itemCount;
    int i = positionStart;
    int j = positionStart;
    int k = 0;
    int m;
    for (int i2 = -1; i < i3 + i4; i2 = m)
    {
      int n;
      int i1;
      if ((mCallback.findViewHolder(i) == null) && (!canFindInPreLayout(i)))
      {
        n = j;
        m = k;
        if (i2 == 1)
        {
          postponeAndUpdateViewHolders(obtainUpdateOp(4, j, k, payload));
          n = i;
          m = 0;
        }
        k = 0;
        j = n;
        i1 = m;
        m = k;
      }
      else
      {
        n = j;
        i1 = k;
        if (i2 == 0)
        {
          dispatchAndUpdateViewHolders(obtainUpdateOp(4, j, k, payload));
          n = i;
          i1 = 0;
        }
        m = 1;
        j = n;
      }
      k = i1 + 1;
      i += 1;
    }
    Object localObject = paramUpdateOp;
    if (k != itemCount)
    {
      localObject = payload;
      recycleUpdateOp(paramUpdateOp);
      localObject = obtainUpdateOp(4, j, k, localObject);
    }
    if (i2 == 0)
    {
      dispatchAndUpdateViewHolders((UpdateOp)localObject);
      return;
    }
    postponeAndUpdateViewHolders((UpdateOp)localObject);
  }
  
  private boolean canFindInPreLayout(int paramInt)
  {
    int k = mPostponedList.size();
    int i = 0;
    while (i < k)
    {
      UpdateOp localUpdateOp = (UpdateOp)mPostponedList.get(i);
      if (cmd == 8)
      {
        if (findPositionOffset(itemCount, i + 1) == paramInt) {
          return true;
        }
      }
      else if (cmd == 1)
      {
        int m = positionStart;
        int n = itemCount;
        int j = positionStart;
        while (j < m + n)
        {
          if (findPositionOffset(j, i + 1) == paramInt) {
            return true;
          }
          j += 1;
        }
      }
      i += 1;
    }
    return false;
  }
  
  private void dispatchAndUpdateViewHolders(UpdateOp paramUpdateOp)
  {
    if ((cmd != 1) && (cmd != 8))
    {
      int k = updatePositionWithPostponed(positionStart, cmd);
      int i = positionStart;
      int j = cmd;
      int m;
      if (j != 2)
      {
        if (j == 4)
        {
          m = 1;
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("op should be remove or update.");
          ((StringBuilder)localObject).append(paramUpdateOp);
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
      }
      else {
        m = 0;
      }
      int n = 1;
      j = 1;
      while (n < itemCount)
      {
        int i2 = updatePositionWithPostponed(positionStart + m * n, cmd);
        int i1 = cmd;
        if (i1 != 2)
        {
          if (i1 != 4) {}
          while (i2 != k + 1)
          {
            i1 = 0;
            break;
          }
        }
        for (;;)
        {
          i1 = 1;
          break label180;
          if (i2 != k) {
            break;
          }
        }
        label180:
        if (i1 != 0)
        {
          j += 1;
        }
        else
        {
          localObject = obtainUpdateOp(cmd, k, j, payload);
          dispatchFirstPassAndUpdateViewHolders((UpdateOp)localObject, i);
          recycleUpdateOp((UpdateOp)localObject);
          k = i;
          if (cmd == 4) {
            k = i + j;
          }
          i1 = i2;
          j = 1;
          i = k;
          k = i1;
        }
        n += 1;
      }
      Object localObject = payload;
      recycleUpdateOp(paramUpdateOp);
      if (j > 0)
      {
        paramUpdateOp = obtainUpdateOp(cmd, k, j, localObject);
        dispatchFirstPassAndUpdateViewHolders(paramUpdateOp, i);
        recycleUpdateOp(paramUpdateOp);
      }
    }
    else
    {
      paramUpdateOp = new IllegalArgumentException("should not dispatch add or move for pre layout");
      throw paramUpdateOp;
    }
  }
  
  private void postponeAndUpdateViewHolders(UpdateOp paramUpdateOp)
  {
    mPostponedList.add(paramUpdateOp);
    int i = cmd;
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 4)
        {
          if (i == 8)
          {
            mCallback.offsetPositionsForMove(positionStart, itemCount);
            return;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unknown update op type for ");
          localStringBuilder.append(paramUpdateOp);
          throw new IllegalArgumentException(localStringBuilder.toString());
        }
        mCallback.markViewHoldersUpdated(positionStart, itemCount, payload);
        return;
      }
      mCallback.offsetPositionsForRemovingLaidOutOrNewView(positionStart, itemCount);
      return;
    }
    mCallback.offsetPositionsForAdd(positionStart, itemCount);
  }
  
  private int updatePositionWithPostponed(int paramInt1, int paramInt2)
  {
    int i = mPostponedList.size() - 1;
    UpdateOp localUpdateOp;
    for (int j = paramInt1; i >= 0; j = paramInt1)
    {
      localUpdateOp = (UpdateOp)mPostponedList.get(i);
      if (cmd == 8)
      {
        int k;
        if (positionStart < itemCount)
        {
          paramInt1 = positionStart;
          k = itemCount;
        }
        else
        {
          paramInt1 = itemCount;
          k = positionStart;
        }
        if ((j >= paramInt1) && (j <= k))
        {
          if (paramInt1 == positionStart)
          {
            if (paramInt2 == 1) {
              itemCount += 1;
            } else if (paramInt2 == 2) {
              itemCount -= 1;
            }
            paramInt1 = j + 1;
          }
          else
          {
            if (paramInt2 == 1) {
              positionStart += 1;
            } else if (paramInt2 == 2) {
              positionStart -= 1;
            }
            paramInt1 = j - 1;
          }
        }
        else
        {
          paramInt1 = j;
          if (j < positionStart) {
            if (paramInt2 == 1)
            {
              positionStart += 1;
              itemCount += 1;
              paramInt1 = j;
            }
            else
            {
              paramInt1 = j;
              if (paramInt2 == 2)
              {
                positionStart -= 1;
                itemCount -= 1;
                paramInt1 = j;
              }
            }
          }
        }
      }
      else if (positionStart <= j)
      {
        if (cmd == 1)
        {
          paramInt1 = j - itemCount;
        }
        else
        {
          paramInt1 = j;
          if (cmd == 2) {
            paramInt1 = j + itemCount;
          }
        }
      }
      else if (paramInt2 == 1)
      {
        positionStart += 1;
        paramInt1 = j;
      }
      else
      {
        paramInt1 = j;
        if (paramInt2 == 2)
        {
          positionStart -= 1;
          paramInt1 = j;
        }
      }
      i -= 1;
    }
    paramInt1 = mPostponedList.size() - 1;
    while (paramInt1 >= 0)
    {
      localUpdateOp = (UpdateOp)mPostponedList.get(paramInt1);
      if (cmd == 8)
      {
        if ((itemCount == positionStart) || (itemCount < 0))
        {
          mPostponedList.remove(paramInt1);
          recycleUpdateOp(localUpdateOp);
        }
      }
      else if (itemCount <= 0)
      {
        mPostponedList.remove(paramInt1);
        recycleUpdateOp(localUpdateOp);
      }
      paramInt1 -= 1;
    }
    return j;
  }
  
  AdapterHelper addUpdateOp(UpdateOp... paramVarArgs)
  {
    Collections.addAll(mPendingUpdates, paramVarArgs);
    return this;
  }
  
  public int applyPendingUpdatesToPosition(int paramInt)
  {
    int m = mPendingUpdates.size();
    int k = 0;
    for (int i = paramInt; k < m; i = paramInt)
    {
      UpdateOp localUpdateOp = (UpdateOp)mPendingUpdates.get(k);
      paramInt = cmd;
      if (paramInt != 1)
      {
        if (paramInt != 2)
        {
          if (paramInt != 8)
          {
            paramInt = i;
          }
          else if (positionStart == i)
          {
            paramInt = itemCount;
          }
          else
          {
            int j = i;
            if (positionStart < i) {
              j = i - 1;
            }
            paramInt = j;
            if (itemCount <= j) {
              paramInt = j + 1;
            }
          }
        }
        else
        {
          paramInt = i;
          if (positionStart <= i)
          {
            if (positionStart + itemCount > i) {
              return -1;
            }
            paramInt = i - itemCount;
          }
        }
      }
      else
      {
        paramInt = i;
        if (positionStart <= i) {
          paramInt = i + itemCount;
        }
      }
      k += 1;
    }
    return i;
  }
  
  void consumePostponedUpdates()
  {
    int j = mPostponedList.size();
    int i = 0;
    while (i < j)
    {
      mCallback.onDispatchSecondPass((UpdateOp)mPostponedList.get(i));
      i += 1;
    }
    recycleUpdateOpsAndClearList(mPostponedList);
    mExistingUpdateTypes = 0;
  }
  
  void consumeUpdatesInOnePass()
  {
    consumePostponedUpdates();
    int j = mPendingUpdates.size();
    int i = 0;
    while (i < j)
    {
      Object localObject = (UpdateOp)mPendingUpdates.get(i);
      int k = cmd;
      if (k != 1)
      {
        if (k != 2)
        {
          if (k != 4)
          {
            if (k == 8)
            {
              mCallback.onDispatchSecondPass((UpdateOp)localObject);
              mCallback.offsetPositionsForMove(positionStart, itemCount);
            }
          }
          else
          {
            mCallback.onDispatchSecondPass((UpdateOp)localObject);
            mCallback.markViewHoldersUpdated(positionStart, itemCount, payload);
          }
        }
        else
        {
          mCallback.onDispatchSecondPass((UpdateOp)localObject);
          mCallback.offsetPositionsForRemovingInvisible(positionStart, itemCount);
        }
      }
      else
      {
        mCallback.onDispatchSecondPass((UpdateOp)localObject);
        mCallback.offsetPositionsForAdd(positionStart, itemCount);
      }
      localObject = mOnItemProcessedCallback;
      if (localObject != null) {
        ((Runnable)localObject).run();
      }
      i += 1;
    }
    recycleUpdateOpsAndClearList(mPendingUpdates);
    mExistingUpdateTypes = 0;
  }
  
  void dispatchFirstPassAndUpdateViewHolders(UpdateOp paramUpdateOp, int paramInt)
  {
    mCallback.onDispatchFirstPass(paramUpdateOp);
    int i = cmd;
    if (i != 2)
    {
      if (i == 4)
      {
        mCallback.markViewHoldersUpdated(paramInt, itemCount, payload);
        return;
      }
      throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
    }
    mCallback.offsetPositionsForRemovingInvisible(paramInt, itemCount);
  }
  
  int findPositionOffset(int paramInt)
  {
    return findPositionOffset(paramInt, 0);
  }
  
  int findPositionOffset(int paramInt1, int paramInt2)
  {
    int k = mPostponedList.size();
    int j = paramInt2;
    for (paramInt2 = paramInt1; j < k; paramInt2 = paramInt1)
    {
      UpdateOp localUpdateOp = (UpdateOp)mPostponedList.get(j);
      if (cmd == 8)
      {
        if (positionStart == paramInt2)
        {
          paramInt1 = itemCount;
        }
        else
        {
          int i = paramInt2;
          if (positionStart < paramInt2) {
            i = paramInt2 - 1;
          }
          paramInt1 = i;
          if (itemCount <= i) {
            paramInt1 = i + 1;
          }
        }
      }
      else
      {
        paramInt1 = paramInt2;
        if (positionStart <= paramInt2) {
          if (cmd == 2)
          {
            if (paramInt2 < positionStart + itemCount) {
              return -1;
            }
            paramInt1 = paramInt2 - itemCount;
          }
          else
          {
            paramInt1 = paramInt2;
            if (cmd == 1) {
              paramInt1 = paramInt2 + itemCount;
            }
          }
        }
      }
      j += 1;
    }
    return paramInt2;
  }
  
  boolean hasAnyUpdateTypes(int paramInt)
  {
    return (paramInt & mExistingUpdateTypes) != 0;
  }
  
  boolean hasPendingUpdates()
  {
    return mPendingUpdates.size() > 0;
  }
  
  boolean hasUpdates()
  {
    return (!mPostponedList.isEmpty()) && (!mPendingUpdates.isEmpty());
  }
  
  public UpdateOp obtainUpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    UpdateOp localUpdateOp = (UpdateOp)mUpdateOpPool.acquire();
    if (localUpdateOp == null) {
      return new UpdateOp(paramInt1, paramInt2, paramInt3, paramObject);
    }
    cmd = paramInt1;
    positionStart = paramInt2;
    itemCount = paramInt3;
    payload = paramObject;
    return localUpdateOp;
  }
  
  boolean onItemRangeChanged(int paramInt1, int paramInt2, Object paramObject)
  {
    if (paramInt2 < 1) {
      return false;
    }
    mPendingUpdates.add(obtainUpdateOp(4, paramInt1, paramInt2, paramObject));
    mExistingUpdateTypes |= 0x4;
    return mPendingUpdates.size() == 1;
  }
  
  boolean onItemRangeInserted(int paramInt1, int paramInt2)
  {
    if (paramInt2 < 1) {
      return false;
    }
    mPendingUpdates.add(obtainUpdateOp(1, paramInt1, paramInt2, null));
    mExistingUpdateTypes |= 0x1;
    return mPendingUpdates.size() == 1;
  }
  
  boolean onItemRangeMoved(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == paramInt2) {
      return false;
    }
    if (paramInt3 == 1)
    {
      mPendingUpdates.add(obtainUpdateOp(8, paramInt1, paramInt2, null));
      mExistingUpdateTypes |= 0x8;
      if (mPendingUpdates.size() == 1) {
        return true;
      }
    }
    else
    {
      throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
    }
    return false;
  }
  
  boolean onItemRangeRemoved(int paramInt1, int paramInt2)
  {
    if (paramInt2 < 1) {
      return false;
    }
    mPendingUpdates.add(obtainUpdateOp(2, paramInt1, paramInt2, null));
    mExistingUpdateTypes |= 0x2;
    return mPendingUpdates.size() == 1;
  }
  
  void preProcess()
  {
    mOpReorderer.reorderOps(mPendingUpdates);
    int j = mPendingUpdates.size();
    int i = 0;
    while (i < j)
    {
      Object localObject = (UpdateOp)mPendingUpdates.get(i);
      int k = cmd;
      if (k != 1)
      {
        if (k != 2)
        {
          if (k != 4)
          {
            if (k == 8) {
              applyMove((UpdateOp)localObject);
            }
          }
          else {
            applyUpdate((UpdateOp)localObject);
          }
        }
        else {
          applyRemove((UpdateOp)localObject);
        }
      }
      else {
        applyAdd((UpdateOp)localObject);
      }
      localObject = mOnItemProcessedCallback;
      if (localObject != null) {
        ((Runnable)localObject).run();
      }
      i += 1;
    }
    mPendingUpdates.clear();
  }
  
  public void recycleUpdateOp(UpdateOp paramUpdateOp)
  {
    if (!mDisableRecycler)
    {
      payload = null;
      mUpdateOpPool.release(paramUpdateOp);
    }
  }
  
  void recycleUpdateOpsAndClearList(List paramList)
  {
    int j = paramList.size();
    int i = 0;
    while (i < j)
    {
      recycleUpdateOp((UpdateOp)paramList.get(i));
      i += 1;
    }
    paramList.clear();
  }
  
  void reset()
  {
    recycleUpdateOpsAndClearList(mPendingUpdates);
    recycleUpdateOpsAndClearList(mPostponedList);
    mExistingUpdateTypes = 0;
  }
  
  static abstract interface Callback
  {
    public abstract RecyclerView.ViewHolder findViewHolder(int paramInt);
    
    public abstract void markViewHoldersUpdated(int paramInt1, int paramInt2, Object paramObject);
    
    public abstract void offsetPositionsForAdd(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForMove(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForRemovingInvisible(int paramInt1, int paramInt2);
    
    public abstract void offsetPositionsForRemovingLaidOutOrNewView(int paramInt1, int paramInt2);
    
    public abstract void onDispatchFirstPass(AdapterHelper.UpdateOp paramUpdateOp);
    
    public abstract void onDispatchSecondPass(AdapterHelper.UpdateOp paramUpdateOp);
  }
  
  static class UpdateOp
  {
    static final int ADD = 1;
    static final int MOVE = 8;
    static final int POOL_SIZE = 30;
    static final int REMOVE = 2;
    static final int UPDATE = 4;
    int cmd;
    int itemCount;
    Object payload;
    int positionStart;
    
    UpdateOp(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
    {
      cmd = paramInt1;
      positionStart = paramInt2;
      itemCount = paramInt3;
      payload = paramObject;
    }
    
    String cmdToString()
    {
      int i = cmd;
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 4)
          {
            if (i != 8) {
              return "??";
            }
            return "mv";
          }
          return "up";
        }
        return "rm";
      }
      return "add";
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {
        return true;
      }
      if (paramObject != null)
      {
        if (getClass() != paramObject.getClass()) {
          return false;
        }
        paramObject = (UpdateOp)paramObject;
        int i = cmd;
        if (i != cmd) {
          return false;
        }
        if ((i == 8) && (Math.abs(itemCount - positionStart) == 1) && (itemCount == positionStart) && (positionStart == itemCount)) {
          return true;
        }
        if (itemCount != itemCount) {
          return false;
        }
        if (positionStart != positionStart) {
          return false;
        }
        Object localObject = payload;
        if (localObject != null)
        {
          if (!localObject.equals(payload)) {
            return false;
          }
        }
        else
        {
          if (payload == null) {
            break label151;
          }
          return false;
        }
        return true;
      }
      else
      {
        return false;
      }
      label151:
      return true;
    }
    
    public int hashCode()
    {
      return (cmd * 31 + positionStart) * 31 + itemCount;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append("[");
      localStringBuilder.append(cmdToString());
      localStringBuilder.append(",s:");
      localStringBuilder.append(positionStart);
      localStringBuilder.append("c:");
      localStringBuilder.append(itemCount);
      localStringBuilder.append(",p:");
      localStringBuilder.append(payload);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
}