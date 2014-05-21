package ca.rldesigns.android.punchcounter;

import ca.rldesigns.android.punchcounter.R;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class AppWidget extends AppWidgetProvider
{
  public static final String TAG = "PunchCounter";

  private SharedPreferences settings;
  private SharedPreferences.Editor prefEditor;
  private int counter = 0;

  @Override
  public void onReceive(Context context, Intent intent)
  {
    settings = context.getSharedPreferences(ApplicationData.DATABASE_NAME, 0);
    counter = settings.getInt(ApplicationData.COUNTER, 0);
    Bundle b = intent.getExtras();
    try
    {
      String value = b.getString("key");

      Log.d(TAG, "key: " + value);
      if (value.equals("increment"))
      {
        counter++;
      }
      else
      {
        counter = 0;
      }
    }
    catch (Exception e)
    {
      Log.e(TAG, "Exception: " + e.toString());
    }

    super.onReceive(context, intent);
  }

  @Override
  public void onUpdate(Context ctxt, AppWidgetManager mgr, int[] appWidgetIds)
  {
    Log.d(TAG, "onUpdate");
    ComponentName me = new ComponentName(ctxt, AppWidget.class);
    mgr.updateAppWidget(me, buildUpdate(ctxt, appWidgetIds));
  }

  private RemoteViews buildUpdate(Context context, int[] appWidgetIds)
  {
    Log.d(TAG, "buildUpdate");

    RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_1x1);

    Intent incrementIntent = new Intent(context, AppWidget.class);
    incrementIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
    incrementIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
    incrementIntent.putExtra("key", "increment");

    PendingIntent incrementPendingIntent = PendingIntent.getBroadcast(context, 0, incrementIntent,
        PendingIntent.FLAG_UPDATE_CURRENT);

    updateViews.setOnClickPendingIntent(R.id.increment, incrementPendingIntent);

    Intent resetIntent = new Intent(context, AppWidget.class);
    resetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
    resetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
    resetIntent.putExtra("key", "reset");
    PendingIntent resetPendingIntent = PendingIntent.getBroadcast(context, 1, resetIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    updateViews.setOnClickPendingIntent(R.id.reset, resetPendingIntent);

    updateViews.setTextViewText(R.id.counter, Integer.toString(counter));

    prefEditor = settings.edit();
    prefEditor.putInt(ApplicationData.COUNTER, counter);
    prefEditor.commit();

    return (updateViews);
  }
}