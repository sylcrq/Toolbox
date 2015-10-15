package com.syl.toolbox.views.activities.launchMode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.syl.toolbox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleTaskAActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = SingleTaskAActivity.class.getSimpleName();

    @Bind(R.id.start_a) Button mStartAButton;
    @Bind(R.id.start_b) Button mStartBButton;
    @Bind(R.id.start_c) Button mStartCButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode_test);

        Log.d(TAG, "Task " + getTaskId() + " # onCreate: " + this);
        ButterKnife.bind(this);

        mStartAButton.setOnClickListener(this);
        mStartBButton.setOnClickListener(this);
        mStartCButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: " + this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Log.d(TAG, "onResume: " + this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Log.d(TAG, "onPause: " + this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Log.d(TAG, "onStart: " + this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Log.d(TAG, "onStop: " + this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d(TAG, "onNewIntent: " + this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_a:
                doStartActivity(SingleTaskAActivity.class);
                break;
            case R.id.start_b:
                doStartActivity(SingleTaskBActivity.class);
                break;
            case R.id.start_c:
                doStartActivity(SingleTaskCActivity.class);
                break;
        }
    }

    private void doStartActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
