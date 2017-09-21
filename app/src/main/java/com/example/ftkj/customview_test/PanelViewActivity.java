package com.example.ftkj.customview_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.example.ftkj.customview_test.customview.DensityUtil;
import com.example.ftkj.customview_test.customview.PanelView;
import com.example.ftkj.customview_test.customview.PopWindow;

public class PanelViewActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private PanelView mPanelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPanelView = (PanelView) findViewById(R.id.main_panelView);
        SeekBar seekBar = (SeekBar) findViewById(R.id.main_seekbar);
        seekBar.setOnSeekBarChangeListener(this);



    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mPanelView.setPrecent(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
