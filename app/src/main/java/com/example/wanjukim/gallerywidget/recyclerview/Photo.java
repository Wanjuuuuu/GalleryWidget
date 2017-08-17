package com.example.wanjukim.gallerywidget.recyclerview;

/**
 * Created by Wanju Kim on 2017-07-30.
 */

public class Photo {
    private int bucketId;
    private int photoId;
    private String path;

    private boolean flag = false;

    public int getBucketId(){
        return bucketId;
    }

    public void setBucketId(int bucketId){
        this.bucketId=bucketId;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path=path;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
