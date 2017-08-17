package com.example.wanjukim.gallerywidget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
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

    private static final String CLICK_ACTION="com.example.wanjukim.gallerywidget.WidgetProvider.CLICK";
    private static int widgetId;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(CLICK_ACTION.equals(intent.getAction())){
            /* individually app widget update */

            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
            Log.d("Debugging_ ","onReceive:: widgetID: "+widgetId);
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
            Log.d("Debugging_there are ids","ID:" +appWidgetIds[i]);
        }
    }

    public void individualUpdate(Context context,AppWidgetManager appWidgetManager,int appWidgetId){
        /* set view to be moved to ConfigWidgetAcitivity when clicking */

        Intent intent=new Intent(context,ConfigWidgetActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // meaning?
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId); // manually sending appWidgetId to update the data
        PendingIntent pendingIntent=PendingIntent.getActivity(context,appWidgetId,intent,PendingIntent.FLAG_UPDATE_CURRENT); // requestCode : not 0, but appWidgetId

        RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
        updateViews.setOnClickPendingIntent(R.id.mLayout,pendingIntent);

        /* getting data from shared preferences and updating */

        SharedPreferences setting=context.getSharedPreferences(String.valueOf(appWidgetId),0);

        String path = setting.getString(GalleryMenuActivity.PHOTO, null);
        String content = setting.getString(TextMenuActivity.TEXT, "");
        String font=setting.getString(TextMenuActivity.FONT,TextMenuActivity.FONT_DEFAULT);
        int size=setting.getInt(TextMenuActivity.SIZE,TextMenuActivity.SIZE_DEFAULT);

        if(path==null)
            updateViews.setImageViewResource(R.id.widget_imageView, R.drawable.photo_vertical);
        else
            updateViews.setImageViewBitmap(R.id.widget_imageView,rescaleBitmap(path));

        updateViews.setImageViewBitmap(R.id.widget_textImage,setText(context,content,font,size,TextMenuActivity.COLOR_DEFAULT));


        appWidgetManager.updateAppWidget(appWidgetId,updateViews); // real update here
    }

    public static void updateWidget(Context context,AppWidgetManager appWidgetManager,int appWidgetId){ // manager?
        widgetId=appWidgetId;
        Intent intent=new Intent();
        intent.setAction(CLICK_ACTION);
        context.sendBroadcast(intent);
    }

    /* setting font,size,color ; to use Typeface, text should be dealt as an imageView */

    public Bitmap setText(Context context, String text, String font, int size, int color){
        Bitmap textImage=Bitmap.createBitmap(10,10,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(textImage);
        Paint paint=new Paint();
        Typeface typeface_font=Typeface.createFromAsset(context.getAssets(),font);

        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(typeface_font);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(text,10,20,paint); // ?

        return textImage;
    }

    /* scale down the bitmap and rotate when selected image is a camera photo*/

    public Bitmap rescaleBitmap(String path){
        /* before allocating memory, check whether it fits within the available memory */

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true; // not allocating yet
        BitmapFactory.decodeFile(path,options);

        options.inSampleSize=calculateSampleSize(options); // calculating fit size

        options.inJustDecodeBounds=false;

        Bitmap image=BitmapFactory.decodeFile(path,options); // resizing and allocating memory
        Log.d("Debugging_ image","Height: "+image.getHeight()+"Width: "+image.getWidth());

        Bitmap newImage;

        /* solving problem when using image take from camera : rotation problem */

        try{
            ExifInterface exif = new ExifInterface(path);

            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int exifDegree = exifOrientationToDegrees(exifOrientation);

            Matrix rotateMatrix = new Matrix();
            rotateMatrix.postRotate(exifDegree);

            newImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), rotateMatrix, false);

            Log.d("Debugging_ image","Height: "+image.getHeight()+"Width: "+image.getWidth());
            Log.d("Debugging_ newImage","Height: "+newImage.getHeight()+"Width: "+newImage.getWidth());

            return newImage;
        }
        catch(Exception e)
        {
            Log.d("Debugging_ bitmap","");
        }

        return image; //
    }

    public int calculateSampleSize(BitmapFactory.Options options){
        int height=options.outHeight;
        int width=options.outWidth;

        int reqHeight=1280; // square root 값 1000
        int reqWidth=1280;

        int sampleSize=1;

        Log.d("Debugging_ calculate_be","height: "+height+"width: "+width);

        if(height>reqHeight||width>reqWidth){
            while((height/sampleSize)>=reqHeight||(width/sampleSize)>=reqWidth) {
                Log.d("Debugging_calculate","sampleSize: "+sampleSize);
                sampleSize *= 2;
            }
        }

        return sampleSize;
    }

    public int exifOrientationToDegrees(int exifOrientation){
        if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_270)
            return 270;
        else
            return 0;
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
