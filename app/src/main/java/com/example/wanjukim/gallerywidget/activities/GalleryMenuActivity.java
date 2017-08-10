package com.example.wanjukim.gallerywidget.activities;

import com.example.wanjukim.gallerywidget.R;
import com.example.wanjukim.gallerywidget.WidgetProvider;
import com.example.wanjukim.gallerywidget.recyclerview.*;

import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Wanju Kim on 2017-07-13.
 */

public class GalleryMenuActivity extends AppCompatActivity implements PhotoAdapter.PhotoClickListener {
    private RecyclerView photoListView;
    private PhotoAdapter adapter;
    private final int STORAGE_PERMISSION_REQUEST=2017;

    private String path=null;
    private int appWidgetId;

    public static final String PHOTO="photo";


    /*functions needed : connection with gallery and choose the photo, saving button and change the photo, back button*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_gallery);

        Intent intent=getIntent();
        appWidgetId=intent.getExtras().getInt("appWidgetId");

        photoListView=(RecyclerView)findViewById(R.id.recyclerview_gallery);

        adapter=new PhotoAdapter(this);
        adapter.setOnItemClickListener(this);

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,3);

        photoListView.setAdapter(adapter);//
        photoListView.setLayoutManager(layoutManager);

        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,permissions,STORAGE_PERMISSION_REQUEST);

        Button button_save=(Button)findViewById(R.id.gallery_save_button);
        button_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(path==null){
                    Toast.makeText(getApplicationContext(),"You haven't chosen a photo yet",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences setting=getSharedPreferences(String.valueOf(appWidgetId),0);
                SharedPreferences.Editor editor=setting.edit();

                editor.remove(PHOTO);
                editor.putString(PHOTO,path);
                editor.commit();

                setResult(RESULT_OK);
                finish();
            }
        });

        Button button_back=(Button)findViewById(R.id.gallery_back_button);
        button_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_PERMISSION_REQUEST:
                if(grantResults[0]== PermissionChecker.PERMISSION_GRANTED){
                    runGalleryScan();
                }
        }
    }

    private void runGalleryScan(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Photo> photoList=GalleryScanner.photoScan(GalleryScanner.ALL_PHOTO_BUCKET);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addPhotoList(photoList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClickPhoto(Photo photo) {
        path=photo.getPath();
        Toast.makeText(this,photo.getPath(),Toast.LENGTH_SHORT).show();
    }
}
