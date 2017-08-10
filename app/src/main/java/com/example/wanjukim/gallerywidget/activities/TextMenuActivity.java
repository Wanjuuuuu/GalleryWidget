package com.example.wanjukim.gallerywidget.activities;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanjukim.gallerywidget.R;
import com.example.wanjukim.gallerywidget.WidgetProvider;

/**
 * Created by Wanju Kim on 2017-07-13.
 */

public class TextMenuActivity extends Activity {
    private int appWidgetId;

    public static final String TEXT="text";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_text);

        Intent intent=getIntent();
        appWidgetId=intent.getExtras().getInt("appWidgetId");

        final EditText editText=(EditText)findViewById(R.id.edit_text);
        final Button button_save=(Button)findViewById(R.id.text_save_button);

        button_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { // 모든 것들을 위젯에 업데이트 시켜주어야함 // 현재는 text만 넘겨줌
                String text=editText.getText().toString();
//                Context context=ConfigWidgetActivity.this;

                SharedPreferences setting=getSharedPreferences(String.valueOf(appWidgetId),0);
                SharedPreferences.Editor editor=setting.edit();

                editor.remove(TEXT); // get rid of previous saved text
                editor.putString(TEXT,text); // put new text in shared preferences
                /* other stuffs */
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

    /*functions needed : text written by user, saving button and change the text, back button*/
}
