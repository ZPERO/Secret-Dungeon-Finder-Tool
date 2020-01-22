package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;

public class LinearSystem
{
  private static final boolean DEBUG = false;
  public static final boolean FULL_DEBUG = false;
  private static int POOL_SIZE;
  public static Metrics sMetrics;
  private int TABLE_SIZE = 32;
  public boolean graphOptimizer;
  private boolean[] mAlreadyTestedCandidates;
  final Cache mCache;
  private Row mGoal;
  private int mMaxColumns;
  private int mMaxRows;
  int mNumColumns;
  int mNumRows;
  private SolverVariable[] mPoolVariables;
  private int mPoolVariablesCount;
  ArrayRow[] mRows;
  private final Row mTempGoal;
  private HashMap<String, SolverVariable> mVariables = null;
  int mVariablesID = 0;
  private ArrayRow[] tempClientsCopy;
  
  public LinearSystem()
  {
    int i = TABLE_SIZE;
    mMaxColumns = i;
    mRows = null;
    graphOptimizer = false;
    mAlreadyTestedCandidates = new boolean[i];
    mNumColumns = 1;
    mNumRows = 0;
    mMaxRows = i;
    mPoolVariables = new SolverVariable[POOL_SIZE];
    mPoolVariablesCount = 0;
    tempClientsCopy = new ArrayRow[i];
    mRows = new ArrayRow[i];
    releaseRows();
    mCache = new Cache();
    mGoal = new GoalRow(mCache);
    mTempGoal = new ArrayRow(mCache);
  }
  
  private SolverVariable acquireSolverVariable(SolverVariable.Type paramType, String paramString)
  {
    SolverVariable localSolverVariable = (SolverVariable)mCache.solverVariablePool.acquire();
    if (localSolverVariable == null)
    {
      localSolverVariable = new SolverVariable(paramType, paramString);
      localSolverVariable.setType(paramType, paramString);
      paramType = localSolverVariable;
    }
    else
    {
      localSolverVariable.reset();
      localSolverVariable.setType(paramType, paramString);
      paramType = localSolverVariable;
    }
    int i = mPoolVariablesCount;
    int j = POOL_SIZE;
    if (i >= j)
    {
      POOL_SIZE = j * 2;
      mPoolVariables = ((SolverVariable[])Arrays.copyOf(mPoolVariables, POOL_SIZE));
    }
    paramString = mPoolVariables;
    i = mPoolVariablesCount;
    mPoolVariablesCount = (i + 1);
    paramString[i] = paramType;
    return paramType;
  }
  
  private void addError(ArrayRow paramArrayRow)
  {
    paramArrayRow.addError(this, 0);
  }
  
  private final void addRow(ArrayRow paramArrayRow)
  {
    if (mRows[mNumRows] != null) {
      mCache.arrayRowPool.release(mRows[mNumRows]);
    }
    mRows[mNumRows] = paramArrayRow;
    SolverVariable localSolverVariable = variable;
    int i = mNumRows;
    definitionId = i;
    mNumRows = (i + 1);
    variable.updateReferencesWithNewDefinition(paramArrayRow);
  }
  
  private void addSingleError(ArrayRow paramArrayRow, int paramInt)
  {
    addSingleError(paramArrayRow, paramInt, 0);
  }
  
  private void computeValues()
  {
    int i = 0;
    while (i < mNumRows)
    {
      ArrayRow localArrayRow = mRows[i];
      variable.computedValue = constantValue;
      i += 1;
    }
  }
  
  public static ArrayRow createRowCentering(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2, boolean paramBoolean)
  {
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    localArrayRow.createRowCentering(paramSolverVariable1, paramSolverVariable2, paramInt1, paramFloat, paramSolverVariable3, paramSolverVariable4, paramInt2);
    if (paramBoolean) {
      localArrayRow.addError(paramLinearSystem, 4);
    }
    return localArrayRow;
  }
  
  public static ArrayRow createRowDimensionPercent(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, float paramFloat, boolean paramBoolean)
  {
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    if (paramBoolean) {
      paramLinearSystem.addError(localArrayRow);
    }
    return localArrayRow.createRowDimensionPercent(paramSolverVariable1, paramSolverVariable2, paramSolverVariable3, paramFloat);
  }
  
  public static ArrayRow createRowEquals(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean)
  {
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    localArrayRow.createRowEquals(paramSolverVariable1, paramSolverVariable2, paramInt);
    if (paramBoolean) {
      paramLinearSystem.addSingleError(localArrayRow, 1);
    }
    return localArrayRow;
  }
  
  public static ArrayRow createRowGreaterThan(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean)
  {
    SolverVariable localSolverVariable = paramLinearSystem.createSlackVariable();
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    localArrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt);
    if (paramBoolean) {
      paramLinearSystem.addSingleError(localArrayRow, (int)(variables.containsValue(localSolverVariable) * -1.0F));
    }
    return localArrayRow;
  }
  
  public static ArrayRow createRowLowerThan(LinearSystem paramLinearSystem, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt, boolean paramBoolean)
  {
    SolverVariable localSolverVariable = paramLinearSystem.createSlackVariable();
    ArrayRow localArrayRow = paramLinearSystem.createRow();
    localArrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt);
    if (paramBoolean) {
      paramLinearSystem.addSingleError(localArrayRow, (int)(variables.containsValue(localSolverVariable) * -1.0F));
    }
    return localArrayRow;
  }
  
  private SolverVariable createVariable(String paramString, SolverVariable.Type paramType)
  {
    Metrics localMetrics = sMetrics;
    if (localMetrics != null) {
      variables += 1L;
    }
    if (mNumColumns + 1 >= mMaxColumns) {
      increaseTableSize();
    }
    paramType = acquireSolverVariable(paramType, null);
    paramType.setName(paramString);
    mVariablesID += 1;
    mNumColumns += 1;
    get = mVariablesID;
    if (mVariables == null) {
      mVariables = new HashMap();
    }
    mVariables.put(paramString, paramType);
    mCache.mIndexedVariables[mVariablesID] = paramType;
    return paramType;
  }
  
  private void displayRows()
  {
    displaySolverVariables();
    String str = "";
    int i = 0;
    while (i < mNumRows)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(mRows[i]);
      str = localStringBuilder.toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append("\n");
      str = localStringBuilder.toString();
      i += 1;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append(mGoal);
    localStringBuilder.append("\n");
    str = localStringBuilder.toString();
    System.out.println(str);
  }
  
  private void displaySolverVariables()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Display Rows (");
    ((StringBuilder)localObject).append(mNumRows);
    ((StringBuilder)localObject).append("x");
    ((StringBuilder)localObject).append(mNumColumns);
    ((StringBuilder)localObject).append(")\n");
    localObject = ((StringBuilder)localObject).toString();
    System.out.println((String)localObject);
  }
  
  private int enforceBFS(Row paramRow)
    throws Exception
  {
    int i = 0;
    while (i < mNumRows)
    {
      if ((mRows[i].variable.mType != SolverVariable.Type.UNRESTRICTED) && (mRows[i].constantValue < 0.0F))
      {
        i = 1;
        break label67;
      }
      i += 1;
    }
    i = 0;
    label67:
    if (i != 0)
    {
      int m = 0;
      int i6;
      for (i = 0; m == 0; i = i6)
      {
        paramRow = sMetrics;
        if (paramRow != null) {
          fsync += 1L;
        }
        i6 = i + 1;
        int n = 0;
        i = -1;
        int j = -1;
        float f1 = Float.MAX_VALUE;
        int i4;
        Object localObject;
        for (int k = 0; n < mNumRows; k = i4)
        {
          paramRow = mRows[n];
          int i2;
          int i3;
          float f2;
          if (variable.mType == SolverVariable.Type.UNRESTRICTED)
          {
            i2 = i;
            i3 = j;
            f2 = f1;
            i4 = k;
          }
          else if (isSimpleDefinition)
          {
            i2 = i;
            i3 = j;
            f2 = f1;
            i4 = k;
          }
          else
          {
            i2 = i;
            i3 = j;
            f2 = f1;
            i4 = k;
            if (constantValue < 0.0F)
            {
              int i1 = 1;
              for (;;)
              {
                i2 = i;
                i3 = j;
                f2 = f1;
                i4 = k;
                if (i1 >= mNumColumns) {
                  break;
                }
                localObject = mCache.mIndexedVariables[i1];
                float f3 = variables.containsValue((SolverVariable)localObject);
                int i5;
                if (f3 <= 0.0F)
                {
                  i3 = i;
                  i4 = j;
                  f2 = f1;
                  i5 = k;
                }
                else
                {
                  i2 = 0;
                  for (;;)
                  {
                    i3 = i;
                    i4 = j;
                    f2 = f1;
                    i5 = k;
                    if (i2 >= 7) {
                      break;
                    }
                    f2 = strengthVector[i2] / f3;
                    if ((f2 >= f1) || (i2 != k))
                    {
                      i3 = k;
                      if (i2 <= k) {}
                    }
                    else
                    {
                      j = i1;
                      i = n;
                      f1 = f2;
                      i3 = i2;
                    }
                    i2 += 1;
                    k = i3;
                  }
                }
                i1 += 1;
                i = i3;
                j = i4;
                f1 = f2;
                k = i5;
              }
            }
          }
          n += 1;
          i = i2;
          j = i3;
          f1 = f2;
        }
        if (i != -1)
        {
          paramRow = mRows[i];
          variable.definitionId = -1;
          localObject = sMetrics;
          if (localObject != null) {
            pivots += 1L;
          }
          paramRow.pivot(mCache.mIndexedVariables[j]);
          variable.definitionId = i;
          variable.updateReferencesWithNewDefinition(paramRow);
        }
        else
        {
          m = 1;
        }
        if (i6 > mNumColumns / 2) {
          m = 1;
        }
      }
    }
    return 0;
    return i;
  }
  
  private String getDisplaySize(int paramInt)
  {
    paramInt *= 4;
    int i = paramInt / 1024;
    int j = i / 1024;
    if (j > 0)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("");
      localStringBuilder.append(j);
      localStringBuilder.append(" Mb");
      return localStringBuilder.toString();
    }
    if (i > 0)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("");
      localStringBuilder.append(i);
      localStringBuilder.append(" Kb");
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" bytes");
    return localStringBuilder.toString();
  }
  
  private String getDisplayStrength(int paramInt)
  {
    if (paramInt == 1) {
      return "LOW";
    }
    if (paramInt == 2) {
      return "MEDIUM";
    }
    if (paramInt == 3) {
      return "HIGH";
    }
    if (paramInt == 4) {
      return "HIGHEST";
    }
    if (paramInt == 5) {
      return "EQUALITY";
    }
    if (paramInt == 6) {
      return "FIXED";
    }
    return "NONE";
  }
  
  public static Metrics getMetrics()
  {
    return sMetrics;
  }
  
  private void increaseTableSize()
  {
    TABLE_SIZE *= 2;
    mRows = ((ArrayRow[])Arrays.copyOf(mRows, TABLE_SIZE));
    Object localObject = mCache;
    mIndexedVariables = ((SolverVariable[])Arrays.copyOf(mIndexedVariables, TABLE_SIZE));
    int i = TABLE_SIZE;
    mAlreadyTestedCandidates = new boolean[i];
    mMaxColumns = i;
    mMaxRows = i;
    localObject = sMetrics;
    if (localObject != null)
    {
      tableSizeIncrease += 1L;
      localObject = sMetrics;
      maxTableSize = Math.max(maxTableSize, TABLE_SIZE);
      localObject = sMetrics;
      lastTableSize = maxTableSize;
    }
  }
  
  private final int optimize(Row paramRow, boolean paramBoolean)
  {
    Object localObject = sMetrics;
    if (localObject != null) {
      optimize += 1L;
    }
    int i = 0;
    while (i < mNumColumns)
    {
      mAlreadyTestedCandidates[i] = false;
      i += 1;
    }
    int j = 0;
    i = 0;
    while (j == 0)
    {
      localObject = sMetrics;
      if (localObject != null) {
        iterations += 1L;
      }
      int n = i + 1;
      if (n >= mNumColumns * 2) {
        return n;
      }
      if (paramRow.getKey() != null) {
        mAlreadyTestedCandidates[getKeyget] = true;
      }
      localObject = paramRow.getPivotCandidate(this, mAlreadyTestedCandidates);
      if (localObject != null)
      {
        if (mAlreadyTestedCandidates[get] != 0) {
          return n;
        }
        mAlreadyTestedCandidates[get] = true;
      }
      if (localObject != null)
      {
        i = 0;
        int k = -1;
        ArrayRow localArrayRow;
        float f2;
        for (float f1 = Float.MAX_VALUE; i < mNumRows; f1 = f2)
        {
          localArrayRow = mRows[i];
          int m;
          if (variable.mType == SolverVariable.Type.UNRESTRICTED)
          {
            m = k;
            f2 = f1;
          }
          else if (isSimpleDefinition)
          {
            m = k;
            f2 = f1;
          }
          else
          {
            m = k;
            f2 = f1;
            if (localArrayRow.hasVariable((SolverVariable)localObject))
            {
              float f3 = variables.containsValue((SolverVariable)localObject);
              m = k;
              f2 = f1;
              if (f3 < 0.0F)
              {
                f3 = -constantValue / f3;
                m = k;
                f2 = f1;
                if (f3 < f1)
                {
                  m = i;
                  f2 = f3;
                }
              }
            }
          }
          i += 1;
          k = m;
        }
        if (k > -1)
        {
          localArrayRow = mRows[k];
          variable.definitionId = -1;
          Metrics localMetrics = sMetrics;
          if (localMetrics != null) {
            pivots += 1L;
          }
          localArrayRow.pivot((SolverVariable)localObject);
          variable.definitionId = k;
          variable.updateReferencesWithNewDefinition(localArrayRow);
          i = n;
          continue;
        }
      }
      j = 1;
      i = n;
    }
    return i;
  }
  
  private void releaseRows()
  {
    int i = 0;
    for (;;)
    {
      Object localObject = mRows;
      if (i >= localObject.length) {
        break;
      }
      localObject = localObject[i];
      if (localObject != null) {
        mCache.arrayRowPool.release(localObject);
      }
      mRows[i] = null;
      i += 1;
    }
  }
  
  private final void updateRowFromVariables(ArrayRow paramArrayRow)
  {
    if (mNumRows > 0)
    {
      variables.updateFromSystem(paramArrayRow, mRows);
      if (variables.currentSize == 0) {
        isSimpleDefinition = true;
      }
    }
  }
  
  public void addCenterPoint(ConstraintWidget paramConstraintWidget1, ConstraintWidget paramConstraintWidget2, float paramFloat, int paramInt)
  {
    SolverVariable localSolverVariable1 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.LEFT));
    SolverVariable localSolverVariable3 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.MIDDLE));
    SolverVariable localSolverVariable2 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.RIGHT));
    SolverVariable localSolverVariable5 = createObjectVariable(paramConstraintWidget1.getAnchor(ConstraintAnchor.Type.BOTTOM));
    paramConstraintWidget1 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT));
    SolverVariable localSolverVariable6 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.MIDDLE));
    SolverVariable localSolverVariable4 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT));
    paramConstraintWidget2 = createObjectVariable(paramConstraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM));
    ArrayRow localArrayRow = createRow();
    double d2 = paramFloat;
    double d3 = Math.sin(d2);
    double d1 = paramInt;
    Double.isNaN(d1);
    localArrayRow.createRowWithAngle(localSolverVariable3, localSolverVariable5, localSolverVariable6, paramConstraintWidget2, (float)(d3 * d1));
    addConstraint(localArrayRow);
    paramConstraintWidget2 = createRow();
    d2 = Math.cos(d2);
    Double.isNaN(d1);
    paramConstraintWidget2.createRowWithAngle(localSolverVariable1, localSolverVariable2, paramConstraintWidget1, localSolverVariable4, (float)(d2 * d1));
    addConstraint(paramConstraintWidget2);
  }
  
  public void addCentering(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, float paramFloat, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, int paramInt2, int paramInt3)
  {
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowCentering(paramSolverVariable1, paramSolverVariable2, paramInt1, paramFloat, paramSolverVariable3, paramSolverVariable4, paramInt2);
    if (paramInt3 != 6) {
      localArrayRow.addError(this, paramInt3);
    }
    addConstraint(localArrayRow);
  }
  
  public void addConstraint(ArrayRow paramArrayRow)
  {
    if (paramArrayRow == null) {
      return;
    }
    Object localObject = sMetrics;
    if (localObject != null)
    {
      constraints += 1L;
      if (isSimpleDefinition)
      {
        localObject = sMetrics;
        simpleconstraints += 1L;
      }
    }
    if ((mNumRows + 1 >= mMaxRows) || (mNumColumns + 1 >= mMaxColumns)) {
      increaseTableSize();
    }
    int i = 0;
    int j = 0;
    if (!isSimpleDefinition)
    {
      updateRowFromVariables(paramArrayRow);
      if (paramArrayRow.isEmpty()) {
        return;
      }
      paramArrayRow.ensurePositiveConstant();
      i = j;
      if (paramArrayRow.chooseSubject(this))
      {
        localObject = createExtraVariable();
        variable = ((SolverVariable)localObject);
        addRow(paramArrayRow);
        mTempGoal.initFromRow(paramArrayRow);
        optimize(mTempGoal, true);
        if (definitionId == -1)
        {
          if (variable == localObject)
          {
            localObject = paramArrayRow.pickPivot((SolverVariable)localObject);
            if (localObject != null)
            {
              Metrics localMetrics = sMetrics;
              if (localMetrics != null) {
                pivots += 1L;
              }
              paramArrayRow.pivot((SolverVariable)localObject);
            }
          }
          if (!isSimpleDefinition) {
            variable.updateReferencesWithNewDefinition(paramArrayRow);
          }
          mNumRows -= 1;
        }
        i = 1;
      }
      if (!paramArrayRow.hasKeyVariable()) {
        return;
      }
    }
    if (i == 0) {
      addRow(paramArrayRow);
    }
  }
  
  public ArrayRow addEquality(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowEquals(paramSolverVariable1, paramSolverVariable2, paramInt1);
    if (paramInt2 != 6) {
      localArrayRow.addError(this, paramInt2);
    }
    addConstraint(localArrayRow);
    return localArrayRow;
  }
  
  public void addEquality(SolverVariable paramSolverVariable, int paramInt)
  {
    int i = definitionId;
    if (definitionId != -1)
    {
      localArrayRow = mRows[i];
      if (isSimpleDefinition)
      {
        constantValue = paramInt;
        return;
      }
      if (variables.currentSize == 0)
      {
        isSimpleDefinition = true;
        constantValue = paramInt;
        return;
      }
      localArrayRow = createRow();
      localArrayRow.createRowEquals(paramSolverVariable, paramInt);
      addConstraint(localArrayRow);
      return;
    }
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowDefinition(paramSolverVariable, paramInt);
    addConstraint(localArrayRow);
  }
  
  public void addEquality(SolverVariable paramSolverVariable, int paramInt1, int paramInt2)
  {
    int i = definitionId;
    if (definitionId != -1)
    {
      localArrayRow = mRows[i];
      if (isSimpleDefinition)
      {
        constantValue = paramInt1;
        return;
      }
      localArrayRow = createRow();
      localArrayRow.createRowEquals(paramSolverVariable, paramInt1);
      localArrayRow.addError(this, paramInt2);
      addConstraint(localArrayRow);
      return;
    }
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowDefinition(paramSolverVariable, paramInt1);
    localArrayRow.addError(this, paramInt2);
    addConstraint(localArrayRow);
  }
  
  public void addGreaterBarrier(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, boolean paramBoolean)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    strength = 0;
    localArrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, 0);
    if (paramBoolean) {
      addSingleError(localArrayRow, (int)(variables.containsValue(localSolverVariable) * -1.0F), 1);
    }
    addConstraint(localArrayRow);
  }
  
  public void addGreaterThan(SolverVariable paramSolverVariable, int paramInt)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    strength = 0;
    localArrayRow.createRowGreaterThan(paramSolverVariable, paramInt, localSolverVariable);
    addConstraint(localArrayRow);
  }
  
  public void addGreaterThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    strength = 0;
    localArrayRow.createRowGreaterThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt1);
    if (paramInt2 != 6) {
      addSingleError(localArrayRow, (int)(variables.containsValue(localSolverVariable) * -1.0F), paramInt2);
    }
    addConstraint(localArrayRow);
  }
  
  public void addLowerBarrier(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, boolean paramBoolean)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    strength = 0;
    localArrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, 0);
    if (paramBoolean) {
      addSingleError(localArrayRow, (int)(variables.containsValue(localSolverVariable) * -1.0F), 1);
    }
    addConstraint(localArrayRow);
  }
  
  public void addLowerThan(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, int paramInt1, int paramInt2)
  {
    ArrayRow localArrayRow = createRow();
    SolverVariable localSolverVariable = createSlackVariable();
    strength = 0;
    localArrayRow.createRowLowerThan(paramSolverVariable1, paramSolverVariable2, localSolverVariable, paramInt1);
    if (paramInt2 != 6) {
      addSingleError(localArrayRow, (int)(variables.containsValue(localSolverVariable) * -1.0F), paramInt2);
    }
    addConstraint(localArrayRow);
  }
  
  public void addRatio(SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, SolverVariable paramSolverVariable3, SolverVariable paramSolverVariable4, float paramFloat, int paramInt)
  {
    ArrayRow localArrayRow = createRow();
    localArrayRow.createRowDimensionRatio(paramSolverVariable1, paramSolverVariable2, paramSolverVariable3, paramSolverVariable4, paramFloat);
    if (paramInt != 6) {
      localArrayRow.addError(this, paramInt);
    }
    addConstraint(localArrayRow);
  }
  
  void addSingleError(ArrayRow paramArrayRow, int paramInt1, int paramInt2)
  {
    paramArrayRow.addSingleError(createErrorVariable(paramInt2, null), paramInt1);
  }
  
  public SolverVariable createErrorVariable(int paramInt, String paramString)
  {
    Metrics localMetrics = sMetrics;
    if (localMetrics != null) {
      errors += 1L;
    }
    if (mNumColumns + 1 >= mMaxColumns) {
      increaseTableSize();
    }
    paramString = acquireSolverVariable(SolverVariable.Type.ERROR, paramString);
    mVariablesID += 1;
    mNumColumns += 1;
    get = mVariablesID;
    strength = paramInt;
    mCache.mIndexedVariables[mVariablesID] = paramString;
    mGoal.addError(paramString);
    return paramString;
  }
  
  public SolverVariable createExtraVariable()
  {
    Object localObject = sMetrics;
    if (localObject != null) {
      extravariables += 1L;
    }
    if (mNumColumns + 1 >= mMaxColumns) {
      increaseTableSize();
    }
    localObject = acquireSolverVariable(SolverVariable.Type.SLACK, null);
    mVariablesID += 1;
    mNumColumns += 1;
    get = mVariablesID;
    mCache.mIndexedVariables[mVariablesID] = localObject;
    return localObject;
  }
  
  public SolverVariable createObjectVariable(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if (mNumColumns + 1 >= mMaxColumns) {
      increaseTableSize();
    }
    if ((paramObject instanceof ConstraintAnchor))
    {
      ConstraintAnchor localConstraintAnchor = (ConstraintAnchor)paramObject;
      SolverVariable localSolverVariable = localConstraintAnchor.getSolverVariable();
      paramObject = localSolverVariable;
      if (localSolverVariable == null)
      {
        localConstraintAnchor.resetSolverVariable(mCache);
        paramObject = localConstraintAnchor.getSolverVariable();
      }
      if ((get == -1) || (get > mVariablesID) || (mCache.mIndexedVariables[get] == null))
      {
        if (get != -1) {
          paramObject.reset();
        }
        mVariablesID += 1;
        mNumColumns += 1;
        get = mVariablesID;
        mType = SolverVariable.Type.UNRESTRICTED;
        mCache.mIndexedVariables[mVariablesID] = paramObject;
        return paramObject;
      }
    }
    else
    {
      return null;
    }
    return paramObject;
  }
  
  public ArrayRow createRow()
  {
    ArrayRow localArrayRow = (ArrayRow)mCache.arrayRowPool.acquire();
    if (localArrayRow == null) {
      localArrayRow = new ArrayRow(mCache);
    } else {
      localArrayRow.reset();
    }
    SolverVariable.increaseErrorId();
    return localArrayRow;
  }
  
  public SolverVariable createSlackVariable()
  {
    Object localObject = sMetrics;
    if (localObject != null) {
      slackvariables += 1L;
    }
    if (mNumColumns + 1 >= mMaxColumns) {
      increaseTableSize();
    }
    localObject = acquireSolverVariable(SolverVariable.Type.SLACK, null);
    mVariablesID += 1;
    mNumColumns += 1;
    get = mVariablesID;
    mCache.mIndexedVariables[mVariablesID] = localObject;
    return localObject;
  }
  
  void displayReadableRows()
  {
    displaySolverVariables();
    String str = " #  ";
    int i = 0;
    while (i < mNumRows)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append(mRows[i].toReadableString());
      str = ((StringBuilder)localObject).toString();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append("\n #  ");
      str = ((StringBuilder)localObject).toString();
      i += 1;
    }
    Object localObject = str;
    if (mGoal != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append(mGoal);
      ((StringBuilder)localObject).append("\n");
      localObject = ((StringBuilder)localObject).toString();
    }
    System.out.println((String)localObject);
  }
  
  void displaySystemInformations()
  {
    int j = 0;
    for (int i = 0; j < TABLE_SIZE; i = k)
    {
      localObject = mRows;
      k = i;
      if (localObject[j] != null) {
        k = i + localObject[j].sizeInBytes();
      }
      j += 1;
    }
    int k = 0;
    int m;
    for (j = 0; k < mNumRows; j = m)
    {
      localObject = mRows;
      m = j;
      if (localObject[k] != null) {
        m = j + localObject[k].sizeInBytes();
      }
      k += 1;
    }
    Object localObject = System.out;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Linear System -> Table size: ");
    localStringBuilder.append(TABLE_SIZE);
    localStringBuilder.append(" (");
    k = TABLE_SIZE;
    localStringBuilder.append(getDisplaySize(k * k));
    localStringBuilder.append(") -- row sizes: ");
    localStringBuilder.append(getDisplaySize(i));
    localStringBuilder.append(", actual size: ");
    localStringBuilder.append(getDisplaySize(j));
    localStringBuilder.append(" rows: ");
    localStringBuilder.append(mNumRows);
    localStringBuilder.append("/");
    localStringBuilder.append(mMaxRows);
    localStringBuilder.append(" cols: ");
    localStringBuilder.append(mNumColumns);
    localStringBuilder.append("/");
    localStringBuilder.append(mMaxColumns);
    localStringBuilder.append(" ");
    localStringBuilder.append(0);
    localStringBuilder.append(" occupied cells, ");
    localStringBuilder.append(getDisplaySize(0));
    ((PrintStream)localObject).println(localStringBuilder.toString());
  }
  
  public void displayVariablesReadableRows()
  {
    displaySolverVariables();
    Object localObject1 = "";
    int i = 0;
    while (i < mNumRows)
    {
      localObject2 = localObject1;
      if (mRows[i].variable.mType == SolverVariable.Type.UNRESTRICTED)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append(mRows[i].toReadableString());
        localObject1 = ((StringBuilder)localObject2).toString();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("\n");
        localObject2 = ((StringBuilder)localObject2).toString();
      }
      i += 1;
      localObject1 = localObject2;
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(mGoal);
    ((StringBuilder)localObject2).append("\n");
    localObject1 = ((StringBuilder)localObject2).toString();
    System.out.println((String)localObject1);
  }
  
  public void fillMetrics(Metrics paramMetrics)
  {
    sMetrics = paramMetrics;
  }
  
  public Cache getCache()
  {
    return mCache;
  }
  
  Row getGoal()
  {
    return mGoal;
  }
  
  public int getMemoryUsed()
  {
    int i = 0;
    int k;
    for (int j = 0; i < mNumRows; j = k)
    {
      ArrayRow[] arrayOfArrayRow = mRows;
      k = j;
      if (arrayOfArrayRow[i] != null) {
        k = j + arrayOfArrayRow[i].sizeInBytes();
      }
      i += 1;
    }
    return j;
  }
  
  public int getNumEquations()
  {
    return mNumRows;
  }
  
  public int getNumVariables()
  {
    return mVariablesID;
  }
  
  public int getObjectVariableValue(Object paramObject)
  {
    paramObject = ((ConstraintAnchor)paramObject).getSolverVariable();
    if (paramObject != null) {
      return (int)(computedValue + 0.5F);
    }
    return 0;
  }
  
  ArrayRow getRow(int paramInt)
  {
    return mRows[paramInt];
  }
  
  float getValueFor(String paramString)
  {
    paramString = getVariable(paramString, SolverVariable.Type.UNRESTRICTED);
    if (paramString == null) {
      return 0.0F;
    }
    return computedValue;
  }
  
  SolverVariable getVariable(String paramString, SolverVariable.Type paramType)
  {
    if (mVariables == null) {
      mVariables = new HashMap();
    }
    SolverVariable localSolverVariable2 = (SolverVariable)mVariables.get(paramString);
    SolverVariable localSolverVariable1 = localSolverVariable2;
    if (localSolverVariable2 == null) {
      localSolverVariable1 = createVariable(paramString, paramType);
    }
    return localSolverVariable1;
  }
  
  public void minimize()
    throws Exception
  {
    Metrics localMetrics = sMetrics;
    if (localMetrics != null) {
      minimize += 1L;
    }
    if (graphOptimizer)
    {
      localMetrics = sMetrics;
      if (localMetrics != null) {
        graphOptimizer += 1L;
      }
      int j = 0;
      int i = 0;
      while (i < mNumRows)
      {
        if (!mRows[i].isSimpleDefinition)
        {
          i = j;
          break label81;
        }
        i += 1;
      }
      i = 1;
      label81:
      if (i == 0)
      {
        minimizeGoal(mGoal);
        return;
      }
      localMetrics = sMetrics;
      if (localMetrics != null) {
        fullySolved += 1L;
      }
      computeValues();
      return;
    }
    minimizeGoal(mGoal);
  }
  
  void minimizeGoal(Row paramRow)
    throws Exception
  {
    Metrics localMetrics = sMetrics;
    if (localMetrics != null)
    {
      minimizeGoal += 1L;
      localMetrics = sMetrics;
      maxVariables = Math.max(maxVariables, mNumColumns);
      localMetrics = sMetrics;
      maxRows = Math.max(maxRows, mNumRows);
    }
    updateRowFromVariables((ArrayRow)paramRow);
    enforceBFS(paramRow);
    optimize(paramRow, false);
    computeValues();
  }
  
  public void reset()
  {
    int i = 0;
    while (i < mCache.mIndexedVariables.length)
    {
      localObject = mCache.mIndexedVariables[i];
      if (localObject != null) {
        ((SolverVariable)localObject).reset();
      }
      i += 1;
    }
    mCache.solverVariablePool.releaseAll(mPoolVariables, mPoolVariablesCount);
    mPoolVariablesCount = 0;
    Arrays.fill(mCache.mIndexedVariables, null);
    Object localObject = mVariables;
    if (localObject != null) {
      ((HashMap)localObject).clear();
    }
    mVariablesID = 0;
    mGoal.clear();
    mNumColumns = 1;
    i = 0;
    while (i < mNumRows)
    {
      mRows[i].used = false;
      i += 1;
    }
    releaseRows();
    mNumRows = 0;
  }
  
  static abstract interface Row
  {
    public abstract void addError(SolverVariable paramSolverVariable);
    
    public abstract void clear();
    
    public abstract SolverVariable getKey();
    
    public abstract SolverVariable getPivotCandidate(LinearSystem paramLinearSystem, boolean[] paramArrayOfBoolean);
    
    public abstract void initFromRow(Row paramRow);
    
    public abstract boolean isEmpty();
  }
}
