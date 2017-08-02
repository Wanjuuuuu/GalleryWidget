package com.example.wanjukim.gallerywidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.wanjukim.gallerywidget.activities.ConfigWidgetActivity;

import java.util.Locale;

/**
 * Created by Wanju Kim on 2017-07-05.
 */

public class WidgetProvider extends AppWidgetProvider{
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int i=0;i<appWidgetIds.length;i++) {
            int appWidgetId=appWidgetIds[i];

            Intent intent=new Intent(context,ConfigWidgetActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);

            RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
            updateViews.setOnClickPendingIntent(R.id.mLayout,pendingIntent);

            /*need to have Screen transition and then update the changes*/

            appWidgetManager.updateAppWidget(appWidgetId,updateViews);
        }
    }

    public void updateAppWidget(int appWidgetId,RemoteViews views){

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    //not yet
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

    }
}
