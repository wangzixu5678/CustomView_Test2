package com.example.ftkj.customview_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ftkj.customview_test.R;
import com.example.ftkj.customview_test.customview.SimpleLineChart;

import java.util.HashMap;

public class LineChartActivity extends AppCompatActivity {
private  SimpleLineChart mSimpleLineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        mSimpleLineChart = (SimpleLineChart) findViewById(R.id.main_linechat);
        String[] xItem = {"1","2","3","4","5","6","7"};

        String[] yItem = {"10k","20k","30k","40k","50k"};

        if(mSimpleLineChart == null)

            Log.e("wing","null!!!!");

        mSimpleLineChart.setXItem(xItem);

        mSimpleLineChart.setYItem(yItem);

        HashMap<Integer,Integer> pointMap = new HashMap();

        for(int i = 0;i<xItem.length;i++){

            pointMap.put(i, (int) (Math.random()*5));

        }

        mSimpleLineChart.setData(pointMap);
    }
    }

