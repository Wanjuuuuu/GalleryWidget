package com.example.wanjukim.gallerywidget.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.wanjukim.gallerywidget.R;

/**
 * Created by Wanju Kim on 2017-07-13.
 */

public class TextMenuActivity extends Activity {
    public static final String TEXT="text";
    public static final String FONT="font";
    public static final String SIZE="size";
    public static final String COLOR="color";

    private int appWidgetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_text);

        /* Getting appWidgetId for preference key */

        Intent intent=getIntent();
        appWidgetId=intent.getExtras().getInt("appWidgetId");

        /* all options for text */

        final String[] fonts={"sans","serif","monospace"};
        final String[] sizes={"small","normal","large"}; // 12, 16, 20 ??

        final EditText editText=(EditText)findViewById(R.id.edit_text);
        final Spinner spinnerFont=(Spinner)findViewById(R.id.spinner_font);
        final Spinner spinnerSize=(Spinner)findViewById(R.id.spinner_size);
        //color option needs to be added

        /* set the options according to previous choices */

        SharedPreferences sp=getSharedPreferences(String.valueOf(appWidgetId),0);
        String preText=sp.getString(TEXT,null);
        editText.setText(preText); // only editText

        /* setting spinner_font */

        ArrayAdapter adapter_font=new ArrayAdapter(getApplicationContext(),R.layout.spinner,fonts);
        adapter_font.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerFont.setAdapter(adapter_font);

        spinnerFont.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        /* setting spinner_size */

        ArrayAdapter adapter_size=new ArrayAdapter(getApplicationContext(),R.layout.spinner,sizes);
        adapter_size.setDropDownViewResource(R.layout.spinner_dropdown);
        spinnerSize.setAdapter(adapter_size);

        spinnerSize.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

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
}
