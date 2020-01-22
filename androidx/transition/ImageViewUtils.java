package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Matrix;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ImageViewUtils
{
  private static final String PAGE_KEY = "ImageViewUtils";
  private static Method sAnimateTransformMethod;
  private static boolean sAnimateTransformMethodFetched;
  
  private ImageViewUtils() {}
  
  static void animateTransform(ImageView paramImageView, Matrix paramMatrix)
  {
    if (Build.VERSION.SDK_INT < 21)
    {
      paramImageView.setImageMatrix(paramMatrix);
      return;
    }
    fetchAnimateTransformMethod();
    Method localMethod = sAnimateTransformMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramImageView, new Object[] { paramMatrix });
        return;
      }
      catch (InvocationTargetException paramImageView)
      {
        throw new RuntimeException(paramImageView.getCause());
      }
      catch (IllegalAccessException paramImageView) {}
    }
  }
  
  private static void fetchAnimateTransformMethod()
  {
    if (!sAnimateTransformMethodFetched)
    {
      try
      {
        Method localMethod = ImageView.class.getDeclaredMethod("animateTransform", new Class[] { Matrix.class });
        sAnimateTransformMethod = localMethod;
        localMethod = sAnimateTransformMethod;
        localMethod.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.i("ImageViewUtils", "Failed to retrieve animateTransform method", localNoSuchMethodException);
      }
      sAnimateTransformMethodFetched = true;
    }
  }
  
  static void reserveEndAnimateTransform(ImageView paramImageView, Animator paramAnimator)
  {
    if (Build.VERSION.SDK_INT < 21) {
      paramAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          Object localObject = (ImageView.ScaleType)val$view.getTag(R.id.save_scale_type);
          val$view.setScaleType((ImageView.ScaleType)localObject);
          val$view.setTag(R.id.save_scale_type, null);
          if (localObject == ImageView.ScaleType.MATRIX)
          {
            localObject = val$view;
            ((ImageView)localObject).setImageMatrix((Matrix)((View)localObject).getTag(R.id.save_image_matrix));
            val$view.setTag(R.id.save_image_matrix, null);
          }
          paramAnonymousAnimator.removeListener(this);
        }
      });
    }
  }
  
  static void startAnimateTransform(ImageView paramImageView)
  {
    if (Build.VERSION.SDK_INT < 21)
    {
      ImageView.ScaleType localScaleType = paramImageView.getScaleType();
      paramImageView.setTag(R.id.save_scale_type, localScaleType);
      if (localScaleType == ImageView.ScaleType.MATRIX) {
        paramImageView.setTag(R.id.save_image_matrix, paramImageView.getImageMatrix());
      } else {
        paramImageView.setScaleType(ImageView.ScaleType.MATRIX);
      }
      paramImageView.setImageMatrix(MatrixUtils.IDENTITY_MATRIX);
    }
  }
}
