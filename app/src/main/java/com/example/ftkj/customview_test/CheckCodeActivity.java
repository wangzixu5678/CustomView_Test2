package com.example.ftkj.customview_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ftkj.customview_test.customview.CheckCodeView;

public class CheckCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);
        CheckCodeView checkCodeView = (CheckCodeView) findViewById(R.id.main_checkCode);
        checkCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AAAA", "onClick: "+"hhha");
            }
        });
    }
}
