package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\000D\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020)\n\002\b\002\032\025\020\n\032\0020\013*\0020\0032\006\020\f\032\0020\002H?\002\0320\020\r\032\0020\016*\0020\0032!\020\017\032\035\022\023\022\0210\002?\006\f\b\021\022\b\b\022\022\004\b\b(\f\022\004\022\0020\0160\020H?\b\032E\020\023\032\0020\016*\0020\00326\020\017\0322\022\023\022\0210\007?\006\f\b\021\022\b\b\022\022\004\b\b(\025\022\023\022\0210\002?\006\f\b\021\022\b\b\022\022\004\b\b(\f\022\004\022\0020\0160\024H?\b\032\025\020\026\032\0020\002*\0020\0032\006\020\025\032\0020\007H?\n\032\r\020\027\032\0020\013*\0020\003H?\b\032\r\020\030\032\0020\013*\0020\003H?\b\032\023\020\031\032\b\022\004\022\0020\0020\032*\0020\003H?\002\032\025\020\033\032\0020\016*\0020\0032\006\020\f\032\0020\002H?\n\"\033\020\000\032\b\022\004\022\0020\0020\001*\0020\0038F?\006\006\032\004\b\004\020\005\"\026\020\006\032\0020\007*\0020\0038?\002?\006\006\032\004\b\b\020\t?\006\034"}, d2={"children", "Lkotlin/sequences/Sequence;", "Landroid/view/MenuItem;", "Landroid/view/Menu;", "getChildren", "(Landroid/view/Menu;)Lkotlin/sequences/Sequence;", "size", "", "getSize", "(Landroid/view/Menu;)I", "contains", "", "item", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function2;", "index", "get", "isEmpty", "isNotEmpty", "iterator", "", "minusAssign", "core-ktx_release"}, k=2, mv={1, 1, 15})
public final class MenuKt
{
  public static final boolean contains(Menu paramMenu, MenuItem paramMenuItem)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$contains");
    Intrinsics.checkParameterIsNotNull(paramMenuItem, "item");
    int j = paramMenu.size();
    int i = 0;
    while (i < j)
    {
      if (Intrinsics.areEqual(paramMenu.getItem(i), paramMenuItem)) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public static final void forEach(Menu paramMenu, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$forEach");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    int j = paramMenu.size();
    int i = 0;
    while (i < j)
    {
      MenuItem localMenuItem = paramMenu.getItem(i);
      Intrinsics.checkExpressionValueIsNotNull(localMenuItem, "getItem(index)");
      paramFunction1.invoke(localMenuItem);
      i += 1;
    }
  }
  
  public static final void forEachIndexed(Menu paramMenu, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$forEachIndexed");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "action");
    int j = paramMenu.size();
    int i = 0;
    while (i < j)
    {
      MenuItem localMenuItem = paramMenu.getItem(i);
      Intrinsics.checkExpressionValueIsNotNull(localMenuItem, "getItem(index)");
      paramFunction2.invoke(Integer.valueOf(i), localMenuItem);
      i += 1;
    }
  }
  
  public static final Sequence getChildren(Menu paramMenu)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$children");
    (Sequence)new Sequence()
    {
      public Iterator iterator()
      {
        return MenuKt.iterator(MenuKt.this);
      }
    };
  }
  
  public static final MenuItem getItem(Menu paramMenu, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$get");
    paramMenu = paramMenu.getItem(paramInt);
    Intrinsics.checkExpressionValueIsNotNull(paramMenu, "getItem(index)");
    return paramMenu;
  }
  
  public static final int getSize(Menu paramMenu)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$size");
    return paramMenu.size();
  }
  
  public static final boolean isEmpty(Menu paramMenu)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$isEmpty");
    return paramMenu.size() == 0;
  }
  
  public static final boolean isNotEmpty(Menu paramMenu)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$isNotEmpty");
    return paramMenu.size() != 0;
  }
  
  public static final Iterator iterator(Menu paramMenu)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$iterator");
    (Iterator)new Iterator()
    {
      private int index;
      
      public boolean hasNext()
      {
        return index < size();
      }
      
      public MenuItem next()
      {
        Object localObject = MenuKt.this;
        int i = index;
        index = (i + 1);
        localObject = ((Menu)localObject).getItem(i);
        if (localObject != null) {
          return localObject;
        }
        throw ((Throwable)new IndexOutOfBoundsException());
      }
      
      public void remove()
      {
        Menu localMenu = MenuKt.this;
        index -= 1;
        localMenu.removeItem(index);
      }
    };
  }
  
  public static final void minusAssign(Menu paramMenu, MenuItem paramMenuItem)
  {
    Intrinsics.checkParameterIsNotNull(paramMenu, "$this$minusAssign");
    Intrinsics.checkParameterIsNotNull(paramMenuItem, "item");
    paramMenu.removeItem(paramMenuItem.getItemId());
  }
}
