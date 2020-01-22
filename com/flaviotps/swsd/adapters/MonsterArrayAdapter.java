package com.flaviotps.swsd.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.flaviotps.swsd.model.MonsterModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt__StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\002\b\002\n\002\030\002\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\030\0002\b\022\004\022\0020\0020\001:\001\030B\033\022\006\020\003\032\0020\004\022\f\020\005\032\b\022\004\022\0020\0020\006?\006\002\020\007J\b\020\017\032\0020\020H\026J\"\020\021\032\0020\0222\006\020\023\032\0020\0242\b\020\025\032\004\030\0010\0222\006\020\026\032\0020\027H\026R\016\020\b\032\0020\tX?\016?\006\002\n\000R(\020\005\032\020\022\f\022\n \n*\004\030\0010\0020\0020\006X?\016?\006\016\n\000\032\004\b\013\020\f\"\004\b\r\020\016?\006\031"}, d2={"Lcom/flaviotps/swsd/adapters/MonsterArrayAdapter;", "Landroid/widget/ArrayAdapter;", "Lcom/flaviotps/swsd/model/MonsterModel;", "context", "Landroid/content/Context;", "monsters", "", "(Landroid/content/Context;Ljava/util/List;)V", "layoutInflater", "Landroid/view/LayoutInflater;", "kotlin.jvm.PlatformType", "getMonsters", "()Ljava/util/List;", "setMonsters", "(Ljava/util/List;)V", "getFilter", "Landroid/widget/Filter;", "getView", "Landroid/view/View;", "position", "", "convertView", "parent", "Landroid/view/ViewGroup;", "ViewHolder", "app_release"}, k=1, mv={1, 1, 16})
public final class MonsterArrayAdapter
  extends ArrayAdapter<MonsterModel>
{
  private LayoutInflater layoutInflater;
  private List<MonsterModel> monsters;
  
  public MonsterArrayAdapter(Context paramContext, List paramList)
  {
    super(paramContext, 0, paramList);
    monsters = CollectionsKt___CollectionsKt.sortedWith((Iterable)new ArrayList((Collection)paramList), (Comparator)new Comparator()
    {
      public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ComparisonsKt__ComparisonsKt.compareValues((Comparable)((MonsterModel)paramAnonymousObject1).getGenericName(), (Comparable)((MonsterModel)paramAnonymousObject2).getGenericName());
      }
    });
    paramContext = LayoutInflater.from(paramContext);
    Intrinsics.checkExpressionValueIsNotNull(paramContext, "LayoutInflater.from(context)");
    layoutInflater = paramContext;
  }
  
  public Filter getFilter()
  {
    (Filter)new Filter()
    {
      protected Filter.FilterResults performFiltering(CharSequence paramAnonymousCharSequence)
      {
        Filter.FilterResults localFilterResults = new Filter.FilterResults();
        List localList = (List)new ArrayList();
        if (paramAnonymousCharSequence != null)
        {
          int i;
          if (paramAnonymousCharSequence.length() > 0) {
            i = 1;
          } else {
            i = 0;
          }
          if (i != 0)
          {
            Iterator localIterator = getMonsters().iterator();
            while (localIterator.hasNext())
            {
              MonsterModel localMonsterModel = (MonsterModel)localIterator.next();
              if (StringsKt__StringsKt.contains((CharSequence)localMonsterModel.getGenericName(), paramAnonymousCharSequence, true))
              {
                Intrinsics.checkExpressionValueIsNotNull(localMonsterModel, "monster");
                localList.add(localMonsterModel);
              }
            }
          }
        }
        values = localList;
        count = localList.size();
        return localFilterResults;
      }
      
      protected void publishResults(CharSequence paramAnonymousCharSequence, Filter.FilterResults paramAnonymousFilterResults)
      {
        clear();
        if ((paramAnonymousFilterResults != null) && (count > 0))
        {
          paramAnonymousCharSequence = MonsterArrayAdapter.this;
          paramAnonymousFilterResults = values;
          if (paramAnonymousFilterResults != null)
          {
            paramAnonymousCharSequence.addAll((Collection)TypeIntrinsics.asMutableList(paramAnonymousFilterResults));
            notifyDataSetChanged();
            return;
          }
          throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<com.flaviotps.swsd.model.MonsterModel>");
        }
      }
    };
  }
  
  public final List getMonsters()
  {
    return monsters;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "parent");
    if (paramView == null)
    {
      paramViewGroup = layoutInflater.inflate(2131558434, paramViewGroup, false);
      paramView = paramViewGroup;
      Intrinsics.checkExpressionValueIsNotNull(paramViewGroup, "layoutInflater.inflate(R?plete_row, parent, false)");
      localObject1 = new ViewHolder(paramViewGroup);
      paramViewGroup.setTag(localObject1);
      paramViewGroup = (ViewGroup)localObject1;
    }
    else
    {
      paramViewGroup = paramView.getTag();
      if (paramViewGroup == null) {
        break label201;
      }
      paramViewGroup = (ViewHolder)paramViewGroup;
    }
    Object localObject1 = (MonsterModel)getItem(paramInt);
    if (localObject1 != null)
    {
      localObject1 = ((MonsterModel)localObject1).getGenericName();
      if (localObject1 != null) {
        paramViewGroup.getName().setText((CharSequence)localObject1);
      }
    }
    localObject1 = (MonsterModel)getItem(paramInt);
    if (localObject1 != null)
    {
      localObject1 = ((MonsterModel)localObject1).getImage();
      if (localObject1 != null)
      {
        paramViewGroup = paramViewGroup.getPreview();
        Context localContext1 = getContext();
        Object localObject2 = getContext();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "context");
        localObject2 = ((Context)localObject2).getResources();
        Context localContext2 = getContext();
        Intrinsics.checkExpressionValueIsNotNull(localContext2, "context");
        paramViewGroup.setImageDrawable(ContextCompat.getDrawable(localContext1, ((Resources)localObject2).getIdentifier((String)localObject1, "drawable", localContext2.getPackageName())));
        return paramView;
        label201:
        throw new TypeCastException("null cannot be cast to non-null type com.flaviotps.swsd.adapters.MonsterArrayAdapter.ViewHolder");
      }
    }
    return paramView;
  }
  
  public final void setMonsters(List paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "<set-?>");
    monsters = paramList;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004R\032\020\005\032\0020\006X?\016?\006\016\n\000\032\004\b\007\020\b\"\004\b\t\020\nR\032\020\013\032\0020\fX?\016?\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020?\006\021"}, d2={"Lcom/flaviotps/swsd/adapters/MonsterArrayAdapter$ViewHolder;", "", "row", "Landroid/view/View;", "(Landroid/view/View;)V", "name", "Landroid/widget/TextView;", "getName", "()Landroid/widget/TextView;", "setName", "(Landroid/widget/TextView;)V", "preview", "Landroid/widget/ImageView;", "getPreview", "()Landroid/widget/ImageView;", "setPreview", "(Landroid/widget/ImageView;)V", "app_release"}, k=1, mv={1, 1, 16})
  public static final class ViewHolder
  {
    private TextView name;
    private ImageView preview;
    
    public ViewHolder(View paramView)
    {
      View localView = paramView.findViewById(2131361967);
      Intrinsics.checkExpressionValueIsNotNull(localView, "row.findViewById(R.id.name)");
      name = ((TextView)localView);
      paramView = paramView.findViewById(2131361986);
      Intrinsics.checkExpressionValueIsNotNull(paramView, "row.findViewById(R.id.preview)");
      preview = ((ImageView)paramView);
    }
    
    public final TextView getName()
    {
      return name;
    }
    
    public final ImageView getPreview()
    {
      return preview;
    }
    
    public final void setName(TextView paramTextView)
    {
      Intrinsics.checkParameterIsNotNull(paramTextView, "<set-?>");
      name = paramTextView;
    }
    
    public final void setPreview(ImageView paramImageView)
    {
      Intrinsics.checkParameterIsNotNull(paramImageView, "<set-?>");
      preview = paramImageView;
    }
  }
}
