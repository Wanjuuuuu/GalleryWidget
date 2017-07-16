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
    private int mAppWidgetId;
    private AppWidgetManager appWidgetManager;
    private RemoteViews remoteViews;
    private Button buttonToGallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_main);

        /*get the App Widget ID from the Intent*/

        final Intent intent=getIntent();
        Bundle extras=intent.getExtras(); // retrieve data
        if(extras!=null){
            mAppWidgetId=extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        appWidgetManager=AppWidgetManager.getInstance(this);
        remoteViews=new RemoteViews(this.getPackageName(),R.layout.widget_layout);

        /*perform App Widget configuration*/

        buttonToGallery=(Button)findViewById(R.id.config_option_button1); // Gallery option
        buttonToGallery.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent_gallery=new Intent(getApplicationContext(),GalleryMenuActivity.class);
                startActivity(intent_gallery);
                //getOption(v);
            }
        });// move to another activity .. doesn't work
    }

    public void getOption(View view){
        /*update the App Widget when configuration is complete*/

        appWidgetManager.updateAppWidget(mAppWidgetId,remoteViews);

        /*create the return intent, set it with the Activity result and finish the acitivity*/

        Intent resultValue=new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
        setResult(RESULT_OK,resultValue);
        finish();
    }
}
