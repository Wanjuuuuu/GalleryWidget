package com.example.wanjukim.gallerywidget.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wanjukim.gallerywidget.R;

/**
 * Created by Wanju Kim on 2017-07-13.
 */

public class TextMenuActivity extends Activity {
    private Bundle extra;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_text);

        extra=new Bundle();
        intent=new Intent();

        final EditText editText=(EditText)findViewById(R.id.edit_text);

        final Button button_save=(Button)findViewById(R.id.text_save_button);
        button_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { // 모든 것들을 위젯에 업데이트 시켜주어야함 // 현재는 text만 넘겨줌
                String text=editText.getText().toString();
                extra.putString("text",text);
                intent.putExtras(extra);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        final Button button_back=(Button)findViewById(R.id.text_back_button);
        button_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*functions needed : text written by user, saving button and change the text, back button*/
}
