package com.example.wanjukim.gallerywidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

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
        appWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(context,getClass()));
        for(int i=0;i<appWidgetIds.length;i++)
            updateAppWidget(context,appWidgetManager,appWidgetIds[i]);
    }

    public static void updateAppWidget(Context context,AppWidgetManager appWidgetManager,int appWidgetId){
        Calendar mCalendar=Calendar.getInstance();
        SimpleDateFormat mFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);

        /*need to have the function to update time periodically*/

        RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
        updateViews.setTextViewText(R.id.widget_textView2,mFormat.format((mCalendar.getTime())));

        Intent intent=new Intent(context,ConfigWidgetActivity.class); // has been changed to configuration activity but don't know whether it works or not
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
        updateViews.setOnClickPendingIntent(R.id.mLayout,pendingIntent);

        /*need to have Screen transition and then update the changes*/

        appWidgetManager.updateAppWidget(appWidgetId,updateViews);
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
