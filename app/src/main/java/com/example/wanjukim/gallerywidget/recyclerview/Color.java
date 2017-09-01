package com.example.wanjukim.gallerywidget.recyclerview;

import android.util.Log;

/**
 * Created by Wanju Kim on 2017-09-01.
 */

public class Color {
    public final static int[] colors={0xFFFF0000,0xFFFFA500,0xFFFFFF00,0xFF008000,0xFF0000FF,0xFF000080
            ,0xFF800080,0xFF000000,0xFFFFFFFF}; // 빨주노초파남보검흰
    private int color;
    private int position;
    private boolean flag=false;

    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color = color;
        Log.d("Debugging_ :", "Color class" + this.color);
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position=position;
    }

    public boolean isFlag(){
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
