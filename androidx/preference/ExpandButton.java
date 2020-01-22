package androidx.preference;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class ExpandButton
  extends Preference
{
  private long mUserId;
  
  ExpandButton(Context paramContext, List paramList, long paramLong)
  {
    super(paramContext);
    initLayout();
    setSummary(paramList);
    mUserId = (paramLong + 1000000L);
  }
  
  private void initLayout()
  {
    setLayoutResource(R.layout.expand_button);
    setIcon(R.drawable.ic_arrow_down_24dp);
    setTitle(R.string.expand_button_title);
    setOrder(999);
  }
  
  private void setSummary(List paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    paramList = null;
    while (localIterator.hasNext())
    {
      Preference localPreference = (Preference)localIterator.next();
      CharSequence localCharSequence = localPreference.getTitle();
      boolean bool = localPreference instanceof PreferenceGroup;
      if ((bool) && (!TextUtils.isEmpty(localCharSequence))) {
        localArrayList.add((PreferenceGroup)localPreference);
      }
      if (localArrayList.contains(localPreference.getParent()))
      {
        if (bool) {
          localArrayList.add((PreferenceGroup)localPreference);
        }
      }
      else if (!TextUtils.isEmpty(localCharSequence)) {
        if (paramList == null) {
          paramList = localCharSequence;
        } else {
          paramList = getContext().getString(R.string.summary_collapsed_preference_list, new Object[] { paramList, localCharSequence });
        }
      }
    }
    setSummary((CharSequence)paramList);
  }
  
  long getId()
  {
    return mUserId;
  }
  
  public void onBindViewHolder(PreferenceViewHolder paramPreferenceViewHolder)
  {
    super.onBindViewHolder(paramPreferenceViewHolder);
    paramPreferenceViewHolder.setDividerAllowedAbove(false);
  }
}