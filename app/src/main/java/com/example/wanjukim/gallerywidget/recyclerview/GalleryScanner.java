package com.example.wanjukim.gallerywidget.recyclerview;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.wanjukim.gallerywidget.AndroidApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wanju Kim on 2017-07-30.
 */

public class GalleryScanner {
    public static final int ALL_PHOTO_BUCKET=0;

    private static final String[] projection={
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
    };

    public static List<Photo> photoScan(int albumId){
        String selection=null;

        if(albumId!=ALL_PHOTO_BUCKET)
            selection=String.format("%s=%d", MediaStore.Images.Media.BUCKET_ID,albumId);

        Cursor cursor= MediaStore.Images.Media.query(
                AndroidApplication.getContext().getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Images.Media.DATE_TAKEN+" DESC"); // 뛰어쓰기

        int photoIdColumn=cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int bucketIdColumn=cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
        int bucketNameColumn=cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int pathColumn=cursor.getColumnIndex(MediaStore.Images.Media.DATA);

        List<Photo> photoList=new ArrayList<>();

        while(cursor.moveToNext()){
            int bucketId=cursor.getInt(bucketIdColumn);
            String bucketName=cursor.getString(bucketNameColumn);
            int photoId=cursor.getInt(photoIdColumn);
            String path=cursor.getString(pathColumn);

            Photo photo=new Photo();
            photo.setBucketId(bucketId);
            photo.setPath(path);

            photoList.add(photo);
        }
        return photoList;
    }
}
