package com.example.wanjukim.gallerywidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wanjukim.gallerywidget.activities.ConfigWidgetActivity;
import com.example.wanjukim.gallerywidget.activities.GalleryMenuActivity;
import com.example.wanjukim.gallerywidget.activities.TextMenuActivity;

import java.util.Locale;

/**
 * Created by Wanju Kim on 2017-07-05.
 */

public class WidgetProvider extends AppWidgetProvider{
    private static final String TAG0="Debugging_0 ";
    private static final String TAG1="Debugging_1 ";
    private static final String TAG2="Debugging_2 ";

    private static final String CLICK_ACTION="com.example.wanjukim.gallerywidget.WidgetProvider.CLICK";
    private static int widgetId;
    private String path="drawable://"+R.drawable.photo_vertical;
    private String content="";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(CLICK_ACTION.equals(intent.getAction())){

            /* getting data from shared preferences */

            SharedPreferences setting=context.getSharedPreferences("setting",0);

            path = setting.getString(GalleryMenuActivity.PHOTO, null);
            content = setting.getString(TextMenuActivity.TEXT, null);
            Log.d(TAG1,"appWidgetID : "+widgetId+ " path : "+ path+", content : "+content);

            /* individually app widget update */

            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
            individualUpdate(context,appWidgetManager,widgetId);

            return;
        }
        else
            super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int i=0;i<appWidgetIds.length;i++) {
            individualUpdate(context,appWidgetManager,appWidgetIds[i]);
        }
    }

    public void individualUpdate(Context context,AppWidgetManager appWidgetManager,int appWidgetId){

        /* when clicking, move to ConfigWidgetAcitivity */

        Intent intent=new Intent(context,ConfigWidgetActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);

        RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget_layout);

        Log.d(TAG0,"appWidgetID : "+appWidgetId+ " path : "+ path+", content : "+content);

        updateViews.setOnClickPendingIntent(R.id.mLayout,pendingIntent);

        /* data update */

        Log.d(TAG2,"appWidgetID : "+appWidgetId+ " path : "+ path+", content : "+content);

        updateViews.setTextViewText(R.id.widget_textView, content);
        updateViews.setImageViewUri(R.id.widget_imageView, Uri.parse(path));

        appWidgetManager.updateAppWidget(appWidgetId,updateViews);
    }

    public static void updateWidget(Context context,AppWidgetManager appWidgetManager,int appWidgetId){
        widgetId=appWidgetId;
//        endServiceFlag=false;
        Intent intent=new Intent();
        intent.setAction(CLICK_ACTION);
        context.sendBroadcast(intent);
//        서비스 사용시 추가할 코드있음
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

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}
