package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.ArrayRow;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import java.util.ArrayList;

class Chain
{
  private static final boolean DEBUG = false;
  
  Chain() {}
  
  static void applyChainConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, int paramInt)
  {
    int k = 0;
    ChainHead[] arrayOfChainHead;
    int i;
    int j;
    if (paramInt == 0)
    {
      arrayOfChainHead = mHorizontalChainsArray;
      i = mHorizontalChainsSize;
      j = 0;
    }
    else
    {
      j = 2;
      i = mVerticalChainsSize;
      arrayOfChainHead = mVerticalChainsArray;
    }
    while (k < i)
    {
      ChainHead localChainHead = arrayOfChainHead[k];
      localChainHead.define();
      if (paramConstraintWidgetContainer.optimizeFor(4))
      {
        if (!Optimizer.applyChainOptimized(paramConstraintWidgetContainer, paramLinearSystem, paramInt, j, localChainHead)) {
          applyChainConstraints(paramConstraintWidgetContainer, paramLinearSystem, paramInt, j, localChainHead);
        }
      }
      else {
        applyChainConstraints(paramConstraintWidgetContainer, paramLinearSystem, paramInt, j, localChainHead);
      }
      k += 1;
    }
  }
  
  static void applyChainConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, int paramInt1, int paramInt2, ChainHead paramChainHead)
  {
    ConstraintWidget localConstraintWidget3 = mFirst;
    ConstraintWidget localConstraintWidget1 = mLast;
    Object localObject1 = mFirstVisibleWidget;
    ConstraintWidget localConstraintWidget2 = mLastVisibleWidget;
    Object localObject6 = mHead;
    float f1 = mTotalWeight;
    Object localObject2 = mFirstMatchConstraintWidget;
    localObject2 = mLastMatchConstraintWidget;
    int n;
    if (mListDimensionBehaviors[paramInt1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
      n = 1;
    } else {
      n = 0;
    }
    int i;
    int j;
    if (paramInt1 == 0)
    {
      if (mHorizontalChainStyle == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (mHorizontalChainStyle == 1) {
        j = 1;
      } else {
        j = 0;
      }
      k = i;
      m = j;
      if (mHorizontalChainStyle != 2) {
        break label202;
      }
    }
    else
    {
      if (mVerticalChainStyle == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (mVerticalChainStyle == 1) {
        j = 1;
      } else {
        j = 0;
      }
      k = i;
      m = j;
      if (mVerticalChainStyle != 2) {
        break label202;
      }
    }
    int i1 = 1;
    int k = i;
    int m = j;
    break label205;
    label202:
    i1 = 0;
    label205:
    Object localObject3 = localConstraintWidget3;
    int i2 = 0;
    Object localObject4;
    int i3;
    Object localObject5;
    for (;;)
    {
      localObject4 = null;
      if (i2 != 0) {
        break;
      }
      localObject2 = mListAnchors[paramInt2];
      if ((n == 0) && (i1 == 0)) {
        i = 4;
      } else {
        i = 1;
      }
      int i4 = ((ConstraintAnchor)localObject2).getMargin();
      j = i4;
      i3 = j;
      if (mTarget != null)
      {
        i3 = j;
        if (localObject3 != localConstraintWidget3) {
          i3 = i4 + mTarget.getMargin();
        }
      }
      if ((i1 != 0) && (localObject3 != localConstraintWidget3) && (localObject3 != localObject1))
      {
        j = 6;
      }
      else
      {
        j = i;
        if (k != 0)
        {
          j = i;
          if (n != 0) {
            j = 4;
          }
        }
      }
      if (mTarget != null)
      {
        if (localObject3 == localObject1) {
          paramLinearSystem.addGreaterThan(mSolverVariable, mTarget.mSolverVariable, i3, 5);
        } else {
          paramLinearSystem.addGreaterThan(mSolverVariable, mTarget.mSolverVariable, i3, 6);
        }
        paramLinearSystem.addEquality(mSolverVariable, mTarget.mSolverVariable, i3, j);
      }
      if (n != 0)
      {
        if ((((ConstraintWidget)localObject3).getVisibility() != 8) && (mListDimensionBehaviors[paramInt1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
          paramLinearSystem.addGreaterThan(mListAnchors[(paramInt2 + 1)].mSolverVariable, mListAnchors[paramInt2].mSolverVariable, 0, 5);
        }
        paramLinearSystem.addGreaterThan(mListAnchors[paramInt2].mSolverVariable, mListAnchors[paramInt2].mSolverVariable, 0, 6);
      }
      localObject5 = mListAnchors[(paramInt2 + 1)].mTarget;
      localObject2 = localObject4;
      if (localObject5 != null)
      {
        localObject5 = mOwner;
        localObject2 = localObject4;
        if (mListAnchors[paramInt2].mTarget != null) {
          if (mListAnchors[paramInt2].mTarget.mOwner != localObject3) {
            localObject2 = localObject4;
          } else {
            localObject2 = localObject5;
          }
        }
      }
      if (localObject2 == null)
      {
        i2 = 1;
        localObject2 = localObject3;
      }
      localObject3 = localObject2;
    }
    if (localConstraintWidget2 != null)
    {
      localObject2 = mListAnchors;
      i = paramInt2 + 1;
      if (mTarget != null)
      {
        localObject2 = mListAnchors[i];
        paramLinearSystem.addLowerThan(mSolverVariable, mListAnchors[i].mTarget.mSolverVariable, -((ConstraintAnchor)localObject2).getMargin(), 5);
      }
    }
    if (n != 0)
    {
      paramConstraintWidgetContainer = mListAnchors;
      i = paramInt2 + 1;
      paramLinearSystem.addGreaterThan(mSolverVariable, mListAnchors[i].mSolverVariable, mListAnchors[i].getMargin(), 6);
    }
    localObject3 = mWeightedMatchConstraintsWidgets;
    label926:
    Object localObject7;
    Object localObject8;
    if (localObject3 != null)
    {
      j = ((ArrayList)localObject3).size();
      if (j > 1)
      {
        float f2 = f1;
        if (mHasUndefinedWeights)
        {
          f2 = f1;
          if (!mHasComplexMatchWeights) {
            f2 = mWidgetsMatchCount;
          }
        }
        paramConstraintWidgetContainer = null;
        i = 0;
        for (float f3 = 0.0F; i < j; f3 = f1)
        {
          localObject2 = (ConstraintWidget)((ArrayList)localObject3).get(i);
          float f4 = mWeight[paramInt1];
          f1 = f4;
          if (f4 < 0.0F)
          {
            if (mHasComplexMatchWeights)
            {
              paramLinearSystem.addEquality(mListAnchors[(paramInt2 + 1)].mSolverVariable, mListAnchors[paramInt2].mSolverVariable, 0, 4);
              break label926;
            }
            f1 = 1.0F;
          }
          if (f1 == 0.0F)
          {
            paramLinearSystem.addEquality(mListAnchors[(paramInt2 + 1)].mSolverVariable, mListAnchors[paramInt2].mSolverVariable, 0, 6);
            f1 = f3;
          }
          else
          {
            if (paramConstraintWidgetContainer != null)
            {
              localObject4 = mListAnchors[paramInt2].mSolverVariable;
              paramConstraintWidgetContainer = mListAnchors;
              n = paramInt2 + 1;
              paramConstraintWidgetContainer = mSolverVariable;
              localObject5 = mListAnchors[paramInt2].mSolverVariable;
              localObject7 = mListAnchors[n].mSolverVariable;
              localObject8 = paramLinearSystem.createRow();
              ((ArrayRow)localObject8).createRowEqualMatchDimensions(f3, f2, f1, (SolverVariable)localObject4, paramConstraintWidgetContainer, (SolverVariable)localObject5, (SolverVariable)localObject7);
              paramLinearSystem.addConstraint((ArrayRow)localObject8);
            }
            paramConstraintWidgetContainer = (ConstraintWidgetContainer)localObject2;
          }
          i += 1;
        }
      }
    }
    if ((localObject1 != null) && ((localObject1 == localConstraintWidget2) || (i1 != 0)))
    {
      localObject2 = mListAnchors[paramInt2];
      paramConstraintWidgetContainer = mListAnchors;
      i = paramInt2 + 1;
      localObject3 = paramConstraintWidgetContainer[i];
      if (mListAnchors[paramInt2].mTarget != null) {
        paramConstraintWidgetContainer = mListAnchors[paramInt2].mTarget.mSolverVariable;
      } else {
        paramConstraintWidgetContainer = null;
      }
      if (mListAnchors[i].mTarget != null) {
        paramChainHead = mListAnchors[i].mTarget.mSolverVariable;
      } else {
        paramChainHead = null;
      }
      if (localObject1 == localConstraintWidget2)
      {
        localObject2 = mListAnchors[paramInt2];
        localObject3 = mListAnchors[i];
      }
      if ((paramConstraintWidgetContainer != null) && (paramChainHead != null))
      {
        if (paramInt1 == 0) {
          f1 = mHorizontalBiasPercent;
        } else {
          f1 = mVerticalBiasPercent;
        }
        paramInt1 = ((ConstraintAnchor)localObject2).getMargin();
        i = ((ConstraintAnchor)localObject3).getMargin();
        paramLinearSystem.addCentering(mSolverVariable, paramConstraintWidgetContainer, paramInt1, f1, paramChainHead, mSolverVariable, i, 5);
      }
    }
    else
    {
      if ((k != 0) && (localObject1 != null))
      {
        if ((mWidgetsMatchCount > 0) && (mWidgetsCount == mWidgetsMatchCount)) {
          n = 1;
        } else {
          n = 0;
        }
        paramChainHead = (ChainHead)localObject1;
        localObject4 = localObject1;
      }
      while (paramChainHead != null)
      {
        for (localObject2 = mNextChainWidget[paramInt1]; (localObject2 != null) && (((ConstraintWidget)localObject2).getVisibility() == 8); localObject2 = mNextChainWidget[paramInt1]) {}
        if ((localObject2 == null) && (paramChainHead != localConstraintWidget2)) {}
        for (;;)
        {
          break;
          localObject5 = mListAnchors[paramInt2];
          localObject8 = mSolverVariable;
          if (mTarget != null) {
            localObject3 = mTarget.mSolverVariable;
          } else {
            localObject3 = null;
          }
          if (localObject4 != paramChainHead)
          {
            paramConstraintWidgetContainer = mListAnchors[(paramInt2 + 1)].mSolverVariable;
          }
          else
          {
            paramConstraintWidgetContainer = (ConstraintWidgetContainer)localObject3;
            if (paramChainHead == localObject1)
            {
              paramConstraintWidgetContainer = (ConstraintWidgetContainer)localObject3;
              if (localObject4 == paramChainHead) {
                if (mListAnchors[paramInt2].mTarget != null) {
                  paramConstraintWidgetContainer = mListAnchors[paramInt2].mTarget.mSolverVariable;
                } else {
                  paramConstraintWidgetContainer = null;
                }
              }
            }
          }
          i1 = ((ConstraintAnchor)localObject5).getMargin();
          j = i1;
          localObject3 = mListAnchors;
          i3 = paramInt2 + 1;
          i2 = localObject3[i3].getMargin();
          i = i2;
          if (localObject2 != null)
          {
            localObject3 = mListAnchors[paramInt2];
            localObject5 = mSolverVariable;
            localObject6 = mListAnchors[i3].mSolverVariable;
          }
          else
          {
            localObject7 = mListAnchors[i3].mTarget;
            if (localObject7 != null) {
              localObject3 = mSolverVariable;
            } else {
              localObject3 = null;
            }
            localObject6 = mListAnchors[i3].mSolverVariable;
            localObject5 = localObject3;
            localObject3 = localObject7;
          }
          if (localObject3 != null) {
            i = i2 + ((ConstraintAnchor)localObject3).getMargin();
          }
          if (localObject4 != null) {
            j = i1 + mListAnchors[i3].getMargin();
          }
          if ((localObject8 != null) && (paramConstraintWidgetContainer != null) && (localObject5 != null) && (localObject6 != null))
          {
            if (paramChainHead == localObject1) {
              j = mListAnchors[paramInt2].getMargin();
            }
            if (paramChainHead == localConstraintWidget2) {
              i = mListAnchors[i3].getMargin();
            }
            if (n != 0) {
              i1 = 6;
            } else {
              i1 = 4;
            }
            paramLinearSystem.addCentering((SolverVariable)localObject8, paramConstraintWidgetContainer, j, 0.5F, (SolverVariable)localObject5, (SolverVariable)localObject6, i, i1);
          }
        }
        if (paramChainHead.getVisibility() != 8) {
          localObject4 = paramChainHead;
        }
        paramChainHead = (ChainHead)localObject2;
        continue;
        if ((m != 0) && (localObject1 != null))
        {
          if ((mWidgetsMatchCount > 0) && (mWidgetsCount == mWidgetsMatchCount)) {
            i = 1;
          } else {
            i = 0;
          }
          paramChainHead = (ChainHead)localObject1;
          localObject2 = localObject1;
          while (paramChainHead != null)
          {
            for (paramConstraintWidgetContainer = mNextChainWidget[paramInt1]; (paramConstraintWidgetContainer != null) && (paramConstraintWidgetContainer.getVisibility() == 8); paramConstraintWidgetContainer = mNextChainWidget[paramInt1]) {}
            localObject3 = paramConstraintWidgetContainer;
            if (paramChainHead != localObject1)
            {
              localObject3 = paramConstraintWidgetContainer;
              if (paramChainHead != localConstraintWidget2)
              {
                localObject3 = paramConstraintWidgetContainer;
                if (paramConstraintWidgetContainer != null)
                {
                  localObject3 = paramConstraintWidgetContainer;
                  if (paramConstraintWidgetContainer == localConstraintWidget2) {
                    localObject3 = null;
                  }
                  paramConstraintWidgetContainer = mListAnchors[paramInt2];
                  localObject7 = mSolverVariable;
                  if (mTarget != null) {
                    localObject4 = mTarget.mSolverVariable;
                  }
                  localObject4 = mListAnchors;
                  i3 = paramInt2 + 1;
                  localObject8 = mSolverVariable;
                  i1 = paramConstraintWidgetContainer.getMargin();
                  n = i1;
                  i2 = mListAnchors[i3].getMargin();
                  j = i2;
                  if (localObject3 != null)
                  {
                    localObject4 = mListAnchors[paramInt2];
                    localObject5 = mSolverVariable;
                    if (mTarget != null) {
                      paramConstraintWidgetContainer = mTarget.mSolverVariable;
                    } else {
                      paramConstraintWidgetContainer = null;
                    }
                  }
                  else
                  {
                    localObject6 = mListAnchors[i3].mTarget;
                    if (localObject6 != null) {
                      localObject4 = mSolverVariable;
                    } else {
                      localObject4 = null;
                    }
                    paramConstraintWidgetContainer = mListAnchors[i3].mSolverVariable;
                    localObject5 = localObject4;
                    localObject4 = localObject6;
                  }
                  if (localObject4 != null) {
                    j = i2 + ((ConstraintAnchor)localObject4).getMargin();
                  }
                  if (localObject2 != null) {
                    n = i1 + mListAnchors[i3].getMargin();
                  }
                  if (i != 0) {
                    i1 = 6;
                  } else {
                    i1 = 4;
                  }
                  if ((localObject7 != null) && (localObject8 != null) && (localObject5 != null) && (paramConstraintWidgetContainer != null)) {
                    paramLinearSystem.addCentering((SolverVariable)localObject7, (SolverVariable)localObject8, n, 0.5F, (SolverVariable)localObject5, paramConstraintWidgetContainer, j, i1);
                  }
                }
              }
            }
            if (paramChainHead.getVisibility() == 8) {
              paramChainHead = (ChainHead)localObject2;
            }
            localObject2 = paramChainHead;
            paramChainHead = (ChainHead)localObject3;
          }
          paramConstraintWidgetContainer = mListAnchors[paramInt2];
          paramChainHead = mListAnchors[paramInt2].mTarget;
          localObject2 = mListAnchors;
          paramInt1 = paramInt2 + 1;
          localObject2 = localObject2[paramInt1];
          localObject3 = mListAnchors[paramInt1].mTarget;
          if (paramChainHead != null) {
            if (localObject1 != localConstraintWidget2) {
              paramLinearSystem.addEquality(mSolverVariable, mSolverVariable, paramConstraintWidgetContainer.getMargin(), 5);
            } else if (localObject3 != null) {
              paramLinearSystem.addCentering(mSolverVariable, mSolverVariable, paramConstraintWidgetContainer.getMargin(), 0.5F, mSolverVariable, mSolverVariable, ((ConstraintAnchor)localObject2).getMargin(), 5);
            }
          }
          if ((localObject3 != null) && (localObject1 != localConstraintWidget2)) {
            paramLinearSystem.addEquality(mSolverVariable, mSolverVariable, -((ConstraintAnchor)localObject2).getMargin(), 5);
          }
        }
      }
    }
    if (((k != 0) || (m != 0)) && (localObject1 != null))
    {
      localObject2 = mListAnchors[paramInt2];
      paramConstraintWidgetContainer = mListAnchors;
      paramInt1 = paramInt2 + 1;
      localObject3 = paramConstraintWidgetContainer[paramInt1];
      if (mTarget != null) {
        paramChainHead = mTarget.mSolverVariable;
      } else {
        paramChainHead = null;
      }
      if (mTarget != null) {
        paramConstraintWidgetContainer = mTarget.mSolverVariable;
      } else {
        paramConstraintWidgetContainer = null;
      }
      if (localConstraintWidget1 != localConstraintWidget2)
      {
        paramConstraintWidgetContainer = mListAnchors[paramInt1];
        if (mTarget != null) {
          paramConstraintWidgetContainer = mTarget.mSolverVariable;
        } else {
          paramConstraintWidgetContainer = null;
        }
      }
      if (localObject1 == localConstraintWidget2)
      {
        localObject2 = mListAnchors[paramInt2];
        localObject3 = mListAnchors[paramInt1];
      }
      if ((paramChainHead != null) && (paramConstraintWidgetContainer != null))
      {
        paramInt2 = ((ConstraintAnchor)localObject2).getMargin();
        if (localConstraintWidget2 == null) {
          localObject1 = localConstraintWidget1;
        } else {
          localObject1 = localConstraintWidget2;
        }
        paramInt1 = mListAnchors[paramInt1].getMargin();
        paramLinearSystem.addCentering(mSolverVariable, paramChainHead, paramInt2, 0.5F, paramConstraintWidgetContainer, mSolverVariable, paramInt1, 5);
      }
    }
  }
}
