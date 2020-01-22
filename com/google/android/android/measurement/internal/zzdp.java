package com.google.android.android.measurement.internal;

import android.os.Bundle;

final class zzdp
  implements Runnable
{
  zzdp(zzdo paramZzdo, boolean paramBoolean, zzdn paramZzdn1, zzdn paramZzdn2) {}
  
  public final void run()
  {
    if ((zzaru) && (zzarx.zzaro != null))
    {
      localObject = zzarx;
      zzdo.access$getSetAlarm((zzdo)localObject, zzaro);
    }
    Object localObject = zzarv;
    int i;
    if ((localObject != null) && (zzarm == zzarw.zzarm) && (zzfk.verifySignature(zzarv.zzarl, zzarw.zzarl)) && (zzfk.verifySignature(zzarv.zzuw, zzarw.zzuw))) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      localObject = new Bundle();
      zzdo.d(zzarw, (Bundle)localObject, true);
      zzdn localZzdn = zzarv;
      if (localZzdn != null)
      {
        if (zzuw != null) {
          ((Bundle)localObject).putString("_pn", zzarv.zzuw);
        }
        ((Bundle)localObject).putString("_pc", zzarv.zzarl);
        ((Bundle)localObject).putLong("_pi", zzarv.zzarm);
      }
      zzarx.zzge().saveToFile("auto", "_vs", (Bundle)localObject);
    }
    localObject = zzarx;
    zzaro = zzarw;
    ((class_4)localObject).zzgg().Open(zzarw);
  }
}
