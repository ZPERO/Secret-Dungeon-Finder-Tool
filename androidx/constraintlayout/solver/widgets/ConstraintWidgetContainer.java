package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.Metrics;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstraintWidgetContainer
  extends WidgetContainer
{
  private static final boolean DEBUG = false;
  static final boolean DEBUG_GRAPH = false;
  private static final boolean DEBUG_LAYOUT = false;
  private static final int MAX_ITERATIONS = 8;
  private static final boolean USE_SNAPSHOT = true;
  int mDebugSolverPassCount = 0;
  public boolean mGroupsWrapOptimized = false;
  private boolean mHeightMeasuredTooSmall = false;
  ChainHead[] mHorizontalChainsArray = new ChainHead[4];
  int mHorizontalChainsSize = 0;
  public boolean mHorizontalWrapOptimized = false;
  private boolean mIsRtl = false;
  private int mOptimizationLevel = 7;
  int mPaddingBottom;
  int mPaddingLeft;
  int mPaddingRight;
  int mPaddingTop;
  public boolean mSkipSolver = false;
  private Snapshot mSnapshot;
  protected LinearSystem mSystem = new LinearSystem();
  ChainHead[] mVerticalChainsArray = new ChainHead[4];
  int mVerticalChainsSize = 0;
  public boolean mVerticalWrapOptimized = false;
  public List<ConstraintWidgetGroup> mWidgetGroups = new ArrayList();
  private boolean mWidthMeasuredTooSmall = false;
  public int mWrapFixedHeight = 0;
  public int mWrapFixedWidth = 0;
  
  public ConstraintWidgetContainer() {}
  
  public ConstraintWidgetContainer(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }
  
  public ConstraintWidgetContainer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  private void addHorizontalChain(ConstraintWidget paramConstraintWidget)
  {
    int i = mHorizontalChainsSize;
    ChainHead[] arrayOfChainHead = mHorizontalChainsArray;
    if (i + 1 >= arrayOfChainHead.length) {
      mHorizontalChainsArray = ((ChainHead[])Arrays.copyOf(arrayOfChainHead, arrayOfChainHead.length * 2));
    }
    mHorizontalChainsArray[mHorizontalChainsSize] = new ChainHead(paramConstraintWidget, 0, isRtl());
    mHorizontalChainsSize += 1;
  }
  
  private void addVerticalChain(ConstraintWidget paramConstraintWidget)
  {
    int i = mVerticalChainsSize;
    ChainHead[] arrayOfChainHead = mVerticalChainsArray;
    if (i + 1 >= arrayOfChainHead.length) {
      mVerticalChainsArray = ((ChainHead[])Arrays.copyOf(arrayOfChainHead, arrayOfChainHead.length * 2));
    }
    mVerticalChainsArray[mVerticalChainsSize] = new ChainHead(paramConstraintWidget, 1, isRtl());
    mVerticalChainsSize += 1;
  }
  
  private void resetChains()
  {
    mHorizontalChainsSize = 0;
    mVerticalChainsSize = 0;
  }
  
  void addChain(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    if (paramInt == 0)
    {
      addHorizontalChain(paramConstraintWidget);
      return;
    }
    if (paramInt == 1) {
      addVerticalChain(paramConstraintWidget);
    }
  }
  
  public boolean addChildrenToSolver(LinearSystem paramLinearSystem)
  {
    addToSolver(paramLinearSystem);
    int j = mChildren.size();
    int i = 0;
    while (i < j)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)mChildren.get(i);
      if ((localConstraintWidget instanceof ConstraintWidgetContainer))
      {
        ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = mListDimensionBehaviors[0];
        ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = mListDimensionBehaviors[1];
        if (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          localConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        }
        if (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          localConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        }
        localConstraintWidget.addToSolver(paramLinearSystem);
        if (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          localConstraintWidget.setHorizontalDimensionBehaviour(localDimensionBehaviour1);
        }
        if (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          localConstraintWidget.setVerticalDimensionBehaviour(localDimensionBehaviour2);
        }
      }
      else
      {
        Optimizer.checkMatchParent(this, paramLinearSystem, localConstraintWidget);
        localConstraintWidget.addToSolver(paramLinearSystem);
      }
      i += 1;
    }
    if (mHorizontalChainsSize > 0) {
      Chain.applyChainConstraints(this, paramLinearSystem, 0);
    }
    if (mVerticalChainsSize > 0) {
      Chain.applyChainConstraints(this, paramLinearSystem, 1);
    }
    return true;
  }
  
  public void analyze(int paramInt)
  {
    super.analyze(paramInt);
    int j = mChildren.size();
    int i = 0;
    while (i < j)
    {
      ((ConstraintWidget)mChildren.get(i)).analyze(paramInt);
      i += 1;
    }
  }
  
  public void fillMetrics(Metrics paramMetrics)
  {
    mSystem.fillMetrics(paramMetrics);
  }
  
  public ArrayList getHorizontalGuidelines()
  {
    ArrayList localArrayList = new ArrayList();
    int j = mChildren.size();
    int i = 0;
    while (i < j)
    {
      Object localObject = (ConstraintWidget)mChildren.get(i);
      if ((localObject instanceof Guideline))
      {
        localObject = (Guideline)localObject;
        if (((Guideline)localObject).getOrientation() == 0) {
          localArrayList.add(localObject);
        }
      }
      i += 1;
    }
    return localArrayList;
  }
  
  public int getOptimizationLevel()
  {
    return mOptimizationLevel;
  }
  
  public LinearSystem getSystem()
  {
    return mSystem;
  }
  
  public String getType()
  {
    return "ConstraintLayout";
  }
  
  public ArrayList getVerticalGuidelines()
  {
    ArrayList localArrayList = new ArrayList();
    int j = mChildren.size();
    int i = 0;
    while (i < j)
    {
      Object localObject = (ConstraintWidget)mChildren.get(i);
      if ((localObject instanceof Guideline))
      {
        localObject = (Guideline)localObject;
        if (((Guideline)localObject).getOrientation() == 1) {
          localArrayList.add(localObject);
        }
      }
      i += 1;
    }
    return localArrayList;
  }
  
  public List getWidgetGroups()
  {
    return mWidgetGroups;
  }
  
  public boolean handlesInternalConstraints()
  {
    return false;
  }
  
  public boolean isHeightMeasuredTooSmall()
  {
    return mHeightMeasuredTooSmall;
  }
  
  public boolean isRtl()
  {
    return mIsRtl;
  }
  
  public boolean isWidthMeasuredTooSmall()
  {
    return mWidthMeasuredTooSmall;
  }
  
  public void layout()
  {
    int i4 = x;
    int i5 = y;
    int i6 = Math.max(0, getWidth());
    int i7 = Math.max(0, getHeight());
    mWidthMeasuredTooSmall = false;
    mHeightMeasuredTooSmall = false;
    if (mParent != null)
    {
      if (mSnapshot == null) {
        mSnapshot = new Snapshot(this);
      }
      mSnapshot.updateFrom(this);
      setX(mPaddingLeft);
      setY(mPaddingTop);
      resetAnchors();
      resetSolverVariables(mSystem.getCache());
    }
    else
    {
      x = 0;
      y = 0;
    }
    if (mOptimizationLevel != 0)
    {
      if (!optimizeFor(8)) {
        optimizeReset();
      }
      if (!optimizeFor(32)) {
        optimize();
      }
      mSystem.graphOptimizer = true;
    }
    else
    {
      mSystem.graphOptimizer = false;
    }
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = mListDimensionBehaviors[1];
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = mListDimensionBehaviors[0];
    resetChains();
    if (mWidgetGroups.size() == 0)
    {
      mWidgetGroups.clear();
      mWidgetGroups.add(0, new ConstraintWidgetGroup(mChildren));
    }
    int n = mWidgetGroups.size();
    ArrayList localArrayList = mChildren;
    int m;
    if ((getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
      m = 0;
    } else {
      m = 1;
    }
    int i = 0;
    int i1 = 0;
    int j;
    label605:
    label778:
    int k;
    while ((i1 < n) && (!mSkipSolver))
    {
      if (!mWidgetGroups.get(i1)).mSkipSolver)
      {
        if (optimizeFor(32)) {
          if ((getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) && (getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED)) {
            mChildren = ((ArrayList)((ConstraintWidgetGroup)mWidgetGroups.get(i1)).getWidgetsToSolve());
          } else {
            mChildren = ((ArrayList)mWidgetGroups.get(i1)).mConstrainedGroup);
          }
        }
        resetChains();
        int i8 = mChildren.size();
        j = 0;
        Object localObject1;
        while (j < i8)
        {
          localObject1 = (ConstraintWidget)mChildren.get(j);
          if ((localObject1 instanceof WidgetContainer)) {
            ((WidgetContainer)localObject1).layout();
          }
          j += 1;
        }
        j = 0;
        boolean bool2 = true;
        while (bool2)
        {
          int i3 = j + 1;
          localObject1 = mSystem;
          try
          {
            ((LinearSystem)localObject1).reset();
            resetChains();
            localObject1 = mSystem;
            createObjectVariables((LinearSystem)localObject1);
            j = 0;
            for (;;)
            {
              if (j < i8)
              {
                localObject1 = mChildren;
                localObject1 = ((ArrayList)localObject1).get(j);
                localObject1 = (ConstraintWidget)localObject1;
                localObject2 = mSystem;
              }
              try
              {
                ((ConstraintWidget)localObject1).createObjectVariables((LinearSystem)localObject2);
                j += 1;
              }
              catch (Exception localException2)
              {
                break label605;
              }
            }
            localObject1 = mSystem;
            bool1 = addChildrenToSolver((LinearSystem)localObject1);
            bool2 = bool1;
            if (bool1)
            {
              localObject1 = mSystem;
              try
              {
                ((LinearSystem)localObject1).minimize();
              }
              catch (Exception localException1) {}
            }
          }
          catch (Exception localException3) {}
          localException3.printStackTrace();
          Object localObject2 = System.out;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("EXCEPTION : ");
          localStringBuilder.append(localException3);
          ((PrintStream)localObject2).println(localStringBuilder.toString());
          boolean bool1 = bool2;
          if (bool1) {
            updateChildrenFromSolver(mSystem, Optimizer.flags);
          }
          ConstraintWidget localConstraintWidget;
          for (;;)
          {
            break;
            updateFromSolver(mSystem);
            j = 0;
            for (;;)
            {
              if (j >= i8) {
                break label778;
              }
              localConstraintWidget = (ConstraintWidget)mChildren.get(j);
              if ((mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.getWidth() < localConstraintWidget.getWrapWidth()))
              {
                Optimizer.flags[2] = true;
                break;
              }
              if ((mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.getHeight() < localConstraintWidget.getWrapHeight()))
              {
                Optimizer.flags[2] = true;
                break;
              }
              j += 1;
            }
          }
          if ((m != 0) && (i3 < 8) && (Optimizer.flags[2] != 0))
          {
            j = 0;
            int i2 = 0;
            k = 0;
            while (j < i8)
            {
              localConstraintWidget = (ConstraintWidget)mChildren.get(j);
              i2 = Math.max(i2, x + localConstraintWidget.getWidth());
              k = Math.max(k, y + localConstraintWidget.getHeight());
              j += 1;
            }
            i2 = Math.max(mMinWidth, i2);
            j = Math.max(mMinHeight, k);
            if ((localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (getWidth() < i2))
            {
              setWidth(i2);
              mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
              bool1 = true;
              i = 1;
            }
            else
            {
              bool1 = false;
            }
            if ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (getHeight() < j))
            {
              setHeight(j);
              mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
              bool1 = true;
              i = 1;
            }
          }
          else
          {
            bool1 = false;
          }
          j = Math.max(mMinWidth, getWidth());
          if (j > getWidth())
          {
            setWidth(j);
            mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
            bool1 = true;
            i = 1;
          }
          j = Math.max(mMinHeight, getHeight());
          if (j > getHeight())
          {
            setHeight(j);
            mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
            bool1 = true;
            i = 1;
          }
          j = i;
          bool2 = bool1;
          if (i == 0)
          {
            k = i;
            boolean bool3 = bool1;
            if (mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
            {
              k = i;
              bool3 = bool1;
              if (i6 > 0)
              {
                k = i;
                bool3 = bool1;
                if (getWidth() > i6)
                {
                  mWidthMeasuredTooSmall = true;
                  mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
                  setWidth(i6);
                  bool3 = true;
                  k = 1;
                }
              }
            }
            j = k;
            bool2 = bool3;
            if (mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
            {
              j = k;
              bool2 = bool3;
              if (i7 > 0)
              {
                j = k;
                bool2 = bool3;
                if (getHeight() > i7)
                {
                  mHeightMeasuredTooSmall = true;
                  mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
                  setHeight(i7);
                  bool2 = true;
                  j = 1;
                }
              }
            }
          }
          i = j;
          j = i3;
        }
        ((ConstraintWidgetGroup)mWidgetGroups.get(i1)).updateUnresolvedWidgets();
      }
      i1 += 1;
    }
    mChildren = ((ArrayList)localArrayList);
    if (mParent != null)
    {
      j = Math.max(mMinWidth, getWidth());
      k = Math.max(mMinHeight, getHeight());
      mSnapshot.applyTo(this);
      setWidth(j + mPaddingLeft + mPaddingRight);
      setHeight(k + mPaddingTop + mPaddingBottom);
    }
    else
    {
      x = i4;
      y = i5;
    }
    if (i != 0)
    {
      mListDimensionBehaviors[0] = localDimensionBehaviour2;
      mListDimensionBehaviors[1] = localDimensionBehaviour1;
    }
    resetSolverVariables(mSystem.getCache());
    if (this == getRootConstraintContainer()) {
      updateDrawPosition();
    }
  }
  
  public void optimize()
  {
    if (!optimizeFor(8)) {
      analyze(mOptimizationLevel);
    }
    solveGraph();
  }
  
  public boolean optimizeFor(int paramInt)
  {
    return (mOptimizationLevel & paramInt) == paramInt;
  }
  
  public void optimizeForDimensions(int paramInt1, int paramInt2)
  {
    if ((mListDimensionBehaviors[0] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (mResolutionWidth != null)) {
      mResolutionWidth.resolve(paramInt1);
    }
    if ((mListDimensionBehaviors[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (mResolutionHeight != null)) {
      mResolutionHeight.resolve(paramInt2);
    }
  }
  
  public void optimizeReset()
  {
    int j = mChildren.size();
    resetResolutionNodes();
    int i = 0;
    while (i < j)
    {
      ((ConstraintWidget)mChildren.get(i)).resetResolutionNodes();
      i += 1;
    }
  }
  
  public void preOptimize()
  {
    optimizeReset();
    analyze(mOptimizationLevel);
  }
  
  public void reset()
  {
    mSystem.reset();
    mPaddingLeft = 0;
    mPaddingRight = 0;
    mPaddingTop = 0;
    mPaddingBottom = 0;
    mWidgetGroups.clear();
    mSkipSolver = false;
    super.reset();
  }
  
  public void resetGraph()
  {
    ResolutionAnchor localResolutionAnchor1 = getAnchor(ConstraintAnchor.Type.LEFT).getResolutionNode();
    ResolutionAnchor localResolutionAnchor2 = getAnchor(ConstraintAnchor.Type.MIDDLE).getResolutionNode();
    localResolutionAnchor1.invalidateAnchors();
    localResolutionAnchor2.invalidateAnchors();
    localResolutionAnchor1.resolve(null, 0.0F);
    localResolutionAnchor2.resolve(null, 0.0F);
  }
  
  public void setOptimizationLevel(int paramInt)
  {
    mOptimizationLevel = paramInt;
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    mPaddingLeft = paramInt1;
    mPaddingTop = paramInt2;
    mPaddingRight = paramInt3;
    mPaddingBottom = paramInt4;
  }
  
  public void setRtl(boolean paramBoolean)
  {
    mIsRtl = paramBoolean;
  }
  
  public void solveGraph()
  {
    ResolutionAnchor localResolutionAnchor1 = getAnchor(ConstraintAnchor.Type.LEFT).getResolutionNode();
    ResolutionAnchor localResolutionAnchor2 = getAnchor(ConstraintAnchor.Type.MIDDLE).getResolutionNode();
    localResolutionAnchor1.resolve(null, 0.0F);
    localResolutionAnchor2.resolve(null, 0.0F);
  }
  
  public void updateChildrenFromSolver(LinearSystem paramLinearSystem, boolean[] paramArrayOfBoolean)
  {
    paramArrayOfBoolean[2] = false;
    updateFromSolver(paramLinearSystem);
    int j = mChildren.size();
    int i = 0;
    while (i < j)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)mChildren.get(i);
      localConstraintWidget.updateFromSolver(paramLinearSystem);
      if ((mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.getWidth() < localConstraintWidget.getWrapWidth())) {
        paramArrayOfBoolean[2] = true;
      }
      if ((mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.getHeight() < localConstraintWidget.getWrapHeight())) {
        paramArrayOfBoolean[2] = true;
      }
      i += 1;
    }
  }
}
