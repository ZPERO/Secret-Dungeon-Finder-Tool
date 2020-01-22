package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import androidx.appcompat.R.styleable;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicCompat;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.animation.AnimationUtils;

public final class CollapsingTextHelper
{
  private static final boolean DEBUG_DRAW = false;
  private static final Paint DEBUG_DRAW_PAINT;
  private static final boolean USE_SCALING_TEXTURE;
  private boolean boundsChanged;
  private final Rect collapsedBounds;
  private float collapsedDrawX;
  private float collapsedDrawY;
  private int collapsedShadowColor;
  private float collapsedShadowDx;
  private float collapsedShadowDy;
  private float collapsedShadowRadius;
  private ColorStateList collapsedTextColor;
  private int collapsedTextGravity = 16;
  private float collapsedTextSize = 15.0F;
  private Typeface collapsedTypeface;
  private final RectF currentBounds;
  private float currentDrawX;
  private float currentDrawY;
  private float currentTextSize;
  private Typeface currentTypeface;
  private boolean drawTitle;
  private final Rect expandedBounds;
  private float expandedDrawX;
  private float expandedDrawY;
  private float expandedFraction;
  private int expandedShadowColor;
  private float expandedShadowDx;
  private float expandedShadowDy;
  private float expandedShadowRadius;
  private ColorStateList expandedTextColor;
  private int expandedTextGravity = 16;
  private float expandedTextSize = 15.0F;
  private Bitmap expandedTitleTexture;
  private Typeface expandedTypeface;
  private boolean isRtl;
  private TimeInterpolator positionInterpolator;
  private float scale;
  private int[] state;
  private CharSequence text;
  private final TextPaint textPaint;
  private TimeInterpolator textSizeInterpolator;
  private CharSequence textToDraw;
  private float textureAscent;
  private float textureDescent;
  private Paint texturePaint;
  private final TextPaint tmpPaint;
  private boolean useTexture;
  private final View view;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT < 18) {
      bool = true;
    } else {
      bool = false;
    }
    USE_SCALING_TEXTURE = bool;
    DEBUG_DRAW_PAINT = null;
    Paint localPaint = DEBUG_DRAW_PAINT;
    if (localPaint != null)
    {
      localPaint.setAntiAlias(true);
      DEBUG_DRAW_PAINT.setColor(-65281);
    }
  }
  
  public CollapsingTextHelper(View paramView)
  {
    view = paramView;
    textPaint = new TextPaint(129);
    tmpPaint = new TextPaint(textPaint);
    collapsedBounds = new Rect();
    expandedBounds = new Rect();
    currentBounds = new RectF();
  }
  
  private static int blendColors(int paramInt1, int paramInt2, float paramFloat)
  {
    float f1 = 1.0F - paramFloat;
    float f2 = Color.alpha(paramInt1);
    float f3 = Color.alpha(paramInt2);
    float f4 = Color.red(paramInt1);
    float f5 = Color.red(paramInt2);
    float f6 = Color.green(paramInt1);
    float f7 = Color.green(paramInt2);
    float f8 = Color.blue(paramInt1);
    float f9 = Color.blue(paramInt2);
    return Color.argb((int)(f2 * f1 + f3 * paramFloat), (int)(f4 * f1 + f5 * paramFloat), (int)(f6 * f1 + f7 * paramFloat), (int)(f8 * f1 + f9 * paramFloat));
  }
  
  private void calculateBaseOffsets()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
  }
  
  private void calculateCurrentOffsets()
  {
    calculateOffsets(expandedFraction);
  }
  
  private boolean calculateIsRtl(CharSequence paramCharSequence)
  {
    int j = ViewCompat.getLayoutDirection(view);
    int i = 1;
    if (j != 1) {
      i = 0;
    }
    TextDirectionHeuristicCompat localTextDirectionHeuristicCompat;
    if (i != 0) {
      localTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
    } else {
      localTextDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    }
    return localTextDirectionHeuristicCompat.isRtl(paramCharSequence, 0, paramCharSequence.length());
  }
  
  private void calculateOffsets(float paramFloat)
  {
    interpolateBounds(paramFloat);
    currentDrawX = lerp(expandedDrawX, collapsedDrawX, paramFloat, positionInterpolator);
    currentDrawY = lerp(expandedDrawY, collapsedDrawY, paramFloat, positionInterpolator);
    setInterpolatedTextSize(lerp(expandedTextSize, collapsedTextSize, paramFloat, textSizeInterpolator));
    if (collapsedTextColor != expandedTextColor) {
      textPaint.setColor(blendColors(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), paramFloat));
    } else {
      textPaint.setColor(getCurrentCollapsedTextColor());
    }
    textPaint.setShadowLayer(lerp(expandedShadowRadius, collapsedShadowRadius, paramFloat, null), lerp(expandedShadowDx, collapsedShadowDx, paramFloat, null), lerp(expandedShadowDy, collapsedShadowDy, paramFloat, null), blendColors(expandedShadowColor, collapsedShadowColor, paramFloat));
    ViewCompat.postInvalidateOnAnimation(view);
  }
  
  private void calculateUsingTextSize(float paramFloat)
  {
    if (text == null) {
      return;
    }
    float f2 = collapsedBounds.width();
    float f3 = expandedBounds.width();
    boolean bool2 = isClose(paramFloat, collapsedTextSize);
    boolean bool1 = true;
    float f1;
    Object localObject;
    Typeface localTypeface;
    int i;
    if (bool2)
    {
      f1 = collapsedTextSize;
      scale = 1.0F;
      localObject = currentTypeface;
      localTypeface = collapsedTypeface;
      if (localObject != localTypeface)
      {
        currentTypeface = localTypeface;
        i = 1;
      }
      else
      {
        i = 0;
      }
      paramFloat = f2;
    }
    else
    {
      f1 = expandedTextSize;
      localObject = currentTypeface;
      localTypeface = expandedTypeface;
      if (localObject != localTypeface)
      {
        currentTypeface = localTypeface;
        i = 1;
      }
      else
      {
        i = 0;
      }
      if (isClose(paramFloat, expandedTextSize)) {
        scale = 1.0F;
      } else {
        scale = (paramFloat / expandedTextSize);
      }
      paramFloat = collapsedTextSize / expandedTextSize;
      if (f3 * paramFloat > f2) {
        paramFloat = Math.min(f2 / paramFloat, f3);
      } else {
        paramFloat = f3;
      }
    }
    int j = i;
    if (paramFloat > 0.0F)
    {
      if ((currentTextSize == f1) && (!boundsChanged) && (i == 0)) {
        i = 0;
      } else {
        i = 1;
      }
      currentTextSize = f1;
      boundsChanged = false;
      j = i;
    }
    if ((textToDraw == null) || (j != 0))
    {
      textPaint.setTextSize(currentTextSize);
      textPaint.setTypeface(currentTypeface);
      localObject = textPaint;
      if (scale == 1.0F) {
        bool1 = false;
      }
      ((Paint)localObject).setLinearText(bool1);
      localObject = TextUtils.ellipsize(text, textPaint, paramFloat, TextUtils.TruncateAt.END);
      if (!TextUtils.equals((CharSequence)localObject, textToDraw))
      {
        textToDraw = ((CharSequence)localObject);
        isRtl = calculateIsRtl(textToDraw);
      }
    }
  }
  
  private void clearTexture()
  {
    Bitmap localBitmap = expandedTitleTexture;
    if (localBitmap != null)
    {
      localBitmap.recycle();
      expandedTitleTexture = null;
    }
  }
  
  private void ensureExpandedTexture()
  {
    if ((expandedTitleTexture == null) && (!expandedBounds.isEmpty()))
    {
      if (TextUtils.isEmpty(textToDraw)) {
        return;
      }
      calculateOffsets(0.0F);
      textureAscent = textPaint.ascent();
      textureDescent = textPaint.descent();
      Object localObject = textPaint;
      CharSequence localCharSequence = textToDraw;
      int i = Math.round(((Paint)localObject).measureText(localCharSequence, 0, localCharSequence.length()));
      int j = Math.round(textureDescent - textureAscent);
      if (i > 0)
      {
        if (j <= 0) {
          return;
        }
        expandedTitleTexture = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        localObject = new Canvas(expandedTitleTexture);
        localCharSequence = textToDraw;
        ((Canvas)localObject).drawText(localCharSequence, 0, localCharSequence.length(), 0.0F, j - textPaint.descent(), textPaint);
        if (texturePaint == null) {
          texturePaint = new Paint(3);
        }
      }
    }
  }
  
  private int getCurrentExpandedTextColor()
  {
    int[] arrayOfInt = state;
    if (arrayOfInt != null) {
      return expandedTextColor.getColorForState(arrayOfInt, 0);
    }
    return expandedTextColor.getDefaultColor();
  }
  
  private void getTextPaintCollapsed(TextPaint paramTextPaint)
  {
    paramTextPaint.setTextSize(collapsedTextSize);
    paramTextPaint.setTypeface(collapsedTypeface);
  }
  
  private void interpolateBounds(float paramFloat)
  {
    currentBounds.left = lerp(expandedBounds.left, collapsedBounds.left, paramFloat, positionInterpolator);
    currentBounds.top = lerp(expandedDrawY, collapsedDrawY, paramFloat, positionInterpolator);
    currentBounds.right = lerp(expandedBounds.right, collapsedBounds.right, paramFloat, positionInterpolator);
    currentBounds.bottom = lerp(expandedBounds.bottom, collapsedBounds.bottom, paramFloat, positionInterpolator);
  }
  
  private static boolean isClose(float paramFloat1, float paramFloat2)
  {
    return Math.abs(paramFloat1 - paramFloat2) < 0.001F;
  }
  
  private static float lerp(float paramFloat1, float paramFloat2, float paramFloat3, TimeInterpolator paramTimeInterpolator)
  {
    float f = paramFloat3;
    if (paramTimeInterpolator != null) {
      f = paramTimeInterpolator.getInterpolation(paramFloat3);
    }
    return AnimationUtils.lerp(paramFloat1, paramFloat2, f);
  }
  
  private Typeface readFontFamilyTypeface(int paramInt)
  {
    TypedArray localTypedArray = view.getContext().obtainStyledAttributes(paramInt, new int[] { 16843692 });
    try
    {
      Object localObject = localTypedArray.getString(0);
      if (localObject != null)
      {
        localObject = Typeface.create((String)localObject, 0);
        localTypedArray.recycle();
        return localObject;
      }
      localTypedArray.recycle();
      return null;
    }
    catch (Throwable localThrowable)
    {
      localTypedArray.recycle();
      throw localThrowable;
    }
  }
  
  private static boolean rectEquals(Rect paramRect, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return (left == paramInt1) && (top == paramInt2) && (right == paramInt3) && (bottom == paramInt4);
  }
  
  private void setInterpolatedTextSize(float paramFloat)
  {
    calculateUsingTextSize(paramFloat);
    boolean bool;
    if ((USE_SCALING_TEXTURE) && (scale != 1.0F)) {
      bool = true;
    } else {
      bool = false;
    }
    useTexture = bool;
    if (useTexture) {
      ensureExpandedTexture();
    }
    ViewCompat.postInvalidateOnAnimation(view);
  }
  
  public float calculateCollapsedTextWidth()
  {
    if (text == null) {
      return 0.0F;
    }
    getTextPaintCollapsed(tmpPaint);
    TextPaint localTextPaint = tmpPaint;
    CharSequence localCharSequence = text;
    return localTextPaint.measureText(localCharSequence, 0, localCharSequence.length());
  }
  
  public void draw(Canvas paramCanvas)
  {
    int j = paramCanvas.save();
    if ((textToDraw != null) && (drawTitle))
    {
      float f4 = currentDrawX;
      float f3 = currentDrawY;
      int i;
      if ((useTexture) && (expandedTitleTexture != null)) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        f2 = textureAscent * scale;
      }
      else
      {
        f2 = textPaint.ascent() * scale;
        textPaint.descent();
      }
      float f1 = f3;
      if (i != 0) {
        f1 = f3 + f2;
      }
      float f2 = scale;
      if (f2 != 1.0F) {
        paramCanvas.scale(f2, f2, f4, f1);
      }
      if (i != 0)
      {
        paramCanvas.drawBitmap(expandedTitleTexture, f4, f1, texturePaint);
      }
      else
      {
        CharSequence localCharSequence = textToDraw;
        paramCanvas.drawText(localCharSequence, 0, localCharSequence.length(), f4, f1, textPaint);
      }
    }
    paramCanvas.restoreToCount(j);
  }
  
  public void getCollapsedTextActualBounds(RectF paramRectF)
  {
    boolean bool = calculateIsRtl(text);
    Rect localRect = collapsedBounds;
    float f;
    if (!bool) {
      f = left;
    } else {
      f = right - calculateCollapsedTextWidth();
    }
    left = f;
    top = collapsedBounds.top;
    if (!bool) {
      f = left + calculateCollapsedTextWidth();
    } else {
      f = collapsedBounds.right;
    }
    right = f;
    bottom = (collapsedBounds.top + getCollapsedTextHeight());
  }
  
  public ColorStateList getCollapsedTextColor()
  {
    return collapsedTextColor;
  }
  
  public int getCollapsedTextGravity()
  {
    return collapsedTextGravity;
  }
  
  public float getCollapsedTextHeight()
  {
    getTextPaintCollapsed(tmpPaint);
    return -tmpPaint.ascent();
  }
  
  public float getCollapsedTextSize()
  {
    return collapsedTextSize;
  }
  
  public Typeface getCollapsedTypeface()
  {
    Typeface localTypeface = collapsedTypeface;
    if (localTypeface != null) {
      return localTypeface;
    }
    return Typeface.DEFAULT;
  }
  
  public int getCurrentCollapsedTextColor()
  {
    int[] arrayOfInt = state;
    if (arrayOfInt != null) {
      return collapsedTextColor.getColorForState(arrayOfInt, 0);
    }
    return collapsedTextColor.getDefaultColor();
  }
  
  public ColorStateList getExpandedTextColor()
  {
    return expandedTextColor;
  }
  
  public int getExpandedTextGravity()
  {
    return expandedTextGravity;
  }
  
  public float getExpandedTextSize()
  {
    return expandedTextSize;
  }
  
  public Typeface getExpandedTypeface()
  {
    Typeface localTypeface = expandedTypeface;
    if (localTypeface != null) {
      return localTypeface;
    }
    return Typeface.DEFAULT;
  }
  
  public float getExpansionFraction()
  {
    return expandedFraction;
  }
  
  public CharSequence getText()
  {
    return text;
  }
  
  public final boolean isStateful()
  {
    ColorStateList localColorStateList = collapsedTextColor;
    if ((localColorStateList == null) || (!localColorStateList.isStateful())) {
      localColorStateList = expandedTextColor;
    }
    return (localColorStateList != null) && (localColorStateList.isStateful());
  }
  
  void onBoundsChanged()
  {
    boolean bool;
    if ((collapsedBounds.width() > 0) && (collapsedBounds.height() > 0) && (expandedBounds.width() > 0) && (expandedBounds.height() > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    drawTitle = bool;
  }
  
  public void recalculate()
  {
    if ((view.getHeight() > 0) && (view.getWidth() > 0))
    {
      calculateBaseOffsets();
      calculateCurrentOffsets();
    }
  }
  
  public void setCollapsedBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!rectEquals(collapsedBounds, paramInt1, paramInt2, paramInt3, paramInt4))
    {
      collapsedBounds.set(paramInt1, paramInt2, paramInt3, paramInt4);
      boundsChanged = true;
      onBoundsChanged();
    }
  }
  
  public void setCollapsedTextAppearance(int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(view.getContext(), paramInt, R.styleable.TextAppearance);
    if (localTintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor)) {
      collapsedTextColor = localTintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
    }
    if (localTintTypedArray.hasValue(R.styleable.TextAppearance_android_textSize)) {
      collapsedTextSize = localTintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)collapsedTextSize);
    }
    collapsedShadowColor = localTintTypedArray.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
    collapsedShadowDx = localTintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
    collapsedShadowDy = localTintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
    collapsedShadowRadius = localTintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F);
    localTintTypedArray.recycle();
    if (Build.VERSION.SDK_INT >= 16) {
      collapsedTypeface = readFontFamilyTypeface(paramInt);
    }
    recalculate();
  }
  
  public void setCollapsedTextColor(ColorStateList paramColorStateList)
  {
    if (collapsedTextColor != paramColorStateList)
    {
      collapsedTextColor = paramColorStateList;
      recalculate();
    }
  }
  
  public void setCollapsedTextGravity(int paramInt)
  {
    if (collapsedTextGravity != paramInt)
    {
      collapsedTextGravity = paramInt;
      recalculate();
    }
  }
  
  public void setCollapsedTextSize(float paramFloat)
  {
    if (collapsedTextSize != paramFloat)
    {
      collapsedTextSize = paramFloat;
      recalculate();
    }
  }
  
  public void setCollapsedTypeface(Typeface paramTypeface)
  {
    if (collapsedTypeface != paramTypeface)
    {
      collapsedTypeface = paramTypeface;
      recalculate();
    }
  }
  
  public void setExpandedBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!rectEquals(expandedBounds, paramInt1, paramInt2, paramInt3, paramInt4))
    {
      expandedBounds.set(paramInt1, paramInt2, paramInt3, paramInt4);
      boundsChanged = true;
      onBoundsChanged();
    }
  }
  
  public void setExpandedTextAppearance(int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(view.getContext(), paramInt, R.styleable.TextAppearance);
    if (localTintTypedArray.hasValue(R.styleable.TextAppearance_android_textColor)) {
      expandedTextColor = localTintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
    }
    if (localTintTypedArray.hasValue(R.styleable.TextAppearance_android_textSize)) {
      expandedTextSize = localTintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)expandedTextSize);
    }
    expandedShadowColor = localTintTypedArray.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
    expandedShadowDx = localTintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
    expandedShadowDy = localTintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
    expandedShadowRadius = localTintTypedArray.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F);
    localTintTypedArray.recycle();
    if (Build.VERSION.SDK_INT >= 16) {
      expandedTypeface = readFontFamilyTypeface(paramInt);
    }
    recalculate();
  }
  
  public void setExpandedTextColor(ColorStateList paramColorStateList)
  {
    if (expandedTextColor != paramColorStateList)
    {
      expandedTextColor = paramColorStateList;
      recalculate();
    }
  }
  
  public void setExpandedTextGravity(int paramInt)
  {
    if (expandedTextGravity != paramInt)
    {
      expandedTextGravity = paramInt;
      recalculate();
    }
  }
  
  public void setExpandedTextSize(float paramFloat)
  {
    if (expandedTextSize != paramFloat)
    {
      expandedTextSize = paramFloat;
      recalculate();
    }
  }
  
  public void setExpandedTypeface(Typeface paramTypeface)
  {
    if (expandedTypeface != paramTypeface)
    {
      expandedTypeface = paramTypeface;
      recalculate();
    }
  }
  
  public void setExpansionFraction(float paramFloat)
  {
    paramFloat = MathUtils.clamp(paramFloat, 0.0F, 1.0F);
    if (paramFloat != expandedFraction)
    {
      expandedFraction = paramFloat;
      calculateCurrentOffsets();
    }
  }
  
  public void setPositionInterpolator(TimeInterpolator paramTimeInterpolator)
  {
    positionInterpolator = paramTimeInterpolator;
    recalculate();
  }
  
  public final boolean setState(int[] paramArrayOfInt)
  {
    state = paramArrayOfInt;
    if (isStateful())
    {
      recalculate();
      return true;
    }
    return false;
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    if ((paramCharSequence == null) || (!paramCharSequence.equals(text)))
    {
      text = paramCharSequence;
      textToDraw = null;
      clearTexture();
      recalculate();
    }
  }
  
  public void setTextSizeInterpolator(TimeInterpolator paramTimeInterpolator)
  {
    textSizeInterpolator = paramTimeInterpolator;
    recalculate();
  }
  
  public void setTypefaces(Typeface paramTypeface)
  {
    expandedTypeface = paramTypeface;
    collapsedTypeface = paramTypeface;
    recalculate();
  }
}
