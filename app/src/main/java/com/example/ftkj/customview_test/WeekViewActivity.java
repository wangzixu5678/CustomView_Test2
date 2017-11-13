package com.example.ftkj.customview_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ftkj.customview_test.customview.weekview.ItemOnclickListener;
import com.example.ftkj.customview_test.customview.weekview.WeekView;

public class WeekViewActivity extends AppCompatActivity implements ItemOnclickListener {
    private WeekView mWeekView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        mWeekView = (WeekView) findViewById(R.id.main_weekview);
        mWeekView.setItemOnclickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        mWeekView.startAnimByClick(position);
    }
}
