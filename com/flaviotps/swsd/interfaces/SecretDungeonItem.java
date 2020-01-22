package com.flaviotps.swsd.interfaces;

import com.flaviotps.swsd.adapters.SecretDungeonRecyclerAdapter.SdViewHolder;
import com.flaviotps.swsd.model.SecretDungeonModel;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\bf\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&J\020\020\006\032\0020\0032\006\020\007\032\0020\bH&J\b\020\t\032\0020\003H&J\020\020\n\032\0020\0032\006\020\007\032\0020\bH&J\020\020\013\032\0020\0032\006\020\007\032\0020\bH&?\006\f"}, d2={"Lcom/flaviotps/swsd/interfaces/SecretDungeonItem;", "", "onCopyClicked", "", "sdViewHolder", "Lcom/flaviotps/swsd/adapters/SecretDungeonRecyclerAdapter$SdViewHolder;", "onDeleteClicked", "secretDungeonModel", "Lcom/flaviotps/swsd/model/SecretDungeonModel;", "onExpired", "onReportClicked", "onShareClicked", "app_release"}, k=1, mv={1, 1, 16})
public abstract interface SecretDungeonItem
{
  public abstract void onCopyClicked(SecretDungeonRecyclerAdapter.SdViewHolder paramSdViewHolder);
  
  public abstract void onDeleteClicked(SecretDungeonModel paramSecretDungeonModel);
  
  public abstract void onExpired();
  
  public abstract void onReportClicked(SecretDungeonModel paramSecretDungeonModel);
  
  public abstract void onShareClicked(SecretDungeonModel paramSecretDungeonModel);
}
