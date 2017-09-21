package com.example.ftkj.customview_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ftkj.customview_test.customview.patternlockview.LockView;

public class LockViewActivity extends AppCompatActivity {

    private LockView mLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_view);

        mLockView = (LockView) findViewById(R.id.ac_lockview);




    }

    public void getPassword(View view) {
        String password = mLockView.getPassword();
        Toast.makeText(this, ""+password, Toast.LENGTH_SHORT).show();
    }
}
