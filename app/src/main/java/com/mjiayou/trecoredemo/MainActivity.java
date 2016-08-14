package com.mjiayou.trecoredemo;

import android.os.Bundle;

import com.mjiayou.trecore.TCActivity;

public class MainActivity extends TCActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRightTextView("DEBUG", MENU_DEBUG);
    }
}
