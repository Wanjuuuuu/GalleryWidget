package com.example.wanjukim.gallerywidget;

import android.app.Application;
import android.content.Context;

/**
 * Created by Wanju Kim on 2017-07-24.
 */

public class AndroidApplication extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=getApplicationContext();
    }

    public static Context getContext(){ // when does it used?
        return applicationContext;
    }
}
