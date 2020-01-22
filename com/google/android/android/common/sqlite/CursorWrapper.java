package com.google.android.android.common.sqlite;

import android.database.AbstractCursor;
import android.database.AbstractWindowedCursor;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;

public class CursorWrapper
  extends android.database.CursorWrapper
  implements CrossProcessCursor
{
  private AbstractWindowedCursor zzez;
  
  public CursorWrapper(Cursor paramCursor)
  {
    super(paramCursor);
    int i = 0;
    while ((i < 10) && ((paramCursor instanceof android.database.CursorWrapper)))
    {
      paramCursor = ((android.database.CursorWrapper)paramCursor).getWrappedCursor();
      i += 1;
    }
    if (!(paramCursor instanceof AbstractWindowedCursor))
    {
      paramCursor = String.valueOf(paramCursor.getClass().getName());
      if (paramCursor.length() != 0) {
        paramCursor = "Unknown type: ".concat(paramCursor);
      } else {
        paramCursor = new String("Unknown type: ");
      }
      throw new IllegalArgumentException(paramCursor);
    }
    zzez = ((AbstractWindowedCursor)paramCursor);
  }
  
  public void fillWindow(int paramInt, CursorWindow paramCursorWindow)
  {
    zzez.fillWindow(paramInt, paramCursorWindow);
  }
  
  public CursorWindow getWindow()
  {
    return zzez.getWindow();
  }
  
  public boolean onMove(int paramInt1, int paramInt2)
  {
    return zzez.onMove(paramInt1, paramInt2);
  }
  
  public void setWindow(CursorWindow paramCursorWindow)
  {
    zzez.setWindow(paramCursorWindow);
  }
}