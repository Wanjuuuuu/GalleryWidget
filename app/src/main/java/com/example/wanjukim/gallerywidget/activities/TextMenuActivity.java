package com.example.wanjukim.gallerywidget.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.wanjukim.gallerywidget.R;
import com.example.wanjukim.gallerywidget.SpinnerArrayAdater;
import com.example.wanjukim.gallerywidget.recyclerview.Color;
import com.example.wanjukim.gallerywidget.recyclerview.ColorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wanju Kim on 2017-07-13.
 */

public class TextMenuActivity extends Activity implements ColorAdapter.ColorClickListener {
    public static final String TEXT="text";
    public static final String FONT="font";
    public static final String ALIGN="align";
    public static final String COLOR="color";

    public static final int FONT_DEFAULT=0;
    public static final int ALIGN_DEFAULT=0;
    public static final int COLOR_DEFAULT=0xFF000000; // black

    private RecyclerView colorListView;
    private ColorAdapter adapter;

    private int appWidgetId;
    private int font;
    private int align;
    private int color; /// ??? int ????

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_text);

        /* Getting appWidgetId for preference key */

        Intent intent=getIntent();
        appWidgetId=intent.getExtras().getInt("appWidgetId");

        /* all options for text */

        final String[] fonts_user={"San-serif 산세리프","Serif 세리프","Monospace 모노스페이스"};
//        final String[] fonts_setting={"SANS_SERIF","SERIF","MONOSPACE"};
        final String[] aligns_user={"Top","Middle","Bottom"};
//        final String[] aligns_setting={"Top","Middle","Bottom"};

        final EditText editText=(EditText)findViewById(R.id.edit_text);
        final Spinner spinnerFont=(Spinner)findViewById(R.id.spinner_font);
        final Spinner spinnerAlign=(Spinner)findViewById(R.id.spinner_align);
        //color option needs to be added

        /* set the options according to previous choices */

        SharedPreferences sp=getSharedPreferences(String.valueOf(appWidgetId),0);

        String preText=sp.getString(TEXT,null);
        int preFont=sp.getInt(FONT,0);
        int preAlign=sp.getInt(ALIGN,0);

        editText.setText(preText); // only editText

        /* setting spinner_font */

        ArrayAdapter adapter_font=new SpinnerArrayAdater(getApplicationContext(),R.layout.spinner,fonts_user,"font"); // to customise spinner
        adapter_font.setDropDownViewResource(R.layout.spinner_dropdown); // dropdown
        spinnerFont.setAdapter(adapter_font);
        spinnerFont.setSelection(preFont);

        font=-1; // not choosen

        spinnerFont.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        font=position; //
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        /* setting spinner_align */

        /* It supposed to be text size setting, however, text should be a bitmap file to change settings in app widget.
           Therefore, I couldn't find the way to fix the size of text. This function has been changed to set the alignment of text. */

        ArrayAdapter adapter_align=new SpinnerArrayAdater(getApplicationContext(),R.layout.spinner,aligns_user,"align"); // to customise spinner
        adapter_align.setDropDownViewResource(R.layout.spinner_dropdown); // dropdown
        spinnerAlign.setAdapter(adapter_align);
        spinnerAlign.setSelection(preAlign);

        align=-1; // not chosen

        spinnerAlign.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        align=position; //
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        /* color setting using recyclerView */ /////

        colorListView=(RecyclerView)findViewById(R.id.recyclerview_color);

        adapter=new ColorAdapter(this);
        adapter.setOnItemClickListener(this);

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,6); // not calculated yet

        colorListView.setAdapter(adapter); // provided by recyclerView
        colorListView.setLayoutManager(layoutManager); //

        runColorScan();

        /* update text as what has been chosen newly */

        final Button button_save=(Button)findViewById(R.id.text_save_button);

        button_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { // 모든 것들을 위젯에 업데이트 시켜주어야함 // 현재는 text만 넘겨줌
                String text=editText.getText().toString();

                SharedPreferences setting=getSharedPreferences(String.valueOf(appWidgetId),0);
                SharedPreferences.Editor editor=setting.edit();

                editor.remove(TEXT); // get rid of previous saved text
                editor.putString(TEXT,text); // put new text in shared preferences

                if(font!=-1) { // Chosen (if not chosen, using previous chosen one)
                    editor.remove(FONT);
                    editor.putInt(FONT, font);
                }

                if(align!=-1) { // Chosen (if not chosen, using previous chosen one)
                    editor.remove(ALIGN);
                    editor.putInt(ALIGN,align);
                }

                editor.commit();

                setResult(RESULT_OK);
                finish();
            }
        });

        final Button button_back=(Button)findViewById(R.id.text_back_button);
        button_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void runColorScan(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                final List<Color> colorList=new ArrayList<Color>();
                int position=0;

                for(int colorCode:Color.colors){
                    Color newColor=new Color();
                    newColor.setColor(colorCode);
                    newColor.setPosition(position);
                    colorList.add(newColor);
                    position++;
                    Log.d("Debugging_ :"," color :"+colorCode+" position: "+position);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addColorList(colorList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(Color color) {
        this.color=color.getColor();
        Log.d("Debugging_ click: ","color : "+color.getColor());
    }
}
