package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.constraintlayout.solver.Metrics;
import androidx.constraintlayout.solver.widgets.Analyzer;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor.Strength;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.ResolutionAnchor;
import androidx.constraintlayout.solver.widgets.ResolutionDimension;
import androidx.constraintlayout.solver.widgets.ResolutionNode;
import androidx.constraintlayout.solver.widgets.WidgetContainer;
import java.util.ArrayList;
import java.util.HashMap;

public class ConstraintLayout
  extends ViewGroup
{
  static final boolean ALLOWS_EMBEDDED = false;
  private static final boolean CACHE_MEASURED_DIMENSION = false;
  private static final boolean DEBUG = false;
  public static final int DESIGN_INFO_ID = 0;
  private static final String PAGE_KEY = "ConstraintLayout";
  private static final boolean USE_CONSTRAINTS_HELPER = true;
  public static final String VERSION = "ConstraintLayout-1.1.3";
  SparseArray<View> mChildrenByIds = new SparseArray();
  private ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList(4);
  private ConstraintSet mConstraintSet = null;
  private int mConstraintSetId = -1;
  private HashMap<String, Integer> mDesignIds = new HashMap();
  private boolean mDirtyHierarchy = true;
  private int mLastMeasureHeight = -1;
  int mLastMeasureHeightMode = 0;
  int mLastMeasureHeightSize = -1;
  private int mLastMeasureWidth = -1;
  int mLastMeasureWidthMode = 0;
  int mLastMeasureWidthSize = -1;
  ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
  private int mMaxHeight = Integer.MAX_VALUE;
  private int mMaxWidth = Integer.MAX_VALUE;
  private Metrics mMetrics;
  private int mMinHeight = 0;
  private int mMinWidth = 0;
  private int mOptimizationLevel = 7;
  private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList(100);
  
  public ConstraintLayout(Context paramContext)
  {
    super(paramContext);
    init(null);
  }
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
  }
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
  }
  
  private final ConstraintWidget getTargetWidget(int paramInt)
  {
    if (paramInt == 0) {
      return mLayoutWidget;
    }
    Object localObject2 = (View)mChildrenByIds.get(paramInt);
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      View localView = findViewById(paramInt);
      localObject2 = localView;
      localObject1 = localObject2;
      if (localView != null)
      {
        localObject1 = localObject2;
        if (localView != this)
        {
          localObject1 = localObject2;
          if (localView.getParent() == this)
          {
            onViewAdded(localView);
            localObject1 = localObject2;
          }
        }
      }
    }
    if (localObject1 == this) {
      return mLayoutWidget;
    }
    if (localObject1 == null) {
      return null;
    }
    return getLayoutParamswidget;
  }
  
  private void init(AttributeSet paramAttributeSet)
  {
    mLayoutWidget.setCompanionWidget(this);
    mChildrenByIds.put(getId(), this);
    mConstraintSet = null;
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int j = paramAttributeSet.getIndexCount();
      int i = 0;
      while (i < j)
      {
        int k = paramAttributeSet.getIndex(i);
        if (k == R.styleable.ConstraintLayout_Layout_android_minWidth) {
          mMinWidth = paramAttributeSet.getDimensionPixelOffset(k, mMinWidth);
        } else if (k == R.styleable.ConstraintLayout_Layout_android_minHeight) {
          mMinHeight = paramAttributeSet.getDimensionPixelOffset(k, mMinHeight);
        } else if (k == R.styleable.ConstraintLayout_Layout_android_maxWidth) {
          mMaxWidth = paramAttributeSet.getDimensionPixelOffset(k, mMaxWidth);
        } else if (k == R.styleable.ConstraintLayout_Layout_android_maxHeight) {
          mMaxHeight = paramAttributeSet.getDimensionPixelOffset(k, mMaxHeight);
        } else if (k == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
          mOptimizationLevel = paramAttributeSet.getInt(k, mOptimizationLevel);
        } else if (k == R.styleable.ConstraintLayout_Layout_constraintSet) {
          k = paramAttributeSet.getResourceId(k, 0);
        }
        try
        {
          ConstraintSet localConstraintSet = new ConstraintSet();
          mConstraintSet = localConstraintSet;
          localConstraintSet = mConstraintSet;
          localConstraintSet.load(getContext(), k);
        }
        catch (Resources.NotFoundException localNotFoundException)
        {
          for (;;) {}
        }
        mConstraintSet = null;
        mConstraintSetId = k;
        i += 1;
      }
      paramAttributeSet.recycle();
    }
    mLayoutWidget.setOptimizationLevel(mOptimizationLevel);
  }
  
  private void internalMeasureChildren(int paramInt1, int paramInt2)
  {
    int i3 = getPaddingTop() + getPaddingBottom();
    int i4 = getPaddingLeft() + getPaddingRight();
    int i5 = getChildCount();
    int n = 0;
    while (n < i5)
    {
      View localView = getChildAt(n);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        ConstraintWidget localConstraintWidget = widget;
        if ((!isGuideline) && (!isHelper))
        {
          localConstraintWidget.setVisibility(localView.getVisibility());
          int i1 = width;
          int i2 = height;
          int i;
          if ((!horizontalDimensionFixed) && (!verticalDimensionFixed) && ((horizontalDimensionFixed) || (matchConstraintDefaultWidth != 1)) && (width != -1) && ((verticalDimensionFixed) || ((matchConstraintDefaultHeight != 1) && (height != -1)))) {
            i = 0;
          } else {
            i = 1;
          }
          int k;
          int m;
          int j;
          if (i != 0)
          {
            if (i1 == 0)
            {
              k = ViewGroup.getChildMeasureSpec(paramInt1, i4, -2);
              i = 1;
            }
            else if (i1 == -1)
            {
              k = ViewGroup.getChildMeasureSpec(paramInt1, i4, -1);
              i = 0;
            }
            else
            {
              if (i1 == -2) {
                i = 1;
              } else {
                i = 0;
              }
              k = ViewGroup.getChildMeasureSpec(paramInt1, i4, i1);
            }
            if (i2 == 0)
            {
              m = ViewGroup.getChildMeasureSpec(paramInt2, i3, -2);
              j = 1;
            }
            else if (i2 == -1)
            {
              m = ViewGroup.getChildMeasureSpec(paramInt2, i3, -1);
              j = 0;
            }
            else
            {
              if (i2 == -2) {
                j = 1;
              } else {
                j = 0;
              }
              m = ViewGroup.getChildMeasureSpec(paramInt2, i3, i2);
            }
            localView.measure(k, m);
            Metrics localMetrics = mMetrics;
            if (localMetrics != null) {
              measures += 1L;
            }
            boolean bool;
            if (i1 == -2) {
              bool = true;
            } else {
              bool = false;
            }
            localConstraintWidget.setWidthWrapContent(bool);
            if (i2 == -2) {
              bool = true;
            } else {
              bool = false;
            }
            localConstraintWidget.setHeightWrapContent(bool);
            k = localView.getMeasuredWidth();
            m = localView.getMeasuredHeight();
          }
          else
          {
            i = 0;
            j = 0;
            m = i2;
            k = i1;
          }
          localConstraintWidget.setWidth(k);
          localConstraintWidget.setHeight(m);
          if (i != 0) {
            localConstraintWidget.setWrapWidth(k);
          }
          if (j != 0) {
            localConstraintWidget.setWrapHeight(m);
          }
          if (needsBaseline)
          {
            i = localView.getBaseline();
            if (i != -1) {
              localConstraintWidget.setBaselineDistance(i);
            }
          }
        }
      }
      n += 1;
    }
  }
  
  private void internalMeasureDimensions(int paramInt1, int paramInt2)
  {
    Object localObject1 = this;
    int i6 = getPaddingTop() + getPaddingBottom();
    int i7 = getPaddingLeft() + getPaddingRight();
    int i8 = getChildCount();
    int i = 0;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    int m;
    int n;
    int j;
    int k;
    Object localObject5;
    boolean bool;
    while (i < i8)
    {
      localObject2 = getChildAt(i);
      if (((View)localObject2).getVisibility() != 8)
      {
        localObject3 = (LayoutParams)((View)localObject2).getLayoutParams();
        localObject4 = widget;
        if ((!isGuideline) && (!isHelper))
        {
          ((ConstraintWidget)localObject4).setVisibility(((View)localObject2).getVisibility());
          m = width;
          n = height;
          if ((m != 0) && (n != 0))
          {
            if (m == -2) {
              j = 1;
            } else {
              j = 0;
            }
            i1 = ViewGroup.getChildMeasureSpec(paramInt1, i7, m);
            if (n == -2) {
              k = 1;
            } else {
              k = 0;
            }
            ((View)localObject2).measure(i1, ViewGroup.getChildMeasureSpec(paramInt2, i6, n));
            localObject5 = mMetrics;
            if (localObject5 != null) {
              measures += 1L;
            }
            if (m == -2) {
              bool = true;
            } else {
              bool = false;
            }
            ((ConstraintWidget)localObject4).setWidthWrapContent(bool);
            if (n == -2) {
              bool = true;
            } else {
              bool = false;
            }
            ((ConstraintWidget)localObject4).setHeightWrapContent(bool);
            m = ((View)localObject2).getMeasuredWidth();
            n = ((View)localObject2).getMeasuredHeight();
            ((ConstraintWidget)localObject4).setWidth(m);
            ((ConstraintWidget)localObject4).setHeight(n);
            if (j != 0) {
              ((ConstraintWidget)localObject4).setWrapWidth(m);
            }
            if (k != 0) {
              ((ConstraintWidget)localObject4).setWrapHeight(n);
            }
            if (needsBaseline)
            {
              j = ((View)localObject2).getBaseline();
              if (j != -1) {
                ((ConstraintWidget)localObject4).setBaselineDistance(j);
              }
            }
            if ((horizontalDimensionFixed) && (verticalDimensionFixed))
            {
              ((ConstraintWidget)localObject4).getResolutionWidth().resolve(m);
              ((ConstraintWidget)localObject4).getResolutionHeight().resolve(n);
            }
          }
          else
          {
            ((ConstraintWidget)localObject4).getResolutionWidth().invalidate();
            ((ConstraintWidget)localObject4).getResolutionHeight().invalidate();
          }
        }
      }
      i += 1;
    }
    mLayoutWidget.solveGraph();
    int i1 = 0;
    while (i1 < i8)
    {
      localObject3 = ((ViewGroup)localObject1).getChildAt(i1);
      if (((View)localObject3).getVisibility() == 8)
      {
        localObject2 = localObject1;
      }
      else
      {
        localObject4 = (LayoutParams)((View)localObject3).getLayoutParams();
        localObject5 = widget;
        localObject2 = localObject1;
        if (!isGuideline) {
          if (isHelper)
          {
            localObject2 = localObject1;
          }
          else
          {
            ((ConstraintWidget)localObject5).setVisibility(((View)localObject3).getVisibility());
            int i3 = width;
            int i2 = height;
            if ((i3 != 0) && (i2 != 0))
            {
              localObject2 = localObject1;
            }
            else
            {
              localObject2 = ((ConstraintWidget)localObject5).getAnchor(ConstraintAnchor.Type.LEFT).getResolutionNode();
              ResolutionAnchor localResolutionAnchor1 = ((ConstraintWidget)localObject5).getAnchor(ConstraintAnchor.Type.RIGHT).getResolutionNode();
              if ((((ConstraintWidget)localObject5).getAnchor(ConstraintAnchor.Type.LEFT).getTarget() != null) && (((ConstraintWidget)localObject5).getAnchor(ConstraintAnchor.Type.RIGHT).getTarget() != null)) {
                j = 1;
              } else {
                j = 0;
              }
              ResolutionAnchor localResolutionAnchor2 = ((ConstraintWidget)localObject5).getAnchor(ConstraintAnchor.Type.MIDDLE).getResolutionNode();
              ResolutionAnchor localResolutionAnchor3 = ((ConstraintWidget)localObject5).getAnchor(ConstraintAnchor.Type.BOTTOM).getResolutionNode();
              if ((((ConstraintWidget)localObject5).getAnchor(ConstraintAnchor.Type.MIDDLE).getTarget() != null) && (((ConstraintWidget)localObject5).getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget() != null)) {
                n = 1;
              } else {
                n = 0;
              }
              if ((i3 == 0) && (i2 == 0) && (j != 0) && (n != 0))
              {
                localObject2 = localObject1;
              }
              else
              {
                if (mLayoutWidget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  m = 1;
                } else {
                  m = 0;
                }
                if (mLayoutWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  i = 1;
                } else {
                  i = 0;
                }
                if (m == 0) {
                  ((ConstraintWidget)localObject5).getResolutionWidth().invalidate();
                }
                if (i == 0) {
                  ((ConstraintWidget)localObject5).getResolutionHeight().invalidate();
                }
                if (i3 == 0)
                {
                  if ((m != 0) && (((ConstraintWidget)localObject5).isSpreadWidth()) && (j != 0) && (((ResolutionNode)localObject2).isResolved()) && (localResolutionAnchor1.isResolved()))
                  {
                    k = (int)(localResolutionAnchor1.getResolvedValue() - ((ResolutionAnchor)localObject2).getResolvedValue());
                    ((ConstraintWidget)localObject5).getResolutionWidth().resolve(k);
                    j = ViewGroup.getChildMeasureSpec(paramInt1, i7, k);
                  }
                  else
                  {
                    j = ViewGroup.getChildMeasureSpec(paramInt1, i7, -2);
                    k = 1;
                    i4 = 0;
                    break label915;
                  }
                }
                else
                {
                  if (i3 != -1) {
                    break label885;
                  }
                  j = ViewGroup.getChildMeasureSpec(paramInt1, i7, -1);
                  k = i3;
                }
                int i5 = 0;
                i3 = k;
                int i4 = m;
                k = i5;
                break label915;
                label885:
                if (i3 == -2) {
                  k = 1;
                } else {
                  k = 0;
                }
                j = ViewGroup.getChildMeasureSpec(paramInt1, i7, i3);
                i4 = m;
                label915:
                if (i2 == 0)
                {
                  if ((i != 0) && (((ConstraintWidget)localObject5).isSpreadHeight()) && (n != 0) && (localResolutionAnchor2.isResolved()) && (localResolutionAnchor3.isResolved()))
                  {
                    n = (int)(localResolutionAnchor3.getResolvedValue() - localResolutionAnchor2.getResolvedValue());
                    ((ConstraintWidget)localObject5).getResolutionHeight().resolve(n);
                    m = ViewGroup.getChildMeasureSpec(paramInt2, i6, n);
                  }
                  else
                  {
                    m = ViewGroup.getChildMeasureSpec(paramInt2, i6, -2);
                    n = 1;
                    i = 0;
                    break label1067;
                  }
                }
                else
                {
                  if (i2 != -1) {
                    break label1041;
                  }
                  m = ViewGroup.getChildMeasureSpec(paramInt2, i6, -1);
                  n = i2;
                }
                i5 = 0;
                i2 = n;
                n = i5;
                break label1067;
                label1041:
                if (i2 == -2) {
                  n = 1;
                } else {
                  n = 0;
                }
                m = ViewGroup.getChildMeasureSpec(paramInt2, i6, i2);
                label1067:
                ((View)localObject3).measure(j, m);
                localObject1 = this;
                localObject2 = mMetrics;
                if (localObject2 != null) {
                  measures += 1L;
                }
                if (i3 == -2) {
                  bool = true;
                } else {
                  bool = false;
                }
                ((ConstraintWidget)localObject5).setWidthWrapContent(bool);
                if (i2 == -2) {
                  bool = true;
                } else {
                  bool = false;
                }
                ((ConstraintWidget)localObject5).setHeightWrapContent(bool);
                j = ((View)localObject3).getMeasuredWidth();
                m = ((View)localObject3).getMeasuredHeight();
                ((ConstraintWidget)localObject5).setWidth(j);
                ((ConstraintWidget)localObject5).setHeight(m);
                if (k != 0) {
                  ((ConstraintWidget)localObject5).setWrapWidth(j);
                }
                if (n != 0) {
                  ((ConstraintWidget)localObject5).setWrapHeight(m);
                }
                if (i4 != 0) {
                  ((ConstraintWidget)localObject5).getResolutionWidth().resolve(j);
                } else {
                  ((ConstraintWidget)localObject5).getResolutionWidth().remove();
                }
                if (i != 0) {
                  ((ConstraintWidget)localObject5).getResolutionHeight().resolve(m);
                } else {
                  ((ConstraintWidget)localObject5).getResolutionHeight().remove();
                }
                if (needsBaseline)
                {
                  i = ((View)localObject3).getBaseline();
                  localObject2 = localObject1;
                  if (i != -1)
                  {
                    ((ConstraintWidget)localObject5).setBaselineDistance(i);
                    localObject2 = localObject1;
                  }
                }
                else
                {
                  localObject2 = localObject1;
                }
              }
            }
          }
        }
      }
      i1 += 1;
      localObject1 = localObject2;
    }
  }
  
  private void setChildrenConstraints()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 423	android/view/View:isInEditMode	()Z
    //   4: istore 12
    //   6: aload_0
    //   7: invokevirtual 253	android/view/ViewGroup:getChildCount	()I
    //   10: istore 11
    //   12: iload 12
    //   14: ifeq +95 -> 109
    //   17: iconst_0
    //   18: istore_2
    //   19: iload_2
    //   20: iload 11
    //   22: if_icmpge +87 -> 109
    //   25: aload_0
    //   26: iload_2
    //   27: invokevirtual 256	android/view/ViewGroup:getChildAt	(I)Landroid/view/View;
    //   30: astore 15
    //   32: aload_0
    //   33: invokevirtual 427	android/view/View:getResources	()Landroid/content/res/Resources;
    //   36: aload 15
    //   38: invokevirtual 166	android/view/View:getId	()I
    //   41: invokevirtual 433	android/content/res/Resources:getResourceName	(I)Ljava/lang/String;
    //   44: astore 14
    //   46: aload 14
    //   48: astore 13
    //   50: aload_0
    //   51: iconst_0
    //   52: aload 14
    //   54: aload 15
    //   56: invokevirtual 166	android/view/View:getId	()I
    //   59: invokestatic 439	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   62: invokevirtual 443	androidx/constraintlayout/widget/ConstraintLayout:setDesignInformation	(ILjava/lang/Object;Ljava/lang/Object;)V
    //   65: aload 14
    //   67: bipush 47
    //   69: invokevirtual 448	java/lang/String:indexOf	(I)I
    //   72: istore_3
    //   73: iload_3
    //   74: iconst_m1
    //   75: if_icmpeq +13 -> 88
    //   78: aload 14
    //   80: iload_3
    //   81: iconst_1
    //   82: iadd
    //   83: invokevirtual 451	java/lang/String:substring	(I)Ljava/lang/String;
    //   86: astore 13
    //   88: aload_0
    //   89: aload 15
    //   91: invokevirtual 166	android/view/View:getId	()I
    //   94: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   97: aload 13
    //   99: invokevirtual 457	androidx/constraintlayout/solver/widgets/ConstraintWidget:setDebugName	(Ljava/lang/String;)V
    //   102: iload_2
    //   103: iconst_1
    //   104: iadd
    //   105: istore_2
    //   106: goto -87 -> 19
    //   109: iconst_0
    //   110: istore_2
    //   111: iload_2
    //   112: iload 11
    //   114: if_icmpge +34 -> 148
    //   117: aload_0
    //   118: aload_0
    //   119: iload_2
    //   120: invokevirtual 256	android/view/ViewGroup:getChildAt	(I)Landroid/view/View;
    //   123: invokevirtual 461	androidx/constraintlayout/widget/ConstraintLayout:getViewWidget	(Landroid/view/View;)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   126: astore 13
    //   128: aload 13
    //   130: ifnonnull +6 -> 136
    //   133: goto +8 -> 141
    //   136: aload 13
    //   138: invokevirtual 464	androidx/constraintlayout/solver/widgets/ConstraintWidget:reset	()V
    //   141: iload_2
    //   142: iconst_1
    //   143: iadd
    //   144: istore_2
    //   145: goto -34 -> 111
    //   148: aload_0
    //   149: getfield 98	androidx/constraintlayout/widget/ConstraintLayout:mConstraintSetId	I
    //   152: iconst_m1
    //   153: if_icmpeq +57 -> 210
    //   156: iconst_0
    //   157: istore_2
    //   158: iload_2
    //   159: iload 11
    //   161: if_icmpge +49 -> 210
    //   164: aload_0
    //   165: iload_2
    //   166: invokevirtual 256	android/view/ViewGroup:getChildAt	(I)Landroid/view/View;
    //   169: astore 13
    //   171: aload 13
    //   173: invokevirtual 166	android/view/View:getId	()I
    //   176: aload_0
    //   177: getfield 98	androidx/constraintlayout/widget/ConstraintLayout:mConstraintSetId	I
    //   180: if_icmpne +23 -> 203
    //   183: aload 13
    //   185: instanceof 466
    //   188: ifeq +15 -> 203
    //   191: aload_0
    //   192: aload 13
    //   194: checkcast 466	androidx/constraintlayout/widget/Constraints
    //   197: invokevirtual 470	androidx/constraintlayout/widget/Constraints:getConstraintSet	()Landroidx/constraintlayout/widget/ConstraintSet;
    //   200: putfield 96	androidx/constraintlayout/widget/ConstraintLayout:mConstraintSet	Landroidx/constraintlayout/widget/ConstraintSet;
    //   203: iload_2
    //   204: iconst_1
    //   205: iadd
    //   206: istore_2
    //   207: goto -49 -> 158
    //   210: aload_0
    //   211: getfield 96	androidx/constraintlayout/widget/ConstraintLayout:mConstraintSet	Landroidx/constraintlayout/widget/ConstraintSet;
    //   214: astore 13
    //   216: aload 13
    //   218: ifnull +9 -> 227
    //   221: aload 13
    //   223: aload_0
    //   224: invokevirtual 474	androidx/constraintlayout/widget/ConstraintSet:applyToInternal	(Landroidx/constraintlayout/widget/ConstraintLayout;)V
    //   227: aload_0
    //   228: getfield 81	androidx/constraintlayout/widget/ConstraintLayout:mLayoutWidget	Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   231: invokevirtual 479	androidx/constraintlayout/solver/widgets/WidgetContainer:removeAllChildren	()V
    //   234: aload_0
    //   235: getfield 74	androidx/constraintlayout/widget/ConstraintLayout:mConstraintHelpers	Ljava/util/ArrayList;
    //   238: invokevirtual 482	java/util/ArrayList:size	()I
    //   241: istore_3
    //   242: iload_3
    //   243: ifle +32 -> 275
    //   246: iconst_0
    //   247: istore_2
    //   248: iload_2
    //   249: iload_3
    //   250: if_icmpge +25 -> 275
    //   253: aload_0
    //   254: getfield 74	androidx/constraintlayout/widget/ConstraintLayout:mConstraintHelpers	Ljava/util/ArrayList;
    //   257: iload_2
    //   258: invokevirtual 483	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   261: checkcast 485	androidx/constraintlayout/widget/ConstraintHelper
    //   264: aload_0
    //   265: invokevirtual 488	androidx/constraintlayout/widget/ConstraintHelper:updatePreLayout	(Landroidx/constraintlayout/widget/ConstraintLayout;)V
    //   268: iload_2
    //   269: iconst_1
    //   270: iadd
    //   271: istore_2
    //   272: goto -24 -> 248
    //   275: iconst_0
    //   276: istore_2
    //   277: iload_2
    //   278: iload 11
    //   280: if_icmpge +34 -> 314
    //   283: aload_0
    //   284: iload_2
    //   285: invokevirtual 256	android/view/ViewGroup:getChildAt	(I)Landroid/view/View;
    //   288: astore 13
    //   290: aload 13
    //   292: instanceof 490
    //   295: ifeq +12 -> 307
    //   298: aload 13
    //   300: checkcast 490	androidx/constraintlayout/widget/Placeholder
    //   303: aload_0
    //   304: invokevirtual 491	androidx/constraintlayout/widget/Placeholder:updatePreLayout	(Landroidx/constraintlayout/widget/ConstraintLayout;)V
    //   307: iload_2
    //   308: iconst_1
    //   309: iadd
    //   310: istore_2
    //   311: goto -34 -> 277
    //   314: iconst_0
    //   315: istore 6
    //   317: iload 6
    //   319: iload 11
    //   321: if_icmpge +1645 -> 1966
    //   324: aload_0
    //   325: iload 6
    //   327: invokevirtual 256	android/view/ViewGroup:getChildAt	(I)Landroid/view/View;
    //   330: astore 15
    //   332: aload_0
    //   333: aload 15
    //   335: invokevirtual 461	androidx/constraintlayout/widget/ConstraintLayout:getViewWidget	(Landroid/view/View;)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   338: astore 14
    //   340: aload 14
    //   342: ifnonnull +6 -> 348
    //   345: goto +1612 -> 1957
    //   348: aload 15
    //   350: invokevirtual 150	android/view/View:getLayoutParams	()Landroid/view/ViewGroup$LayoutParams;
    //   353: checkcast 6	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams
    //   356: astore 13
    //   358: aload 13
    //   360: invokevirtual 494	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:validate	()V
    //   363: aload 13
    //   365: getfield 497	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:helped	Z
    //   368: ifeq +12 -> 380
    //   371: aload 13
    //   373: iconst_0
    //   374: putfield 497	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:helped	Z
    //   377: goto +73 -> 450
    //   380: iload 12
    //   382: ifeq +68 -> 450
    //   385: aload_0
    //   386: invokevirtual 427	android/view/View:getResources	()Landroid/content/res/Resources;
    //   389: aload 15
    //   391: invokevirtual 166	android/view/View:getId	()I
    //   394: invokevirtual 433	android/content/res/Resources:getResourceName	(I)Ljava/lang/String;
    //   397: astore 16
    //   399: aload_0
    //   400: iconst_0
    //   401: aload 16
    //   403: aload 15
    //   405: invokevirtual 166	android/view/View:getId	()I
    //   408: invokestatic 439	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   411: invokevirtual 443	androidx/constraintlayout/widget/ConstraintLayout:setDesignInformation	(ILjava/lang/Object;Ljava/lang/Object;)V
    //   414: aload 16
    //   416: ldc_w 499
    //   419: invokevirtual 502	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   422: istore_2
    //   423: aload 16
    //   425: iload_2
    //   426: iconst_3
    //   427: iadd
    //   428: invokevirtual 451	java/lang/String:substring	(I)Ljava/lang/String;
    //   431: astore 16
    //   433: aload_0
    //   434: aload 15
    //   436: invokevirtual 166	android/view/View:getId	()I
    //   439: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   442: aload 16
    //   444: invokevirtual 457	androidx/constraintlayout/solver/widgets/ConstraintWidget:setDebugName	(Ljava/lang/String;)V
    //   447: goto +3 -> 450
    //   450: aload 14
    //   452: aload 15
    //   454: invokevirtual 259	android/view/View:getVisibility	()I
    //   457: invokevirtual 268	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVisibility	(I)V
    //   460: aload 13
    //   462: getfield 505	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:isInPlaceholder	Z
    //   465: ifeq +10 -> 475
    //   468: aload 14
    //   470: bipush 8
    //   472: invokevirtual 268	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVisibility	(I)V
    //   475: aload 14
    //   477: aload 15
    //   479: invokevirtual 162	androidx/constraintlayout/solver/widgets/ConstraintWidget:setCompanionWidget	(Ljava/lang/Object;)V
    //   482: aload_0
    //   483: getfield 81	androidx/constraintlayout/widget/ConstraintLayout:mLayoutWidget	Landroidx/constraintlayout/solver/widgets/ConstraintWidgetContainer;
    //   486: aload 14
    //   488: invokevirtual 509	androidx/constraintlayout/solver/widgets/WidgetContainer:close	(Landroidx/constraintlayout/solver/widgets/ConstraintWidget;)V
    //   491: aload 13
    //   493: getfield 282	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:verticalDimensionFixed	Z
    //   496: ifeq +11 -> 507
    //   499: aload 13
    //   501: getfield 279	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:horizontalDimensionFixed	Z
    //   504: ifne +13 -> 517
    //   507: aload_0
    //   508: getfield 76	androidx/constraintlayout/widget/ConstraintLayout:mVariableDimensionsWidgets	Ljava/util/ArrayList;
    //   511: aload 14
    //   513: invokevirtual 513	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   516: pop
    //   517: aload 13
    //   519: getfield 262	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:isGuideline	Z
    //   522: ifeq +99 -> 621
    //   525: aload 14
    //   527: checkcast 515	androidx/constraintlayout/solver/widgets/Guideline
    //   530: astore 14
    //   532: aload 13
    //   534: getfield 518	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolvedGuideBegin	I
    //   537: istore_2
    //   538: aload 13
    //   540: getfield 521	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolvedGuideEnd	I
    //   543: istore_3
    //   544: aload 13
    //   546: getfield 525	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolvedGuidePercent	F
    //   549: fstore_1
    //   550: getstatic 530	android/os/Build$VERSION:SDK_INT	I
    //   553: bipush 17
    //   555: if_icmpge +21 -> 576
    //   558: aload 13
    //   560: getfield 533	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:guideBegin	I
    //   563: istore_2
    //   564: aload 13
    //   566: getfield 536	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:guideEnd	I
    //   569: istore_3
    //   570: aload 13
    //   572: getfield 539	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:guidePercent	F
    //   575: fstore_1
    //   576: fload_1
    //   577: ldc_w 540
    //   580: fcmpl
    //   581: ifeq +12 -> 593
    //   584: aload 14
    //   586: fload_1
    //   587: invokevirtual 544	androidx/constraintlayout/solver/widgets/Guideline:setGuidePercent	(F)V
    //   590: goto +1367 -> 1957
    //   593: iload_2
    //   594: iconst_m1
    //   595: if_icmpeq +12 -> 607
    //   598: aload 14
    //   600: iload_2
    //   601: invokevirtual 547	androidx/constraintlayout/solver/widgets/Guideline:setGuideBegin	(I)V
    //   604: goto +1353 -> 1957
    //   607: iload_3
    //   608: iconst_m1
    //   609: if_icmpeq +1348 -> 1957
    //   612: aload 14
    //   614: iload_3
    //   615: invokevirtual 550	androidx/constraintlayout/solver/widgets/Guideline:setGuideEnd	(I)V
    //   618: goto +1339 -> 1957
    //   621: aload 13
    //   623: getfield 553	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:leftToLeft	I
    //   626: iconst_m1
    //   627: if_icmpne +156 -> 783
    //   630: aload 13
    //   632: getfield 556	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:leftToRight	I
    //   635: iconst_m1
    //   636: if_icmpne +147 -> 783
    //   639: aload 13
    //   641: getfield 559	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:rightToLeft	I
    //   644: iconst_m1
    //   645: if_icmpne +138 -> 783
    //   648: aload 13
    //   650: getfield 562	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:rightToRight	I
    //   653: iconst_m1
    //   654: if_icmpne +129 -> 783
    //   657: aload 13
    //   659: getfield 565	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:startToStart	I
    //   662: iconst_m1
    //   663: if_icmpne +120 -> 783
    //   666: aload 13
    //   668: getfield 568	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:startToEnd	I
    //   671: iconst_m1
    //   672: if_icmpne +111 -> 783
    //   675: aload 13
    //   677: getfield 571	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:endToStart	I
    //   680: iconst_m1
    //   681: if_icmpne +102 -> 783
    //   684: aload 13
    //   686: getfield 574	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:endToEnd	I
    //   689: iconst_m1
    //   690: if_icmpne +93 -> 783
    //   693: aload 13
    //   695: getfield 577	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:topToTop	I
    //   698: iconst_m1
    //   699: if_icmpne +84 -> 783
    //   702: aload 13
    //   704: getfield 580	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:topToBottom	I
    //   707: iconst_m1
    //   708: if_icmpne +75 -> 783
    //   711: aload 13
    //   713: getfield 583	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:bottomToTop	I
    //   716: iconst_m1
    //   717: if_icmpne +66 -> 783
    //   720: aload 13
    //   722: getfield 586	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:bottomToBottom	I
    //   725: iconst_m1
    //   726: if_icmpne +57 -> 783
    //   729: aload 13
    //   731: getfield 589	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:baselineToBaseline	I
    //   734: iconst_m1
    //   735: if_icmpne +48 -> 783
    //   738: aload 13
    //   740: getfield 592	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:editorAbsoluteX	I
    //   743: iconst_m1
    //   744: if_icmpne +39 -> 783
    //   747: aload 13
    //   749: getfield 595	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:editorAbsoluteY	I
    //   752: iconst_m1
    //   753: if_icmpne +30 -> 783
    //   756: aload 13
    //   758: getfield 598	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:circleConstraint	I
    //   761: iconst_m1
    //   762: if_icmpne +21 -> 783
    //   765: aload 13
    //   767: getfield 273	android/view/ViewGroup$LayoutParams:width	I
    //   770: iconst_m1
    //   771: if_icmpeq +12 -> 783
    //   774: aload 13
    //   776: getfield 276	android/view/ViewGroup$LayoutParams:height	I
    //   779: iconst_m1
    //   780: if_icmpne +1177 -> 1957
    //   783: aload 13
    //   785: getfield 601	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolvedLeftToLeft	I
    //   788: istore_3
    //   789: aload 13
    //   791: getfield 604	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolvedRightToLeft	I
    //   794: istore 4
    //   796: aload 13
    //   798: getfield 607	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolvedRightToRight	I
    //   801: istore 5
    //   803: aload 13
    //   805: getfield 610	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolvedHorizontalBias	F
    //   808: fstore_1
    //   809: getstatic 530	android/os/Build$VERSION:SDK_INT	I
    //   812: bipush 17
    //   814: if_icmpge +202 -> 1016
    //   817: aload 13
    //   819: getfield 553	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:leftToLeft	I
    //   822: istore 4
    //   824: aload 13
    //   826: getfield 556	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:leftToRight	I
    //   829: istore 5
    //   831: aload 13
    //   833: getfield 559	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:rightToLeft	I
    //   836: istore 9
    //   838: aload 13
    //   840: getfield 562	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:rightToRight	I
    //   843: istore 10
    //   845: aload 13
    //   847: getfield 613	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:goneLeftMargin	I
    //   850: istore 7
    //   852: aload 13
    //   854: getfield 616	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:goneRightMargin	I
    //   857: istore 8
    //   859: aload 13
    //   861: getfield 619	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:horizontalBias	F
    //   864: fstore_1
    //   865: iload 4
    //   867: istore_3
    //   868: iload 5
    //   870: istore_2
    //   871: iload 4
    //   873: iconst_m1
    //   874: if_icmpne +60 -> 934
    //   877: iload 4
    //   879: istore_3
    //   880: iload 5
    //   882: istore_2
    //   883: iload 5
    //   885: iconst_m1
    //   886: if_icmpne +48 -> 934
    //   889: aload 13
    //   891: getfield 565	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:startToStart	I
    //   894: iconst_m1
    //   895: if_icmpeq +15 -> 910
    //   898: aload 13
    //   900: getfield 565	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:startToStart	I
    //   903: istore_3
    //   904: iload 5
    //   906: istore_2
    //   907: goto +27 -> 934
    //   910: iload 4
    //   912: istore_3
    //   913: iload 5
    //   915: istore_2
    //   916: aload 13
    //   918: getfield 568	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:startToEnd	I
    //   921: iconst_m1
    //   922: if_icmpeq +12 -> 934
    //   925: aload 13
    //   927: getfield 568	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:startToEnd	I
    //   930: istore_2
    //   931: iload 4
    //   933: istore_3
    //   934: iload 10
    //   936: istore 5
    //   938: iload 9
    //   940: istore 4
    //   942: iload 9
    //   944: iconst_m1
    //   945: if_icmpne +68 -> 1013
    //   948: iload 10
    //   950: istore 5
    //   952: iload 9
    //   954: istore 4
    //   956: iload 10
    //   958: iconst_m1
    //   959: if_icmpne +54 -> 1013
    //   962: aload 13
    //   964: getfield 571	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:endToStart	I
    //   967: iconst_m1
    //   968: if_icmpeq +17 -> 985
    //   971: aload 13
    //   973: getfield 571	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:endToStart	I
    //   976: istore 4
    //   978: iload 10
    //   980: istore 5
    //   982: goto +31 -> 1013
    //   985: iload 10
    //   987: istore 5
    //   989: iload 9
    //   991: istore 4
    //   993: aload 13
    //   995: getfield 574	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:endToEnd	I
    //   998: iconst_m1
    //   999: if_icmpeq +14 -> 1013
    //   1002: aload 13
    //   1004: getfield 574	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:endToEnd	I
    //   1007: istore 5
    //   1009: iload 9
    //   1011: istore 4
    //   1013: goto +23 -> 1036
    //   1016: aload 13
    //   1018: getfield 622	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolvedLeftToRight	I
    //   1021: istore_2
    //   1022: aload 13
    //   1024: getfield 625	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolveGoneRightMargin	I
    //   1027: istore 8
    //   1029: aload 13
    //   1031: getfield 628	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:resolveGoneLeftMargin	I
    //   1034: istore 7
    //   1036: aload 13
    //   1038: getfield 598	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:circleConstraint	I
    //   1041: iconst_m1
    //   1042: if_icmpeq +39 -> 1081
    //   1045: aload_0
    //   1046: aload 13
    //   1048: getfield 598	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:circleConstraint	I
    //   1051: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1054: astore 15
    //   1056: aload 15
    //   1058: ifnull +563 -> 1621
    //   1061: aload 14
    //   1063: aload 15
    //   1065: aload 13
    //   1067: getfield 631	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:circleAngle	F
    //   1070: aload 13
    //   1072: getfield 634	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:circleRadius	I
    //   1075: invokevirtual 638	androidx/constraintlayout/solver/widgets/ConstraintWidget:connectCircularConstraint	(Landroidx/constraintlayout/solver/widgets/ConstraintWidget;FI)V
    //   1078: goto +543 -> 1621
    //   1081: iload_3
    //   1082: iconst_m1
    //   1083: if_icmpeq +41 -> 1124
    //   1086: aload_0
    //   1087: iload_3
    //   1088: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1091: astore 15
    //   1093: aload 15
    //   1095: ifnull +26 -> 1121
    //   1098: aload 14
    //   1100: getstatic 364	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:LEFT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1103: aload 15
    //   1105: getstatic 364	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:LEFT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1108: aload 13
    //   1110: getfield 643	android/view/ViewGroup$MarginLayoutParams:leftMargin	I
    //   1113: iload 7
    //   1115: invokevirtual 647	androidx/constraintlayout/solver/widgets/ConstraintWidget:immediateConnect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;II)V
    //   1118: goto +3 -> 1121
    //   1121: goto +40 -> 1161
    //   1124: iload_2
    //   1125: iconst_m1
    //   1126: if_icmpeq +35 -> 1161
    //   1129: aload_0
    //   1130: iload_2
    //   1131: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1134: astore 15
    //   1136: aload 15
    //   1138: ifnull +23 -> 1161
    //   1141: aload 14
    //   1143: getstatic 364	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:LEFT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1146: aload 15
    //   1148: getstatic 377	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:RIGHT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1151: aload 13
    //   1153: getfield 643	android/view/ViewGroup$MarginLayoutParams:leftMargin	I
    //   1156: iload 7
    //   1158: invokevirtual 647	androidx/constraintlayout/solver/widgets/ConstraintWidget:immediateConnect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;II)V
    //   1161: iload 4
    //   1163: iconst_m1
    //   1164: if_icmpeq +39 -> 1203
    //   1167: aload_0
    //   1168: iload 4
    //   1170: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1173: astore 15
    //   1175: aload 15
    //   1177: ifnull +65 -> 1242
    //   1180: aload 14
    //   1182: getstatic 377	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:RIGHT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1185: aload 15
    //   1187: getstatic 364	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:LEFT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1190: aload 13
    //   1192: getfield 650	android/view/ViewGroup$MarginLayoutParams:rightMargin	I
    //   1195: iload 8
    //   1197: invokevirtual 647	androidx/constraintlayout/solver/widgets/ConstraintWidget:immediateConnect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;II)V
    //   1200: goto +42 -> 1242
    //   1203: iload 5
    //   1205: iconst_m1
    //   1206: if_icmpeq +36 -> 1242
    //   1209: aload_0
    //   1210: iload 5
    //   1212: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1215: astore 15
    //   1217: aload 15
    //   1219: ifnull +23 -> 1242
    //   1222: aload 14
    //   1224: getstatic 377	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:RIGHT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1227: aload 15
    //   1229: getstatic 377	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:RIGHT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1232: aload 13
    //   1234: getfield 650	android/view/ViewGroup$MarginLayoutParams:rightMargin	I
    //   1237: iload 8
    //   1239: invokevirtual 647	androidx/constraintlayout/solver/widgets/ConstraintWidget:immediateConnect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;II)V
    //   1242: aload 13
    //   1244: getfield 577	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:topToTop	I
    //   1247: iconst_m1
    //   1248: if_icmpeq +45 -> 1293
    //   1251: aload_0
    //   1252: aload 13
    //   1254: getfield 577	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:topToTop	I
    //   1257: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1260: astore 15
    //   1262: aload 15
    //   1264: ifnull +77 -> 1341
    //   1267: aload 14
    //   1269: getstatic 384	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:MIDDLE	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1272: aload 15
    //   1274: getstatic 384	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:MIDDLE	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1277: aload 13
    //   1279: getfield 653	android/view/ViewGroup$MarginLayoutParams:topMargin	I
    //   1282: aload 13
    //   1284: getfield 656	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:goneTopMargin	I
    //   1287: invokevirtual 647	androidx/constraintlayout/solver/widgets/ConstraintWidget:immediateConnect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;II)V
    //   1290: goto +51 -> 1341
    //   1293: aload 13
    //   1295: getfield 580	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:topToBottom	I
    //   1298: iconst_m1
    //   1299: if_icmpeq +42 -> 1341
    //   1302: aload_0
    //   1303: aload 13
    //   1305: getfield 580	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:topToBottom	I
    //   1308: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1311: astore 15
    //   1313: aload 15
    //   1315: ifnull +26 -> 1341
    //   1318: aload 14
    //   1320: getstatic 384	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:MIDDLE	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1323: aload 15
    //   1325: getstatic 387	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:BOTTOM	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1328: aload 13
    //   1330: getfield 653	android/view/ViewGroup$MarginLayoutParams:topMargin	I
    //   1333: aload 13
    //   1335: getfield 656	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:goneTopMargin	I
    //   1338: invokevirtual 647	androidx/constraintlayout/solver/widgets/ConstraintWidget:immediateConnect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;II)V
    //   1341: aload 13
    //   1343: getfield 583	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:bottomToTop	I
    //   1346: iconst_m1
    //   1347: if_icmpeq +45 -> 1392
    //   1350: aload_0
    //   1351: aload 13
    //   1353: getfield 583	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:bottomToTop	I
    //   1356: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1359: astore 15
    //   1361: aload 15
    //   1363: ifnull +77 -> 1440
    //   1366: aload 14
    //   1368: getstatic 387	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:BOTTOM	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1371: aload 15
    //   1373: getstatic 384	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:MIDDLE	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1376: aload 13
    //   1378: getfield 659	android/view/ViewGroup$MarginLayoutParams:bottomMargin	I
    //   1381: aload 13
    //   1383: getfield 662	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:goneBottomMargin	I
    //   1386: invokevirtual 647	androidx/constraintlayout/solver/widgets/ConstraintWidget:immediateConnect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;II)V
    //   1389: goto +51 -> 1440
    //   1392: aload 13
    //   1394: getfield 586	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:bottomToBottom	I
    //   1397: iconst_m1
    //   1398: if_icmpeq +42 -> 1440
    //   1401: aload_0
    //   1402: aload 13
    //   1404: getfield 586	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:bottomToBottom	I
    //   1407: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1410: astore 15
    //   1412: aload 15
    //   1414: ifnull +26 -> 1440
    //   1417: aload 14
    //   1419: getstatic 387	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:BOTTOM	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1422: aload 15
    //   1424: getstatic 387	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:BOTTOM	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1427: aload 13
    //   1429: getfield 659	android/view/ViewGroup$MarginLayoutParams:bottomMargin	I
    //   1432: aload 13
    //   1434: getfield 662	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:goneBottomMargin	I
    //   1437: invokevirtual 647	androidx/constraintlayout/solver/widgets/ConstraintWidget:immediateConnect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;Landroidx/constraintlayout/solver/widgets/ConstraintWidget;Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;II)V
    //   1440: aload 13
    //   1442: getfield 589	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:baselineToBaseline	I
    //   1445: iconst_m1
    //   1446: if_icmpeq +123 -> 1569
    //   1449: aload_0
    //   1450: getfield 67	androidx/constraintlayout/widget/ConstraintLayout:mChildrenByIds	Landroid/util/SparseArray;
    //   1453: aload 13
    //   1455: getfield 589	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:baselineToBaseline	I
    //   1458: invokevirtual 132	android/util/SparseArray:get	(I)Ljava/lang/Object;
    //   1461: checkcast 134	android/view/View
    //   1464: astore 16
    //   1466: aload_0
    //   1467: aload 13
    //   1469: getfield 589	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:baselineToBaseline	I
    //   1472: invokespecial 453	androidx/constraintlayout/widget/ConstraintLayout:getTargetWidget	(I)Landroidx/constraintlayout/solver/widgets/ConstraintWidget;
    //   1475: astore 15
    //   1477: aload 15
    //   1479: ifnull +90 -> 1569
    //   1482: aload 16
    //   1484: ifnull +85 -> 1569
    //   1487: aload 16
    //   1489: invokevirtual 150	android/view/View:getLayoutParams	()Landroid/view/ViewGroup$LayoutParams;
    //   1492: instanceof 6
    //   1495: ifeq +74 -> 1569
    //   1498: aload 16
    //   1500: invokevirtual 150	android/view/View:getLayoutParams	()Landroid/view/ViewGroup$LayoutParams;
    //   1503: checkcast 6	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams
    //   1506: astore 16
    //   1508: aload 13
    //   1510: iconst_1
    //   1511: putfield 331	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:needsBaseline	Z
    //   1514: aload 16
    //   1516: iconst_1
    //   1517: putfield 331	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:needsBaseline	Z
    //   1520: aload 14
    //   1522: getstatic 665	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:BASELINE	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1525: invokevirtual 368	androidx/constraintlayout/solver/widgets/ConstraintWidget:getAnchor	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1528: aload 15
    //   1530: getstatic 665	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:BASELINE	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1533: invokevirtual 368	androidx/constraintlayout/solver/widgets/ConstraintWidget:getAnchor	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1536: iconst_0
    //   1537: iconst_m1
    //   1538: getstatic 671	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Strength:STRONG	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Strength;
    //   1541: iconst_0
    //   1542: iconst_1
    //   1543: invokevirtual 675	androidx/constraintlayout/solver/widgets/ConstraintAnchor:connect	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;IILandroidx/constraintlayout/solver/widgets/ConstraintAnchor$Strength;IZ)Z
    //   1546: pop
    //   1547: aload 14
    //   1549: getstatic 384	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:MIDDLE	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1552: invokevirtual 368	androidx/constraintlayout/solver/widgets/ConstraintWidget:getAnchor	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1555: invokevirtual 676	androidx/constraintlayout/solver/widgets/ConstraintAnchor:reset	()V
    //   1558: aload 14
    //   1560: getstatic 387	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:BOTTOM	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1563: invokevirtual 368	androidx/constraintlayout/solver/widgets/ConstraintWidget:getAnchor	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1566: invokevirtual 676	androidx/constraintlayout/solver/widgets/ConstraintAnchor:reset	()V
    //   1569: fload_1
    //   1570: fconst_0
    //   1571: fcmpl
    //   1572: iflt +17 -> 1589
    //   1575: fload_1
    //   1576: ldc_w 677
    //   1579: fcmpl
    //   1580: ifeq +9 -> 1589
    //   1583: aload 14
    //   1585: fload_1
    //   1586: invokevirtual 680	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHorizontalBiasPercent	(F)V
    //   1589: aload 13
    //   1591: getfield 683	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:verticalBias	F
    //   1594: fconst_0
    //   1595: fcmpl
    //   1596: iflt +25 -> 1621
    //   1599: aload 13
    //   1601: getfield 683	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:verticalBias	F
    //   1604: ldc_w 677
    //   1607: fcmpl
    //   1608: ifeq +13 -> 1621
    //   1611: aload 14
    //   1613: aload 13
    //   1615: getfield 683	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:verticalBias	F
    //   1618: invokevirtual 686	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVerticalBiasPercent	(F)V
    //   1621: iload 12
    //   1623: ifeq +36 -> 1659
    //   1626: aload 13
    //   1628: getfield 592	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:editorAbsoluteX	I
    //   1631: iconst_m1
    //   1632: if_icmpne +12 -> 1644
    //   1635: aload 13
    //   1637: getfield 595	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:editorAbsoluteY	I
    //   1640: iconst_m1
    //   1641: if_icmpeq +18 -> 1659
    //   1644: aload 14
    //   1646: aload 13
    //   1648: getfield 592	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:editorAbsoluteX	I
    //   1651: aload 13
    //   1653: getfield 595	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:editorAbsoluteY	I
    //   1656: invokevirtual 689	androidx/constraintlayout/solver/widgets/ConstraintWidget:setOrigin	(II)V
    //   1659: aload 13
    //   1661: getfield 279	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:horizontalDimensionFixed	Z
    //   1664: ifne +72 -> 1736
    //   1667: aload 13
    //   1669: getfield 273	android/view/ViewGroup$LayoutParams:width	I
    //   1672: iconst_m1
    //   1673: if_icmpne +46 -> 1719
    //   1676: aload 14
    //   1678: getstatic 692	androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour:MATCH_PARENT	Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1681: invokevirtual 696	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHorizontalDimensionBehaviour	(Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   1684: aload 14
    //   1686: getstatic 364	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:LEFT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1689: invokevirtual 368	androidx/constraintlayout/solver/widgets/ConstraintWidget:getAnchor	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1692: aload 13
    //   1694: getfield 643	android/view/ViewGroup$MarginLayoutParams:leftMargin	I
    //   1697: putfield 699	androidx/constraintlayout/solver/widgets/ConstraintAnchor:mMargin	I
    //   1700: aload 14
    //   1702: getstatic 377	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:RIGHT	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1705: invokevirtual 368	androidx/constraintlayout/solver/widgets/ConstraintWidget:getAnchor	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1708: aload 13
    //   1710: getfield 650	android/view/ViewGroup$MarginLayoutParams:rightMargin	I
    //   1713: putfield 699	androidx/constraintlayout/solver/widgets/ConstraintAnchor:mMargin	I
    //   1716: goto +38 -> 1754
    //   1719: aload 14
    //   1721: getstatic 702	androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour:MATCH_CONSTRAINT	Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1724: invokevirtual 696	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHorizontalDimensionBehaviour	(Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   1727: aload 14
    //   1729: iconst_0
    //   1730: invokevirtual 319	androidx/constraintlayout/solver/widgets/ConstraintWidget:setWidth	(I)V
    //   1733: goto +21 -> 1754
    //   1736: aload 14
    //   1738: getstatic 705	androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour:FIXED	Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1741: invokevirtual 696	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHorizontalDimensionBehaviour	(Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   1744: aload 14
    //   1746: aload 13
    //   1748: getfield 273	android/view/ViewGroup$LayoutParams:width	I
    //   1751: invokevirtual 319	androidx/constraintlayout/solver/widgets/ConstraintWidget:setWidth	(I)V
    //   1754: aload 13
    //   1756: getfield 282	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:verticalDimensionFixed	Z
    //   1759: ifne +72 -> 1831
    //   1762: aload 13
    //   1764: getfield 276	android/view/ViewGroup$LayoutParams:height	I
    //   1767: iconst_m1
    //   1768: if_icmpne +46 -> 1814
    //   1771: aload 14
    //   1773: getstatic 692	androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour:MATCH_PARENT	Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1776: invokevirtual 708	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVerticalDimensionBehaviour	(Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   1779: aload 14
    //   1781: getstatic 384	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:MIDDLE	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1784: invokevirtual 368	androidx/constraintlayout/solver/widgets/ConstraintWidget:getAnchor	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1787: aload 13
    //   1789: getfield 653	android/view/ViewGroup$MarginLayoutParams:topMargin	I
    //   1792: putfield 699	androidx/constraintlayout/solver/widgets/ConstraintAnchor:mMargin	I
    //   1795: aload 14
    //   1797: getstatic 387	androidx/constraintlayout/solver/widgets/ConstraintAnchor$Type:BOTTOM	Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;
    //   1800: invokevirtual 368	androidx/constraintlayout/solver/widgets/ConstraintWidget:getAnchor	(Landroidx/constraintlayout/solver/widgets/ConstraintAnchor$Type;)Landroidx/constraintlayout/solver/widgets/ConstraintAnchor;
    //   1803: aload 13
    //   1805: getfield 659	android/view/ViewGroup$MarginLayoutParams:bottomMargin	I
    //   1808: putfield 699	androidx/constraintlayout/solver/widgets/ConstraintAnchor:mMargin	I
    //   1811: goto +38 -> 1849
    //   1814: aload 14
    //   1816: getstatic 702	androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour:MATCH_CONSTRAINT	Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1819: invokevirtual 708	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVerticalDimensionBehaviour	(Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   1822: aload 14
    //   1824: iconst_0
    //   1825: invokevirtual 322	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHeight	(I)V
    //   1828: goto +21 -> 1849
    //   1831: aload 14
    //   1833: getstatic 705	androidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour:FIXED	Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;
    //   1836: invokevirtual 708	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVerticalDimensionBehaviour	(Landroidx/constraintlayout/solver/widgets/ConstraintWidget$DimensionBehaviour;)V
    //   1839: aload 14
    //   1841: aload 13
    //   1843: getfield 276	android/view/ViewGroup$LayoutParams:height	I
    //   1846: invokevirtual 322	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHeight	(I)V
    //   1849: aload 13
    //   1851: getfield 711	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:dimensionRatio	Ljava/lang/String;
    //   1854: ifnull +13 -> 1867
    //   1857: aload 14
    //   1859: aload 13
    //   1861: getfield 711	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:dimensionRatio	Ljava/lang/String;
    //   1864: invokevirtual 714	androidx/constraintlayout/solver/widgets/ConstraintWidget:setDimensionRatio	(Ljava/lang/String;)V
    //   1867: aload 14
    //   1869: aload 13
    //   1871: getfield 717	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:horizontalWeight	F
    //   1874: invokevirtual 720	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHorizontalWeight	(F)V
    //   1877: aload 14
    //   1879: aload 13
    //   1881: getfield 723	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:verticalWeight	F
    //   1884: invokevirtual 726	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVerticalWeight	(F)V
    //   1887: aload 14
    //   1889: aload 13
    //   1891: getfield 729	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:horizontalChainStyle	I
    //   1894: invokevirtual 732	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHorizontalChainStyle	(I)V
    //   1897: aload 14
    //   1899: aload 13
    //   1901: getfield 735	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:verticalChainStyle	I
    //   1904: invokevirtual 738	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVerticalChainStyle	(I)V
    //   1907: aload 14
    //   1909: aload 13
    //   1911: getfield 285	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:matchConstraintDefaultWidth	I
    //   1914: aload 13
    //   1916: getfield 741	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:matchConstraintMinWidth	I
    //   1919: aload 13
    //   1921: getfield 744	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:matchConstraintMaxWidth	I
    //   1924: aload 13
    //   1926: getfield 747	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:matchConstraintPercentWidth	F
    //   1929: invokevirtual 751	androidx/constraintlayout/solver/widgets/ConstraintWidget:setHorizontalMatchStyle	(IIIF)V
    //   1932: aload 14
    //   1934: aload 13
    //   1936: getfield 288	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:matchConstraintDefaultHeight	I
    //   1939: aload 13
    //   1941: getfield 754	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:matchConstraintMinHeight	I
    //   1944: aload 13
    //   1946: getfield 757	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:matchConstraintMaxHeight	I
    //   1949: aload 13
    //   1951: getfield 760	androidx/constraintlayout/widget/ConstraintLayout$LayoutParams:matchConstraintPercentHeight	F
    //   1954: invokevirtual 763	androidx/constraintlayout/solver/widgets/ConstraintWidget:setVerticalMatchStyle	(IIIF)V
    //   1957: iload 6
    //   1959: iconst_1
    //   1960: iadd
    //   1961: istore 6
    //   1963: goto -1646 -> 317
    //   1966: return
    //   1967: astore 13
    //   1969: goto -1867 -> 102
    //   1972: astore 16
    //   1974: goto -1524 -> 450
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1977	0	this	ConstraintLayout
    //   549	1037	1	f	float
    //   18	1113	2	i	int
    //   72	1016	3	j	int
    //   794	375	4	k	int
    //   801	410	5	m	int
    //   315	1647	6	n	int
    //   850	307	7	i1	int
    //   857	381	8	i2	int
    //   836	174	9	i3	int
    //   843	143	10	i4	int
    //   10	312	11	i5	int
    //   4	1618	12	bool	boolean
    //   48	1902	13	localObject1	Object
    //   1967	1	13	localNotFoundException1	Resources.NotFoundException
    //   44	1889	14	localObject2	Object
    //   30	1499	15	localObject3	Object
    //   397	1118	16	localObject4	Object
    //   1972	1	16	localNotFoundException2	Resources.NotFoundException
    // Exception table:
    //   from	to	target	type
    //   32	46	1967	android/content/res/Resources$NotFoundException
    //   50	73	1967	android/content/res/Resources$NotFoundException
    //   78	88	1967	android/content/res/Resources$NotFoundException
    //   88	102	1967	android/content/res/Resources$NotFoundException
    //   385	423	1972	android/content/res/Resources$NotFoundException
    //   423	447	1972	android/content/res/Resources$NotFoundException
  }
  
  private void setSelfDimensionBehaviour(int paramInt1, int paramInt2)
  {
    int i1 = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    paramInt1 = j;
    int k = View.MeasureSpec.getMode(paramInt2);
    int i = View.MeasureSpec.getSize(paramInt2);
    paramInt2 = i;
    int m = getPaddingTop();
    int n = getPaddingBottom();
    int i2 = getPaddingLeft();
    int i3 = getPaddingRight();
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.FIXED;
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
    getLayoutParams();
    if (i1 != Integer.MIN_VALUE)
    {
      if (i1 != 0) {
        if (i1 == 1073741824) {}
      }
      for (;;)
      {
        paramInt1 = 0;
        break;
        paramInt1 = Math.min(mMaxWidth, j) - (i2 + i3);
        break;
        localDimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      }
    }
    localDimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
    if (k != Integer.MIN_VALUE)
    {
      if (k != 0) {
        if (k == 1073741824) {}
      }
      for (;;)
      {
        paramInt2 = 0;
        break;
        paramInt2 = Math.min(mMaxHeight, i) - (m + n);
        break;
        localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      }
    }
    localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
    mLayoutWidget.setMinWidth(0);
    mLayoutWidget.setMinHeight(0);
    mLayoutWidget.setHorizontalDimensionBehaviour(localDimensionBehaviour1);
    mLayoutWidget.setWidth(paramInt1);
    mLayoutWidget.setVerticalDimensionBehaviour(localDimensionBehaviour2);
    mLayoutWidget.setHeight(paramInt2);
    mLayoutWidget.setMinWidth(mMinWidth - getPaddingLeft() - getPaddingRight());
    mLayoutWidget.setMinHeight(mMinHeight - getPaddingTop() - getPaddingBottom());
  }
  
  private void updateHierarchy()
  {
    int m = getChildCount();
    int k = 0;
    int i = 0;
    int j;
    for (;;)
    {
      j = k;
      if (i >= m) {
        break;
      }
      if (getChildAt(i).isLayoutRequested())
      {
        j = 1;
        break;
      }
      i += 1;
    }
    if (j != 0)
    {
      mVariableDimensionsWidgets.clear();
      setChildrenConstraints();
    }
  }
  
  private void updatePostMeasures()
  {
    int k = getChildCount();
    int j = 0;
    int i = 0;
    while (i < k)
    {
      View localView = getChildAt(i);
      if ((localView instanceof Placeholder)) {
        ((Placeholder)localView).updatePostMeasure(this);
      }
      i += 1;
    }
    k = mConstraintHelpers.size();
    if (k > 0)
    {
      i = j;
      while (i < k)
      {
        ((ConstraintHelper)mConstraintHelpers.get(i)).updatePostMeasure(this);
        i += 1;
      }
    }
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.addView(paramView, paramInt, paramLayoutParams);
    if (Build.VERSION.SDK_INT < 14) {
      onViewAdded(paramView);
    }
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  public void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if (isInEditMode())
    {
      int j = getChildCount();
      float f1 = getWidth();
      float f2 = getHeight();
      int i = 0;
      while (i < j)
      {
        Object localObject = getChildAt(i);
        if (((View)localObject).getVisibility() != 8)
        {
          localObject = ((View)localObject).getTag();
          if ((localObject != null) && ((localObject instanceof String)))
          {
            localObject = ((String)localObject).split(",");
            if (localObject.length == 4)
            {
              int m = Integer.parseInt(localObject[0]);
              int i1 = Integer.parseInt(localObject[1]);
              int n = Integer.parseInt(localObject[2]);
              int k = Integer.parseInt(localObject[3]);
              m = (int)(m / 1080.0F * f1);
              i1 = (int)(i1 / 1920.0F * f2);
              n = (int)(n / 1080.0F * f1);
              k = (int)(k / 1920.0F * f2);
              localObject = new Paint();
              ((Paint)localObject).setColor(-65536);
              float f3 = m;
              float f4 = i1;
              float f5 = m + n;
              paramCanvas.drawLine(f3, f4, f5, f4, (Paint)localObject);
              float f6 = i1 + k;
              paramCanvas.drawLine(f5, f4, f5, f6, (Paint)localObject);
              paramCanvas.drawLine(f5, f6, f3, f6, (Paint)localObject);
              paramCanvas.drawLine(f3, f6, f3, f4, (Paint)localObject);
              ((Paint)localObject).setColor(-16711936);
              paramCanvas.drawLine(f3, f4, f5, f6, (Paint)localObject);
              paramCanvas.drawLine(f3, f6, f5, f4, (Paint)localObject);
            }
          }
        }
        i += 1;
      }
    }
  }
  
  public void fillMetrics(Metrics paramMetrics)
  {
    mMetrics = paramMetrics;
    mLayoutWidget.fillMetrics(paramMetrics);
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  public Object getDesignInformation(int paramInt, Object paramObject)
  {
    if ((paramInt == 0) && ((paramObject instanceof String)))
    {
      paramObject = (String)paramObject;
      HashMap localHashMap = mDesignIds;
      if ((localHashMap != null) && (localHashMap.containsKey(paramObject))) {
        return mDesignIds.get(paramObject);
      }
    }
    return null;
  }
  
  public int getMaxHeight()
  {
    return mMaxHeight;
  }
  
  public int getMaxWidth()
  {
    return mMaxWidth;
  }
  
  public int getMinHeight()
  {
    return mMinHeight;
  }
  
  public int getMinWidth()
  {
    return mMinWidth;
  }
  
  public int getOptimizationLevel()
  {
    return mLayoutWidget.getOptimizationLevel();
  }
  
  public View getViewById(int paramInt)
  {
    return (View)mChildrenByIds.get(paramInt);
  }
  
  public final ConstraintWidget getViewWidget(View paramView)
  {
    if (paramView == this) {
      return mLayoutWidget;
    }
    if (paramView == null) {
      return null;
    }
    return getLayoutParamswidget;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt3 = getChildCount();
    paramBoolean = isInEditMode();
    paramInt2 = 0;
    paramInt1 = 0;
    while (paramInt1 < paramInt3)
    {
      View localView = getChildAt(paramInt1);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      ConstraintWidget localConstraintWidget = widget;
      if (((localView.getVisibility() != 8) || (isGuideline) || (isHelper) || (paramBoolean)) && (!isInPlaceholder))
      {
        paramInt4 = localConstraintWidget.getDrawX();
        int i = localConstraintWidget.getDrawY();
        int j = localConstraintWidget.getWidth() + paramInt4;
        int k = localConstraintWidget.getHeight() + i;
        localView.layout(paramInt4, i, j, k);
        if ((localView instanceof Placeholder))
        {
          localView = ((Placeholder)localView).getContent();
          if (localView != null)
          {
            localView.setVisibility(0);
            localView.layout(paramInt4, i, j, k);
          }
        }
      }
      paramInt1 += 1;
    }
    paramInt3 = mConstraintHelpers.size();
    if (paramInt3 > 0)
    {
      paramInt1 = paramInt2;
      while (paramInt1 < paramInt3)
      {
        ((ConstraintHelper)mConstraintHelpers.get(paramInt1)).updatePostLayout(this);
        paramInt1 += 1;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    System.currentTimeMillis();
    int m = View.MeasureSpec.getMode(paramInt1);
    int n = View.MeasureSpec.getSize(paramInt1);
    int i2 = View.MeasureSpec.getMode(paramInt2);
    int i3 = View.MeasureSpec.getSize(paramInt2);
    int j = getPaddingLeft();
    int k = getPaddingTop();
    mLayoutWidget.setX(j);
    mLayoutWidget.setY(k);
    mLayoutWidget.setMaxWidth(mMaxWidth);
    mLayoutWidget.setMaxHeight(mMaxHeight);
    Object localObject;
    if (Build.VERSION.SDK_INT >= 17)
    {
      localObject = mLayoutWidget;
      boolean bool;
      if (getLayoutDirection() == 1) {
        bool = true;
      } else {
        bool = false;
      }
      ((ConstraintWidgetContainer)localObject).setRtl(bool);
    }
    setSelfDimensionBehaviour(paramInt1, paramInt2);
    int i8 = mLayoutWidget.getWidth();
    int i9 = mLayoutWidget.getHeight();
    if (mDirtyHierarchy)
    {
      mDirtyHierarchy = false;
      updateHierarchy();
      i = 1;
    }
    else
    {
      i = 0;
    }
    int i1;
    if ((mOptimizationLevel & 0x8) == 8) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    if (i1 != 0)
    {
      mLayoutWidget.preOptimize();
      mLayoutWidget.optimizeForDimensions(i8, i9);
      internalMeasureDimensions(paramInt1, paramInt2);
    }
    else
    {
      internalMeasureChildren(paramInt1, paramInt2);
    }
    updatePostMeasures();
    if ((getChildCount() > 0) && (i != 0)) {
      Analyzer.determineGroups(mLayoutWidget);
    }
    if (mLayoutWidget.mGroupsWrapOptimized)
    {
      if ((mLayoutWidget.mHorizontalWrapOptimized) && (m == Integer.MIN_VALUE))
      {
        if (mLayoutWidget.mWrapFixedWidth < n)
        {
          localObject = mLayoutWidget;
          ((ConstraintWidget)localObject).setWidth(mWrapFixedWidth);
        }
        mLayoutWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
      }
      if ((mLayoutWidget.mVerticalWrapOptimized) && (i2 == Integer.MIN_VALUE))
      {
        if (mLayoutWidget.mWrapFixedHeight < i3)
        {
          localObject = mLayoutWidget;
          ((ConstraintWidget)localObject).setHeight(mWrapFixedHeight);
        }
        mLayoutWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
      }
    }
    int i4;
    if ((mOptimizationLevel & 0x20) == 32)
    {
      i = mLayoutWidget.getWidth();
      i4 = mLayoutWidget.getHeight();
      if ((mLastMeasureWidth != i) && (m == 1073741824)) {
        Analyzer.setPosition(mLayoutWidget.mWidgetGroups, 0, i);
      }
      if ((mLastMeasureHeight != i4) && (i2 == 1073741824)) {
        Analyzer.setPosition(mLayoutWidget.mWidgetGroups, 1, i4);
      }
      if ((mLayoutWidget.mHorizontalWrapOptimized) && (mLayoutWidget.mWrapFixedWidth > n)) {
        Analyzer.setPosition(mLayoutWidget.mWidgetGroups, 0, n);
      }
      if ((mLayoutWidget.mVerticalWrapOptimized) && (mLayoutWidget.mWrapFixedHeight > i3)) {
        Analyzer.setPosition(mLayoutWidget.mWidgetGroups, 1, i3);
      }
    }
    if (getChildCount() > 0) {
      solveLinearSystem("First pass");
    }
    int i11 = mVariableDimensionsWidgets.size();
    int i10 = k + getPaddingBottom();
    int i12 = j + getPaddingRight();
    if (i11 > 0)
    {
      if (mLayoutWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        i2 = 1;
      } else {
        i2 = 0;
      }
      if (mLayoutWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        i3 = 1;
      } else {
        i3 = 0;
      }
      j = Math.max(mLayoutWidget.getWidth(), mMinWidth);
      i = Math.max(mLayoutWidget.getHeight(), mMinHeight);
      i4 = 0;
      m = 0;
      k = 0;
      View localView;
      while (i4 < i11)
      {
        localObject = (ConstraintWidget)mVariableDimensionsWidgets.get(i4);
        localView = (View)((ConstraintWidget)localObject).getCompanionWidget();
        int i5;
        int i6;
        int i7;
        if (localView == null)
        {
          i5 = k;
          i6 = i;
          n = j;
          i7 = m;
        }
        else
        {
          LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
          i5 = k;
          i6 = i;
          n = j;
          i7 = m;
          if (!isHelper) {
            if (isGuideline)
            {
              i5 = k;
              i6 = i;
              n = j;
              i7 = m;
            }
            else
            {
              if (localView.getVisibility() == 8) {}
              while ((i1 != 0) && (((ConstraintWidget)localObject).getResolutionWidth().isResolved()) && (((ConstraintWidget)localObject).getResolutionHeight().isResolved()))
              {
                i5 = k;
                i6 = i;
                n = j;
                i7 = m;
                break;
              }
              if ((width == -2) && (horizontalDimensionFixed)) {
                n = ViewGroup.getChildMeasureSpec(paramInt1, i12, width);
              } else {
                n = View.MeasureSpec.makeMeasureSpec(((ConstraintWidget)localObject).getWidth(), 1073741824);
              }
              if ((height == -2) && (verticalDimensionFixed)) {
                i5 = ViewGroup.getChildMeasureSpec(paramInt2, i10, height);
              } else {
                i5 = View.MeasureSpec.makeMeasureSpec(((ConstraintWidget)localObject).getHeight(), 1073741824);
              }
              localView.measure(n, i5);
              Metrics localMetrics = mMetrics;
              if (localMetrics != null) {
                additionalMeasures += 1L;
              }
              i6 = localView.getMeasuredWidth();
              i5 = localView.getMeasuredHeight();
              n = j;
              if (i6 != ((ConstraintWidget)localObject).getWidth())
              {
                ((ConstraintWidget)localObject).setWidth(i6);
                if (i1 != 0) {
                  ((ConstraintWidget)localObject).getResolutionWidth().resolve(i6);
                }
                n = j;
                if (i2 != 0)
                {
                  n = j;
                  if (((ConstraintWidget)localObject).getRight() > j) {
                    n = Math.max(j, ((ConstraintWidget)localObject).getRight() + ((ConstraintWidget)localObject).getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                  }
                }
                m = 1;
              }
              j = i;
              if (i5 != ((ConstraintWidget)localObject).getHeight())
              {
                ((ConstraintWidget)localObject).setHeight(i5);
                if (i1 != 0) {
                  ((ConstraintWidget)localObject).getResolutionHeight().resolve(i5);
                }
                j = i;
                if (i3 != 0)
                {
                  j = i;
                  if (((ConstraintWidget)localObject).getBottom() > i) {
                    j = Math.max(i, ((ConstraintWidget)localObject).getBottom() + ((ConstraintWidget)localObject).getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                  }
                }
                m = 1;
              }
              i = m;
              if (needsBaseline)
              {
                i5 = localView.getBaseline();
                i = m;
                if (i5 != -1)
                {
                  i = m;
                  if (i5 != ((ConstraintWidget)localObject).getBaselineDistance())
                  {
                    ((ConstraintWidget)localObject).setBaselineDistance(i5);
                    i = 1;
                  }
                }
              }
              if (Build.VERSION.SDK_INT >= 11)
              {
                i5 = View.combineMeasuredStates(k, localView.getMeasuredState());
                i6 = j;
                i7 = i;
              }
              else
              {
                i5 = k;
                i6 = j;
                i7 = i;
              }
            }
          }
        }
        i4 += 1;
        k = i5;
        i = i6;
        j = n;
        m = i7;
      }
      if (m != 0)
      {
        mLayoutWidget.setWidth(i8);
        mLayoutWidget.setHeight(i9);
        if (i1 != 0) {
          mLayoutWidget.solveGraph();
        }
        solveLinearSystem("2nd pass");
        if (mLayoutWidget.getWidth() < j)
        {
          mLayoutWidget.setWidth(j);
          j = 1;
        }
        else
        {
          j = 0;
        }
        if (mLayoutWidget.getHeight() < i)
        {
          mLayoutWidget.setHeight(i);
          j = 1;
        }
        if (j != 0) {
          solveLinearSystem("3rd pass");
        }
      }
      j = 0;
      for (;;)
      {
        i = k;
        if (j >= i11) {
          break;
        }
        localObject = (ConstraintWidget)mVariableDimensionsWidgets.get(j);
        localView = (View)((ConstraintWidget)localObject).getCompanionWidget();
        if (localView != null)
        {
          while (((localView.getMeasuredWidth() == ((ConstraintWidget)localObject).getWidth()) && (localView.getMeasuredHeight() == ((ConstraintWidget)localObject).getHeight())) || (((ConstraintWidget)localObject).getVisibility() == 8)) {}
          localView.measure(View.MeasureSpec.makeMeasureSpec(((ConstraintWidget)localObject).getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(((ConstraintWidget)localObject).getHeight(), 1073741824));
          localObject = mMetrics;
          if (localObject != null) {
            additionalMeasures += 1L;
          }
        }
        j += 1;
      }
    }
    int i = 0;
    j = mLayoutWidget.getWidth() + i12;
    k = mLayoutWidget.getHeight() + i10;
    if (Build.VERSION.SDK_INT >= 11)
    {
      paramInt1 = View.resolveSizeAndState(j, paramInt1, i);
      i = View.resolveSizeAndState(k, paramInt2, i << 16);
      j = Math.min(mMaxWidth, paramInt1 & 0xFFFFFF);
      paramInt2 = j;
      i = Math.min(mMaxHeight, i & 0xFFFFFF);
      paramInt1 = i;
      if (mLayoutWidget.isWidthMeasuredTooSmall()) {
        paramInt2 = j | 0x1000000;
      }
      if (mLayoutWidget.isHeightMeasuredTooSmall()) {
        paramInt1 = i | 0x1000000;
      }
      setMeasuredDimension(paramInt2, paramInt1);
      mLastMeasureWidth = paramInt2;
      mLastMeasureHeight = paramInt1;
      return;
    }
    setMeasuredDimension(j, k);
    mLastMeasureWidth = j;
    mLastMeasureHeight = k;
  }
  
  public void onViewAdded(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      super.onViewAdded(paramView);
    }
    Object localObject = getViewWidget(paramView);
    if (((paramView instanceof Guideline)) && (!(localObject instanceof androidx.constraintlayout.solver.widgets.Guideline)))
    {
      localObject = (LayoutParams)paramView.getLayoutParams();
      widget = new androidx.constraintlayout.solver.widgets.Guideline();
      isGuideline = true;
      ((androidx.constraintlayout.solver.widgets.Guideline)widget).setOrientation(orientation);
    }
    if ((paramView instanceof ConstraintHelper))
    {
      localObject = (ConstraintHelper)paramView;
      ((ConstraintHelper)localObject).validateParams();
      getLayoutParamsisHelper = true;
      if (!mConstraintHelpers.contains(localObject)) {
        mConstraintHelpers.add(localObject);
      }
    }
    mChildrenByIds.put(paramView.getId(), paramView);
    mDirtyHierarchy = true;
  }
  
  public void onViewRemoved(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      super.onViewRemoved(paramView);
    }
    mChildrenByIds.remove(paramView.getId());
    ConstraintWidget localConstraintWidget = getViewWidget(paramView);
    mLayoutWidget.remove(localConstraintWidget);
    mConstraintHelpers.remove(paramView);
    mVariableDimensionsWidgets.remove(localConstraintWidget);
    mDirtyHierarchy = true;
  }
  
  public void removeView(View paramView)
  {
    super.removeView(paramView);
    if (Build.VERSION.SDK_INT < 14) {
      onViewRemoved(paramView);
    }
  }
  
  public void requestLayout()
  {
    super.requestLayout();
    mDirtyHierarchy = true;
    mLastMeasureWidth = -1;
    mLastMeasureHeight = -1;
    mLastMeasureWidthSize = -1;
    mLastMeasureHeightSize = -1;
    mLastMeasureWidthMode = 0;
    mLastMeasureHeightMode = 0;
  }
  
  public void setConstraintSet(ConstraintSet paramConstraintSet)
  {
    mConstraintSet = paramConstraintSet;
  }
  
  public void setDesignInformation(int paramInt, Object paramObject1, Object paramObject2)
  {
    if ((paramInt == 0) && ((paramObject1 instanceof String)) && ((paramObject2 instanceof Integer)))
    {
      if (mDesignIds == null) {
        mDesignIds = new HashMap();
      }
      String str = (String)paramObject1;
      paramInt = str.indexOf("/");
      paramObject1 = str;
      if (paramInt != -1) {
        paramObject1 = str.substring(paramInt + 1);
      }
      paramInt = ((Integer)paramObject2).intValue();
      mDesignIds.put(paramObject1, Integer.valueOf(paramInt));
    }
  }
  
  public void setId(int paramInt)
  {
    mChildrenByIds.remove(getId());
    super.setId(paramInt);
    mChildrenByIds.put(getId(), this);
  }
  
  public void setMaxHeight(int paramInt)
  {
    if (paramInt == mMaxHeight) {
      return;
    }
    mMaxHeight = paramInt;
    requestLayout();
  }
  
  public void setMaxWidth(int paramInt)
  {
    if (paramInt == mMaxWidth) {
      return;
    }
    mMaxWidth = paramInt;
    requestLayout();
  }
  
  public void setMinHeight(int paramInt)
  {
    if (paramInt == mMinHeight) {
      return;
    }
    mMinHeight = paramInt;
    requestLayout();
  }
  
  public void setMinWidth(int paramInt)
  {
    if (paramInt == mMinWidth) {
      return;
    }
    mMinWidth = paramInt;
    requestLayout();
  }
  
  public void setOptimizationLevel(int paramInt)
  {
    mLayoutWidget.setOptimizationLevel(paramInt);
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return false;
  }
  
  protected void solveLinearSystem(String paramString)
  {
    mLayoutWidget.layout();
    paramString = mMetrics;
    if (paramString != null) {
      resolutions += 1L;
    }
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    public static final int BASELINE = 5;
    public static final int BOTTOM = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static final int HORIZONTAL = 0;
    public static final int LEFT = 1;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int PARENT_ID = 0;
    public static final int PRIORITY_MIDHIGH = 7;
    public static final int RIGHT = 2;
    public static final int SORT_MENU_ITEM = 3;
    public static final int START = 6;
    public static final int UNSET = -1;
    public static final int VERTICAL = 1;
    public int baselineToBaseline = -1;
    public int bottomToBottom = -1;
    public int bottomToTop = -1;
    public float circleAngle = 0.0F;
    public int circleConstraint = -1;
    public int circleRadius = 0;
    public boolean constrainedHeight = false;
    public boolean constrainedWidth = false;
    public String dimensionRatio = null;
    int dimensionRatioSide = 1;
    float dimensionRatioValue = 0.0F;
    public int editorAbsoluteX = -1;
    public int editorAbsoluteY = -1;
    public int endToEnd = -1;
    public int endToStart = -1;
    public int goneBottomMargin = -1;
    public int goneEndMargin = -1;
    public int goneLeftMargin = -1;
    public int goneRightMargin = -1;
    public int goneStartMargin = -1;
    public int goneTopMargin = -1;
    public int guideBegin = -1;
    public int guideEnd = -1;
    public float guidePercent = -1.0F;
    public boolean helped = false;
    public float horizontalBias = 0.5F;
    public int horizontalChainStyle = 0;
    boolean horizontalDimensionFixed = true;
    public float horizontalWeight = -1.0F;
    boolean isGuideline = false;
    boolean isHelper = false;
    boolean isInPlaceholder = false;
    public int leftToLeft = -1;
    public int leftToRight = -1;
    public int matchConstraintDefaultHeight = 0;
    public int matchConstraintDefaultWidth = 0;
    public int matchConstraintMaxHeight = 0;
    public int matchConstraintMaxWidth = 0;
    public int matchConstraintMinHeight = 0;
    public int matchConstraintMinWidth = 0;
    public float matchConstraintPercentHeight = 1.0F;
    public float matchConstraintPercentWidth = 1.0F;
    boolean needsBaseline = false;
    public int orientation = -1;
    int resolveGoneLeftMargin = -1;
    int resolveGoneRightMargin = -1;
    int resolvedGuideBegin;
    int resolvedGuideEnd;
    float resolvedGuidePercent;
    float resolvedHorizontalBias = 0.5F;
    int resolvedLeftToLeft = -1;
    int resolvedLeftToRight = -1;
    int resolvedRightToLeft = -1;
    int resolvedRightToRight = -1;
    public int rightToLeft = -1;
    public int rightToRight = -1;
    public int startToEnd = -1;
    public int startToStart = -1;
    public int topToBottom = -1;
    public int topToTop = -1;
    public float verticalBias = 0.5F;
    public int verticalChainStyle = 0;
    boolean verticalDimensionFixed = true;
    public float verticalWeight = -1.0F;
    ConstraintWidget widget = new ConstraintWidget();
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int k = paramContext.getIndexCount();
      int i = 0;
      while (i < k)
      {
        int j = paramContext.getIndex(i);
        String str;
        switch (Table.subdomains.get(j))
        {
        default: 
          break;
        case 43: 
          break;
        case 50: 
          editorAbsoluteY = paramContext.getDimensionPixelOffset(j, editorAbsoluteY);
          break;
        case 49: 
          editorAbsoluteX = paramContext.getDimensionPixelOffset(j, editorAbsoluteX);
          break;
        case 48: 
          verticalChainStyle = paramContext.getInt(j, 0);
          break;
        case 47: 
          horizontalChainStyle = paramContext.getInt(j, 0);
          break;
        case 46: 
          verticalWeight = paramContext.getFloat(j, verticalWeight);
          break;
        case 45: 
          horizontalWeight = paramContext.getFloat(j, horizontalWeight);
          break;
        case 44: 
          dimensionRatio = paramContext.getString(j);
          dimensionRatioValue = NaN.0F;
          dimensionRatioSide = -1;
          paramAttributeSet = dimensionRatio;
          if (paramAttributeSet != null)
          {
            m = paramAttributeSet.length();
            j = dimensionRatio.indexOf(',');
            if ((j > 0) && (j < m - 1))
            {
              paramAttributeSet = dimensionRatio.substring(0, j);
              if (paramAttributeSet.equalsIgnoreCase("W")) {
                dimensionRatioSide = 0;
              } else if (paramAttributeSet.equalsIgnoreCase("H")) {
                dimensionRatioSide = 1;
              }
              j += 1;
            }
            else
            {
              j = 0;
            }
            int n = dimensionRatio.indexOf(':');
            if ((n >= 0) && (n < m - 1))
            {
              paramAttributeSet = dimensionRatio.substring(j, n);
              str = dimensionRatio.substring(n + 1);
              if ((paramAttributeSet.length() <= 0) || (str.length() <= 0)) {
                break;
              }
            }
          }
          break;
        }
        try
        {
          f1 = Float.parseFloat(paramAttributeSet);
          float f2 = Float.parseFloat(str);
          if ((f1 <= 0.0F) || (f2 <= 0.0F)) {
            break label2275;
          }
          if (dimensionRatioSide == 1)
          {
            f1 = f2 / f1;
            f1 = Math.abs(f1);
            dimensionRatioValue = f1;
          }
          else
          {
            f1 /= f2;
            f1 = Math.abs(f1);
            dimensionRatioValue = f1;
          }
        }
        catch (NumberFormatException paramAttributeSet)
        {
          float f1;
          for (;;) {}
        }
        paramAttributeSet = dimensionRatio.substring(j);
        if (paramAttributeSet.length() > 0) {}
        try
        {
          f1 = Float.parseFloat(paramAttributeSet);
          dimensionRatioValue = f1;
        }
        catch (NumberFormatException paramAttributeSet)
        {
          for (;;) {}
        }
        matchConstraintPercentHeight = Math.max(0.0F, paramContext.getFloat(j, matchConstraintPercentHeight));
        break label2275;
        int m = matchConstraintMaxHeight;
        try
        {
          m = paramContext.getDimensionPixelSize(j, m);
          matchConstraintMaxHeight = m;
        }
        catch (Exception paramAttributeSet)
        {
          for (;;) {}
        }
        if (paramContext.getInt(j, matchConstraintMaxHeight) == -2)
        {
          matchConstraintMaxHeight = -2;
          break label2275;
          m = matchConstraintMinHeight;
        }
        try
        {
          m = paramContext.getDimensionPixelSize(j, m);
          matchConstraintMinHeight = m;
        }
        catch (Exception paramAttributeSet)
        {
          for (;;) {}
        }
        if (paramContext.getInt(j, matchConstraintMinHeight) == -2)
        {
          matchConstraintMinHeight = -2;
          break label2275;
          matchConstraintPercentWidth = Math.max(0.0F, paramContext.getFloat(j, matchConstraintPercentWidth));
          break label2275;
          m = matchConstraintMaxWidth;
        }
        try
        {
          m = paramContext.getDimensionPixelSize(j, m);
          matchConstraintMaxWidth = m;
        }
        catch (Exception paramAttributeSet)
        {
          label2275:
          for (;;) {}
        }
        if (paramContext.getInt(j, matchConstraintMaxWidth) == -2)
        {
          matchConstraintMaxWidth = -2;
          break label2275;
          m = matchConstraintMinWidth;
        }
        try
        {
          m = paramContext.getDimensionPixelSize(j, m);
          matchConstraintMinWidth = m;
        }
        catch (Exception paramAttributeSet)
        {
          for (;;) {}
        }
        if (paramContext.getInt(j, matchConstraintMinWidth) == -2)
        {
          matchConstraintMinWidth = -2;
          break label2275;
          matchConstraintDefaultHeight = paramContext.getInt(j, 0);
          if (matchConstraintDefaultHeight == 1)
          {
            Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
            break label2275;
            matchConstraintDefaultWidth = paramContext.getInt(j, 0);
            if (matchConstraintDefaultWidth == 1)
            {
              Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
              break label2275;
              verticalBias = paramContext.getFloat(j, verticalBias);
              break label2275;
              horizontalBias = paramContext.getFloat(j, horizontalBias);
              break label2275;
              constrainedHeight = paramContext.getBoolean(j, constrainedHeight);
              break label2275;
              constrainedWidth = paramContext.getBoolean(j, constrainedWidth);
              break label2275;
              goneEndMargin = paramContext.getDimensionPixelSize(j, goneEndMargin);
              break label2275;
              goneStartMargin = paramContext.getDimensionPixelSize(j, goneStartMargin);
              break label2275;
              goneBottomMargin = paramContext.getDimensionPixelSize(j, goneBottomMargin);
              break label2275;
              goneRightMargin = paramContext.getDimensionPixelSize(j, goneRightMargin);
              break label2275;
              goneTopMargin = paramContext.getDimensionPixelSize(j, goneTopMargin);
              break label2275;
              goneLeftMargin = paramContext.getDimensionPixelSize(j, goneLeftMargin);
              break label2275;
              endToEnd = paramContext.getResourceId(j, endToEnd);
              if (endToEnd == -1)
              {
                endToEnd = paramContext.getInt(j, -1);
                break label2275;
                endToStart = paramContext.getResourceId(j, endToStart);
                if (endToStart == -1)
                {
                  endToStart = paramContext.getInt(j, -1);
                  break label2275;
                  startToStart = paramContext.getResourceId(j, startToStart);
                  if (startToStart == -1)
                  {
                    startToStart = paramContext.getInt(j, -1);
                    break label2275;
                    startToEnd = paramContext.getResourceId(j, startToEnd);
                    if (startToEnd == -1)
                    {
                      startToEnd = paramContext.getInt(j, -1);
                      break label2275;
                      baselineToBaseline = paramContext.getResourceId(j, baselineToBaseline);
                      if (baselineToBaseline == -1)
                      {
                        baselineToBaseline = paramContext.getInt(j, -1);
                        break label2275;
                        bottomToBottom = paramContext.getResourceId(j, bottomToBottom);
                        if (bottomToBottom == -1)
                        {
                          bottomToBottom = paramContext.getInt(j, -1);
                          break label2275;
                          bottomToTop = paramContext.getResourceId(j, bottomToTop);
                          if (bottomToTop == -1)
                          {
                            bottomToTop = paramContext.getInt(j, -1);
                            break label2275;
                            topToBottom = paramContext.getResourceId(j, topToBottom);
                            if (topToBottom == -1)
                            {
                              topToBottom = paramContext.getInt(j, -1);
                              break label2275;
                              topToTop = paramContext.getResourceId(j, topToTop);
                              if (topToTop == -1)
                              {
                                topToTop = paramContext.getInt(j, -1);
                                break label2275;
                                rightToRight = paramContext.getResourceId(j, rightToRight);
                                if (rightToRight == -1)
                                {
                                  rightToRight = paramContext.getInt(j, -1);
                                  break label2275;
                                  rightToLeft = paramContext.getResourceId(j, rightToLeft);
                                  if (rightToLeft == -1)
                                  {
                                    rightToLeft = paramContext.getInt(j, -1);
                                    break label2275;
                                    leftToRight = paramContext.getResourceId(j, leftToRight);
                                    if (leftToRight == -1)
                                    {
                                      leftToRight = paramContext.getInt(j, -1);
                                      break label2275;
                                      leftToLeft = paramContext.getResourceId(j, leftToLeft);
                                      if (leftToLeft == -1)
                                      {
                                        leftToLeft = paramContext.getInt(j, -1);
                                        break label2275;
                                        guidePercent = paramContext.getFloat(j, guidePercent);
                                        break label2275;
                                        guideEnd = paramContext.getDimensionPixelOffset(j, guideEnd);
                                        break label2275;
                                        guideBegin = paramContext.getDimensionPixelOffset(j, guideBegin);
                                        break label2275;
                                        circleAngle = (paramContext.getFloat(j, circleAngle) % 360.0F);
                                        f1 = circleAngle;
                                        if (f1 < 0.0F)
                                        {
                                          circleAngle = ((360.0F - f1) % 360.0F);
                                          break label2275;
                                          circleRadius = paramContext.getDimensionPixelSize(j, circleRadius);
                                          break label2275;
                                          circleConstraint = paramContext.getResourceId(j, circleConstraint);
                                          if (circleConstraint == -1)
                                          {
                                            circleConstraint = paramContext.getInt(j, -1);
                                            break label2275;
                                            orientation = paramContext.getInt(j, orientation);
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        i += 1;
      }
      paramContext.recycle();
      validate();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      guideBegin = guideBegin;
      guideEnd = guideEnd;
      guidePercent = guidePercent;
      leftToLeft = leftToLeft;
      leftToRight = leftToRight;
      rightToLeft = rightToLeft;
      rightToRight = rightToRight;
      topToTop = topToTop;
      topToBottom = topToBottom;
      bottomToTop = bottomToTop;
      bottomToBottom = bottomToBottom;
      baselineToBaseline = baselineToBaseline;
      circleConstraint = circleConstraint;
      circleRadius = circleRadius;
      circleAngle = circleAngle;
      startToEnd = startToEnd;
      startToStart = startToStart;
      endToStart = endToStart;
      endToEnd = endToEnd;
      goneLeftMargin = goneLeftMargin;
      goneTopMargin = goneTopMargin;
      goneRightMargin = goneRightMargin;
      goneBottomMargin = goneBottomMargin;
      goneStartMargin = goneStartMargin;
      goneEndMargin = goneEndMargin;
      horizontalBias = horizontalBias;
      verticalBias = verticalBias;
      dimensionRatio = dimensionRatio;
      dimensionRatioValue = dimensionRatioValue;
      dimensionRatioSide = dimensionRatioSide;
      horizontalWeight = horizontalWeight;
      verticalWeight = verticalWeight;
      horizontalChainStyle = horizontalChainStyle;
      verticalChainStyle = verticalChainStyle;
      constrainedWidth = constrainedWidth;
      constrainedHeight = constrainedHeight;
      matchConstraintDefaultWidth = matchConstraintDefaultWidth;
      matchConstraintDefaultHeight = matchConstraintDefaultHeight;
      matchConstraintMinWidth = matchConstraintMinWidth;
      matchConstraintMaxWidth = matchConstraintMaxWidth;
      matchConstraintMinHeight = matchConstraintMinHeight;
      matchConstraintMaxHeight = matchConstraintMaxHeight;
      matchConstraintPercentWidth = matchConstraintPercentWidth;
      matchConstraintPercentHeight = matchConstraintPercentHeight;
      editorAbsoluteX = editorAbsoluteX;
      editorAbsoluteY = editorAbsoluteY;
      orientation = orientation;
      horizontalDimensionFixed = horizontalDimensionFixed;
      verticalDimensionFixed = verticalDimensionFixed;
      needsBaseline = needsBaseline;
      isGuideline = isGuideline;
      resolvedLeftToLeft = resolvedLeftToLeft;
      resolvedLeftToRight = resolvedLeftToRight;
      resolvedRightToLeft = resolvedRightToLeft;
      resolvedRightToRight = resolvedRightToRight;
      resolveGoneLeftMargin = resolveGoneLeftMargin;
      resolveGoneRightMargin = resolveGoneRightMargin;
      resolvedHorizontalBias = resolvedHorizontalBias;
      widget = widget;
    }
    
    public int getMarginEnd()
    {
      throw new Error("Unresolved compilation error: Method <androidx.constraintlayout.widget.ConstraintLayout$LayoutParams: int getMarginEnd()> does not exist!");
    }
    
    public int getMarginStart()
    {
      throw new Error("Unresolved compilation error: Method <androidx.constraintlayout.widget.ConstraintLayout$LayoutParams: int getMarginStart()> does not exist!");
    }
    
    public void reset()
    {
      ConstraintWidget localConstraintWidget = widget;
      if (localConstraintWidget != null) {
        localConstraintWidget.reset();
      }
    }
    
    public void resolveLayoutDirection(int paramInt)
    {
      int j = leftMargin;
      int k = rightMargin;
      super.resolveLayoutDirection(paramInt);
      resolvedRightToLeft = -1;
      resolvedRightToRight = -1;
      resolvedLeftToLeft = -1;
      resolvedLeftToRight = -1;
      resolveGoneLeftMargin = -1;
      resolveGoneRightMargin = -1;
      resolveGoneLeftMargin = goneLeftMargin;
      resolveGoneRightMargin = goneRightMargin;
      resolvedHorizontalBias = horizontalBias;
      resolvedGuideBegin = guideBegin;
      resolvedGuideEnd = guideEnd;
      resolvedGuidePercent = guidePercent;
      paramInt = getLayoutDirection();
      int i = 0;
      if (1 == paramInt) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (paramInt != 0)
      {
        paramInt = startToEnd;
        if (paramInt != -1) {
          resolvedRightToLeft = paramInt;
        }
        for (;;)
        {
          paramInt = 1;
          break;
          int m = startToStart;
          paramInt = i;
          if (m == -1) {
            break;
          }
          resolvedRightToRight = m;
        }
        i = endToStart;
        if (i != -1)
        {
          resolvedLeftToRight = i;
          paramInt = 1;
        }
        i = endToEnd;
        if (i != -1)
        {
          resolvedLeftToLeft = i;
          paramInt = 1;
        }
        i = goneStartMargin;
        if (i != -1) {
          resolveGoneRightMargin = i;
        }
        i = goneEndMargin;
        if (i != -1) {
          resolveGoneLeftMargin = i;
        }
        if (paramInt != 0) {
          resolvedHorizontalBias = (1.0F - horizontalBias);
        }
        if ((isGuideline) && (orientation == 1))
        {
          float f = guidePercent;
          if (f != -1.0F)
          {
            resolvedGuidePercent = (1.0F - f);
            resolvedGuideBegin = -1;
            resolvedGuideEnd = -1;
          }
          else
          {
            paramInt = guideBegin;
            if (paramInt != -1)
            {
              resolvedGuideEnd = paramInt;
              resolvedGuideBegin = -1;
              resolvedGuidePercent = -1.0F;
            }
            else
            {
              paramInt = guideEnd;
              if (paramInt != -1)
              {
                resolvedGuideBegin = paramInt;
                resolvedGuideEnd = -1;
                resolvedGuidePercent = -1.0F;
              }
            }
          }
        }
      }
      else
      {
        paramInt = startToEnd;
        if (paramInt != -1) {
          resolvedLeftToRight = paramInt;
        }
        paramInt = startToStart;
        if (paramInt != -1) {
          resolvedLeftToLeft = paramInt;
        }
        paramInt = endToStart;
        if (paramInt != -1) {
          resolvedRightToLeft = paramInt;
        }
        paramInt = endToEnd;
        if (paramInt != -1) {
          resolvedRightToRight = paramInt;
        }
        paramInt = goneStartMargin;
        if (paramInt != -1) {
          resolveGoneLeftMargin = paramInt;
        }
        paramInt = goneEndMargin;
        if (paramInt != -1) {
          resolveGoneRightMargin = paramInt;
        }
      }
      if ((endToStart == -1) && (endToEnd == -1) && (startToStart == -1) && (startToEnd == -1))
      {
        paramInt = rightToLeft;
        if (paramInt != -1)
        {
          resolvedRightToLeft = paramInt;
          if ((rightMargin <= 0) && (k > 0)) {
            rightMargin = k;
          }
        }
        else
        {
          paramInt = rightToRight;
          if (paramInt != -1)
          {
            resolvedRightToRight = paramInt;
            if ((rightMargin <= 0) && (k > 0)) {
              rightMargin = k;
            }
          }
        }
        paramInt = leftToLeft;
        if (paramInt != -1)
        {
          resolvedLeftToLeft = paramInt;
          if ((leftMargin <= 0) && (j > 0)) {
            leftMargin = j;
          }
        }
        else
        {
          paramInt = leftToRight;
          if (paramInt != -1)
          {
            resolvedLeftToRight = paramInt;
            if ((leftMargin <= 0) && (j > 0)) {
              leftMargin = j;
            }
          }
        }
      }
    }
    
    public void setMarginEnd(int paramInt)
    {
      throw new Error("Unresolved compilation error: Method <androidx.constraintlayout.widget.ConstraintLayout$LayoutParams: void setMarginEnd(int)> does not exist!");
    }
    
    public void setMarginStart(int paramInt)
    {
      throw new Error("Unresolved compilation error: Method <androidx.constraintlayout.widget.ConstraintLayout$LayoutParams: void setMarginStart(int)> does not exist!");
    }
    
    public void validate()
    {
      isGuideline = false;
      horizontalDimensionFixed = true;
      verticalDimensionFixed = true;
      if ((width == -2) && (constrainedWidth))
      {
        horizontalDimensionFixed = false;
        matchConstraintDefaultWidth = 1;
      }
      if ((height == -2) && (constrainedHeight))
      {
        verticalDimensionFixed = false;
        matchConstraintDefaultHeight = 1;
      }
      if ((width == 0) || (width == -1))
      {
        horizontalDimensionFixed = false;
        if ((width == 0) && (matchConstraintDefaultWidth == 1))
        {
          width = -2;
          constrainedWidth = true;
        }
      }
      if ((height == 0) || (height == -1))
      {
        verticalDimensionFixed = false;
        if ((height == 0) && (matchConstraintDefaultHeight == 1))
        {
          height = -2;
          constrainedHeight = true;
        }
      }
      if ((guidePercent != -1.0F) || (guideBegin != -1) || (guideEnd != -1))
      {
        isGuideline = true;
        horizontalDimensionFixed = true;
        verticalDimensionFixed = true;
        if (!(widget instanceof androidx.constraintlayout.solver.widgets.Guideline)) {
          widget = new androidx.constraintlayout.solver.widgets.Guideline();
        }
        ((androidx.constraintlayout.solver.widgets.Guideline)widget).setOrientation(orientation);
      }
    }
    
    private static class Table
    {
      public static final int ANDROID_ORIENTATION = 1;
      public static final int LAYOUT_CONSTRAINED_HEIGHT = 28;
      public static final int LAYOUT_CONSTRAINED_WIDTH = 27;
      public static final int LAYOUT_CONSTRAINT_BASELINE_CREATOR = 43;
      public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BASELINE_OF = 16;
      public static final int LAYOUT_CONSTRAINT_BOTTOM_CREATOR = 42;
      public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_BOTTOM_OF = 15;
      public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_TOP_OF = 14;
      public static final int LAYOUT_CONSTRAINT_CIRCLE = 2;
      public static final int LAYOUT_CONSTRAINT_CIRCLE_ANGLE = 4;
      public static final int LAYOUT_CONSTRAINT_CIRCLE_RADIUS = 3;
      public static final int LAYOUT_CONSTRAINT_DIMENSION_RATIO = 44;
      public static final int LAYOUT_CONSTRAINT_END_TO_END_OF = 20;
      public static final int LAYOUT_CONSTRAINT_END_TO_START_OF = 19;
      public static final int LAYOUT_CONSTRAINT_GUIDE_BEGIN = 5;
      public static final int LAYOUT_CONSTRAINT_GUIDE_END = 6;
      public static final int LAYOUT_CONSTRAINT_GUIDE_PERCENT = 7;
      public static final int LAYOUT_CONSTRAINT_HEIGHT_DEFAULT = 32;
      public static final int LAYOUT_CONSTRAINT_HEIGHT_MAX = 37;
      public static final int LAYOUT_CONSTRAINT_HEIGHT_MIN = 36;
      public static final int LAYOUT_CONSTRAINT_HEIGHT_PERCENT = 38;
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_BIAS = 29;
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_CHAINSTYLE = 47;
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_WEIGHT = 45;
      public static final int LAYOUT_CONSTRAINT_LEFT_CREATOR = 39;
      public static final int LAYOUT_CONSTRAINT_LEFT_TO_LEFT_OF = 8;
      public static final int LAYOUT_CONSTRAINT_LEFT_TO_RIGHT_OF = 9;
      public static final int LAYOUT_CONSTRAINT_RIGHT_CREATOR = 41;
      public static final int LAYOUT_CONSTRAINT_RIGHT_TO_LEFT_OF = 10;
      public static final int LAYOUT_CONSTRAINT_RIGHT_TO_RIGHT_OF = 11;
      public static final int LAYOUT_CONSTRAINT_START_TO_END_OF = 17;
      public static final int LAYOUT_CONSTRAINT_START_TO_START_OF = 18;
      public static final int LAYOUT_CONSTRAINT_TOP_CREATOR = 40;
      public static final int LAYOUT_CONSTRAINT_TOP_TO_BOTTOM_OF = 13;
      public static final int LAYOUT_CONSTRAINT_TOP_TO_TOP_OF = 12;
      public static final int LAYOUT_CONSTRAINT_VERTICAL_BIAS = 30;
      public static final int LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE = 48;
      public static final int LAYOUT_CONSTRAINT_VERTICAL_WEIGHT = 46;
      public static final int LAYOUT_CONSTRAINT_WIDTH_DEFAULT = 31;
      public static final int LAYOUT_CONSTRAINT_WIDTH_MAX = 34;
      public static final int LAYOUT_CONSTRAINT_WIDTH_MIN = 33;
      public static final int LAYOUT_CONSTRAINT_WIDTH_PERCENT = 35;
      public static final int LAYOUT_EDITOR_ABSOLUTEX = 49;
      public static final int LAYOUT_EDITOR_ABSOLUTEY = 50;
      public static final int LAYOUT_GONE_MARGIN_BOTTOM = 24;
      public static final int LAYOUT_GONE_MARGIN_END = 26;
      public static final int LAYOUT_GONE_MARGIN_LEFT = 21;
      public static final int LAYOUT_GONE_MARGIN_RIGHT = 23;
      public static final int LAYOUT_GONE_MARGIN_START = 25;
      public static final int LAYOUT_GONE_MARGIN_TOP = 22;
      public static final int UNUSED = 0;
      public static final SparseIntArray subdomains = new SparseIntArray();
      
      static
      {
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
        subdomains.append(R.styleable.ConstraintLayout_Layout_android_orientation, 1);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
        subdomains.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
      }
      
      private Table() {}
    }
  }
}
