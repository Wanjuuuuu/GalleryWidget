package com.example.wanjukim.gallerywidget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;

/**
 * Created by Wanju Kim on 2017-08-20.
 */

public class SpinnerArrayAdater extends ArrayAdapter {
    private String type; // not static

    final Typeface[] fonts_setting={Typeface.SANS_SERIF,Typeface.SERIF,Typeface.MONOSPACE};
//    color~~!
//    final int[] sizes_setting={20,30,40};

    /* to get customised spinner */

    public SpinnerArrayAdater(Context context, int id, String[] strings,String option){
        super(context,id,strings);
        type=option;
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view=(TextView)super.getView(position, convertView, parent); // target view
//        Log.d("Debugging_view: ",type);

        if(type.equals("font")) {
            Typeface font = Typeface.create(fonts_setting[position], Typeface.BOLD);
            view.setTypeface(font);
//            Log.d("Debugging_font,view: ",Integer.toString(position));
            return view;
        }

//        view.setTextSize(sizes_setting[position]);
//        Log.d("Debugging_size,view: ",Integer.toString(sizes_setting[position]));

        return view;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView view=(TextView)super.getDropDownView(position, convertView, parent); // target view
//        Log.d("Debugging_view: ",type);

        if(type.equals("font")) {
            Typeface font = Typeface.create(fonts_setting[position], Typeface.BOLD);
            view.setTypeface(font);
//            Log.d("Debugging_font,drop: ",Integer.toString(position));
            return view;
        }

//        view.setTextSize(sizes_setting[position]);
//        Log.d("Debugging_size,drop: ",Integer.toString(sizes_setting[position]));

        return view;
    }
}
