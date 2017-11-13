package com.example.ftkj.customview_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ftkj.customview_test.customview.MyTabLayout;

import java.util.ArrayList;
import java.util.List;

public class TablayoutActivity extends AppCompatActivity {

    private MyTabLayout mMyTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        mMyTab = (MyTabLayout) findViewById(R.id.main_tablayout);
        List<String> list = new ArrayList<>();
        list.add("哈哈");
        list.add("呵呵");
        list.add("啊哈哈哈");
        list.add("再见噢噢噢噢");
        mMyTab.addData(list);
        Log.d("AAA", "onCreate: BB");
        mMyTab.setOnItemClickListener(new MyTabLayout.onItemClick() {
            @Override
            public void itemClick(int position, String data, View view) {
                Toast.makeText(TablayoutActivity.this, ""+position + data, Toast.LENGTH_SHORT).show();
            }


        });
    }
}
