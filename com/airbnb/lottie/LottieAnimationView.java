package com.airbnb.lottie;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.View;
import android.view.View.BaseSavedState;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LottieAnimationView
  extends AppCompatImageView
{
  private static final LottieListener<Throwable> DEFAULT_FAILURE_LISTENER = new LottieListener()
  {
    public void onResult(Throwable paramAnonymousThrowable)
    {
      if (Utils.isNetworkException(paramAnonymousThrowable))
      {
        Logger.warning("Unable to load composition.", paramAnonymousThrowable);
        return;
      }
      throw new IllegalStateException("Unable to parse composition", paramAnonymousThrowable);
    }
  };
  private static final String LOGTAG = LottieAnimationView.class.getSimpleName();
  private String animationName;
  private int animationResId;
  private boolean autoPlay = false;
  private int buildDrawingCacheDepth = 0;
  private boolean cacheComposition = true;
  private LottieComposition composition;
  private LottieTask<LottieComposition> compositionTask;
  private LottieListener<Throwable> failureListener;
  private int fallbackResource = 0;
  private boolean isInitialized;
  private final LottieListener<LottieComposition> loadedListener = new LottieListener()
  {
    public void onResult(LottieComposition paramAnonymousLottieComposition)
    {
      setComposition(paramAnonymousLottieComposition);
    }
  };
  private final LottieDrawable lottieDrawable = new LottieDrawable();
  private Set<LottieOnCompositionLoadedListener> lottieOnCompositionLoadedListeners = new HashSet();
  private RenderMode renderMode = RenderMode.AUTOMATIC;
  private boolean wasAnimatingWhenDetached = false;
  private boolean wasAnimatingWhenNotShown = false;
  private final LottieListener<Throwable> wrappedFailureListener = new LottieListener()
  {
    public void onResult(Throwable paramAnonymousThrowable)
    {
      Object localObject;
      if (fallbackResource != 0)
      {
        localObject = LottieAnimationView.this;
        ((LottieAnimationView)localObject).setImageResource(fallbackResource);
      }
      if (failureListener == null) {
        localObject = LottieAnimationView.DEFAULT_FAILURE_LISTENER;
      } else {
        localObject = failureListener;
      }
      ((LottieListener)localObject).onResult(paramAnonymousThrowable);
    }
  };
  
  public LottieAnimationView(Context paramContext)
  {
    super(paramContext);
    init(null);
  }
  
  public LottieAnimationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
  }
  
  public LottieAnimationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
  }
  
  private void cancelLoaderTask()
  {
    LottieTask localLottieTask = compositionTask;
    if (localLottieTask != null)
    {
      localLottieTask.removeListener(loadedListener);
      compositionTask.removeFailureListener(wrappedFailureListener);
    }
  }
  
  private void clearComposition()
  {
    composition = null;
    lottieDrawable.clearComposition();
  }
  
  private void enableOrDisableHardwareLayer()
  {
    int k = 5.$SwitchMap$com$airbnb$lottie$RenderMode[renderMode.ordinal()];
    int j = 2;
    int i = j;
    if (k != 1)
    {
      if ((k == 2) || (k != 3)) {}
      do
      {
        i = 1;
        break;
        LottieComposition localLottieComposition = composition;
        i = 0;
        if ((localLottieComposition == null) || (!localLottieComposition.hasDashPattern()) || (Build.VERSION.SDK_INT >= 28))
        {
          localLottieComposition = composition;
          if (((localLottieComposition == null) || (localLottieComposition.getMaskAndMatteCount() <= 4)) && (Build.VERSION.SDK_INT >= 21)) {
            i = 1;
          }
        }
      } while (i == 0);
      i = j;
    }
    if (i != getLayerType()) {
      setLayerType(i, null);
    }
  }
  
  private void init(AttributeSet paramAttributeSet)
  {
    paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.LottieAnimationView);
    boolean bool2 = isInEditMode();
    boolean bool1 = false;
    int i;
    Object localObject1;
    if (!bool2)
    {
      cacheComposition = paramAttributeSet.getBoolean(R.styleable.LottieAnimationView_lottie_cacheComposition, true);
      bool2 = paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_rawRes);
      boolean bool3 = paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_fileName);
      boolean bool4 = paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_url);
      if ((bool2) && (bool3)) {
        throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use only one at once.");
      }
      if (bool2)
      {
        i = paramAttributeSet.getResourceId(R.styleable.LottieAnimationView_lottie_rawRes, 0);
        if (i != 0) {
          setAnimation(i);
        }
      }
      else if (bool3)
      {
        localObject1 = paramAttributeSet.getString(R.styleable.LottieAnimationView_lottie_fileName);
        if (localObject1 != null) {
          setAnimation((String)localObject1);
        }
      }
      else if (bool4)
      {
        localObject1 = paramAttributeSet.getString(R.styleable.LottieAnimationView_lottie_url);
        if (localObject1 != null) {
          setAnimationFromUrl((String)localObject1);
        }
      }
      setFallbackResource(paramAttributeSet.getResourceId(R.styleable.LottieAnimationView_lottie_fallbackRes, 0));
    }
    if (paramAttributeSet.getBoolean(R.styleable.LottieAnimationView_lottie_autoPlay, false))
    {
      wasAnimatingWhenDetached = true;
      autoPlay = true;
    }
    if (paramAttributeSet.getBoolean(R.styleable.LottieAnimationView_lottie_loop, false)) {
      lottieDrawable.setRepeatCount(-1);
    }
    if (paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_repeatMode)) {
      setRepeatMode(paramAttributeSet.getInt(R.styleable.LottieAnimationView_lottie_repeatMode, 1));
    }
    if (paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_repeatCount)) {
      setRepeatCount(paramAttributeSet.getInt(R.styleable.LottieAnimationView_lottie_repeatCount, -1));
    }
    if (paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_speed)) {
      setSpeed(paramAttributeSet.getFloat(R.styleable.LottieAnimationView_lottie_speed, 1.0F));
    }
    setImageAssetsFolder(paramAttributeSet.getString(R.styleable.LottieAnimationView_lottie_imageAssetsFolder));
    setProgress(paramAttributeSet.getFloat(R.styleable.LottieAnimationView_lottie_progress, 0.0F));
    enableMergePathsForKitKatAndAbove(paramAttributeSet.getBoolean(R.styleable.LottieAnimationView_lottie_enableMergePathsForKitKatAndAbove, false));
    if (paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_colorFilter))
    {
      Object localObject2 = new SimpleColorFilter(paramAttributeSet.getColor(R.styleable.LottieAnimationView_lottie_colorFilter, 0));
      localObject1 = new KeyPath(new String[] { "**" });
      localObject2 = new LottieValueCallback(localObject2);
      addValueCallback((KeyPath)localObject1, LottieProperty.COLOR_FILTER, (LottieValueCallback)localObject2);
    }
    if (paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_scale)) {
      lottieDrawable.setScale(paramAttributeSet.getFloat(R.styleable.LottieAnimationView_lottie_scale, 1.0F));
    }
    if (paramAttributeSet.hasValue(R.styleable.LottieAnimationView_lottie_renderMode))
    {
      int j = paramAttributeSet.getInt(R.styleable.LottieAnimationView_lottie_renderMode, RenderMode.AUTOMATIC.ordinal());
      i = j;
      if (j >= RenderMode.values().length) {
        i = RenderMode.AUTOMATIC.ordinal();
      }
      setRenderMode(RenderMode.values()[i]);
    }
    if (getScaleType() != null) {
      lottieDrawable.setScaleType(getScaleType());
    }
    paramAttributeSet.recycle();
    paramAttributeSet = lottieDrawable;
    if (Utils.getAnimationScale(getContext()) != 0.0F) {
      bool1 = true;
    }
    paramAttributeSet.setSystemAnimationsAreEnabled(Boolean.valueOf(bool1));
    enableOrDisableHardwareLayer();
    isInitialized = true;
  }
  
  private void setCompositionTask(LottieTask paramLottieTask)
  {
    clearComposition();
    cancelLoaderTask();
    compositionTask = paramLottieTask.addListener(loadedListener).addFailureListener(wrappedFailureListener);
  }
  
  public void addAnimatorListener(Animator.AnimatorListener paramAnimatorListener)
  {
    lottieDrawable.addAnimatorListener(paramAnimatorListener);
  }
  
  public void addAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener paramAnimatorUpdateListener)
  {
    lottieDrawable.addAnimatorUpdateListener(paramAnimatorUpdateListener);
  }
  
  public boolean addLottieOnCompositionLoadedListener(LottieOnCompositionLoadedListener paramLottieOnCompositionLoadedListener)
  {
    LottieComposition localLottieComposition = composition;
    if (localLottieComposition != null) {
      paramLottieOnCompositionLoadedListener.onCompositionLoaded(localLottieComposition);
    }
    return lottieOnCompositionLoadedListeners.add(paramLottieOnCompositionLoadedListener);
  }
  
  public void addValueCallback(KeyPath paramKeyPath, Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    lottieDrawable.addValueCallback(paramKeyPath, paramObject, paramLottieValueCallback);
  }
  
  public void addValueCallback(KeyPath paramKeyPath, Object paramObject, final SimpleLottieValueCallback paramSimpleLottieValueCallback)
  {
    lottieDrawable.addValueCallback(paramKeyPath, paramObject, new LottieValueCallback()
    {
      public Object getValue(LottieFrameInfo paramAnonymousLottieFrameInfo)
      {
        return paramSimpleLottieValueCallback.getValue(paramAnonymousLottieFrameInfo);
      }
    });
  }
  
  public void buildDrawingCache(boolean paramBoolean)
  {
    Way.beginSection("buildDrawingCache");
    buildDrawingCacheDepth += 1;
    super.buildDrawingCache(paramBoolean);
    if ((buildDrawingCacheDepth == 1) && (getWidth() > 0) && (getHeight() > 0) && (getLayerType() == 1) && (getDrawingCache(paramBoolean) == null)) {
      setRenderMode(RenderMode.HARDWARE);
    }
    buildDrawingCacheDepth -= 1;
    Way.endSection("buildDrawingCache");
  }
  
  public void cancelAnimation()
  {
    wasAnimatingWhenNotShown = false;
    lottieDrawable.cancelAnimation();
    enableOrDisableHardwareLayer();
  }
  
  public void disableExtraScaleModeInFitXY()
  {
    lottieDrawable.disableExtraScaleModeInFitXY();
  }
  
  public void enableMergePathsForKitKatAndAbove(boolean paramBoolean)
  {
    lottieDrawable.enableMergePathsForKitKatAndAbove(paramBoolean);
  }
  
  public LottieComposition getComposition()
  {
    return composition;
  }
  
  public long getDuration()
  {
    LottieComposition localLottieComposition = composition;
    if (localLottieComposition != null) {
      return localLottieComposition.getDuration();
    }
    return 0L;
  }
  
  public int getFrame()
  {
    return lottieDrawable.getFrame();
  }
  
  public String getImageAssetsFolder()
  {
    return lottieDrawable.getImageAssetsFolder();
  }
  
  public float getMaxFrame()
  {
    return lottieDrawable.getMaxFrame();
  }
  
  public float getMinFrame()
  {
    return lottieDrawable.getMinFrame();
  }
  
  public PerformanceTracker getPerformanceTracker()
  {
    return lottieDrawable.getPerformanceTracker();
  }
  
  public float getProgress()
  {
    return lottieDrawable.getProgress();
  }
  
  public int getRepeatCount()
  {
    return lottieDrawable.getRepeatCount();
  }
  
  public int getRepeatMode()
  {
    return lottieDrawable.getRepeatMode();
  }
  
  public float getScale()
  {
    return lottieDrawable.getScale();
  }
  
  public float getSpeed()
  {
    return lottieDrawable.getSpeed();
  }
  
  public boolean hasMasks()
  {
    return lottieDrawable.hasMasks();
  }
  
  public boolean hasMatte()
  {
    return lottieDrawable.hasMatte();
  }
  
  public void invalidateDrawable(Drawable paramDrawable)
  {
    Drawable localDrawable = getDrawable();
    LottieDrawable localLottieDrawable = lottieDrawable;
    if (localDrawable == localLottieDrawable)
    {
      super.invalidateDrawable(localLottieDrawable);
      return;
    }
    super.invalidateDrawable(paramDrawable);
  }
  
  public boolean isAnimating()
  {
    return lottieDrawable.isAnimating();
  }
  
  public boolean isMergePathsEnabledForKitKatAndAbove()
  {
    return lottieDrawable.isMergePathsEnabledForKitKatAndAbove();
  }
  
  public void loop(boolean paramBoolean)
  {
    LottieDrawable localLottieDrawable = lottieDrawable;
    int i;
    if (paramBoolean) {
      i = -1;
    } else {
      i = 0;
    }
    localLottieDrawable.setRepeatCount(i);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if ((autoPlay) || (wasAnimatingWhenDetached))
    {
      playAnimation();
      autoPlay = false;
      wasAnimatingWhenDetached = false;
    }
    if (Build.VERSION.SDK_INT < 23) {
      onVisibilityChanged(this, getVisibility());
    }
  }
  
  protected void onDetachedFromWindow()
  {
    if (isAnimating())
    {
      cancelAnimation();
      wasAnimatingWhenDetached = true;
    }
    super.onDetachedFromWindow();
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    animationName = animationName;
    if (!TextUtils.isEmpty(animationName)) {
      setAnimation(animationName);
    }
    animationResId = animationResId;
    int i = animationResId;
    if (i != 0) {
      setAnimation(i);
    }
    setProgress(progress);
    if (isAnimating) {
      playAnimation();
    }
    lottieDrawable.setImagesAssetsFolder(imageAssetsFolder);
    setRepeatMode(repeatMode);
    setRepeatCount(repeatCount);
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    animationName = animationName;
    animationResId = animationResId;
    progress = lottieDrawable.getProgress();
    boolean bool;
    if ((!lottieDrawable.isAnimating()) && ((ViewCompat.isAttachedToWindow(this)) || (!wasAnimatingWhenDetached))) {
      bool = false;
    } else {
      bool = true;
    }
    isAnimating = bool;
    imageAssetsFolder = lottieDrawable.getImageAssetsFolder();
    repeatMode = lottieDrawable.getRepeatMode();
    repeatCount = lottieDrawable.getRepeatCount();
    return localSavedState;
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    if (!isInitialized) {
      return;
    }
    if (isShown())
    {
      if (wasAnimatingWhenNotShown)
      {
        resumeAnimation();
        wasAnimatingWhenNotShown = false;
      }
    }
    else if (isAnimating())
    {
      pauseAnimation();
      wasAnimatingWhenNotShown = true;
    }
  }
  
  public void pauseAnimation()
  {
    autoPlay = false;
    wasAnimatingWhenDetached = false;
    wasAnimatingWhenNotShown = false;
    lottieDrawable.pauseAnimation();
    enableOrDisableHardwareLayer();
  }
  
  public void playAnimation()
  {
    if (isShown())
    {
      lottieDrawable.playAnimation();
      enableOrDisableHardwareLayer();
      return;
    }
    wasAnimatingWhenNotShown = true;
  }
  
  public void removeAllAnimatorListeners()
  {
    lottieDrawable.removeAllAnimatorListeners();
  }
  
  public void removeAllLottieOnCompositionLoadedListener()
  {
    lottieOnCompositionLoadedListeners.clear();
  }
  
  public void removeAllUpdateListeners()
  {
    lottieDrawable.removeAllUpdateListeners();
  }
  
  public void removeAnimatorListener(Animator.AnimatorListener paramAnimatorListener)
  {
    lottieDrawable.removeAnimatorListener(paramAnimatorListener);
  }
  
  public boolean removeLottieOnCompositionLoadedListener(LottieOnCompositionLoadedListener paramLottieOnCompositionLoadedListener)
  {
    return lottieOnCompositionLoadedListeners.remove(paramLottieOnCompositionLoadedListener);
  }
  
  public void removeUpdateListener(ValueAnimator.AnimatorUpdateListener paramAnimatorUpdateListener)
  {
    lottieDrawable.removeAnimatorUpdateListener(paramAnimatorUpdateListener);
  }
  
  public List resolveKeyPath(KeyPath paramKeyPath)
  {
    return lottieDrawable.resolveKeyPath(paramKeyPath);
  }
  
  public void resumeAnimation()
  {
    if (isShown())
    {
      lottieDrawable.resumeAnimation();
      enableOrDisableHardwareLayer();
      return;
    }
    wasAnimatingWhenNotShown = true;
  }
  
  public void reverseAnimationSpeed()
  {
    lottieDrawable.reverseAnimationSpeed();
  }
  
  public void setAnimation(int paramInt)
  {
    animationResId = paramInt;
    animationName = null;
    LottieTask localLottieTask;
    if (cacheComposition) {
      localLottieTask = LottieCompositionFactory.fromRawRes(getContext(), paramInt);
    } else {
      localLottieTask = LottieCompositionFactory.fromRawRes(getContext(), paramInt, null);
    }
    setCompositionTask(localLottieTask);
  }
  
  public void setAnimation(InputStream paramInputStream, String paramString)
  {
    setCompositionTask(LottieCompositionFactory.fromJsonInputStream(paramInputStream, paramString));
  }
  
  public void setAnimation(String paramString)
  {
    animationName = paramString;
    animationResId = 0;
    if (cacheComposition) {
      paramString = LottieCompositionFactory.fromAsset(getContext(), paramString);
    } else {
      paramString = LottieCompositionFactory.fromAsset(getContext(), paramString, null);
    }
    setCompositionTask(paramString);
  }
  
  public void setAnimationFromJson(String paramString)
  {
    setAnimationFromJson(paramString, null);
  }
  
  public void setAnimationFromJson(String paramString1, String paramString2)
  {
    setAnimation(new ByteArrayInputStream(paramString1.getBytes()), paramString2);
  }
  
  public void setAnimationFromUrl(String paramString)
  {
    if (cacheComposition) {
      paramString = LottieCompositionFactory.fromUrl(getContext(), paramString);
    } else {
      paramString = LottieCompositionFactory.fromUrl(getContext(), paramString, null);
    }
    setCompositionTask(paramString);
  }
  
  public void setApplyingOpacityToLayersEnabled(boolean paramBoolean)
  {
    lottieDrawable.setApplyingOpacityToLayersEnabled(paramBoolean);
  }
  
  public void setCacheComposition(boolean paramBoolean)
  {
    cacheComposition = paramBoolean;
  }
  
  public void setComposition(LottieComposition paramLottieComposition)
  {
    if (Way.isAndroid)
    {
      localObject = LOGTAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Set Composition \n");
      localStringBuilder.append(paramLottieComposition);
      Log.v((String)localObject, localStringBuilder.toString());
    }
    lottieDrawable.setCallback(this);
    composition = paramLottieComposition;
    boolean bool = lottieDrawable.setComposition(paramLottieComposition);
    enableOrDisableHardwareLayer();
    if ((getDrawable() == lottieDrawable) && (!bool)) {
      return;
    }
    setImageDrawable(null);
    setImageDrawable(lottieDrawable);
    onVisibilityChanged(this, getVisibility());
    requestLayout();
    Object localObject = lottieOnCompositionLoadedListeners.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((LottieOnCompositionLoadedListener)((Iterator)localObject).next()).onCompositionLoaded(paramLottieComposition);
    }
  }
  
  public void setFailureListener(LottieListener paramLottieListener)
  {
    failureListener = paramLottieListener;
  }
  
  public void setFallbackResource(int paramInt)
  {
    fallbackResource = paramInt;
  }
  
  public void setFontAssetDelegate(FontAssetDelegate paramFontAssetDelegate)
  {
    lottieDrawable.setFontAssetDelegate(paramFontAssetDelegate);
  }
  
  public void setFrame(int paramInt)
  {
    lottieDrawable.setFrame(paramInt);
  }
  
  public void setImageAssetDelegate(ImageAssetDelegate paramImageAssetDelegate)
  {
    lottieDrawable.setImageAssetDelegate(paramImageAssetDelegate);
  }
  
  public void setImageAssetsFolder(String paramString)
  {
    lottieDrawable.setImagesAssetsFolder(paramString);
  }
  
  public void setImageBitmap(Bitmap paramBitmap)
  {
    cancelLoaderTask();
    super.setImageBitmap(paramBitmap);
  }
  
  public void setImageDrawable(Drawable paramDrawable)
  {
    cancelLoaderTask();
    super.setImageDrawable(paramDrawable);
  }
  
  public void setImageResource(int paramInt)
  {
    cancelLoaderTask();
    super.setImageResource(paramInt);
  }
  
  public void setMaxFrame(int paramInt)
  {
    lottieDrawable.setMaxFrame(paramInt);
  }
  
  public void setMaxFrame(String paramString)
  {
    lottieDrawable.setMaxFrame(paramString);
  }
  
  public void setMaxProgress(float paramFloat)
  {
    lottieDrawable.setMaxProgress(paramFloat);
  }
  
  public void setMinAndMaxFrame(int paramInt1, int paramInt2)
  {
    lottieDrawable.setMinAndMaxFrame(paramInt1, paramInt2);
  }
  
  public void setMinAndMaxFrame(String paramString)
  {
    lottieDrawable.setMinAndMaxFrame(paramString);
  }
  
  public void setMinAndMaxFrame(String paramString1, String paramString2, boolean paramBoolean)
  {
    lottieDrawable.setMinAndMaxFrame(paramString1, paramString2, paramBoolean);
  }
  
  public void setMinAndMaxProgress(float paramFloat1, float paramFloat2)
  {
    lottieDrawable.setMinAndMaxProgress(paramFloat1, paramFloat2);
  }
  
  public void setMinFrame(int paramInt)
  {
    lottieDrawable.setMinFrame(paramInt);
  }
  
  public void setMinFrame(String paramString)
  {
    lottieDrawable.setMinFrame(paramString);
  }
  
  public void setMinProgress(float paramFloat)
  {
    lottieDrawable.setMinProgress(paramFloat);
  }
  
  public void setPerformanceTrackingEnabled(boolean paramBoolean)
  {
    lottieDrawable.setPerformanceTrackingEnabled(paramBoolean);
  }
  
  public void setProgress(float paramFloat)
  {
    lottieDrawable.setProgress(paramFloat);
  }
  
  public void setRenderMode(RenderMode paramRenderMode)
  {
    renderMode = paramRenderMode;
    enableOrDisableHardwareLayer();
  }
  
  public void setRepeatCount(int paramInt)
  {
    lottieDrawable.setRepeatCount(paramInt);
  }
  
  public void setRepeatMode(int paramInt)
  {
    lottieDrawable.setRepeatMode(paramInt);
  }
  
  public void setSafeMode(boolean paramBoolean)
  {
    lottieDrawable.setSafeMode(paramBoolean);
  }
  
  public void setScale(float paramFloat)
  {
    lottieDrawable.setScale(paramFloat);
    if (getDrawable() == lottieDrawable)
    {
      setImageDrawable(null);
      setImageDrawable(lottieDrawable);
    }
  }
  
  public void setScaleType(ImageView.ScaleType paramScaleType)
  {
    super.setScaleType(paramScaleType);
    LottieDrawable localLottieDrawable = lottieDrawable;
    if (localLottieDrawable != null) {
      localLottieDrawable.setScaleType(paramScaleType);
    }
  }
  
  public void setSpeed(float paramFloat)
  {
    lottieDrawable.setSpeed(paramFloat);
  }
  
  public void setTextDelegate(TextDelegate paramTextDelegate)
  {
    lottieDrawable.setTextDelegate(paramTextDelegate);
  }
  
  public Bitmap updateBitmap(String paramString, Bitmap paramBitmap)
  {
    return lottieDrawable.updateBitmap(paramString, paramBitmap);
  }
  
  private static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public LottieAnimationView.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new LottieAnimationView.SavedState(paramAnonymousParcel, null);
      }
      
      public LottieAnimationView.SavedState[] newArray(int paramAnonymousInt)
      {
        return new LottieAnimationView.SavedState[paramAnonymousInt];
      }
    };
    String animationName;
    int animationResId;
    String imageAssetsFolder;
    boolean isAnimating;
    float progress;
    int repeatCount;
    int repeatMode;
    
    private SavedState(Parcel paramParcel)
    {
      super();
      animationName = paramParcel.readString();
      progress = paramParcel.readFloat();
      int i = paramParcel.readInt();
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      isAnimating = bool;
      imageAssetsFolder = paramParcel.readString();
      repeatMode = paramParcel.readInt();
      repeatCount = paramParcel.readInt();
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
    }
  }
}
