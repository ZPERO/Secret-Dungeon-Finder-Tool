package com.google.firebase.messaging;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.android.tasks.Task;
import com.google.android.android.tasks.Tasks;
import com.google.firebase.package_9.IRCService;
import com.google.firebase.package_9.zzab;
import com.google.firebase.package_9.zzav;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FirebaseMessagingService
  extends IRCService
{
  private static final Queue<String> zzdr = new ArrayDeque(10);
  
  public FirebaseMessagingService() {}
  
  static void deleteAllSettings(Bundle paramBundle)
  {
    paramBundle = paramBundle.keySet().iterator();
    while (paramBundle.hasNext())
    {
      String str = (String)paramBundle.next();
      if ((str != null) && (str.startsWith("google.c."))) {
        paramBundle.remove();
      }
    }
  }
  
  public final void doInBackground(Intent paramIntent)
  {
    Object localObject1 = paramIntent.getAction();
    if ((!"com.google.android.c2dm.intent.RECEIVE".equals(localObject1)) && (!"com.google.firebase.messaging.RECEIVE_DIRECT_BOOT".equals(localObject1)))
    {
      if ("com.google.firebase.messaging.NOTIFICATION_DISMISS".equals(localObject1))
      {
        if (MessagingAnalytics.shouldUploadMetrics(paramIntent)) {
          MessagingAnalytics.logNotificationDismiss(paramIntent);
        }
      }
      else
      {
        if ("com.google.firebase.messaging.NEW_TOKEN".equals(localObject1))
        {
          onNewToken(paramIntent.getStringExtra("token"));
          return;
        }
        paramIntent = String.valueOf(paramIntent.getAction());
        if (paramIntent.length() != 0) {
          paramIntent = "Unknown intent action: ".concat(paramIntent);
        } else {
          paramIntent = new String("Unknown intent action: ");
        }
        Log.d("FirebaseMessaging", paramIntent);
      }
    }
    else
    {
      Object localObject2 = paramIntent.getStringExtra("google.message_id");
      if (TextUtils.isEmpty((CharSequence)localObject2))
      {
        localObject1 = Tasks.forResult(null);
      }
      else
      {
        localObject1 = new Bundle();
        ((Bundle)localObject1).putString("google.message_id", (String)localObject2);
        localObject1 = zzab.get(this).save(2, (Bundle)localObject1);
      }
      boolean bool = TextUtils.isEmpty((CharSequence)localObject2);
      int j = 0;
      if (bool) {}
      int i;
      for (;;)
      {
        i = 0;
        break;
        if (zzdr.contains(localObject2))
        {
          if (Log.isLoggable("FirebaseMessaging", 3))
          {
            localObject2 = String.valueOf(localObject2);
            if (((String)localObject2).length() != 0) {
              localObject2 = "Received duplicate message: ".concat((String)localObject2);
            } else {
              localObject2 = new String("Received duplicate message: ");
            }
            Log.d("FirebaseMessaging", (String)localObject2);
          }
          i = 1;
          break;
        }
        if (zzdr.size() >= 10) {
          zzdr.remove();
        }
        zzdr.add(localObject2);
      }
      if (i == 0)
      {
        Object localObject3 = paramIntent.getStringExtra("message_type");
        localObject2 = localObject3;
        if (localObject3 == null) {
          localObject2 = "gcm";
        }
        switch (((String)localObject2).hashCode())
        {
        default: 
          break;
        case 814800675: 
          if (((String)localObject2).equals("send_event")) {
            i = 2;
          }
          break;
        case 814694033: 
          if (((String)localObject2).equals("send_error")) {
            i = 3;
          }
          break;
        case 102161: 
          if (((String)localObject2).equals("gcm")) {
            i = j;
          }
          break;
        case -2062414158: 
          if (((String)localObject2).equals("deleted_messages")) {
            i = 1;
          }
          break;
        }
        i = -1;
        if (i != 0)
        {
          if (i != 1)
          {
            if (i != 2)
            {
              if (i != 3)
              {
                paramIntent = String.valueOf(localObject2);
                if (paramIntent.length() != 0) {
                  paramIntent = "Received message with unknown type: ".concat(paramIntent);
                } else {
                  paramIntent = new String("Received message with unknown type: ");
                }
                Log.w("FirebaseMessaging", paramIntent);
              }
              else
              {
                localObject3 = paramIntent.getStringExtra("google.message_id");
                localObject2 = localObject3;
                if (localObject3 == null) {
                  localObject2 = paramIntent.getStringExtra("message_id");
                }
                onSendError((String)localObject2, new SendException(paramIntent.getStringExtra("error")));
              }
            }
            else {
              onMessageSent(paramIntent.getStringExtra("google.message_id"));
            }
          }
          else {
            onDeletedMessages();
          }
        }
        else
        {
          if (MessagingAnalytics.shouldUploadMetrics(paramIntent)) {
            MessagingAnalytics.logNotificationReceived(paramIntent);
          }
          localObject3 = paramIntent.getExtras();
          localObject2 = localObject3;
          if (localObject3 == null) {
            localObject2 = new Bundle();
          }
          ((Bundle)localObject2).remove("androidx.contentpager.content.wakelockid");
          if (Model.load((Bundle)localObject2))
          {
            if (new Model(this).onPostExecute((Bundle)localObject2)) {
              break label659;
            }
            if (MessagingAnalytics.shouldUploadMetrics(paramIntent)) {
              MessagingAnalytics.logNotificationForeground(paramIntent);
            }
          }
          onMessageReceived(new RemoteMessage((Bundle)localObject2));
        }
      }
      label659:
      paramIntent = TimeUnit.SECONDS;
      try
      {
        Tasks.await((Task)localObject1, 1L, paramIntent);
        return;
      }
      catch (TimeoutException paramIntent) {}catch (InterruptedException paramIntent) {}catch (ExecutionException paramIntent) {}
      paramIntent = String.valueOf(paramIntent);
      localObject1 = new StringBuilder(String.valueOf(paramIntent).length() + 20);
      ((StringBuilder)localObject1).append("Message ack failed: ");
      ((StringBuilder)localObject1).append(paramIntent);
      Log.w("FirebaseMessaging", ((StringBuilder)localObject1).toString());
    }
  }
  
  protected final Intent execute(Intent paramIntent)
  {
    return zzav.zzai().zzaj();
  }
  
  public void onDeletedMessages() {}
  
  public void onMessageReceived(RemoteMessage paramRemoteMessage) {}
  
  public void onMessageSent(String paramString) {}
  
  public void onNewToken(String paramString) {}
  
  public void onSendError(String paramString, Exception paramException) {}
  
  public final boolean send(Intent paramIntent)
  {
    PendingIntent localPendingIntent;
    if ("com.google.firebase.messaging.NOTIFICATION_OPEN".equals(paramIntent.getAction()))
    {
      localPendingIntent = (PendingIntent)paramIntent.getParcelableExtra("pending_intent");
      if (localPendingIntent == null) {}
    }
    try
    {
      localPendingIntent.send();
    }
    catch (PendingIntent.CanceledException localCanceledException)
    {
      for (;;) {}
    }
    Log.e("FirebaseMessaging", "Notification pending intent canceled");
    if (MessagingAnalytics.shouldUploadMetrics(paramIntent)) {
      MessagingAnalytics.logNotificationOpen(paramIntent);
    }
    return true;
    return false;
  }
}
