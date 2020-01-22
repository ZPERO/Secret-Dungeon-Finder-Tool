package androidx.appcompat.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DataSetObserver;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.appcompat.R.attr;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.content.wiki.AppCompatResources;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.TintableBackgroundView;
import androidx.core.view.ViewCompat;

public class AppCompatSpinner
  extends Spinner
  implements TintableBackgroundView
{
  private static final int[] ATTRS_ANDROID_SPINNERMODE = { 16843505 };
  private static final int MAX_ITEMS_MEASURED = 15;
  private static final int MODE_DIALOG = 0;
  private static final int MODE_DROPDOWN = 1;
  private static final int MODE_THEME = -1;
  private static final String TAG = "AppCompatSpinner";
  private final AppCompatBackgroundHelper mBackgroundTintHelper;
  int mDropDownWidth;
  private ForwardingListener mForwardingListener;
  private SpinnerPopup mPopup;
  private final Context mPopupContext;
  private final boolean mPopupSet;
  private SpinnerAdapter mTempAdapter;
  final Rect mTempRect;
  
  public AppCompatSpinner(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppCompatSpinner(Context paramContext, int paramInt)
  {
    this(paramContext, null, R.attr.spinnerStyle, paramInt);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.spinnerStyle);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, -1);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this(paramContext, paramAttributeSet, paramInt1, paramInt2, null);
  }
  
  /* Error */
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, final Resources.Theme paramTheme)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: iload_3
    //   4: invokespecial 94	android/widget/Spinner:<init>	(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   7: aload_0
    //   8: new 96	android/graphics/Rect
    //   11: dup
    //   12: invokespecial 98	android/graphics/Rect:<init>	()V
    //   15: putfield 100	androidx/appcompat/widget/AppCompatSpinner:mTempRect	Landroid/graphics/Rect;
    //   18: aload_1
    //   19: aload_2
    //   20: getstatic 105	androidx/appcompat/R$styleable:Spinner	[I
    //   23: iload_3
    //   24: iconst_0
    //   25: invokestatic 111	androidx/appcompat/widget/TintTypedArray:obtainStyledAttributes	(Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroidx/appcompat/widget/TintTypedArray;
    //   28: astore 11
    //   30: aload_0
    //   31: new 113	androidx/appcompat/widget/AppCompatBackgroundHelper
    //   34: dup
    //   35: aload_0
    //   36: invokespecial 116	androidx/appcompat/widget/AppCompatBackgroundHelper:<init>	(Landroid/view/View;)V
    //   39: putfield 118	androidx/appcompat/widget/AppCompatSpinner:mBackgroundTintHelper	Landroidx/appcompat/widget/AppCompatBackgroundHelper;
    //   42: aload 5
    //   44: ifnull +20 -> 64
    //   47: aload_0
    //   48: new 120	androidx/appcompat/view/ContextThemeWrapper
    //   51: dup
    //   52: aload_1
    //   53: aload 5
    //   55: invokespecial 123	androidx/appcompat/view/ContextThemeWrapper:<init>	(Landroid/content/Context;Landroid/content/res/Resources$Theme;)V
    //   58: putfield 125	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   61: goto +41 -> 102
    //   64: aload 11
    //   66: getstatic 128	androidx/appcompat/R$styleable:Spinner_popupTheme	I
    //   69: iconst_0
    //   70: invokevirtual 132	androidx/appcompat/widget/TintTypedArray:getResourceId	(II)I
    //   73: istore 6
    //   75: iload 6
    //   77: ifeq +20 -> 97
    //   80: aload_0
    //   81: new 120	androidx/appcompat/view/ContextThemeWrapper
    //   84: dup
    //   85: aload_1
    //   86: iload 6
    //   88: invokespecial 134	androidx/appcompat/view/ContextThemeWrapper:<init>	(Landroid/content/Context;I)V
    //   91: putfield 125	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   94: goto +8 -> 102
    //   97: aload_0
    //   98: aload_1
    //   99: putfield 125	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   102: iload 4
    //   104: istore 7
    //   106: iload 4
    //   108: iconst_m1
    //   109: if_icmpne +138 -> 247
    //   112: getstatic 68	androidx/appcompat/widget/AppCompatSpinner:ATTRS_ANDROID_SPINNERMODE	[I
    //   115: astore 5
    //   117: aload_1
    //   118: aload_2
    //   119: aload 5
    //   121: iload_3
    //   122: iconst_0
    //   123: invokevirtual 139	android/content/Context:obtainStyledAttributes	(Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;
    //   126: astore 10
    //   128: aload 10
    //   130: astore 5
    //   132: aload 5
    //   134: astore 9
    //   136: aload 10
    //   138: iconst_0
    //   139: invokevirtual 145	android/content/res/TypedArray:hasValue	(I)Z
    //   142: istore 8
    //   144: iload 4
    //   146: istore 6
    //   148: iload 8
    //   150: ifeq +16 -> 166
    //   153: aload 5
    //   155: astore 9
    //   157: aload 10
    //   159: iconst_0
    //   160: iconst_0
    //   161: invokevirtual 148	android/content/res/TypedArray:getInt	(II)I
    //   164: istore 6
    //   166: iload 6
    //   168: istore 7
    //   170: aload 10
    //   172: ifnull +75 -> 247
    //   175: iload 6
    //   177: istore 4
    //   179: aload 5
    //   181: invokevirtual 151	android/content/res/TypedArray:recycle	()V
    //   184: iload 4
    //   186: istore 7
    //   188: goto +59 -> 247
    //   191: astore 10
    //   193: goto +15 -> 208
    //   196: astore_1
    //   197: aconst_null
    //   198: astore 9
    //   200: goto +35 -> 235
    //   203: astore 10
    //   205: aconst_null
    //   206: astore 5
    //   208: aload 5
    //   210: astore 9
    //   212: ldc 48
    //   214: ldc -103
    //   216: aload 10
    //   218: invokestatic 159	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   221: pop
    //   222: iload 4
    //   224: istore 7
    //   226: aload 5
    //   228: ifnull +19 -> 247
    //   231: goto -52 -> 179
    //   234: astore_1
    //   235: aload 9
    //   237: ifnull +8 -> 245
    //   240: aload 9
    //   242: invokevirtual 151	android/content/res/TypedArray:recycle	()V
    //   245: aload_1
    //   246: athrow
    //   247: iload 7
    //   249: ifeq +112 -> 361
    //   252: iload 7
    //   254: iconst_1
    //   255: if_icmpeq +6 -> 261
    //   258: goto +132 -> 390
    //   261: new 18	androidx/appcompat/widget/AppCompatSpinner$DropdownPopup
    //   264: dup
    //   265: aload_0
    //   266: aload_0
    //   267: getfield 125	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   270: aload_2
    //   271: iload_3
    //   272: invokespecial 162	androidx/appcompat/widget/AppCompatSpinner$DropdownPopup:<init>	(Landroidx/appcompat/widget/AppCompatSpinner;Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   275: astore 5
    //   277: aload_0
    //   278: getfield 125	androidx/appcompat/widget/AppCompatSpinner:mPopupContext	Landroid/content/Context;
    //   281: aload_2
    //   282: getstatic 105	androidx/appcompat/R$styleable:Spinner	[I
    //   285: iload_3
    //   286: iconst_0
    //   287: invokestatic 111	androidx/appcompat/widget/TintTypedArray:obtainStyledAttributes	(Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroidx/appcompat/widget/TintTypedArray;
    //   290: astore 9
    //   292: aload_0
    //   293: aload 9
    //   295: getstatic 165	androidx/appcompat/R$styleable:Spinner_android_dropDownWidth	I
    //   298: bipush -2
    //   300: invokevirtual 168	androidx/appcompat/widget/TintTypedArray:getLayoutDimension	(II)I
    //   303: putfield 170	androidx/appcompat/widget/AppCompatSpinner:mDropDownWidth	I
    //   306: aload 5
    //   308: aload 9
    //   310: getstatic 173	androidx/appcompat/R$styleable:Spinner_android_popupBackground	I
    //   313: invokevirtual 177	androidx/appcompat/widget/TintTypedArray:getDrawable	(I)Landroid/graphics/drawable/Drawable;
    //   316: invokevirtual 183	androidx/appcompat/widget/ListPopupWindow:setBackgroundDrawable	(Landroid/graphics/drawable/Drawable;)V
    //   319: aload 5
    //   321: aload 11
    //   323: getstatic 186	androidx/appcompat/R$styleable:Spinner_android_prompt	I
    //   326: invokevirtual 190	androidx/appcompat/widget/TintTypedArray:getString	(I)Ljava/lang/String;
    //   329: invokevirtual 194	androidx/appcompat/widget/AppCompatSpinner$DropdownPopup:setPromptText	(Ljava/lang/CharSequence;)V
    //   332: aload 9
    //   334: invokevirtual 195	androidx/appcompat/widget/TintTypedArray:recycle	()V
    //   337: aload_0
    //   338: aload 5
    //   340: putfield 197	androidx/appcompat/widget/AppCompatSpinner:mPopup	Landroidx/appcompat/widget/AppCompatSpinner$SpinnerPopup;
    //   343: aload_0
    //   344: new 8	androidx/appcompat/widget/AppCompatSpinner$1
    //   347: dup
    //   348: aload_0
    //   349: aload_0
    //   350: aload 5
    //   352: invokespecial 200	androidx/appcompat/widget/AppCompatSpinner$1:<init>	(Landroidx/appcompat/widget/AppCompatSpinner;Landroid/view/View;Landroidx/appcompat/widget/AppCompatSpinner$DropdownPopup;)V
    //   355: putfield 202	androidx/appcompat/widget/AppCompatSpinner:mForwardingListener	Landroidx/appcompat/widget/ForwardingListener;
    //   358: goto +32 -> 390
    //   361: aload_0
    //   362: new 12	androidx/appcompat/widget/AppCompatSpinner$DialogPopup
    //   365: dup
    //   366: aload_0
    //   367: invokespecial 205	androidx/appcompat/widget/AppCompatSpinner$DialogPopup:<init>	(Landroidx/appcompat/widget/AppCompatSpinner;)V
    //   370: putfield 197	androidx/appcompat/widget/AppCompatSpinner:mPopup	Landroidx/appcompat/widget/AppCompatSpinner$SpinnerPopup;
    //   373: aload_0
    //   374: getfield 197	androidx/appcompat/widget/AppCompatSpinner:mPopup	Landroidx/appcompat/widget/AppCompatSpinner$SpinnerPopup;
    //   377: aload 11
    //   379: getstatic 186	androidx/appcompat/R$styleable:Spinner_android_prompt	I
    //   382: invokevirtual 190	androidx/appcompat/widget/TintTypedArray:getString	(I)Ljava/lang/String;
    //   385: invokeinterface 206 2 0
    //   390: aload 11
    //   392: getstatic 209	androidx/appcompat/R$styleable:Spinner_android_entries	I
    //   395: invokevirtual 213	androidx/appcompat/widget/TintTypedArray:getTextArray	(I)[Ljava/lang/CharSequence;
    //   398: astore 5
    //   400: aload 5
    //   402: ifnull +28 -> 430
    //   405: new 215	android/widget/ArrayAdapter
    //   408: dup
    //   409: aload_1
    //   410: ldc -40
    //   412: aload 5
    //   414: invokespecial 219	android/widget/ArrayAdapter:<init>	(Landroid/content/Context;I[Ljava/lang/Object;)V
    //   417: astore_1
    //   418: aload_1
    //   419: getstatic 224	androidx/appcompat/R$layout:support_simple_spinner_dropdown_item	I
    //   422: invokevirtual 228	android/widget/ArrayAdapter:setDropDownViewResource	(I)V
    //   425: aload_0
    //   426: aload_1
    //   427: invokevirtual 232	androidx/appcompat/widget/AppCompatSpinner:setAdapter	(Landroid/widget/SpinnerAdapter;)V
    //   430: aload 11
    //   432: invokevirtual 195	androidx/appcompat/widget/TintTypedArray:recycle	()V
    //   435: aload_0
    //   436: iconst_1
    //   437: putfield 234	androidx/appcompat/widget/AppCompatSpinner:mPopupSet	Z
    //   440: aload_0
    //   441: getfield 236	androidx/appcompat/widget/AppCompatSpinner:mTempAdapter	Landroid/widget/SpinnerAdapter;
    //   444: astore_1
    //   445: aload_1
    //   446: ifnull +13 -> 459
    //   449: aload_0
    //   450: aload_1
    //   451: invokevirtual 232	androidx/appcompat/widget/AppCompatSpinner:setAdapter	(Landroid/widget/SpinnerAdapter;)V
    //   454: aload_0
    //   455: aconst_null
    //   456: putfield 236	androidx/appcompat/widget/AppCompatSpinner:mTempAdapter	Landroid/widget/SpinnerAdapter;
    //   459: aload_0
    //   460: getfield 118	androidx/appcompat/widget/AppCompatSpinner:mBackgroundTintHelper	Landroidx/appcompat/widget/AppCompatBackgroundHelper;
    //   463: aload_2
    //   464: iload_3
    //   465: invokevirtual 240	androidx/appcompat/widget/AppCompatBackgroundHelper:loadFromAttributes	(Landroid/util/AttributeSet;I)V
    //   468: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	469	0	this	AppCompatSpinner
    //   0	469	1	paramContext	Context
    //   0	469	2	paramAttributeSet	AttributeSet
    //   0	469	3	paramInt1	int
    //   0	469	4	paramInt2	int
    //   0	469	5	paramTheme	Resources.Theme
    //   73	103	6	i	int
    //   104	152	7	j	int
    //   142	7	8	bool	boolean
    //   134	199	9	localObject	Object
    //   126	45	10	localTypedArray	android.content.res.TypedArray
    //   191	1	10	localException1	Exception
    //   203	14	10	localException2	Exception
    //   28	403	11	localTintTypedArray	TintTypedArray
    // Exception table:
    //   from	to	target	type
    //   136	144	191	java/lang/Exception
    //   157	166	191	java/lang/Exception
    //   117	128	196	java/lang/Throwable
    //   117	128	203	java/lang/Exception
    //   136	144	234	java/lang/Throwable
    //   157	166	234	java/lang/Throwable
    //   212	222	234	java/lang/Throwable
  }
  
  int compatMeasureContentWidth(SpinnerAdapter paramSpinnerAdapter, Drawable paramDrawable)
  {
    int k = 0;
    if (paramSpinnerAdapter == null) {
      return 0;
    }
    int i1 = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
    int i2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
    int i = Math.max(0, getSelectedItemPosition());
    int i3 = Math.min(paramSpinnerAdapter.getCount(), i + 15);
    i = Math.max(0, i - (15 - (i3 - i)));
    Object localObject = null;
    int j = 0;
    while (i < i3)
    {
      int n = paramSpinnerAdapter.getItemViewType(i);
      int m = k;
      if (n != k)
      {
        localObject = null;
        m = n;
      }
      View localView = paramSpinnerAdapter.getView(i, (View)localObject, this);
      localObject = localView;
      if (localView.getLayoutParams() == null) {
        localView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
      }
      localView.measure(i1, i2);
      j = Math.max(j, localView.getMeasuredWidth());
      i += 1;
      k = m;
    }
    if (paramDrawable != null)
    {
      paramDrawable.getPadding(mTempRect);
      return j + (mTempRect.left + mTempRect.right);
    }
    return j;
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.applySupportBackgroundTint();
    }
  }
  
  public int getDropDownHorizontalOffset()
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null) {
      return localSpinnerPopup.getHorizontalOffset();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownHorizontalOffset();
    }
    return 0;
  }
  
  public int getDropDownVerticalOffset()
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null) {
      return localSpinnerPopup.getVerticalOffset();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownVerticalOffset();
    }
    return 0;
  }
  
  public int getDropDownWidth()
  {
    if (mPopup != null) {
      return mDropDownWidth;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getDropDownWidth();
    }
    return 0;
  }
  
  final SpinnerPopup getInternalPopup()
  {
    return mPopup;
  }
  
  public Drawable getPopupBackground()
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null) {
      return localSpinnerPopup.getBackground();
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return super.getPopupBackground();
    }
    return null;
  }
  
  public Context getPopupContext()
  {
    return mPopupContext;
  }
  
  public CharSequence getPrompt()
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null) {
      return localSpinnerPopup.getHintText();
    }
    return super.getPrompt();
  }
  
  public ColorStateList getSupportBackgroundTintList()
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      return localAppCompatBackgroundHelper.getSupportBackgroundTintList();
    }
    return null;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode()
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      return localAppCompatBackgroundHelper.getSupportBackgroundTintMode();
    }
    return null;
  }
  
  public int getTextAlignment()
  {
    throw new Error("Unresolved compilation error: Method <androidx.appcompat.widget.AppCompatSpinner: int getTextAlignment()> does not exist!");
  }
  
  public int getTextDirection()
  {
    throw new Error("Unresolved compilation error: Method <androidx.appcompat.widget.AppCompatSpinner: int getTextDirection()> does not exist!");
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    SpinnerPopup localSpinnerPopup = mPopup;
    if ((localSpinnerPopup != null) && (localSpinnerPopup.isShowing())) {
      mPopup.dismiss();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((mPopup != null) && (View.MeasureSpec.getMode(paramInt1) == Integer.MIN_VALUE)) {
      setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(paramInt1)), getMeasuredHeight());
    }
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    if (mShowDropdown)
    {
      paramParcelable = getViewTreeObserver();
      if (paramParcelable != null) {
        paramParcelable.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            if (!getInternalPopup().isShowing()) {
              showPopup();
            }
            ViewTreeObserver localViewTreeObserver = getViewTreeObserver();
            if (localViewTreeObserver != null)
            {
              if (Build.VERSION.SDK_INT >= 16)
              {
                localViewTreeObserver.removeOnGlobalLayoutListener(this);
                return;
              }
              localViewTreeObserver.removeGlobalOnLayoutListener(this);
            }
          }
        });
      }
    }
  }
  
  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    SpinnerPopup localSpinnerPopup = mPopup;
    boolean bool;
    if ((localSpinnerPopup != null) && (localSpinnerPopup.isShowing())) {
      bool = true;
    } else {
      bool = false;
    }
    mShowDropdown = bool;
    return localSavedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    ForwardingListener localForwardingListener = mForwardingListener;
    if ((localForwardingListener != null) && (localForwardingListener.onTouch(this, paramMotionEvent))) {
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public boolean performClick()
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null)
    {
      if (!localSpinnerPopup.isShowing()) {
        showPopup();
      }
      return true;
    }
    return super.performClick();
  }
  
  public void setAdapter(SpinnerAdapter paramSpinnerAdapter)
  {
    if (!mPopupSet)
    {
      mTempAdapter = paramSpinnerAdapter;
      return;
    }
    super.setAdapter(paramSpinnerAdapter);
    if (mPopup != null)
    {
      Context localContext2 = mPopupContext;
      Context localContext1 = localContext2;
      if (localContext2 == null) {
        localContext1 = getContext();
      }
      mPopup.setAdapter(new DropDownAdapter(paramSpinnerAdapter, localContext1.getTheme()));
    }
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable)
  {
    super.setBackgroundDrawable(paramDrawable);
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.onSetBackgroundDrawable(paramDrawable);
    }
  }
  
  public void setBackgroundResource(int paramInt)
  {
    super.setBackgroundResource(paramInt);
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.onSetBackgroundResource(paramInt);
    }
  }
  
  public void setDropDownHorizontalOffset(int paramInt)
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null)
    {
      localSpinnerPopup.setHorizontalOriginalOffset(paramInt);
      mPopup.setHorizontalOffset(paramInt);
      return;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownHorizontalOffset(paramInt);
    }
  }
  
  public void setDropDownVerticalOffset(int paramInt)
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null)
    {
      localSpinnerPopup.setVerticalOffset(paramInt);
      return;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownVerticalOffset(paramInt);
    }
  }
  
  public void setDropDownWidth(int paramInt)
  {
    if (mPopup != null)
    {
      mDropDownWidth = paramInt;
      return;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownWidth(paramInt);
    }
  }
  
  public void setPopupBackgroundDrawable(Drawable paramDrawable)
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null)
    {
      localSpinnerPopup.setBackgroundDrawable(paramDrawable);
      return;
    }
    if (Build.VERSION.SDK_INT >= 16) {
      super.setPopupBackgroundDrawable(paramDrawable);
    }
  }
  
  public void setPopupBackgroundResource(int paramInt)
  {
    setPopupBackgroundDrawable(AppCompatResources.getDrawable(getPopupContext(), paramInt));
  }
  
  public void setPrompt(CharSequence paramCharSequence)
  {
    SpinnerPopup localSpinnerPopup = mPopup;
    if (localSpinnerPopup != null)
    {
      localSpinnerPopup.setPromptText(paramCharSequence);
      return;
    }
    super.setPrompt(paramCharSequence);
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList)
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.setSupportBackgroundTintList(paramColorStateList);
    }
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode)
  {
    AppCompatBackgroundHelper localAppCompatBackgroundHelper = mBackgroundTintHelper;
    if (localAppCompatBackgroundHelper != null) {
      localAppCompatBackgroundHelper.setSupportBackgroundTintMode(paramMode);
    }
  }
  
  void showPopup()
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      mPopup.show(getTextDirection(), getTextAlignment());
      return;
    }
    mPopup.show(-1, -1);
  }
  
  class DialogPopup
    implements AppCompatSpinner.SpinnerPopup, DialogInterface.OnClickListener
  {
    private ListAdapter mListAdapter;
    AlertDialog mPopup;
    private CharSequence mPrompt;
    
    DialogPopup() {}
    
    public void dismiss()
    {
      AlertDialog localAlertDialog = mPopup;
      if (localAlertDialog != null)
      {
        localAlertDialog.dismiss();
        mPopup = null;
      }
    }
    
    public Drawable getBackground()
    {
      return null;
    }
    
    public CharSequence getHintText()
    {
      return mPrompt;
    }
    
    public int getHorizontalOffset()
    {
      return 0;
    }
    
    public int getHorizontalOriginalOffset()
    {
      return 0;
    }
    
    public int getVerticalOffset()
    {
      return 0;
    }
    
    public boolean isShowing()
    {
      AlertDialog localAlertDialog = mPopup;
      if (localAlertDialog != null) {
        return localAlertDialog.isShowing();
      }
      return false;
    }
    
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      setSelection(paramInt);
      if (getOnItemClickListener() != null) {
        performItemClick(null, paramInt, mListAdapter.getItemId(paramInt));
      }
      dismiss();
    }
    
    public void setAdapter(ListAdapter paramListAdapter)
    {
      mListAdapter = paramListAdapter;
    }
    
    public void setBackgroundDrawable(Drawable paramDrawable)
    {
      Log.e("AppCompatSpinner", "Cannot set popup background for MODE_DIALOG, ignoring");
    }
    
    public void setHorizontalOffset(int paramInt)
    {
      Log.e("AppCompatSpinner", "Cannot set horizontal offset for MODE_DIALOG, ignoring");
    }
    
    public void setHorizontalOriginalOffset(int paramInt)
    {
      Log.e("AppCompatSpinner", "Cannot set horizontal (original) offset for MODE_DIALOG, ignoring");
    }
    
    public void setPromptText(CharSequence paramCharSequence)
    {
      mPrompt = paramCharSequence;
    }
    
    public void setVerticalOffset(int paramInt)
    {
      Log.e("AppCompatSpinner", "Cannot set vertical offset for MODE_DIALOG, ignoring");
    }
    
    public void show(int paramInt1, int paramInt2)
    {
      if (mListAdapter == null) {
        return;
      }
      Object localObject = new AlertDialog.Builder(getPopupContext());
      CharSequence localCharSequence = mPrompt;
      if (localCharSequence != null) {
        ((AlertDialog.Builder)localObject).setTitle(localCharSequence);
      }
      mPopup = ((AlertDialog.Builder)localObject).setSingleChoiceItems(mListAdapter, getSelectedItemPosition(), this).create();
      localObject = mPopup.getListView();
      if (Build.VERSION.SDK_INT >= 17)
      {
        ((ListView)localObject).setTextDirection(paramInt1);
        ((ListView)localObject).setTextAlignment(paramInt2);
      }
      mPopup.show();
    }
  }
  
  private static class DropDownAdapter
    implements ListAdapter, SpinnerAdapter
  {
    private SpinnerAdapter mAdapter;
    private ListAdapter mListAdapter;
    
    public DropDownAdapter(SpinnerAdapter paramSpinnerAdapter, Resources.Theme paramTheme)
    {
      mAdapter = paramSpinnerAdapter;
      if ((paramSpinnerAdapter instanceof ListAdapter)) {
        mListAdapter = ((ListAdapter)paramSpinnerAdapter);
      }
      if (paramTheme != null) {
        if ((Build.VERSION.SDK_INT >= 23) && ((paramSpinnerAdapter instanceof android.widget.ThemedSpinnerAdapter)))
        {
          paramSpinnerAdapter = (android.widget.ThemedSpinnerAdapter)paramSpinnerAdapter;
          if (paramSpinnerAdapter.getDropDownViewTheme() != paramTheme) {
            paramSpinnerAdapter.setDropDownViewTheme(paramTheme);
          }
        }
        else if ((paramSpinnerAdapter instanceof ThemedSpinnerAdapter))
        {
          paramSpinnerAdapter = (ThemedSpinnerAdapter)paramSpinnerAdapter;
          if (paramSpinnerAdapter.getDropDownViewTheme() == null) {
            paramSpinnerAdapter.setDropDownViewTheme(paramTheme);
          }
        }
      }
    }
    
    public boolean areAllItemsEnabled()
    {
      ListAdapter localListAdapter = mListAdapter;
      if (localListAdapter != null) {
        return localListAdapter.areAllItemsEnabled();
      }
      return true;
    }
    
    public int getCount()
    {
      SpinnerAdapter localSpinnerAdapter = mAdapter;
      if (localSpinnerAdapter == null) {
        return 0;
      }
      return localSpinnerAdapter.getCount();
    }
    
    public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      SpinnerAdapter localSpinnerAdapter = mAdapter;
      if (localSpinnerAdapter == null) {
        return null;
      }
      return localSpinnerAdapter.getDropDownView(paramInt, paramView, paramViewGroup);
    }
    
    public Object getItem(int paramInt)
    {
      SpinnerAdapter localSpinnerAdapter = mAdapter;
      if (localSpinnerAdapter == null) {
        return null;
      }
      return localSpinnerAdapter.getItem(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      SpinnerAdapter localSpinnerAdapter = mAdapter;
      if (localSpinnerAdapter == null) {
        return -1L;
      }
      return localSpinnerAdapter.getItemId(paramInt);
    }
    
    public int getItemViewType(int paramInt)
    {
      return 0;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      return getDropDownView(paramInt, paramView, paramViewGroup);
    }
    
    public int getViewTypeCount()
    {
      return 1;
    }
    
    public boolean hasStableIds()
    {
      SpinnerAdapter localSpinnerAdapter = mAdapter;
      return (localSpinnerAdapter != null) && (localSpinnerAdapter.hasStableIds());
    }
    
    public boolean isEmpty()
    {
      return getCount() == 0;
    }
    
    public boolean isEnabled(int paramInt)
    {
      ListAdapter localListAdapter = mListAdapter;
      if (localListAdapter != null) {
        return localListAdapter.isEnabled(paramInt);
      }
      return true;
    }
    
    public void registerDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      SpinnerAdapter localSpinnerAdapter = mAdapter;
      if (localSpinnerAdapter != null) {
        localSpinnerAdapter.registerDataSetObserver(paramDataSetObserver);
      }
    }
    
    public void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
    {
      SpinnerAdapter localSpinnerAdapter = mAdapter;
      if (localSpinnerAdapter != null) {
        localSpinnerAdapter.unregisterDataSetObserver(paramDataSetObserver);
      }
    }
  }
  
  class DropdownPopup
    extends ListPopupWindow
    implements AppCompatSpinner.SpinnerPopup
  {
    ListAdapter mAdapter;
    private CharSequence mHintText;
    private int mOriginalHorizontalOffset;
    private final Rect mVisibleRect = new Rect();
    
    public DropdownPopup(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
      setAnchorView(AppCompatSpinner.this);
      setModal(true);
      setPromptPosition(0);
      setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
        {
          setSelection(paramAnonymousInt);
          if (getOnItemClickListener() != null) {
            performItemClick(paramAnonymousView, paramAnonymousInt, mAdapter.getItemId(paramAnonymousInt));
          }
          dismiss();
        }
      });
    }
    
    void computeContentWidth()
    {
      Object localObject = getBackground();
      int i = 0;
      if (localObject != null)
      {
        ((Drawable)localObject).getPadding(mTempRect);
        if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
          i = mTempRect.right;
        } else {
          i = -mTempRect.left;
        }
      }
      else
      {
        localObject = mTempRect;
        mTempRect.right = 0;
        left = 0;
      }
      int n = getPaddingLeft();
      int i1 = getPaddingRight();
      int i2 = getWidth();
      if (mDropDownWidth == -2)
      {
        int k = compatMeasureContentWidth((SpinnerAdapter)mAdapter, getBackground());
        int j = k;
        int m = getContext().getResources().getDisplayMetrics().widthPixels - mTempRect.left - mTempRect.right;
        if (k > m) {
          j = m;
        }
        setContentWidth(Math.max(j, i2 - n - i1));
      }
      else if (mDropDownWidth == -1)
      {
        setContentWidth(i2 - n - i1);
      }
      else
      {
        setContentWidth(mDropDownWidth);
      }
      if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
        i += i2 - i1 - getWidth() - getHorizontalOriginalOffset();
      } else {
        i += n + getHorizontalOriginalOffset();
      }
      setHorizontalOffset(i);
    }
    
    public CharSequence getHintText()
    {
      return mHintText;
    }
    
    public int getHorizontalOriginalOffset()
    {
      return mOriginalHorizontalOffset;
    }
    
    boolean isVisibleToUser(View paramView)
    {
      return (ViewCompat.isAttachedToWindow(paramView)) && (paramView.getGlobalVisibleRect(mVisibleRect));
    }
    
    public void setAdapter(ListAdapter paramListAdapter)
    {
      super.setAdapter(paramListAdapter);
      mAdapter = paramListAdapter;
    }
    
    public void setHorizontalOriginalOffset(int paramInt)
    {
      mOriginalHorizontalOffset = paramInt;
    }
    
    public void setPromptText(CharSequence paramCharSequence)
    {
      mHintText = paramCharSequence;
    }
    
    public void show(int paramInt1, int paramInt2)
    {
      boolean bool = isShowing();
      computeContentWidth();
      setInputMethodMode(2);
      super.show();
      Object localObject = getListView();
      ((AbsListView)localObject).setChoiceMode(1);
      if (Build.VERSION.SDK_INT >= 17)
      {
        ((ListView)localObject).setTextDirection(paramInt1);
        ((ListView)localObject).setTextAlignment(paramInt2);
      }
      setSelection(getSelectedItemPosition());
      if (bool) {
        return;
      }
      localObject = getViewTreeObserver();
      if (localObject != null)
      {
        final ViewTreeObserver.OnGlobalLayoutListener local2 = new ViewTreeObserver.OnGlobalLayoutListener()
        {
          public void onGlobalLayout()
          {
            AppCompatSpinner.DropdownPopup localDropdownPopup = AppCompatSpinner.DropdownPopup.this;
            if (!localDropdownPopup.isVisibleToUser(this$0))
            {
              dismiss();
              return;
            }
            computeContentWidth();
            AppCompatSpinner.DropdownPopup.this.show();
          }
        };
        ((ViewTreeObserver)localObject).addOnGlobalLayoutListener(local2);
        setOnDismissListener(new PopupWindow.OnDismissListener()
        {
          public void onDismiss()
          {
            ViewTreeObserver localViewTreeObserver = getViewTreeObserver();
            if (localViewTreeObserver != null) {
              localViewTreeObserver.removeGlobalOnLayoutListener(local2);
            }
          }
        });
      }
    }
  }
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public AppCompatSpinner.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new AppCompatSpinner.SavedState(paramAnonymousParcel);
      }
      
      public AppCompatSpinner.SavedState[] newArray(int paramAnonymousInt)
      {
        return new AppCompatSpinner.SavedState[paramAnonymousInt];
      }
    };
    boolean mShowDropdown;
    
    SavedState(Parcel paramParcel)
    {
      super();
      boolean bool;
      if (paramParcel.readByte() != 0) {
        bool = true;
      } else {
        bool = false;
      }
      mShowDropdown = bool;
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeByte((byte)mShowDropdown);
    }
  }
  
  static abstract interface SpinnerPopup
  {
    public abstract void dismiss();
    
    public abstract Drawable getBackground();
    
    public abstract CharSequence getHintText();
    
    public abstract int getHorizontalOffset();
    
    public abstract int getHorizontalOriginalOffset();
    
    public abstract int getVerticalOffset();
    
    public abstract boolean isShowing();
    
    public abstract void setAdapter(ListAdapter paramListAdapter);
    
    public abstract void setBackgroundDrawable(Drawable paramDrawable);
    
    public abstract void setHorizontalOffset(int paramInt);
    
    public abstract void setHorizontalOriginalOffset(int paramInt);
    
    public abstract void setPromptText(CharSequence paramCharSequence);
    
    public abstract void setVerticalOffset(int paramInt);
    
    public abstract void show(int paramInt1, int paramInt2);
  }
}
