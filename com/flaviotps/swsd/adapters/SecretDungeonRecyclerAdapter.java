package com.flaviotps.swsd.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.flaviotps.swsd.enum.MonsterElement;
import com.flaviotps.swsd.enum.ServerLocation;
import com.flaviotps.swsd.interfaces.SecretDungeonItem;
import com.flaviotps.swsd.model.MonsterModel;
import com.flaviotps.swsd.model.SecretDungeonModel;
import com.flaviotps.swsd.utils.ParseUtils;
import com.flaviotps.swsd.utils.ParseUtils.Companion;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt__StringsJVMKt;

@Metadata(bv={1, 0, 3}, d1={"\000X\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020!\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\004\030\0002\b\022\004\022\0020\0020\001:\001\037B-\022\b\020\003\032\004\030\0010\004\022\f\020\005\032\b\022\004\022\0020\0070\006\022\006\020\b\032\0020\t\022\006\020\n\032\0020\013?\006\002\020\fJ\020\020\023\032\0020\0072\006\020\024\032\0020\025H\002J\b\020\026\032\0020\025H\026J\030\020\027\032\0020\0302\006\020\031\032\0020\0022\006\020\024\032\0020\025H\026J\030\020\032\032\0020\0022\006\020\033\032\0020\0342\006\020\035\032\0020\025H\026J\b\020\036\032\0020\030H\002R\016\020\b\032\0020\tX?\004?\006\002\n\000R\024\020\005\032\b\022\004\022\0020\0070\006X?\016?\006\002\n\000R\016\020\r\032\0020\016X?\004?\006\002\n\000R\016\020\n\032\0020\013X?\016?\006\002\n\000R\020\020\003\032\004\030\0010\004X?\016?\006\002\n\000R\016\020\017\032\0020\020X?\004?\006\002\n\000R\024\020\021\032\b\022\004\022\0020\0020\022X?\016?\006\002\n\000?\006 "}, d2={"Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$SdViewHolder;", "token", "", "list", "", "Lcom/flaviotps/swsd/model/SecretDungeonModel;", "context", "Landroid/content/Context;", "secretDungeonItem", "Lcom/flaviotps/swsd/interfaces/SecretDungeonItem;", "(Ljava/lang/String;Ljava/util/List;Landroid/content/Context;Lcom/flaviotps/swsd/interfaces/SecretDungeonItem;)V", "mHandler", "Landroid/os/Handler;", "updateRemainingTimeRunnable", "Ljava/lang/Runnable;", "viewHolders", "", "getItem", "position", "", "getItemCount", "onBindViewHolder", "", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "startUpdate", "SdViewHolder", "app_release"}, k=1, mv={1, 1, 16})
public final class SecretDungeonRecyclerAdapter
  extends RecyclerView.Adapter<SdViewHolder>
{
  private final Context context;
  private List<SecretDungeonModel> list;
  private final Handler mHandler;
  private SecretDungeonItem secretDungeonItem;
  private String token;
  private final Runnable updateRemainingTimeRunnable;
  private List<SdViewHolder> viewHolders;
  
  public SecretDungeonRecyclerAdapter(String paramString, List paramList, Context paramContext, SecretDungeonItem paramSecretDungeonItem)
  {
    token = paramString;
    list = paramList;
    context = paramContext;
    secretDungeonItem = paramSecretDungeonItem;
    viewHolders = ((List)new ArrayList());
    mHandler = new Handler();
    updateRemainingTimeRunnable = ((Runnable)new Runnable()
    {
      /* Error */
      public final void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 32	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$updateRemainingTimeRunnable$1:this$0	Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;
        //   4: invokestatic 44	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter:access$getViewHolders$p	(Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;)Ljava/util/List;
        //   7: astore_3
        //   8: aload_3
        //   9: monitorenter
        //   10: aload_0
        //   11: getfield 32	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$updateRemainingTimeRunnable$1:this$0	Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;
        //   14: astore 4
        //   16: aload_0
        //   17: astore_2
        //   18: aload 4
        //   20: invokestatic 44	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter:access$getViewHolders$p	(Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;)Ljava/util/List;
        //   23: astore 4
        //   25: getstatic 48	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$updateRemainingTimeRunnable$1$1$1:INSTANCE	Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$updateRemainingTimeRunnable$1$1$1;
        //   28: checkcast 50	kotlin/jvm/functions/Function1
        //   31: astore 5
        //   33: aload 4
        //   35: aload 5
        //   37: invokestatic 56	kotlin/collections/CollectionsKt__MutableCollectionsKt:removeAll	(Ljava/util/List;Lkotlin/jvm/functions/Function1;)Z
        //   40: istore_1
        //   41: iload_1
        //   42: ifeq +17 -> 59
        //   45: aload_2
        //   46: getfield 32	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$updateRemainingTimeRunnable$1:this$0	Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;
        //   49: astore_2
        //   50: aload_2
        //   51: invokestatic 60	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter:access$getSecretDungeonItem$p	(Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;)Lcom/flaviotps/swsd/interfaces/SecretDungeonItem;
        //   54: invokeinterface 65 1 0
        //   59: aload_0
        //   60: getfield 32	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$updateRemainingTimeRunnable$1:this$0	Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;
        //   63: astore_2
        //   64: aload_2
        //   65: invokestatic 44	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter:access$getViewHolders$p	(Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter;)Ljava/util/List;
        //   68: invokeinterface 71 1 0
        //   73: astore_2
        //   74: aload_2
        //   75: invokeinterface 77 1 0
        //   80: istore_1
        //   81: iload_1
        //   82: ifeq +43 -> 125
        //   85: aload_2
        //   86: invokeinterface 81 1 0
        //   91: astore 4
        //   93: aload 4
        //   95: checkcast 83	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$SdViewHolder
        //   98: astore 4
        //   100: aload 4
        //   102: aload 4
        //   104: invokevirtual 87	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$SdViewHolder:getSecretDungeonModel$app_release	()Lcom/flaviotps/swsd/model/SecretDungeonModel;
        //   107: invokevirtual 93	com/flaviotps/swsd/model/SecretDungeonModel:getDungeonTimeLeft	()Ljava/lang/String;
        //   110: invokevirtual 97	com/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$SdViewHolder:update	(Ljava/lang/String;)V
        //   113: goto -39 -> 74
        //   116: astore_2
        //   117: goto +15 -> 132
        //   120: astore_2
        //   121: aload_2
        //   122: invokevirtual 100	java/lang/Exception:printStackTrace	()V
        //   125: getstatic 105	kotlin/Unit:INSTANCE	Lkotlin/Unit;
        //   128: astore_2
        //   129: aload_3
        //   130: monitorexit
        //   131: return
        //   132: aload_3
        //   133: monitorexit
        //   134: goto +3 -> 137
        //   137: aload_2
        //   138: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	139	0	this	1
        //   40	42	1	bool	boolean
        //   17	69	2	localObject1	Object
        //   116	1	2	localThrowable	Throwable
        //   120	2	2	localException	Exception
        //   128	10	2	localUnit	kotlin.Unit
        //   7	126	3	localList	List
        //   14	89	4	localObject2	Object
        //   31	5	5	localFunction1	Function1
        // Exception table:
        //   from	to	target	type
        //   10	16	116	java/lang/Throwable
        //   18	25	116	java/lang/Throwable
        //   25	33	116	java/lang/Throwable
        //   33	41	116	java/lang/Throwable
        //   45	50	116	java/lang/Throwable
        //   50	59	116	java/lang/Throwable
        //   59	64	116	java/lang/Throwable
        //   64	74	116	java/lang/Throwable
        //   74	81	116	java/lang/Throwable
        //   85	93	116	java/lang/Throwable
        //   100	113	116	java/lang/Throwable
        //   121	125	116	java/lang/Throwable
        //   125	129	116	java/lang/Throwable
        //   18	25	120	java/lang/Exception
        //   33	41	120	java/lang/Exception
        //   50	59	120	java/lang/Exception
        //   64	74	120	java/lang/Exception
        //   74	81	120	java/lang/Exception
        //   85	93	120	java/lang/Exception
        //   100	113	120	java/lang/Exception
      }
    });
    startUpdate();
  }
  
  private final SecretDungeonModel getItem(int paramInt)
  {
    return (SecretDungeonModel)list.get(paramInt);
  }
  
  private final void startUpdate()
  {
    new Timer().schedule((TimerTask)new TimerTask()
    {
      public void run()
      {
        SecretDungeonRecyclerAdapter.access$getMHandler$p(SecretDungeonRecyclerAdapter.this).post(SecretDungeonRecyclerAdapter.access$getUpdateRemainingTimeRunnable$p(SecretDungeonRecyclerAdapter.this));
      }
    }, 1000L, 1000L);
  }
  
  public int getItemCount()
  {
    return list.size();
  }
  
  public void onBindViewHolder(SdViewHolder paramSdViewHolder, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSdViewHolder, "holder");
    List localList = viewHolders;
    try
    {
      viewHolders.add(paramSdViewHolder);
      paramSdViewHolder.setData(getItem(paramInt), secretDungeonItem);
      return;
    }
    catch (Throwable paramSdViewHolder)
    {
      throw paramSdViewHolder;
    }
  }
  
  public SdViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "parent");
    paramViewGroup = LayoutInflater.from(context).inflate(2131558457, paramViewGroup, false);
    Intrinsics.checkExpressionValueIsNotNull(paramViewGroup, "view");
    return new SdViewHolder(paramViewGroup, context, token);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000R\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\006\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\004\030\0002\0020\001B\037\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\b\020\006\032\004\030\0010\007?\006\002\020\bJ\030\020#\032\0020$2\006\020%\032\0020&2\006\020\025\032\0020\026H\002J\006\020'\032\0020$J\b\020(\032\0020)H\002J\026\020*\032\0020$2\006\020\025\032\0020\0262\006\020%\032\0020&J\016\020+\032\0020$2\006\020,\032\0020\007R\032\020\004\032\0020\005X?\016?\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\fR\016\020\r\032\0020\016X?\016?\006\002\n\000R\016\020\017\032\0020\020X?\016?\006\002\n\000R\016\020\021\032\0020\020X?\016?\006\002\n\000R\016\020\022\032\0020\016X?\016?\006\002\n\000R\016\020\023\032\0020\016X?\016?\006\002\n\000R\016\020\024\032\0020\016X?\016?\006\002\n\000R\032\020\025\032\0020\026X?\016?\006\016\n\000\032\004\b\027\020\030\"\004\b\031\020\032R\016\020\033\032\0020\020X?\016?\006\002\n\000R\016\020\034\032\0020\035X?\016?\006\002\n\000R\016\020\036\032\0020\020X?\016?\006\002\n\000R\034\020\006\032\004\030\0010\007X?\016?\006\016\n\000\032\004\b\037\020 \"\004\b!\020\"?\006-"}, d2={"Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$SdViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "context", "Landroid/content/Context;", "token", "", "(Landroid/view/View;Landroid/content/Context;Ljava/lang/String;)V", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "copyButton", "Landroid/widget/ImageView;", "genericName", "Landroid/widget/TextView;", "inGameName", "ivElement", "moreButton", "preview", "secretDungeonModel", "Lcom/flaviotps/swsd/model/SecretDungeonModel;", "getSecretDungeonModel$app_release", "()Lcom/flaviotps/swsd/model/SecretDungeonModel;", "setSecretDungeonModel$app_release", "(Lcom/flaviotps/swsd/model/SecretDungeonModel;)V", "server", "stars", "Landroid/widget/RatingBar;", "timeLeft", "getToken", "()Ljava/lang/String;", "setToken", "(Ljava/lang/String;)V", "createPopupMenu", "", "secretDungeonItem", "Lcom/flaviotps/swsd/interfaces/SecretDungeonItem;", "disableCopyButton", "isMine", "", "setData", "update", "time", "app_release"}, k=1, mv={1, 1, 16})
  public static final class SdViewHolder
    extends RecyclerView.ViewHolder
  {
    private Context context;
    private ImageView copyButton;
    private TextView genericName;
    private TextView inGameName;
    private ImageView ivElement;
    private ImageView moreButton;
    private ImageView preview;
    private SecretDungeonModel secretDungeonModel;
    private TextView server;
    private RatingBar stars;
    private TextView timeLeft;
    private String token;
    
    public SdViewHolder(View paramView, Context paramContext, String paramString)
    {
      super();
      context = paramContext;
      token = paramString;
      secretDungeonModel = new SecretDungeonModel();
      paramContext = paramView.findViewById(2131361920);
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "itemView.findViewById(R.id.genericName)");
      genericName = ((TextView)paramContext);
      paramContext = paramView.findViewById(2131361962);
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "itemView.findViewById(R.id.monsterStars)");
      stars = ((RatingBar)paramContext);
      paramContext = paramView.findViewById(2131361939);
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "itemView.findViewById(R.id.ingameName)");
      inGameName = ((TextView)paramContext);
      paramContext = paramView.findViewById(2131362089);
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "itemView.findViewById(R.id.tvTimeLeft)");
      timeLeft = ((TextView)paramContext);
      paramContext = paramView.findViewById(2131362087);
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "itemView.findViewById(R.id.tvServer)");
      server = ((TextView)paramContext);
      paramContext = paramView.findViewById(2131361986);
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "itemView.findViewById(R.id.preview)");
      preview = ((ImageView)paramContext);
      paramContext = paramView.findViewById(2131361890);
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "itemView.findViewById(R.id.copyButton)");
      copyButton = ((ImageView)paramContext);
      paramContext = paramView.findViewById(2131361963);
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "itemView.findViewById(R.id.more)");
      moreButton = ((ImageView)paramContext);
      paramView = paramView.findViewById(2131361943);
      Intrinsics.checkExpressionValueIsNotNull(paramView, "itemView.findViewById(R.id.ivElement)");
      ivElement = ((ImageView)paramView);
    }
    
    private final void createPopupMenu(SecretDungeonItem paramSecretDungeonItem, final SecretDungeonModel paramSecretDungeonModel)
    {
      PopupMenu localPopupMenu = new PopupMenu(context, (View)moreButton);
      localPopupMenu.inflate(2131623937);
      MenuItem localMenuItem = localPopupMenu.getMenu().findItem(2131361896);
      Intrinsics.checkExpressionValueIsNotNull(localMenuItem, "popup.menu.findItem(R.id.delete)");
      boolean bool;
      if (!isMine()) {
        bool = false;
      } else {
        bool = true;
      }
      localMenuItem.setVisible(bool);
      localPopupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener)new PopupMenu.OnMenuItemClickListener()
      {
        public final boolean onMenuItemClick(MenuItem paramAnonymousMenuItem)
        {
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousMenuItem, "it");
          int i = paramAnonymousMenuItem.getItemId();
          if (i != 2131361896)
          {
            if (i != 2131361992)
            {
              if (i != 2131362022) {
                return false;
              }
              onShareClicked(paramSecretDungeonModel);
              return true;
            }
            onReportClicked(paramSecretDungeonModel);
            return true;
          }
          onDeleteClicked(paramSecretDungeonModel);
          return true;
        }
      });
      localPopupMenu.show();
    }
    
    private final boolean isMine()
    {
      CharSequence localCharSequence = (CharSequence)token;
      int i;
      if ((localCharSequence != null) && (localCharSequence.length() != 0)) {
        i = 0;
      } else {
        i = 1;
      }
      return (i == 0) && (StringsKt__StringsJVMKt.equals(token, secretDungeonModel.getFcmToken(), true));
    }
    
    public final void disableCopyButton()
    {
      copyButton.setClickable(false);
      copyButton.setAlpha(0.45F);
    }
    
    public final Context getContext()
    {
      return context;
    }
    
    public final SecretDungeonModel getSecretDungeonModel$app_release()
    {
      return secretDungeonModel;
    }
    
    public final String getToken()
    {
      return token;
    }
    
    public final void setContext(Context paramContext)
    {
      Intrinsics.checkParameterIsNotNull(paramContext, "<set-?>");
      context = paramContext;
    }
    
    public final void setData(final SecretDungeonModel paramSecretDungeonModel, final SecretDungeonItem paramSecretDungeonItem)
    {
      Intrinsics.checkParameterIsNotNull(paramSecretDungeonModel, "secretDungeonModel");
      Intrinsics.checkParameterIsNotNull(paramSecretDungeonItem, "secretDungeonItem");
      secretDungeonModel = paramSecretDungeonModel;
      Object localObject2 = inGameName;
      Object localObject3 = ParseUtils.Companion;
      String str = paramSecretDungeonModel.getInGameName();
      Object localObject1 = null;
      ((TextView)localObject2).setText((CharSequence)ParseUtils.Companion.trim$default((ParseUtils.Companion)localObject3, str, 0, 2, null));
      server.setText((CharSequence)paramSecretDungeonModel.getServerLocation().getValue());
      stars.setRating(paramSecretDungeonModel.getMonster().getStars());
      genericName.setText((CharSequence)paramSecretDungeonModel.getMonster().getGenericName());
      ivElement.setImageResource(paramSecretDungeonModel.getMonster().getElement().getIcon());
      localObject2 = preview;
      localObject3 = paramSecretDungeonModel.getMonster().getImage();
      if (localObject3 != null)
      {
        localObject1 = context;
        localObject1 = ContextCompat.getDrawable((Context)localObject1, ((Context)localObject1).getResources().getIdentifier((String)localObject3, "drawable", context.getPackageName()));
      }
      ((ImageView)localObject2).setImageDrawable((Drawable)localObject1);
      itemView.setBackgroundColor(ContextCompat.getColor(context, paramSecretDungeonModel.getMonster().getElement().getColor()));
      if (isMine())
      {
        copyButton.setVisibility(4);
        copyButton.setClickable(false);
        moreButton.setOnClickListener((View.OnClickListener)new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            SecretDungeonRecyclerAdapter.SdViewHolder.access$createPopupMenu(SecretDungeonRecyclerAdapter.SdViewHolder.this, paramSecretDungeonItem, paramSecretDungeonModel);
          }
        });
        return;
      }
      moreButton.setVisibility(4);
      moreButton.setClickable(false);
      copyButton.setOnClickListener((View.OnClickListener)new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          paramSecretDungeonItem.onCopyClicked(SecretDungeonRecyclerAdapter.SdViewHolder.this);
        }
      });
    }
    
    public final void setSecretDungeonModel$app_release(SecretDungeonModel paramSecretDungeonModel)
    {
      Intrinsics.checkParameterIsNotNull(paramSecretDungeonModel, "<set-?>");
      secretDungeonModel = paramSecretDungeonModel;
    }
    
    public final void setToken(String paramString)
    {
      token = paramString;
    }
    
    public final void update(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "time");
      timeLeft.setText((CharSequence)paramString);
    }
  }
}
