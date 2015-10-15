package com.syl.toolbox.views.activities.launchMode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.syl.toolbox.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StandardBActivity extends AppCompatActivity {

    public static final String TAG = StandardBActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode_test);

        Log.d(TAG, "onCreate: " + this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: " + this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: " + this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: " + this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: " + this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: " + this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d(TAG, "onNewIntent: " + this);
    }

    @OnClick({R.id.start_a, R.id.start_b, R.id.start_c})
    public void OnClickStartButton(View v) {
        switch (v.getId()) {
            case R.id.start_a:
                doStartActivity(StandardAActivity.class);
                break;
            case R.id.start_b:
                doStartActivity(StandardBActivity.class);
                break;
            case R.id.start_c:
                doStartActivity(StandardCActivity.class);
                break;
        }
    }

    private void doStartActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
