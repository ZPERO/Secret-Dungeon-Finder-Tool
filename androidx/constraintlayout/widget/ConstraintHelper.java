package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import androidx.constraintlayout.solver.widgets.Helper;
import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class ConstraintHelper
  extends View
{
  protected int mCount;
  protected Helper mHelperWidget;
  protected int[] mIds = new int[32];
  private String mReferenceIds;
  protected boolean mUseViewMeasure = false;
  protected Context myContext;
  
  public ConstraintHelper(Context paramContext)
  {
    super(paramContext);
    myContext = paramContext;
    init(null);
  }
  
  public ConstraintHelper(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    myContext = paramContext;
    init(paramAttributeSet);
  }
  
  public ConstraintHelper(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    myContext = paramContext;
    init(paramAttributeSet);
  }
  
  private void addID(String paramString)
  {
    if (paramString == null) {
      return;
    }
    if (myContext == null) {
      return;
    }
    paramString = paramString.trim();
    try
    {
      j = R.id.class.getField(paramString).getInt(null);
    }
    catch (Exception localException)
    {
      int j;
      int i;
      Object localObject;
      for (;;) {}
    }
    j = 0;
    i = j;
    if (j == 0) {
      i = myContext.getResources().getIdentifier(paramString, "id", myContext.getPackageName());
    }
    j = i;
    if (i == 0)
    {
      j = i;
      if (isInEditMode())
      {
        j = i;
        if ((getParent() instanceof ConstraintLayout))
        {
          localObject = ((ConstraintLayout)getParent()).getDesignInformation(0, paramString);
          j = i;
          if (localObject != null)
          {
            j = i;
            if ((localObject instanceof Integer)) {
              j = ((Integer)localObject).intValue();
            }
          }
        }
      }
    }
    if (j != 0)
    {
      setTag(j, null);
      return;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Could not find id of \"");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("\"");
    Log.w("ConstraintHelper", ((StringBuilder)localObject).toString());
  }
  
  private void setIds(String paramString)
  {
    if (paramString == null) {
      return;
    }
    int j;
    for (int i = 0;; i = j + 1)
    {
      j = paramString.indexOf(',', i);
      if (j == -1)
      {
        addID(paramString.substring(i));
        return;
      }
      addID(paramString.substring(i, j));
    }
  }
  
  public int[] getReferencedIds()
  {
    return Arrays.copyOf(mIds, mCount);
  }
  
  protected void init(AttributeSet paramAttributeSet)
  {
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int j = paramAttributeSet.getIndexCount();
      int i = 0;
      while (i < j)
      {
        int k = paramAttributeSet.getIndex(i);
        if (k == R.styleable.ConstraintLayout_Layout_constraint_referenced_ids)
        {
          mReferenceIds = paramAttributeSet.getString(k);
          setIds(mReferenceIds);
        }
        i += 1;
      }
    }
  }
  
  public void onDraw(Canvas paramCanvas) {}
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (mUseViewMeasure)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
    setMeasuredDimension(0, 0);
  }
  
  public void setReferencedIds(int[] paramArrayOfInt)
  {
    int i = 0;
    mCount = 0;
    while (i < paramArrayOfInt.length)
    {
      setTag(paramArrayOfInt[i], null);
      i += 1;
    }
  }
  
  public void setTag(int paramInt, Object paramObject)
  {
    int i = mCount;
    paramObject = mIds;
    if (i + 1 > paramObject.length) {
      mIds = Arrays.copyOf(paramObject, paramObject.length * 2);
    }
    paramObject = mIds;
    i = mCount;
    paramObject[i] = paramInt;
    mCount = (i + 1);
  }
  
  public void updatePostLayout(ConstraintLayout paramConstraintLayout) {}
  
  public void updatePostMeasure(ConstraintLayout paramConstraintLayout) {}
  
  public void updatePreLayout(ConstraintLayout paramConstraintLayout)
  {
    if (isInEditMode()) {
      setIds(mReferenceIds);
    }
    Object localObject = mHelperWidget;
    if (localObject == null) {
      return;
    }
    ((Helper)localObject).removeAllIds();
    int i = 0;
    while (i < mCount)
    {
      localObject = paramConstraintLayout.getViewById(mIds[i]);
      if (localObject != null) {
        mHelperWidget.getBytes(paramConstraintLayout.getViewWidget((View)localObject));
      }
      i += 1;
    }
  }
  
  public void validateParams()
  {
    if (mHelperWidget == null) {
      return;
    }
    ViewGroup.LayoutParams localLayoutParams = getLayoutParams();
    if ((localLayoutParams instanceof ConstraintLayout.LayoutParams)) {
      widget = mHelperWidget;
    }
  }
}
