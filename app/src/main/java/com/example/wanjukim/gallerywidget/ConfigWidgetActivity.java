package com.example.wanjukim.gallerywidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

/**
 * Created by Wanju Kim on 2017-07-12.
 */

public class ConfigWidgetActivity extends Activity {
    int mAppWidgetId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_main);

        /*get the App Widget ID from the Intent*/

        Intent intent=getIntent();
        Bundle extras=intent.getExtras(); // retrieve data
        if(extras!=null){
            mAppWidgetId=extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        /*perform App Widget configuration*/

      

        /*update the App Widget when configuration is complete*/

        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(this);
        RemoteViews views=new RemoteViews(this.getPackageName(),R.layout.widget_layout);
        appWidgetManager.updateAppWidget(mAppWidgetId,views);

        /*create the return intent, set it with the Activity result and finish the acitivity*/

        Intent resultValue=new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
        setResult(RESULT_OK,resultValue);
        finish();
    }
}
