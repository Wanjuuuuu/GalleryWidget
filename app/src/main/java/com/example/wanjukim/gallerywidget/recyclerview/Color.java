package com.example.wanjukim.gallerywidget.recyclerview;

/**
 * Created by Wanju Kim on 2017-09-01.
 */

public class Color {
    public final static int[] colors={0xFF0000,0xFFA500,0xFFFF00,0x008000,0x0000FF,0x000080
            ,0x800080,0x000000,0xFFFFFF}; // 빨주노초파남보검흰
    private int color; ///???? int!!!! works??
    private int position;
    private boolean flag=false;

    public int getColor(){
        return color;
    }

    public void setColor(int color){
        this.color=color;
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
