package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public class ContentGroup
  implements DrawingContent, PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElement
{
  private final List<Content> contents;
  private final boolean hidden;
  private final LottieDrawable lottieDrawable;
  private final Matrix matrix = new Matrix();
  private final String name;
  private Paint offScreenPaint = new LPaint();
  private RectF offScreenRectF = new RectF();
  private final Path path = new Path();
  private List<PathContent> pathContents;
  private final RectF rect = new RectF();
  private TransformKeyframeAnimation transformAnimation;
  
  public ContentGroup(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer, ShapeGroup paramShapeGroup)
  {
    this(paramLottieDrawable, paramBaseLayer, paramShapeGroup.getName(), paramShapeGroup.isHidden(), contentsFromModels(paramLottieDrawable, paramBaseLayer, paramShapeGroup.getItems()), findTransform(paramShapeGroup.getItems()));
  }
  
  ContentGroup(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer, String paramString, boolean paramBoolean, List paramList, AnimatableTransform paramAnimatableTransform)
  {
    name = paramString;
    lottieDrawable = paramLottieDrawable;
    hidden = paramBoolean;
    contents = paramList;
    if (paramAnimatableTransform != null)
    {
      transformAnimation = paramAnimatableTransform.createAnimation();
      transformAnimation.addAnimationsToLayer(paramBaseLayer);
      transformAnimation.addListener(this);
    }
    paramLottieDrawable = new ArrayList();
    int i = paramList.size() - 1;
    while (i >= 0)
    {
      paramBaseLayer = (Content)paramList.get(i);
      if ((paramBaseLayer instanceof GreedyContent)) {
        paramLottieDrawable.add((GreedyContent)paramBaseLayer);
      }
      i -= 1;
    }
    i = paramLottieDrawable.size() - 1;
    while (i >= 0)
    {
      ((GreedyContent)paramLottieDrawable.get(i)).absorbContent(paramList.listIterator(paramList.size()));
      i -= 1;
    }
  }
  
  private static List contentsFromModels(LottieDrawable paramLottieDrawable, BaseLayer paramBaseLayer, List paramList)
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    int i = 0;
    while (i < paramList.size())
    {
      Content localContent = ((ContentModel)paramList.get(i)).toContent(paramLottieDrawable, paramBaseLayer);
      if (localContent != null) {
        localArrayList.add(localContent);
      }
      i += 1;
    }
    return localArrayList;
  }
  
  static AnimatableTransform findTransform(List paramList)
  {
    int i = 0;
    while (i < paramList.size())
    {
      ContentModel localContentModel = (ContentModel)paramList.get(i);
      if ((localContentModel instanceof AnimatableTransform)) {
        return (AnimatableTransform)localContentModel;
      }
      i += 1;
    }
    return null;
  }
  
  private boolean hasTwoOrMoreDrawableContent()
  {
    int j = 0;
    int i;
    for (int k = 0; j < contents.size(); k = i)
    {
      i = k;
      if ((contents.get(j) instanceof DrawingContent))
      {
        k += 1;
        i = k;
        if (k >= 2) {
          return true;
        }
      }
      j += 1;
    }
    return false;
  }
  
  public void addValueCallback(Object paramObject, LottieValueCallback paramLottieValueCallback)
  {
    TransformKeyframeAnimation localTransformKeyframeAnimation = transformAnimation;
    if (localTransformKeyframeAnimation != null) {
      localTransformKeyframeAnimation.applyValueCallback(paramObject, paramLottieValueCallback);
    }
  }
  
  public void draw(Canvas paramCanvas, Matrix paramMatrix, int paramInt)
  {
    if (hidden) {
      return;
    }
    matrix.set(paramMatrix);
    paramMatrix = transformAnimation;
    int i = paramInt;
    if (paramMatrix != null)
    {
      matrix.preConcat(paramMatrix.getMatrix());
      if (transformAnimation.getOpacity() == null) {
        i = 100;
      } else {
        i = ((Integer)transformAnimation.getOpacity().getValue()).intValue();
      }
      i = (int)(i / 100.0F * paramInt / 255.0F * 255.0F);
    }
    if ((lottieDrawable.isApplyingOpacityToLayersEnabled()) && (hasTwoOrMoreDrawableContent()) && (i != 255)) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (paramInt != 0)
    {
      offScreenRectF.set(0.0F, 0.0F, 0.0F, 0.0F);
      getBounds(offScreenRectF, matrix, true);
      offScreenPaint.setAlpha(i);
      Utils.saveLayerCompat(paramCanvas, offScreenRectF, offScreenPaint);
    }
    if (paramInt != 0) {
      i = 255;
    }
    int j = contents.size() - 1;
    while (j >= 0)
    {
      paramMatrix = contents.get(j);
      if ((paramMatrix instanceof DrawingContent)) {
        ((DrawingContent)paramMatrix).draw(paramCanvas, matrix, i);
      }
      j -= 1;
    }
    if (paramInt != 0) {
      paramCanvas.restore();
    }
  }
  
  public void getBounds(RectF paramRectF, Matrix paramMatrix, boolean paramBoolean)
  {
    matrix.set(paramMatrix);
    paramMatrix = transformAnimation;
    if (paramMatrix != null) {
      matrix.preConcat(paramMatrix.getMatrix());
    }
    rect.set(0.0F, 0.0F, 0.0F, 0.0F);
    int i = contents.size() - 1;
    while (i >= 0)
    {
      paramMatrix = (Content)contents.get(i);
      if ((paramMatrix instanceof DrawingContent))
      {
        ((DrawingContent)paramMatrix).getBounds(rect, matrix, paramBoolean);
        paramRectF.union(rect);
      }
      i -= 1;
    }
  }
  
  public String getName()
  {
    return name;
  }
  
  public Path getPath()
  {
    matrix.reset();
    Object localObject = transformAnimation;
    if (localObject != null) {
      matrix.set(((TransformKeyframeAnimation)localObject).getMatrix());
    }
    path.reset();
    if (hidden) {
      return path;
    }
    int i = contents.size() - 1;
    while (i >= 0)
    {
      localObject = (Content)contents.get(i);
      if ((localObject instanceof PathContent)) {
        path.addPath(((PathContent)localObject).getPath(), matrix);
      }
      i -= 1;
    }
    return path;
  }
  
  List getPathList()
  {
    if (pathContents == null)
    {
      pathContents = new ArrayList();
      int i = 0;
      while (i < contents.size())
      {
        Content localContent = (Content)contents.get(i);
        if ((localContent instanceof PathContent)) {
          pathContents.add((PathContent)localContent);
        }
        i += 1;
      }
    }
    return pathContents;
  }
  
  Matrix getTransformationMatrix()
  {
    TransformKeyframeAnimation localTransformKeyframeAnimation = transformAnimation;
    if (localTransformKeyframeAnimation != null) {
      return localTransformKeyframeAnimation.getMatrix();
    }
    matrix.reset();
    return matrix;
  }
  
  public void onValueChanged()
  {
    lottieDrawable.invalidateSelf();
  }
  
  public void resolveKeyPath(KeyPath paramKeyPath1, int paramInt, List paramList, KeyPath paramKeyPath2)
  {
    if (!paramKeyPath1.matches(getName(), paramInt)) {
      return;
    }
    KeyPath localKeyPath1 = paramKeyPath2;
    if (!"__container".equals(getName()))
    {
      KeyPath localKeyPath2 = paramKeyPath2.addKey(getName());
      paramKeyPath2 = localKeyPath2;
      localKeyPath1 = paramKeyPath2;
      if (paramKeyPath1.fullyResolvesTo(getName(), paramInt))
      {
        paramList.add(localKeyPath2.resolve(this));
        localKeyPath1 = paramKeyPath2;
      }
    }
    if (paramKeyPath1.propagateToChildren(getName(), paramInt))
    {
      int j = paramKeyPath1.incrementDepthBy(getName(), paramInt);
      int i = 0;
      while (i < contents.size())
      {
        paramKeyPath2 = (Content)contents.get(i);
        if ((paramKeyPath2 instanceof KeyPathElement)) {
          ((KeyPathElement)paramKeyPath2).resolveKeyPath(paramKeyPath1, paramInt + j, paramList, localKeyPath1);
        }
        i += 1;
      }
    }
  }
  
  public void setContents(List paramList1, List paramList2)
  {
    paramList2 = new ArrayList(paramList1.size() + contents.size());
    paramList2.addAll(paramList1);
    int i = contents.size() - 1;
    while (i >= 0)
    {
      paramList1 = (Content)contents.get(i);
      paramList1.setContents(paramList2, contents.subList(0, i));
      paramList2.add(paramList1);
      i -= 1;
    }
  }
}
