package com.google.android.android.measurement.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import java.io.File;

final class zzam
  extends SQLiteOpenHelper
{
  zzam(zzal paramZzal, Context paramContext, String paramString)
  {
    super(paramContext, paramString, null, 1);
  }
  
  public final SQLiteDatabase getWritableDatabase()
    throws SQLiteException
  {
    try
    {
      localSQLiteDatabase = super.getWritableDatabase();
      return localSQLiteDatabase;
    }
    catch (SQLiteDatabaseLockedException localSQLiteDatabaseLockedException)
    {
      SQLiteDatabase localSQLiteDatabase;
      throw localSQLiteDatabaseLockedException;
    }
    catch (SQLiteException localSQLiteException2)
    {
      for (;;) {}
    }
    zzals.zzgo().zzjd().zzbx("Opening the local database failed, dropping and recreating it");
    if (!zzals.getContext().getDatabasePath("google_app_measurement_local.db").delete()) {
      zzals.zzgo().zzjd().append("Failed to delete corrupted local db file", "google_app_measurement_local.db");
    }
    try
    {
      localSQLiteDatabase = super.getWritableDatabase();
      return localSQLiteDatabase;
    }
    catch (SQLiteException localSQLiteException1)
    {
      zzals.zzgo().zzjd().append("Failed to open local database. Events will bypass local storage", localSQLiteException1);
      return null;
    }
  }
  
  public final void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    FileSystem.rename(zzals.zzgo(), paramSQLiteDatabase);
  }
  
  public final void onDowngrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
  
  public final void onOpen(SQLiteDatabase paramSQLiteDatabase)
  {
    if (Build.VERSION.SDK_INT < 15)
    {
      Object localObject = null;
      try
      {
        Cursor localCursor = paramSQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
        localObject = localCursor;
        localCursor.moveToFirst();
        if (localCursor != null) {
          localCursor.close();
        }
      }
      catch (Throwable paramSQLiteDatabase)
      {
        if (localObject != null) {
          localObject.close();
        }
        throw paramSQLiteDatabase;
      }
    }
    FileSystem.delete(zzals.zzgo(), paramSQLiteDatabase, "messages", "create table if not exists messages ( type INTEGER NOT NULL, entry BLOB NOT NULL)", "type,entry", null);
  }
  
  public final void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {}
}
