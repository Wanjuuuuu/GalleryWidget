package com.example.wanjukim.gallerywidget.activities;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.wanjukim.gallerywidget.R;

/**
 * Created by Wanju Kim on 2017-07-12.
 */

public class ConfigWidgetActivity extends Activity {
    private int mAppWidgetId;
    private AppWidgetManager appWidgetManager;
    private RemoteViews remoteViews;
    private Button buttonToGallery;
    private Button buttonToTextMenu;

    private static final int GALLERY_ACTIVITY=2018;
    private static final int TEXT_ACTIVITY=2019;

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
            @Override
            public void onClick(View v) {
                Intent intent_gallery=new Intent(getApplicationContext(),GalleryMenuActivity.class);
                startActivityForResult(intent_gallery,GALLERY_ACTIVITY);
                //getOption(v);
            }
        });

        buttonToTextMenu=(Button)findViewById(R.id.config_option_button2); // Text menu option
        buttonToTextMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_textMenu=new Intent(getApplicationContext(),TextMenuActivity.class);
                startActivityForResult(intent_textMenu,TEXT_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode){
            case GALLERY_ACTIVITY:
                if(resultCode==RESULT_OK){
                    /* not yet */
                }
                break;
            case TEXT_ACTIVITY:
                if(resultCode==RESULT_OK){
                    /* only set text done */
                    String text=intent.getExtras().getString("text");//
//                    Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
                    remoteViews.setTextViewText(R.id.widget_textView2,text);
                }
                break;
        }

        /*update the App Widget when configuration is complete*/

        appWidgetManager.updateAppWidget(mAppWidgetId,remoteViews);

        /*create the return intent, set it with the Activity result and finish the activity*/

        Intent resultValue=new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
        setResult(RESULT_OK,resultValue);
        finish();
    }
}
