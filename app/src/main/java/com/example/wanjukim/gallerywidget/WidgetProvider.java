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
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
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

import java.util.ArrayList;
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

        String path = setting.getString(GalleryMenuActivity.PHOTO,null);
        String content = setting.getString(TextMenuActivity.TEXT, "");
        int font=setting.getInt(TextMenuActivity.FONT,TextMenuActivity.FONT_DEFAULT);
        int align=setting.getInt(TextMenuActivity.ALIGN,TextMenuActivity.ALIGN_DEFAULT);

        Bitmap photo=rescaleBitmap(context,path);
        updateViews.setImageViewBitmap(R.id.widget_imageView, photo);

        updateViews.setImageViewBitmap(R.id.widget_textImage,setText(appWidgetManager,context,photo,content,font,align,TextMenuActivity.COLOR_DEFAULT));

        appWidgetManager.updateAppWidget(appWidgetId,updateViews); // real update here

    }

    public static void updateWidget(Context context,AppWidgetManager appWidgetManager,int appWidgetId){ // manager?
        widgetId=appWidgetId;
        Intent intent=new Intent();
        intent.setAction(CLICK_ACTION);
        context.sendBroadcast(intent);
    }

    /* setting font,size,color ; to use Typeface, text should be dealt as an imageView */

    public Bitmap setText(AppWidgetManager appWidgetManager,Context context, Bitmap photo, String text, int font, int align, int color){

        Bitmap textImage=Bitmap.createBitmap(photo.getWidth(),photo.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(textImage);
        Paint paint=new Paint();

        Typeface typeface_font=null;

        switch(font){
            case 0:
                typeface_font=Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD);
                break;
            case 1:
                typeface_font=Typeface.create(Typeface.SERIF,Typeface.BOLD);
                break;
            case 2:
                typeface_font=Typeface.create(Typeface.MONOSPACE,Typeface.BOLD);
                break;
        }

        paint.setAntiAlias(true);
//        paint.setSubpixelText(false);
        paint.setTypeface(typeface_font);
//        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color); // not defined how to set color
        paint.setTextSize(40); // its size is changing when transforming the size of widget

        /* when text is not given */

        int x=0;
        int y=0;

        if(text.length()==0){ // default case :""
            canvas.drawText(text,x,y,paint);
            return textImage;
        }

        /* when text is given, then using number of lines to align the text */

        boolean condition=true;
        int start=0;
        int textLength=0; // max length of text based on photo width
        int numOfLines=0;

        Rect sampleBounds=new Rect();

        while(condition){
            paint.getTextBounds(text,0,0,sampleBounds);

            while(sampleBounds.width()<photo.getWidth()){
                textLength++;
                paint.getTextBounds(text,start,textLength,sampleBounds);
                if(textLength>=text.length()) { // when textLength is longer than original text
                    condition=false;
                    break;
                }
            }
            if(textLength!=text.length()) //
                textLength--; // it finishes when text width is longer than photo width
            paint.getTextBounds(text,start,textLength,sampleBounds);

            numOfLines++;
            start=textLength;
        }

        /*
         ****
         * <Warning>
         * x,y in drawText is the first start(x) and very bottom(y) of text
         ****
         */

        switch (align){
            case 0:
                y=sampleBounds.height()+10;
                break;
            case 1:
                y=(photo.getHeight()-(sampleBounds.height()*(numOfLines-2)+10*(numOfLines-1)))/2;
                break;
            case 2:
                y=photo.getHeight()-(sampleBounds.height()*(numOfLines-1)+10*numOfLines);
                break;
        }

        /* Draw text image in the right location */

        condition=true;
        start=0;
        textLength=0;

        while(condition){
            paint.getTextBounds(text,0,0,sampleBounds);

            while(sampleBounds.width()<photo.getWidth()){
                textLength++;
                paint.getTextBounds(text,start,textLength,sampleBounds);
                if(textLength>=text.length()){
                    condition=false;
                    break;
                }
            }
            if(textLength!=text.length())
                textLength--; // it finishes when text width is longer than photo width
            paint.getTextBounds(text,start,textLength,sampleBounds);

            x=(photo.getWidth()-sampleBounds.width())/2;
            canvas.drawText(text.substring(start,textLength),x,y,paint);
            y-=paint.descent()+paint.ascent()-10; // sentences have been overlapped without subtracting 10

            start=textLength;
        }

        return textImage;
    }

    /* scale down the bitmap and rotate when selected image is a camera photo*/

    public Bitmap rescaleBitmap(Context context, String path){
        /* before allocating memory, check whether it fits within the available memory */

        Bitmap image,newImage;

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true; // not allocating yet

        if(path==null)
            BitmapFactory.decodeResource(context.getResources(),R.drawable.photo); //
        else
            BitmapFactory.decodeFile(path,options);

        options.inSampleSize=calculateSampleSize(options); // calculating fit size

        options.inJustDecodeBounds=false;

        if(path==null)
            image=BitmapFactory.decodeResource(context.getResources(),R.drawable.photo);
        else
            image=BitmapFactory.decodeFile(path,options); // resizing and allocating memory

        Log.d("Debugging_ path","Path: "+path);
        Log.d("Debugging_ image","Height: "+image.getHeight()+"Width: "+image.getWidth());

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

        int reqHeight=1000; // square root 값 1000
        int reqWidth=1000; //

        int sampleSize=1;

        Log.d("Debugging_ calculate_be","height: "+height+"width: "+width);

        if(height>reqHeight||width>reqWidth){
            while((height/sampleSize)>reqHeight||(width/sampleSize)>reqWidth) {
                Log.d("Debugging_calculate","sampleSize: "+sampleSize);
                sampleSize *= 2; //
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
