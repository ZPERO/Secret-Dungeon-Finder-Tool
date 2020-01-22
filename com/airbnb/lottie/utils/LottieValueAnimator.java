package com.airbnb.lottie.utils;

import android.animation.ValueAnimator;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.Way;

public class LottieValueAnimator
  extends BaseLottieAnimator
  implements Choreographer.FrameCallback
{
  private LottieComposition composition;
  private float frame = 0.0F;
  private long lastFrameTimeNs = 0L;
  private float maxFrame = 2.14748365E9F;
  private float minFrame = -2.14748365E9F;
  private int repeatCount = 0;
  protected boolean running = false;
  private float speed = 1.0F;
  private boolean speedReversedForRepeatMode = false;
  
  public LottieValueAnimator() {}
  
  private float getFrameDurationNs()
  {
    LottieComposition localLottieComposition = composition;
    if (localLottieComposition == null) {
      return Float.MAX_VALUE;
    }
    return 1.0E9F / localLottieComposition.getFrameRate() / Math.abs(speed);
  }
  
  private boolean isReversed()
  {
    return getSpeed() < 0.0F;
  }
  
  private void verifyFrame()
  {
    if (composition == null) {
      return;
    }
    float f = frame;
    if ((f >= minFrame) && (f <= maxFrame)) {
      return;
    }
    throw new IllegalStateException(String.format("Frame must be [%f,%f]. It is %f", new Object[] { Float.valueOf(minFrame), Float.valueOf(maxFrame), Float.valueOf(frame) }));
  }
  
  public void cancel()
  {
    notifyCancel();
    removeFrameCallback();
  }
  
  public void clearComposition()
  {
    composition = null;
    minFrame = -2.14748365E9F;
    maxFrame = 2.14748365E9F;
  }
  
  public void doFrame(long paramLong)
  {
    postFrameCallback();
    if (composition != null)
    {
      if (!isRunning()) {
        return;
      }
      Way.beginSection("LottieValueAnimator#doFrame");
      long l2 = lastFrameTimeNs;
      long l1 = 0L;
      if (l2 != 0L) {
        l1 = paramLong - l2;
      }
      float f1 = getFrameDurationNs();
      float f2 = (float)l1 / f1;
      float f3 = frame;
      f1 = f2;
      if (isReversed()) {
        f1 = -f2;
      }
      frame = (f3 + f1);
      boolean bool = MiscUtils.contains(frame, getMinFrame(), getMaxFrame());
      frame = MiscUtils.clamp(frame, getMinFrame(), getMaxFrame());
      lastFrameTimeNs = paramLong;
      notifyUpdate();
      if ((bool ^ true)) {
        if ((getRepeatCount() != -1) && (repeatCount >= getRepeatCount()))
        {
          if (speed < 0.0F) {
            f1 = getMinFrame();
          } else {
            f1 = getMaxFrame();
          }
          frame = f1;
          removeFrameCallback();
          notifyEnd(isReversed());
        }
        else
        {
          notifyRepeat();
          repeatCount += 1;
          if (getRepeatMode() == 2)
          {
            speedReversedForRepeatMode ^= true;
            reverseAnimationSpeed();
          }
          else
          {
            if (isReversed()) {
              f1 = getMaxFrame();
            } else {
              f1 = getMinFrame();
            }
            frame = f1;
          }
          lastFrameTimeNs = paramLong;
        }
      }
      verifyFrame();
      Way.endSection("LottieValueAnimator#doFrame");
    }
  }
  
  public void endAnimation()
  {
    removeFrameCallback();
    notifyEnd(isReversed());
  }
  
  public float getAnimatedFraction()
  {
    if (composition == null) {
      return 0.0F;
    }
    float f1;
    float f2;
    if (isReversed())
    {
      f1 = getMaxFrame() - frame;
      f2 = getMaxFrame();
    }
    for (float f3 = getMinFrame();; f3 = getMinFrame())
    {
      return f1 / (f2 - f3);
      f1 = frame - getMinFrame();
      f2 = getMaxFrame();
    }
  }
  
  public Object getAnimatedValue()
  {
    return Float.valueOf(getAnimatedValueAbsolute());
  }
  
  public float getAnimatedValueAbsolute()
  {
    LottieComposition localLottieComposition = composition;
    if (localLottieComposition == null) {
      return 0.0F;
    }
    return (frame - localLottieComposition.getStartFrame()) / (composition.getEndFrame() - composition.getStartFrame());
  }
  
  public long getDuration()
  {
    LottieComposition localLottieComposition = composition;
    if (localLottieComposition == null) {
      return 0L;
    }
    return localLottieComposition.getDuration();
  }
  
  public float getFrame()
  {
    return frame;
  }
  
  public float getMaxFrame()
  {
    LottieComposition localLottieComposition = composition;
    if (localLottieComposition == null) {
      return 0.0F;
    }
    float f2 = maxFrame;
    float f1 = f2;
    if (f2 == 2.14748365E9F) {
      f1 = localLottieComposition.getEndFrame();
    }
    return f1;
  }
  
  public float getMinFrame()
  {
    LottieComposition localLottieComposition = composition;
    if (localLottieComposition == null) {
      return 0.0F;
    }
    float f2 = minFrame;
    float f1 = f2;
    if (f2 == -2.14748365E9F) {
      f1 = localLottieComposition.getStartFrame();
    }
    return f1;
  }
  
  public float getSpeed()
  {
    return speed;
  }
  
  public boolean isRunning()
  {
    return running;
  }
  
  public void pauseAnimation()
  {
    removeFrameCallback();
  }
  
  public void playAnimation()
  {
    running = true;
    notifyStart(isReversed());
    float f;
    if (isReversed()) {
      f = getMaxFrame();
    } else {
      f = getMinFrame();
    }
    setFrame((int)f);
    lastFrameTimeNs = 0L;
    repeatCount = 0;
    postFrameCallback();
  }
  
  protected void postFrameCallback()
  {
    if (isRunning())
    {
      removeFrameCallback(false);
      Choreographer.getInstance().postFrameCallback(this);
    }
  }
  
  protected void removeFrameCallback()
  {
    removeFrameCallback(true);
  }
  
  protected void removeFrameCallback(boolean paramBoolean)
  {
    Choreographer.getInstance().removeFrameCallback(this);
    if (paramBoolean) {
      running = false;
    }
  }
  
  public void resumeAnimation()
  {
    running = true;
    postFrameCallback();
    lastFrameTimeNs = 0L;
    if ((isReversed()) && (getFrame() == getMinFrame()))
    {
      frame = getMaxFrame();
      return;
    }
    if ((!isReversed()) && (getFrame() == getMaxFrame())) {
      frame = getMinFrame();
    }
  }
  
  public void reverseAnimationSpeed()
  {
    setSpeed(-getSpeed());
  }
  
  public void setComposition(LottieComposition paramLottieComposition)
  {
    int i;
    if (composition == null) {
      i = 1;
    } else {
      i = 0;
    }
    composition = paramLottieComposition;
    if (i != 0) {
      setMinAndMaxFrames((int)Math.max(minFrame, paramLottieComposition.getStartFrame()), (int)Math.min(maxFrame, paramLottieComposition.getEndFrame()));
    } else {
      setMinAndMaxFrames((int)paramLottieComposition.getStartFrame(), (int)paramLottieComposition.getEndFrame());
    }
    float f = frame;
    frame = 0.0F;
    setFrame((int)f);
  }
  
  public void setFrame(float paramFloat)
  {
    if (frame == paramFloat) {
      return;
    }
    frame = MiscUtils.clamp(paramFloat, getMinFrame(), getMaxFrame());
    lastFrameTimeNs = 0L;
    notifyUpdate();
  }
  
  public void setMaxFrame(float paramFloat)
  {
    setMinAndMaxFrames(minFrame, paramFloat);
  }
  
  public void setMinAndMaxFrames(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 <= paramFloat2)
    {
      LottieComposition localLottieComposition = composition;
      float f1;
      if (localLottieComposition == null) {
        f1 = -3.4028235E38F;
      } else {
        f1 = localLottieComposition.getStartFrame();
      }
      localLottieComposition = composition;
      float f2;
      if (localLottieComposition == null) {
        f2 = Float.MAX_VALUE;
      } else {
        f2 = localLottieComposition.getEndFrame();
      }
      minFrame = MiscUtils.clamp(paramFloat1, f1, f2);
      maxFrame = MiscUtils.clamp(paramFloat2, f1, f2);
      setFrame((int)MiscUtils.clamp(frame, paramFloat1, paramFloat2));
      return;
    }
    throw new IllegalArgumentException(String.format("minFrame (%s) must be <= maxFrame (%s)", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2) }));
  }
  
  public void setMinFrame(int paramInt)
  {
    setMinAndMaxFrames(paramInt, (int)maxFrame);
  }
  
  public void setRepeatMode(int paramInt)
  {
    super.setRepeatMode(paramInt);
    if ((paramInt != 2) && (speedReversedForRepeatMode))
    {
      speedReversedForRepeatMode = false;
      reverseAnimationSpeed();
    }
  }
  
  public void setSpeed(float paramFloat)
  {
    speed = paramFloat;
  }
}