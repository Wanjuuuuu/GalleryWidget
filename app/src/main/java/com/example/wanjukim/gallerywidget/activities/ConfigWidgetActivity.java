package com.example.wanjukim.gallerywidget.activities;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wanjukim.gallerywidget.R;
import com.example.wanjukim.gallerywidget.WidgetProvider;

/**
 * Created by Wanju Kim on 2017-07-12.
 */

public class ConfigWidgetActivity extends Activity {
    private int mAppWidgetId;
    private AppWidgetManager appWidgetManager;
    private RemoteViews remoteViews;
    private Button buttonToGallery;
    private Button buttonToTextMenu;
    private Button button;

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
            Log.d("Debugging_ ","ID: " +mAppWidgetId);
        }
        else
            Log.d("Debugging_ ","EXTRAS=NULL CASE: ID: " +mAppWidgetId);

        appWidgetManager=AppWidgetManager.getInstance(this);
        remoteViews=new RemoteViews(this.getPackageName(),R.layout.widget_layout);

        /* perform App Widget configuration */

        buttonToGallery=(Button)findViewById(R.id.config_option_button1); // Gallery option
        buttonToGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_gallery=new Intent(getApplicationContext(),GalleryMenuActivity.class);
                startActivity(intent_gallery);
//                startActivityForResult(intent_gallery,GALLERY_ACTIVITY);
            }
        });

        buttonToTextMenu=(Button)findViewById(R.id.config_option_button2); // Text menu option
        buttonToTextMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_textMenu=new Intent(getApplicationContext(),TextMenuActivity.class);
                startActivity(intent_textMenu);
//                startActivityForResult(intent_textMenu,TEXT_ACTIVITY);
            }
        });

        /* send app widget update */

        button=(Button)findViewById(R.id.config_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*update the App Widget when configuration is complete*/

                Context context=ConfigWidgetActivity.this;//
//                Context context=getApplicationContext();
                WidgetProvider.updateWidget(context,AppWidgetManager.getInstance(context),mAppWidgetId);
//                appWidgetManager.updateAppWidget(mAppWidgetId,remoteViews);

                /* create the return intent, set it with the Activity result and finish the activity with passing back original appWidgetID*/

                Intent resultValue=new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetId);
                setResult(RESULT_OK,resultValue);
                finish();
            }
        });
    }

    /* onreceive로 옮기기 하기 */
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//
//        SharedPreferences setting=getSharedPreferences("setting",0);
//
//        switch(requestCode) {
//            case GALLERY_ACTIVITY:
//                if (resultCode == RESULT_OK) {
//                    String path = setting.getString("photo", null);
//                    remoteViews.setImageViewUri(R.id.widget_imageView, Uri.parse(path));
//                }
//                break;
//            case TEXT_ACTIVITY:
//                if (resultCode == RESULT_OK) {
//                    /* only set text done */
//                    String content = setting.getString("text", null);
//                    remoteViews.setTextViewText(R.id.widget_textView, content);
//                    /* other stuffs */
//                }
//                break;
//        }
//    }
}
