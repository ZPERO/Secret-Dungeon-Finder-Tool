package androidx.constraintlayout.solver.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Analyzer
{
  private Analyzer() {}
  
  public static void determineGroups(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    if ((paramConstraintWidgetContainer.getOptimizationLevel() & 0x20) != 32)
    {
      singleGroup(paramConstraintWidgetContainer);
      return;
    }
    mSkipSolver = true;
    mGroupsWrapOptimized = false;
    mHorizontalWrapOptimized = false;
    mVerticalWrapOptimized = false;
    Object localObject1 = mChildren;
    List localList = mWidgetGroups;
    int i;
    if (paramConstraintWidgetContainer.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (paramConstraintWidgetContainer.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
      j = 1;
    } else {
      j = 0;
    }
    boolean bool;
    if ((i == 0) && (j == 0)) {
      bool = false;
    } else {
      bool = true;
    }
    localList.clear();
    Object localObject2 = ((List)localObject1).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)((Iterator)localObject2).next();
      mBelongingGroup = null;
      mGroupsToSolver = false;
      localConstraintWidget.resetResolutionNodes();
    }
    localObject1 = ((List)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ConstraintWidget)((Iterator)localObject1).next();
      if ((mBelongingGroup == null) && (!determineGroups((ConstraintWidget)localObject2, localList, bool)))
      {
        singleGroup(paramConstraintWidgetContainer);
        mSkipSolver = false;
        return;
      }
    }
    localObject1 = localList.iterator();
    int m = 0;
    for (int k = 0; ((Iterator)localObject1).hasNext(); k = Math.max(k, getMaxDimension((ConstraintWidgetGroup)localObject2, 1)))
    {
      localObject2 = (ConstraintWidgetGroup)((Iterator)localObject1).next();
      m = Math.max(m, getMaxDimension((ConstraintWidgetGroup)localObject2, 0));
    }
    if (i != 0)
    {
      paramConstraintWidgetContainer.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
      paramConstraintWidgetContainer.setWidth(m);
      mGroupsWrapOptimized = true;
      mHorizontalWrapOptimized = true;
      mWrapFixedWidth = m;
    }
    if (j != 0)
    {
      paramConstraintWidgetContainer.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
      paramConstraintWidgetContainer.setHeight(k);
      mGroupsWrapOptimized = true;
      mVerticalWrapOptimized = true;
      mWrapFixedHeight = k;
    }
    setPosition(localList, 0, paramConstraintWidgetContainer.getWidth());
    setPosition(localList, 1, paramConstraintWidgetContainer.getHeight());
  }
  
  private static boolean determineGroups(ConstraintWidget paramConstraintWidget, List paramList, boolean paramBoolean)
  {
    ConstraintWidgetGroup localConstraintWidgetGroup = new ConstraintWidgetGroup(new ArrayList(), true);
    paramList.add(localConstraintWidgetGroup);
    return traverse(paramConstraintWidget, localConstraintWidgetGroup, paramList, paramBoolean);
  }
  
  private static int getMaxDimension(ConstraintWidgetGroup paramConstraintWidgetGroup, int paramInt)
  {
    int k = paramInt * 2;
    List localList = paramConstraintWidgetGroup.getStartWidgets(paramInt);
    int m = localList.size();
    int i = 0;
    int j = 0;
    while (i < m)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)localList.get(i);
      ConstraintAnchor[] arrayOfConstraintAnchor = mListAnchors;
      int n = k + 1;
      boolean bool;
      if ((mTarget != null) && ((mListAnchors[k].mTarget == null) || (mListAnchors[n].mTarget == null))) {
        bool = false;
      } else {
        bool = true;
      }
      j = Math.max(j, getMaxDimensionTraversal(localConstraintWidget, paramInt, bool, 0));
      i += 1;
    }
    mGroupDimensions[paramInt] = j;
    return j;
  }
  
  private static int getMaxDimensionTraversal(ConstraintWidget paramConstraintWidget, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    boolean bool = mOptimizerMeasurable;
    int i3 = 0;
    if (!bool) {
      return 0;
    }
    if ((mBaseline.mTarget != null) && (paramInt1 == 1)) {
      k = 1;
    } else {
      k = 0;
    }
    int i;
    int j;
    if (paramBoolean)
    {
      n = paramConstraintWidget.getBaselineDistance();
      i1 = paramConstraintWidget.getHeight() - paramConstraintWidget.getBaselineDistance();
      i = paramInt1 * 2;
      j = i + 1;
    }
    else
    {
      n = paramConstraintWidget.getHeight() - paramConstraintWidget.getBaselineDistance();
      i1 = paramConstraintWidget.getBaselineDistance();
      j = paramInt1 * 2;
      i = j + 1;
    }
    int m;
    if ((mListAnchors[j].mTarget != null) && (mListAnchors[i].mTarget == null))
    {
      m = i;
      i = j;
      i2 = -1;
      j = m;
      m = i2;
    }
    else
    {
      m = 1;
    }
    int i2 = paramInt2;
    if (k != 0) {
      i2 = paramInt2 - n;
    }
    int i5 = mListAnchors[i].getMargin() * m + getParentBiasOffset(paramConstraintWidget, paramInt1);
    i2 += i5;
    if (paramInt1 == 0) {
      paramInt2 = paramConstraintWidget.getWidth();
    } else {
      paramInt2 = paramConstraintWidget.getHeight();
    }
    int i6 = paramInt2 * m;
    Object localObject = mListAnchors[i].getResolutionNode().dependents.iterator();
    for (paramInt2 = i3; ((Iterator)localObject).hasNext(); paramInt2 = Math.max(paramInt2, getMaxDimensionTraversal(nextmyAnchor.mOwner, paramInt1, paramBoolean, i2))) {}
    localObject = mListAnchors[j].getResolutionNode().dependents.iterator();
    for (i3 = 0; ((Iterator)localObject).hasNext(); i3 = Math.max(i3, getMaxDimensionTraversal(nextmyAnchor.mOwner, paramInt1, paramBoolean, i6 + i2))) {}
    int i4;
    if (k != 0)
    {
      paramInt2 -= n;
      i4 = i3 + i1;
    }
    for (i3 = paramInt2;; i3 = paramInt2)
    {
      break;
      if (paramInt1 == 0) {
        i4 = paramConstraintWidget.getWidth();
      } else {
        i4 = paramConstraintWidget.getHeight();
      }
      i4 = i3 + i4 * m;
    }
    if (paramInt1 == 1)
    {
      localObject = mBaseline.getResolutionNode().dependents.iterator();
      paramInt2 = 0;
      while (((Iterator)localObject).hasNext())
      {
        ResolutionAnchor localResolutionAnchor = (ResolutionAnchor)((Iterator)localObject).next();
        if (m == 1) {
          paramInt2 = Math.max(paramInt2, getMaxDimensionTraversal(myAnchor.mOwner, paramInt1, paramBoolean, n + i2));
        } else {
          paramInt2 = Math.max(paramInt2, getMaxDimensionTraversal(myAnchor.mOwner, paramInt1, paramBoolean, i1 * m + i2));
        }
      }
      if ((mBaseline.getResolutionNode().dependents.size() > 0) && (k == 0)) {
        if (m == 1) {
          paramInt2 += n;
        } else {
          paramInt2 -= i1;
        }
      }
    }
    else
    {
      paramInt2 = 0;
    }
    int i1 = Math.max(i3, Math.max(i4, paramInt2));
    int k = i2 + i6;
    int n = k;
    paramInt2 = i2;
    if (m == -1)
    {
      paramInt2 = k;
      n = i2;
    }
    if (paramBoolean)
    {
      Optimizer.setOptimizedWidget(paramConstraintWidget, paramInt1, paramInt2);
      paramConstraintWidget.setFrame(paramInt2, n, paramInt1);
    }
    else
    {
      mBelongingGroup.addWidgetsToSet(paramConstraintWidget, paramInt1);
      paramConstraintWidget.setRelativePositioning(paramInt2, paramInt1);
    }
    if ((paramConstraintWidget.getDimensionBehaviour(paramInt1) == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (mDimensionRatio != 0.0F)) {
      mBelongingGroup.addWidgetsToSet(paramConstraintWidget, paramInt1);
    }
    if ((mListAnchors[i].mTarget != null) && (mListAnchors[j].mTarget != null))
    {
      localObject = paramConstraintWidget.getParent();
      if ((mListAnchors[i].mTarget.mOwner == localObject) && (mListAnchors[j].mTarget.mOwner == localObject)) {
        mBelongingGroup.addWidgetsToSet(paramConstraintWidget, paramInt1);
      }
    }
    return i5 + i1;
  }
  
  private static int getParentBiasOffset(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    int i = paramInt * 2;
    ConstraintAnchor localConstraintAnchor1 = mListAnchors[i];
    ConstraintAnchor localConstraintAnchor2 = mListAnchors[(i + 1)];
    if ((mTarget != null) && (mTarget.mOwner == mParent) && (mTarget != null) && (mTarget.mOwner == mParent))
    {
      i = mParent.getLength(paramInt);
      float f;
      if (paramInt == 0) {
        f = mHorizontalBiasPercent;
      } else {
        f = mVerticalBiasPercent;
      }
      paramInt = paramConstraintWidget.getLength(paramInt);
      return (int)((i - localConstraintAnchor1.getMargin() - localConstraintAnchor2.getMargin() - paramInt) * f);
    }
    return 0;
  }
  
  private static void invalidate(ConstraintWidgetContainer paramConstraintWidgetContainer, ConstraintWidget paramConstraintWidget, ConstraintWidgetGroup paramConstraintWidgetGroup)
  {
    mSkipSolver = false;
    mSkipSolver = false;
    mOptimizerMeasurable = false;
  }
  
  private static int resolveDimensionRatio(ConstraintWidget paramConstraintWidget)
  {
    float f;
    int i;
    if (paramConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
    {
      if (mDimensionRatioSide == 0) {
        f = paramConstraintWidget.getHeight() * mDimensionRatio;
      } else {
        f = paramConstraintWidget.getHeight() / mDimensionRatio;
      }
      i = (int)f;
      paramConstraintWidget.setWidth(i);
      return i;
    }
    if (paramConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
    {
      if (mDimensionRatioSide == 1) {
        f = paramConstraintWidget.getWidth() * mDimensionRatio;
      } else {
        f = paramConstraintWidget.getWidth() / mDimensionRatio;
      }
      i = (int)f;
      paramConstraintWidget.setHeight(i);
      return i;
    }
    return -1;
  }
  
  private static void setConnection(ConstraintAnchor paramConstraintAnchor)
  {
    ResolutionAnchor localResolutionAnchor = paramConstraintAnchor.getResolutionNode();
    if ((mTarget != null) && (mTarget.mTarget != paramConstraintAnchor)) {
      mTarget.getResolutionNode().addDependent(localResolutionAnchor);
    }
  }
  
  public static void setPosition(List paramList, int paramInt1, int paramInt2)
  {
    int j = paramList.size();
    int i = 0;
    while (i < j)
    {
      Iterator localIterator = ((ConstraintWidgetGroup)paramList.get(i)).getWidgetsToSet(paramInt1).iterator();
      while (localIterator.hasNext())
      {
        ConstraintWidget localConstraintWidget = (ConstraintWidget)localIterator.next();
        if (mOptimizerMeasurable) {
          updateSizeDependentWidgets(localConstraintWidget, paramInt1, paramInt2);
        }
      }
      i += 1;
    }
  }
  
  private static void singleGroup(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    mWidgetGroups.clear();
    mWidgetGroups.add(0, new ConstraintWidgetGroup(mChildren));
  }
  
  private static boolean traverse(ConstraintWidget paramConstraintWidget, ConstraintWidgetGroup paramConstraintWidgetGroup, List paramList, boolean paramBoolean)
  {
    if (paramConstraintWidget == null) {
      return true;
    }
    mOptimizerMeasured = false;
    ConstraintWidgetContainer localConstraintWidgetContainer = (ConstraintWidgetContainer)paramConstraintWidget.getParent();
    if (mBelongingGroup == null)
    {
      mOptimizerMeasurable = true;
      mConstrainedGroup.add(paramConstraintWidget);
      mBelongingGroup = paramConstraintWidgetGroup;
      if ((mLeft.mTarget == null) && (mRight.mTarget == null) && (mTop.mTarget == null) && (mBottom.mTarget == null) && (mBaseline.mTarget == null) && (mCenter.mTarget == null))
      {
        invalidate(localConstraintWidgetContainer, paramConstraintWidget, paramConstraintWidgetGroup);
        if (paramBoolean) {
          return false;
        }
      }
      Object localObject;
      if ((mTop.mTarget != null) && (mBottom.mTarget != null))
      {
        localConstraintWidgetContainer.getVerticalDimensionBehaviour();
        localObject = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (paramBoolean)
        {
          invalidate(localConstraintWidgetContainer, paramConstraintWidget, paramConstraintWidgetGroup);
          return false;
        }
        if ((mTop.mTarget.mOwner != paramConstraintWidget.getParent()) || (mBottom.mTarget.mOwner != paramConstraintWidget.getParent())) {
          invalidate(localConstraintWidgetContainer, paramConstraintWidget, paramConstraintWidgetGroup);
        }
      }
      if ((mLeft.mTarget != null) && (mRight.mTarget != null))
      {
        localConstraintWidgetContainer.getHorizontalDimensionBehaviour();
        localObject = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (paramBoolean)
        {
          invalidate(localConstraintWidgetContainer, paramConstraintWidget, paramConstraintWidgetGroup);
          return false;
        }
        if ((mLeft.mTarget.mOwner != paramConstraintWidget.getParent()) || (mRight.mTarget.mOwner != paramConstraintWidget.getParent())) {
          invalidate(localConstraintWidgetContainer, paramConstraintWidget, paramConstraintWidgetGroup);
        }
      }
      if (paramConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        i = 1;
      } else {
        i = 0;
      }
      if (paramConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        j = 1;
      } else {
        j = 0;
      }
      if (((i ^ j) != 0) && (mDimensionRatio != 0.0F))
      {
        resolveDimensionRatio(paramConstraintWidget);
      }
      else if ((paramConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (paramConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))
      {
        invalidate(localConstraintWidgetContainer, paramConstraintWidget, paramConstraintWidgetGroup);
        if (paramBoolean) {
          return false;
        }
      }
      if (((mLeft.mTarget == null) && (mRight.mTarget == null)) || ((mLeft.mTarget != null) && (mLeft.mTarget.mOwner == mParent) && (mRight.mTarget == null)) || ((mRight.mTarget != null) && (mRight.mTarget.mOwner == mParent) && (mLeft.mTarget == null)) || ((mLeft.mTarget != null) && (mLeft.mTarget.mOwner == mParent) && (mRight.mTarget != null) && (mRight.mTarget.mOwner == mParent) && (mCenter.mTarget == null) && (!(paramConstraintWidget instanceof Guideline)) && (!(paramConstraintWidget instanceof Helper)))) {
        mStartHorizontalWidgets.add(paramConstraintWidget);
      }
      if (((mTop.mTarget == null) && (mBottom.mTarget == null)) || ((mTop.mTarget != null) && (mTop.mTarget.mOwner == mParent) && (mBottom.mTarget == null)) || ((mBottom.mTarget != null) && (mBottom.mTarget.mOwner == mParent) && (mTop.mTarget == null)) || ((mTop.mTarget != null) && (mTop.mTarget.mOwner == mParent) && (mBottom.mTarget != null) && (mBottom.mTarget.mOwner == mParent) && (mCenter.mTarget == null) && (mBaseline.mTarget == null) && (!(paramConstraintWidget instanceof Guideline)) && (!(paramConstraintWidget instanceof Helper)))) {
        mStartVerticalWidgets.add(paramConstraintWidget);
      }
      if ((paramConstraintWidget instanceof Helper))
      {
        invalidate(localConstraintWidgetContainer, paramConstraintWidget, paramConstraintWidgetGroup);
        if (paramBoolean) {
          return false;
        }
        localObject = (Helper)paramConstraintWidget;
        i = 0;
        while (i < mWidgetsCount)
        {
          if (!traverse(mWidgets[i], paramConstraintWidgetGroup, paramList, paramBoolean)) {
            return false;
          }
          i += 1;
        }
      }
      int j = mListAnchors.length;
      int i = 0;
      while (i < j)
      {
        localObject = mListAnchors[i];
        if ((mTarget != null) && (mTarget.mOwner != paramConstraintWidget.getParent()))
        {
          if (mType == ConstraintAnchor.Type.CENTER)
          {
            invalidate(localConstraintWidgetContainer, paramConstraintWidget, paramConstraintWidgetGroup);
            if (paramBoolean) {
              return false;
            }
          }
          else
          {
            setConnection((ConstraintAnchor)localObject);
          }
          if (!traverse(mTarget.mOwner, paramConstraintWidgetGroup, paramList, paramBoolean)) {
            return false;
          }
        }
        i += 1;
      }
      return true;
    }
    if (mBelongingGroup != paramConstraintWidgetGroup)
    {
      mConstrainedGroup.addAll(mBelongingGroup.mConstrainedGroup);
      mStartHorizontalWidgets.addAll(mBelongingGroup.mStartHorizontalWidgets);
      mStartVerticalWidgets.addAll(mBelongingGroup.mStartVerticalWidgets);
      if (!mBelongingGroup.mSkipSolver) {
        mSkipSolver = false;
      }
      paramList.remove(mBelongingGroup);
      paramConstraintWidget = mBelongingGroup.mConstrainedGroup.iterator();
      while (paramConstraintWidget.hasNext()) {
        nextmBelongingGroup = paramConstraintWidgetGroup;
      }
    }
    return true;
  }
  
  private static void updateSizeDependentWidgets(ConstraintWidget paramConstraintWidget, int paramInt1, int paramInt2)
  {
    int j = paramInt1 * 2;
    ConstraintAnchor localConstraintAnchor1 = mListAnchors[j];
    ConstraintAnchor localConstraintAnchor2 = mListAnchors[(j + 1)];
    if ((mTarget != null) && (mTarget != null)) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      Optimizer.setOptimizedWidget(paramConstraintWidget, paramInt1, getParentBiasOffset(paramConstraintWidget, paramInt1) + localConstraintAnchor1.getMargin());
      return;
    }
    if ((mDimensionRatio != 0.0F) && (paramConstraintWidget.getDimensionBehaviour(paramInt1) == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))
    {
      paramInt2 = resolveDimensionRatio(paramConstraintWidget);
      i = (int)mListAnchors[j].getResolutionNode().resolvedOffset;
      getResolutionNoderesolvedTarget = localConstraintAnchor1.getResolutionNode();
      getResolutionNoderesolvedOffset = paramInt2;
      getResolutionNodestate = 1;
      paramConstraintWidget.setFrame(i, i + paramInt2, paramInt1);
      return;
    }
    paramInt2 -= paramConstraintWidget.getRelativePositioning(paramInt1);
    int i = paramInt2 - paramConstraintWidget.getLength(paramInt1);
    paramConstraintWidget.setFrame(i, paramInt2, paramInt1);
    Optimizer.setOptimizedWidget(paramConstraintWidget, paramInt1, i);
  }
}