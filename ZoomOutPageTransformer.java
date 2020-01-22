import android.view.View;
import androidx.viewpager.widget.ViewPager.PageTransformer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\007\n\000\030\0002\0020\001B\005?\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\026?\006\t"}, d2={"LZoomOutPageTransformer;", "Landroidx/viewpager/widget/ViewPager$PageTransformer;", "()V", "transformPage", "", "view", "Landroid/view/View;", "position", "", "app_release"}, k=1, mv={1, 1, 16})
public final class ZoomOutPageTransformer
  implements ViewPager.PageTransformer
{
  public ZoomOutPageTransformer() {}
  
  public void transformPage(View paramView, float paramFloat)
  {
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    int i = paramView.getWidth();
    int j = paramView.getHeight();
    if (paramFloat < -1.0F)
    {
      paramView.setAlpha(0.0F);
      return;
    }
    if (paramFloat <= 1.0F)
    {
      float f1 = Math.max(0.85F, 1.0F - Math.abs(paramFloat));
      float f3 = j;
      float f2 = 1.0F - f1;
      f3 = f3 * f2 / 2.0F;
      f2 = i * f2 / 2.0F;
      if (paramFloat < 0.0F) {
        paramFloat = f2 - f3 / 2.0F;
      } else {
        paramFloat = f2 + f3 / 2.0F;
      }
      paramView.setTranslationX(paramFloat);
      paramView.setScaleX(f1);
      paramView.setScaleY(f1);
      paramView.setAlpha((f1 - 0.85F) / 0.14999998F * 0.5F + 0.5F);
      return;
    }
    paramView.setAlpha(0.0F);
  }
}
