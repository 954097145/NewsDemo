package com.example.administrator.newsdemo.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.newsdemo.R;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        LogoActivity.start(GuideActivity.this);
        finish();

    }
}
