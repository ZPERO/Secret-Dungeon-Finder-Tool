package androidx.constraintlayout.solver;

import java.io.PrintStream;
import java.util.Arrays;

public class ArrayLinkedVariables
{
  private static final boolean DEBUG = false;
  private static final boolean FULL_NEW_CHECK = false;
  private static final int NONE = -1;
  private int ROW_SIZE = 8;
  private SolverVariable candidate = null;
  int currentSize = 0;
  private int[] mArrayIndices;
  private int[] mArrayNextIndices;
  private float[] mArrayValues;
  private final Cache mCache;
  private boolean mDidFillOnce;
  private int mHead;
  private int mLast;
  private final ArrayRow mRow;
  
  ArrayLinkedVariables(ArrayRow paramArrayRow, Cache paramCache)
  {
    int i = ROW_SIZE;
    mArrayIndices = new int[i];
    mArrayNextIndices = new int[i];
    mArrayValues = new float[i];
    mHead = -1;
    mLast = -1;
    mDidFillOnce = false;
    mRow = paramArrayRow;
    mCache = paramCache;
  }
  
  private boolean isNew(SolverVariable paramSolverVariable, LinearSystem paramLinearSystem)
  {
    return usageInRowCount <= 1;
  }
  
  SolverVariable chooseSubject(LinearSystem paramLinearSystem)
  {
    int j = mHead;
    Object localObject4 = null;
    int i = 0;
    Object localObject3 = null;
    float f5 = 0.0F;
    boolean bool4 = false;
    float f4 = 0.0F;
    label130:
    boolean bool2;
    for (boolean bool3 = false; (j != -1) && (i < currentSize); bool3 = bool2)
    {
      float f2 = mArrayValues[j];
      SolverVariable localSolverVariable = mCache.mIndexedVariables[mArrayIndices[j]];
      if (f2 < 0.0F)
      {
        f1 = f2;
        if (f2 <= -0.001F) {
          break label130;
        }
        mArrayValues[j] = 0.0F;
        localSolverVariable.removeFromRow(mRow);
      }
      else
      {
        f1 = f2;
        if (f2 >= 0.001F) {
          break label130;
        }
        mArrayValues[j] = 0.0F;
        localSolverVariable.removeFromRow(mRow);
      }
      float f1 = 0.0F;
      Object localObject1 = localObject4;
      Object localObject2 = localObject3;
      f2 = f5;
      boolean bool1 = bool4;
      float f3 = f4;
      bool2 = bool3;
      if (f1 != 0.0F) {
        if (mType == SolverVariable.Type.UNRESTRICTED)
        {
          if (localObject3 == null) {}
          for (bool1 = isNew(localSolverVariable, paramLinearSystem);; bool1 = isNew(localSolverVariable, paramLinearSystem))
          {
            localObject1 = localObject4;
            localObject2 = localSolverVariable;
            f2 = f1;
            f3 = f4;
            bool2 = bool3;
            break label502;
            if (f5 <= f1) {
              break;
            }
          }
          localObject1 = localObject4;
          localObject2 = localObject3;
          f2 = f5;
          bool1 = bool4;
          f3 = f4;
          bool2 = bool3;
          if (!bool4)
          {
            localObject1 = localObject4;
            localObject2 = localObject3;
            f2 = f5;
            bool1 = bool4;
            f3 = f4;
            bool2 = bool3;
            if (isNew(localSolverVariable, paramLinearSystem))
            {
              bool1 = true;
              localObject1 = localObject4;
              localObject2 = localSolverVariable;
              f2 = f1;
              f3 = f4;
              bool2 = bool3;
            }
          }
        }
        else
        {
          localObject1 = localObject4;
          localObject2 = localObject3;
          f2 = f5;
          bool1 = bool4;
          f3 = f4;
          bool2 = bool3;
          if (localObject3 == null)
          {
            localObject1 = localObject4;
            localObject2 = localObject3;
            f2 = f5;
            bool1 = bool4;
            f3 = f4;
            bool2 = bool3;
            if (f1 < 0.0F)
            {
              if (localObject4 == null) {}
              for (bool2 = isNew(localSolverVariable, paramLinearSystem);; bool2 = isNew(localSolverVariable, paramLinearSystem))
              {
                localObject1 = localSolverVariable;
                localObject2 = localObject3;
                f2 = f5;
                bool1 = bool4;
                f3 = f1;
                break label502;
                if (f4 <= f1) {
                  break;
                }
              }
              localObject1 = localObject4;
              localObject2 = localObject3;
              f2 = f5;
              bool1 = bool4;
              f3 = f4;
              bool2 = bool3;
              if (!bool3)
              {
                localObject1 = localObject4;
                localObject2 = localObject3;
                f2 = f5;
                bool1 = bool4;
                f3 = f4;
                bool2 = bool3;
                if (isNew(localSolverVariable, paramLinearSystem))
                {
                  bool2 = true;
                  f3 = f1;
                  bool1 = bool4;
                  f2 = f5;
                  localObject2 = localObject3;
                  localObject1 = localSolverVariable;
                }
              }
            }
          }
        }
      }
      label502:
      j = mArrayNextIndices[j];
      i += 1;
      localObject4 = localObject1;
      localObject3 = localObject2;
      f5 = f2;
      bool4 = bool1;
      f4 = f3;
    }
    if (localObject3 != null) {
      return localObject3;
    }
    return localObject4;
  }
  
  public final void clear()
  {
    int j = mHead;
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      SolverVariable localSolverVariable = mCache.mIndexedVariables[mArrayIndices[j]];
      if (localSolverVariable != null) {
        localSolverVariable.removeFromRow(mRow);
      }
      j = mArrayNextIndices[j];
      i += 1;
    }
    mHead = -1;
    mLast = -1;
    mDidFillOnce = false;
    currentSize = 0;
  }
  
  public final void combine(SolverVariable paramSolverVariable, float paramFloat)
  {
    if (paramFloat == 0.0F)
    {
      remove(paramSolverVariable, true);
      return;
    }
    int i = mHead;
    Object localObject;
    if (i == -1)
    {
      mHead = 0;
      localObject = mArrayValues;
      i = mHead;
      localObject[i] = paramFloat;
      mArrayIndices[i] = get;
      mArrayNextIndices[mHead] = -1;
      usageInRowCount += 1;
      paramSolverVariable.addToRow(mRow);
      currentSize += 1;
      if (!mDidFillOnce)
      {
        mLast += 1;
        i = mLast;
        paramSolverVariable = mArrayIndices;
        if (i >= paramSolverVariable.length)
        {
          mDidFillOnce = true;
          mLast = (paramSolverVariable.length - 1);
        }
      }
    }
    else
    {
      int j = 0;
      int m = -1;
      while ((i != -1) && (j < currentSize))
      {
        if (mArrayIndices[i] == get)
        {
          mArrayValues[i] = paramFloat;
          return;
        }
        if (mArrayIndices[i] < get) {
          m = i;
        }
        i = mArrayNextIndices[i];
        j += 1;
      }
      i = mLast;
      if (mDidFillOnce)
      {
        localObject = mArrayIndices;
        if (localObject[i] != -1) {
          i = localObject.length;
        }
      }
      else
      {
        i += 1;
      }
      localObject = mArrayIndices;
      j = i;
      if (i >= localObject.length)
      {
        j = i;
        if (currentSize < localObject.length)
        {
          int k = 0;
          for (;;)
          {
            localObject = mArrayIndices;
            j = i;
            if (k >= localObject.length) {
              break;
            }
            if (localObject[k] == -1)
            {
              j = k;
              break;
            }
            k += 1;
          }
        }
      }
      localObject = mArrayIndices;
      i = j;
      if (j >= localObject.length)
      {
        i = localObject.length;
        ROW_SIZE *= 2;
        mDidFillOnce = false;
        mLast = (i - 1);
        mArrayValues = Arrays.copyOf(mArrayValues, ROW_SIZE);
        mArrayIndices = Arrays.copyOf(mArrayIndices, ROW_SIZE);
        mArrayNextIndices = Arrays.copyOf(mArrayNextIndices, ROW_SIZE);
      }
      mArrayIndices[i] = get;
      mArrayValues[i] = paramFloat;
      if (m != -1)
      {
        localObject = mArrayNextIndices;
        localObject[i] = localObject[m];
        localObject[m] = i;
      }
      else
      {
        mArrayNextIndices[i] = mHead;
        mHead = i;
      }
      usageInRowCount += 1;
      paramSolverVariable.addToRow(mRow);
      currentSize += 1;
      if (!mDidFillOnce) {
        mLast += 1;
      }
      if (currentSize >= mArrayIndices.length) {
        mDidFillOnce = true;
      }
      i = mLast;
      paramSolverVariable = mArrayIndices;
      if (i >= paramSolverVariable.length)
      {
        mDidFillOnce = true;
        mLast = (paramSolverVariable.length - 1);
      }
    }
  }
  
  final boolean containsKey(SolverVariable paramSolverVariable)
  {
    int j = mHead;
    if (j == -1) {
      return false;
    }
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      if (mArrayIndices[j] == get) {
        return true;
      }
      j = mArrayNextIndices[j];
      i += 1;
    }
    return false;
  }
  
  public final float containsValue(SolverVariable paramSolverVariable)
  {
    int j = mHead;
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      if (mArrayIndices[j] == get) {
        return mArrayValues[j];
      }
      j = mArrayNextIndices[j];
      i += 1;
    }
    return 0.0F;
  }
  
  public void display()
  {
    int j = currentSize;
    System.out.print("{ ");
    int i = 0;
    while (i < j)
    {
      SolverVariable localSolverVariable = getVariable(i);
      if (localSolverVariable != null)
      {
        PrintStream localPrintStream = System.out;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(localSolverVariable);
        localStringBuilder.append(" = ");
        localStringBuilder.append(getVariableValue(i));
        localStringBuilder.append(" ");
        localPrintStream.print(localStringBuilder.toString());
      }
      i += 1;
    }
    System.out.println(" }");
  }
  
  void divideByAmount(float paramFloat)
  {
    int j = mHead;
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      float[] arrayOfFloat = mArrayValues;
      arrayOfFloat[j] /= paramFloat;
      j = mArrayNextIndices[j];
      i += 1;
    }
  }
  
  SolverVariable getPivotCandidate()
  {
    Object localObject1 = candidate;
    Object localObject2 = localObject1;
    if (localObject1 == null)
    {
      int j = mHead;
      int i = 0;
      for (localObject1 = null;; localObject1 = localObject2)
      {
        localObject2 = localObject1;
        if (j == -1) {
          break;
        }
        localObject2 = localObject1;
        if (i >= currentSize) {
          break;
        }
        localObject2 = localObject1;
        if (mArrayValues[j] < 0.0F)
        {
          SolverVariable localSolverVariable = mCache.mIndexedVariables[mArrayIndices[j]];
          if (localObject1 != null)
          {
            localObject2 = localObject1;
            if (strength >= strength) {}
          }
          else
          {
            localObject2 = localSolverVariable;
          }
        }
        j = mArrayNextIndices[j];
        i += 1;
      }
    }
    return localObject2;
  }
  
  SolverVariable getPivotCandidate(boolean[] paramArrayOfBoolean, SolverVariable paramSolverVariable)
  {
    int j = mHead;
    int i = 0;
    Object localObject1 = null;
    float f2;
    for (float f1 = 0.0F; (j != -1) && (i < currentSize); f1 = f2)
    {
      Object localObject2 = localObject1;
      f2 = f1;
      if (mArrayValues[j] < 0.0F)
      {
        SolverVariable localSolverVariable = mCache.mIndexedVariables[mArrayIndices[j]];
        if (paramArrayOfBoolean != null)
        {
          localObject2 = localObject1;
          f2 = f1;
          if (paramArrayOfBoolean[get] != 0) {}
        }
        else
        {
          localObject2 = localObject1;
          f2 = f1;
          if (localSolverVariable != paramSolverVariable) {
            if (mType != SolverVariable.Type.SLACK)
            {
              localObject2 = localObject1;
              f2 = f1;
              if (mType != SolverVariable.Type.ERROR) {}
            }
            else
            {
              float f3 = mArrayValues[j];
              localObject2 = localObject1;
              f2 = f1;
              if (f3 < f1)
              {
                localObject2 = localSolverVariable;
                f2 = f3;
              }
            }
          }
        }
      }
      j = mArrayNextIndices[j];
      i += 1;
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  final SolverVariable getVariable(int paramInt)
  {
    int j = mHead;
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      if (i == paramInt) {
        return mCache.mIndexedVariables[mArrayIndices[j]];
      }
      j = mArrayNextIndices[j];
      i += 1;
    }
    return null;
  }
  
  final float getVariableValue(int paramInt)
  {
    int j = mHead;
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      if (i == paramInt) {
        return mArrayValues[j];
      }
      j = mArrayNextIndices[j];
      i += 1;
    }
    return 0.0F;
  }
  
  boolean hasAtLeastOnePositiveVariable()
  {
    int j = mHead;
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      if (mArrayValues[j] > 0.0F) {
        return true;
      }
      j = mArrayNextIndices[j];
      i += 1;
    }
    return false;
  }
  
  final void intersect(SolverVariable paramSolverVariable, float paramFloat, boolean paramBoolean)
  {
    if (paramFloat == 0.0F) {
      return;
    }
    int i = mHead;
    Object localObject;
    if (i == -1)
    {
      mHead = 0;
      localObject = mArrayValues;
      i = mHead;
      localObject[i] = paramFloat;
      mArrayIndices[i] = get;
      mArrayNextIndices[mHead] = -1;
      usageInRowCount += 1;
      paramSolverVariable.addToRow(mRow);
      currentSize += 1;
      if (!mDidFillOnce)
      {
        mLast += 1;
        i = mLast;
        paramSolverVariable = mArrayIndices;
        if (i >= paramSolverVariable.length)
        {
          mDidFillOnce = true;
          mLast = (paramSolverVariable.length - 1);
        }
      }
    }
    else
    {
      int j = 0;
      int m = -1;
      while ((i != -1) && (j < currentSize))
      {
        if (mArrayIndices[i] == get)
        {
          localObject = mArrayValues;
          localObject[i] += paramFloat;
          if (localObject[i] != 0.0F) {
            return;
          }
          if (i == mHead)
          {
            mHead = mArrayNextIndices[i];
          }
          else
          {
            localObject = mArrayNextIndices;
            localObject[m] = localObject[i];
          }
          if (paramBoolean) {
            paramSolverVariable.removeFromRow(mRow);
          }
          if (mDidFillOnce) {
            mLast = i;
          }
          usageInRowCount -= 1;
          currentSize -= 1;
          return;
        }
        if (mArrayIndices[i] < get) {
          m = i;
        }
        i = mArrayNextIndices[i];
        j += 1;
      }
      i = mLast;
      if (mDidFillOnce)
      {
        localObject = mArrayIndices;
        if (localObject[i] != -1) {
          i = localObject.length;
        }
      }
      else
      {
        i += 1;
      }
      localObject = mArrayIndices;
      j = i;
      if (i >= localObject.length)
      {
        j = i;
        if (currentSize < localObject.length)
        {
          int k = 0;
          for (;;)
          {
            localObject = mArrayIndices;
            j = i;
            if (k >= localObject.length) {
              break;
            }
            if (localObject[k] == -1)
            {
              j = k;
              break;
            }
            k += 1;
          }
        }
      }
      localObject = mArrayIndices;
      i = j;
      if (j >= localObject.length)
      {
        i = localObject.length;
        ROW_SIZE *= 2;
        mDidFillOnce = false;
        mLast = (i - 1);
        mArrayValues = Arrays.copyOf(mArrayValues, ROW_SIZE);
        mArrayIndices = Arrays.copyOf(mArrayIndices, ROW_SIZE);
        mArrayNextIndices = Arrays.copyOf(mArrayNextIndices, ROW_SIZE);
      }
      mArrayIndices[i] = get;
      mArrayValues[i] = paramFloat;
      if (m != -1)
      {
        localObject = mArrayNextIndices;
        localObject[i] = localObject[m];
        localObject[m] = i;
      }
      else
      {
        mArrayNextIndices[i] = mHead;
        mHead = i;
      }
      usageInRowCount += 1;
      paramSolverVariable.addToRow(mRow);
      currentSize += 1;
      if (!mDidFillOnce) {
        mLast += 1;
      }
      i = mLast;
      paramSolverVariable = mArrayIndices;
      if (i >= paramSolverVariable.length)
      {
        mDidFillOnce = true;
        mLast = (paramSolverVariable.length - 1);
      }
    }
  }
  
  void invert()
  {
    int j = mHead;
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      float[] arrayOfFloat = mArrayValues;
      arrayOfFloat[j] *= -1.0F;
      j = mArrayNextIndices[j];
      i += 1;
    }
  }
  
  public final float remove(SolverVariable paramSolverVariable, boolean paramBoolean)
  {
    if (candidate == paramSolverVariable) {
      candidate = null;
    }
    int i = mHead;
    if (i == -1) {
      return 0.0F;
    }
    int j = 0;
    int k = -1;
    while ((i != -1) && (j < currentSize))
    {
      if (mArrayIndices[i] == get)
      {
        if (i == mHead)
        {
          mHead = mArrayNextIndices[i];
        }
        else
        {
          arrayOfInt = mArrayNextIndices;
          arrayOfInt[k] = arrayOfInt[i];
        }
        if (paramBoolean) {
          paramSolverVariable.removeFromRow(mRow);
        }
        usageInRowCount -= 1;
        currentSize -= 1;
        mArrayIndices[i] = -1;
        if (mDidFillOnce) {
          mLast = i;
        }
        return mArrayValues[i];
      }
      int[] arrayOfInt = mArrayNextIndices;
      j += 1;
      k = i;
      i = arrayOfInt[i];
    }
    return 0.0F;
  }
  
  int sizeInBytes()
  {
    return mArrayIndices.length * 4 * 3 + 0 + 36;
  }
  
  public String toString()
  {
    int j = mHead;
    String str = "";
    int i = 0;
    while ((j != -1) && (i < currentSize))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(" -> ");
      str = localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(mArrayValues[j]);
      localStringBuilder.append(" : ");
      str = localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(mCache.mIndexedVariables[mArrayIndices[j]]);
      str = localStringBuilder.toString();
      j = mArrayNextIndices[j];
      i += 1;
    }
    return str;
  }
  
  final void updateFromRow(ArrayRow paramArrayRow1, ArrayRow paramArrayRow2, boolean paramBoolean)
  {
    int i = mHead;
    int j = 0;
    for (;;)
    {
      if ((i == -1) || (j >= currentSize)) {
        return;
      }
      if (mArrayIndices[i] == variable.get)
      {
        float f = mArrayValues[i];
        remove(variable, paramBoolean);
        ArrayLinkedVariables localArrayLinkedVariables = (ArrayLinkedVariables)variables;
        j = mHead;
        i = 0;
        while ((j != -1) && (i < currentSize))
        {
          intersect(mCache.mIndexedVariables[mArrayIndices[j]], mArrayValues[j] * f, paramBoolean);
          j = mArrayNextIndices[j];
          i += 1;
        }
        constantValue += constantValue * f;
        if (paramBoolean) {
          variable.removeFromRow(paramArrayRow1);
        }
        i = mHead;
        break;
      }
      i = mArrayNextIndices[i];
      j += 1;
    }
  }
  
  void updateFromSystem(ArrayRow paramArrayRow, ArrayRow[] paramArrayOfArrayRow)
  {
    int i = mHead;
    ArrayLinkedVariables localArrayLinkedVariables1 = this;
    int j = 0;
    for (;;)
    {
      if ((i == -1) || (j >= currentSize)) {
        return;
      }
      Object localObject = mCache.mIndexedVariables[mArrayIndices[i]];
      if (definitionId != -1)
      {
        float f = mArrayValues[i];
        localArrayLinkedVariables1.remove((SolverVariable)localObject, true);
        localObject = paramArrayOfArrayRow[definitionId];
        if (!isSimpleDefinition)
        {
          ArrayLinkedVariables localArrayLinkedVariables2 = (ArrayLinkedVariables)variables;
          j = mHead;
          i = 0;
          while ((j != -1) && (i < currentSize))
          {
            localArrayLinkedVariables1.intersect(mCache.mIndexedVariables[mArrayIndices[j]], mArrayValues[j] * f, true);
            j = mArrayNextIndices[j];
            i += 1;
          }
        }
        constantValue += constantValue * f;
        variable.removeFromRow(paramArrayRow);
        i = mHead;
        break;
      }
      i = mArrayNextIndices[i];
      j += 1;
    }
  }
}
