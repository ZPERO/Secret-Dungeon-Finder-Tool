package androidx.constraintlayout.solver.widgets;

import java.util.ArrayList;

public class ChainHead
{
  private boolean mDefined;
  protected ConstraintWidget mFirst;
  protected ConstraintWidget mFirstMatchConstraintWidget;
  protected ConstraintWidget mFirstVisibleWidget;
  protected boolean mHasComplexMatchWeights;
  protected boolean mHasDefinedWeights;
  protected boolean mHasUndefinedWeights;
  protected ConstraintWidget mHead;
  private boolean mIsRtl = false;
  protected ConstraintWidget mLast;
  protected ConstraintWidget mLastMatchConstraintWidget;
  protected ConstraintWidget mLastVisibleWidget;
  private int mOrientation;
  protected float mTotalWeight = 0.0F;
  protected ArrayList<ConstraintWidget> mWeightedMatchConstraintsWidgets;
  protected int mWidgetsCount;
  protected int mWidgetsMatchCount;
  
  public ChainHead(ConstraintWidget paramConstraintWidget, int paramInt, boolean paramBoolean)
  {
    mFirst = paramConstraintWidget;
    mOrientation = paramInt;
    mIsRtl = paramBoolean;
  }
  
  private void defineChainProperties()
  {
    int j = mOrientation * 2;
    Object localObject2 = mFirst;
    boolean bool2 = false;
    Object localObject1 = localObject2;
    int i = 0;
    while (i == 0)
    {
      mWidgetsCount += 1;
      Object localObject4 = mNextChainWidget;
      int k = mOrientation;
      Object localObject3 = null;
      localObject4[k] = null;
      mListNextMatchConstraintsWidget[mOrientation] = null;
      if (localObject1.getVisibility() != 8)
      {
        if (mFirstVisibleWidget == null) {
          mFirstVisibleWidget = localObject1;
        }
        mLastVisibleWidget = localObject1;
        if ((mListDimensionBehaviors[mOrientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && ((mResolvedMatchConstraintDefault[mOrientation] == 0) || (mResolvedMatchConstraintDefault[mOrientation] == 3) || (mResolvedMatchConstraintDefault[mOrientation] == 2)))
        {
          mWidgetsMatchCount += 1;
          float f = mWeight[mOrientation];
          if (f > 0.0F) {
            mTotalWeight += mWeight[mOrientation];
          }
          if (isMatchConstraintEqualityCandidate(localObject1, mOrientation))
          {
            if (f < 0.0F) {
              mHasUndefinedWeights = true;
            } else {
              mHasDefinedWeights = true;
            }
            if (mWeightedMatchConstraintsWidgets == null) {
              mWeightedMatchConstraintsWidgets = new ArrayList();
            }
            mWeightedMatchConstraintsWidgets.add(localObject1);
          }
          if (mFirstMatchConstraintWidget == null) {
            mFirstMatchConstraintWidget = localObject1;
          }
          localObject4 = mLastMatchConstraintWidget;
          if (localObject4 != null) {
            mListNextMatchConstraintsWidget[mOrientation] = localObject1;
          }
          mLastMatchConstraintWidget = localObject1;
        }
      }
      if (localObject2 != localObject1) {
        mNextChainWidget[mOrientation] = localObject1;
      }
      localObject4 = mListAnchors[(j + 1)].mTarget;
      localObject2 = localObject3;
      if (localObject4 != null)
      {
        localObject4 = mOwner;
        localObject2 = localObject3;
        if (mListAnchors[j].mTarget != null) {
          if (mListAnchors[j].mTarget.mOwner != localObject1) {
            localObject2 = localObject3;
          } else {
            localObject2 = localObject4;
          }
        }
      }
      if (localObject2 == null)
      {
        localObject2 = localObject1;
        i = 1;
      }
      localObject3 = localObject1;
      localObject1 = localObject2;
      localObject2 = localObject3;
    }
    mLast = localObject1;
    if ((mOrientation == 0) && (mIsRtl)) {
      mHead = mLast;
    } else {
      mHead = mFirst;
    }
    boolean bool1 = bool2;
    if (mHasDefinedWeights)
    {
      bool1 = bool2;
      if (mHasUndefinedWeights) {
        bool1 = true;
      }
    }
    mHasComplexMatchWeights = bool1;
  }
  
  private static boolean isMatchConstraintEqualityCandidate(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    return (paramConstraintWidget.getVisibility() != 8) && (mListDimensionBehaviors[paramInt] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && ((mResolvedMatchConstraintDefault[paramInt] == 0) || (mResolvedMatchConstraintDefault[paramInt] == 3));
  }
  
  public void define()
  {
    if (!mDefined) {
      defineChainProperties();
    }
    mDefined = true;
  }
  
  public ConstraintWidget getFirst()
  {
    return mFirst;
  }
  
  public ConstraintWidget getFirstMatchConstraintWidget()
  {
    return mFirstMatchConstraintWidget;
  }
  
  public ConstraintWidget getFirstVisibleWidget()
  {
    return mFirstVisibleWidget;
  }
  
  public ConstraintWidget getHead()
  {
    return mHead;
  }
  
  public ConstraintWidget getLast()
  {
    return mLast;
  }
  
  public ConstraintWidget getLastMatchConstraintWidget()
  {
    return mLastMatchConstraintWidget;
  }
  
  public ConstraintWidget getLastVisibleWidget()
  {
    return mLastVisibleWidget;
  }
  
  public float getTotalWeight()
  {
    return mTotalWeight;
  }
}
