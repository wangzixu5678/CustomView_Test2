package com.example.ftkj.customview_test;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ftkj.customview_test.customview.CakeBeen;
import com.example.ftkj.customview_test.customview.CakeView;

import java.util.ArrayList;

public class CakeViewActivity extends AppCompatActivity {
    private String[] names = {"php", "object-c", "c", "c++", "java", "android", "linux"};
    private float[] values = {2f, 2f, 3f, 4f, 5f, 6f, 7f};
    private int[] colArrs = {Color.RED, Color.parseColor("#4ebcd3"), Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.parseColor("#f68b2b"), Color.parseColor("#6fb30d")};//圆弧颜色
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_view);
        CakeView cakeView = (CakeView) findViewById(R.id.main_cakeview);
        ArrayList<CakeBeen> list = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            CakeBeen cakeBeen = new CakeBeen();
            cakeBeen.value = values[i];
            cakeBeen.mColor = colArrs[i];
            cakeBeen.name = names[i];
            list.add(cakeBeen);
        }
        cakeView.setData(list);
    }
}
