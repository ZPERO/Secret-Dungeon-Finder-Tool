package com.google.android.android.common.internal;

import android.net.Uri;
import android.net.Uri.Builder;

public final class ResourceUtils
{
  private static final Uri zzet = new Uri.Builder().scheme("android.resource").authority("com.google.android.gms").appendPath("drawable").build();
  
  private ResourceUtils() {}
}