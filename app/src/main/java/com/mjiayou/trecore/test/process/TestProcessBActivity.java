package com.mjiayou.trecore.test.process;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mjiayou.trecore.TCActivity;
import com.mjiayou.trecoredemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TestProcessBActivity extends TCActivity {

    @InjectView(R.id.btn_process_c)
    Button mBtnProcessC;

    @OnClick(R.id.btn_process_c)
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.btn_process_c:
                TestProcessCActivity.open(mContext);
                break;
        }
    }

    /**
     * startActivity
     */
    public static void open(Context context) {
        Intent intent = new Intent(context, TestProcessBActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_process_b);
        ButterKnife.inject(this);

        setTitle(TAG);
    }
}
