package androidx.fragment.package_8;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

final class FragmentManagerState
  implements Parcelable
{
  public static final Parcelable.Creator<androidx.fragment.app.FragmentManagerState> CREATOR = new Parcelable.Creator()
  {
    public FragmentManagerState createFromParcel(Parcel paramAnonymousParcel)
    {
      return new FragmentManagerState(paramAnonymousParcel);
    }
    
    public FragmentManagerState[] newArray(int paramAnonymousInt)
    {
      return new FragmentManagerState[paramAnonymousInt];
    }
  };
  ArrayList<androidx.fragment.app.FragmentState> mActive;
  ArrayList<String> mAdded;
  BackStackState[] mBackStack;
  int mNextFragmentIndex;
  String mPrimaryNavActiveWho = null;
  
  public FragmentManagerState() {}
  
  public FragmentManagerState(Parcel paramParcel)
  {
    mActive = paramParcel.createTypedArrayList(FragmentState.CREATOR);
    mAdded = paramParcel.createStringArrayList();
    mBackStack = ((BackStackState[])paramParcel.createTypedArray(BackStackState.CREATOR));
    mPrimaryNavActiveWho = paramParcel.readString();
    mNextFragmentIndex = paramParcel.readInt();
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeTypedList(mActive);
    paramParcel.writeStringList(mAdded);
    paramParcel.writeTypedArray(mBackStack, paramInt);
    paramParcel.writeString(mPrimaryNavActiveWho);
    paramParcel.writeInt(mNextFragmentIndex);
  }
}
