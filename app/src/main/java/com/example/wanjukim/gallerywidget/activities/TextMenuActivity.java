package com.example.wanjukim.gallerywidget.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.wanjukim.gallerywidget.R;
import com.example.wanjukim.gallerywidget.SpinnerArrayAdater;

/**
 * Created by Wanju Kim on 2017-07-13.
 */

public class TextMenuActivity extends Activity {
    public static final String TEXT="text";
    public static final String FONT="font";
    public static final String ALIGN="align";
    public static final String COLOR="color";

    public static final String FONT_DEFAULT="SANS_SERIF";
    public static final String ALIGN_DEFAULT="Middle";
    public static final int COLOR_DEFAULT=0xFF000000; // black

    private int appWidgetId;
    private String font;
    private String align;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_text);

        /* Getting appWidgetId for preference key */

        Intent intent=getIntent();
        appWidgetId=intent.getExtras().getInt("appWidgetId");

        /* all options for text */

        final String[] fonts_user={"SANS_SERIF","SERIF","MONOSPACE"};
        final String[] fonts_setting={"SANS_SERIF","SERIF","MONOSPACE"};
        final String[] aligns_user={"Top","Middle","Bottom"};
        final String[] aligns_setting={"Top","Middle","Bottom"};

        final EditText editText=(EditText)findViewById(R.id.edit_text);
        final Spinner spinnerFont=(Spinner)findViewById(R.id.spinner_font);
        final Spinner spinnerAlign=(Spinner)findViewById(R.id.spinner_align);
        //color option needs to be added

        /* set the options according to previous choices */

        SharedPreferences sp=getSharedPreferences(String.valueOf(appWidgetId),0);
        String preText=sp.getString(TEXT,null);
        editText.setText(preText); // only editText

        /* setting spinner_font */

        ArrayAdapter adapter_font=new SpinnerArrayAdater(getApplicationContext(),R.layout.spinner,fonts_user,"font"); // to customise spinner
        adapter_font.setDropDownViewResource(R.layout.spinner_dropdown); // dropdown
        spinnerFont.setAdapter(adapter_font);

        font=null; // not choosen

        spinnerFont.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        font=fonts_setting[position]; //
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

        align=null; // not chosen

        spinnerAlign.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        align=aligns_setting[position]; //
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        /* color setting using recyclerView */

        //....

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

                if(font!=null) { // Chosen (if not chosen, using previous chosen one)
                    editor.remove(FONT);
                    editor.putString(FONT, font);
                }

                if(align!=null) { // Chosen (if not chosen, using previous chosen one)
                    editor.remove(ALIGN);
                    editor.putString(ALIGN,align);
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
}
