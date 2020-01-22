package com.airbnb.lottie.model;

import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

public abstract interface KeyPathElement
{
  public abstract void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback);
  
  public abstract void resolveKeyPath(KeyPath paramKeyPath1, int paramInt, List paramList, KeyPath paramKeyPath2);
}
