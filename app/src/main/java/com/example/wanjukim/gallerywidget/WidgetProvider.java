package com.example.wanjukim.gallerywidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
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

    //broadcast receiver에서 onupdate가 제대로 불리는지  확인해보기
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int i=0;i<appWidgetIds.length;i++) {
            int appWidgetId=appWidgetIds[i];

            RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
            /* configwidgetAcitivity 클릭하면 뛰도록 */
            Intent intent=new Intent(context,ConfigWidgetActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            updateViews.setOnClickPendingIntent(R.id.mLayout,pendingIntent);


            // TODO: sharedPreference 에서 path 가져오기
            // Path 로 Uri 만들기

//            updateViews.setImageViewUri(R.id.widget_imageView, new);
//            SharedPreferences setting=context.getSharedPreferences("setting",0);
//            String content=setting.getString("text",null);
//            Log.d("Dedubbing_","sharedPreference:" +content);
//            updateViews.setTextViewText(R.id.widget_textView,content);

            appWidgetManager.updateAppWidget(appWidgetId,updateViews);
        }
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
