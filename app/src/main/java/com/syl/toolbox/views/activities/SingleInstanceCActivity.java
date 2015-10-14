package com.syl.toolbox.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.syl.toolbox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleInstanceCActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = SingleInstanceCActivity.class.getSimpleName();

    @Bind(R.id.start_a) Button mStartAButton;
    @Bind(R.id.start_b) Button mStartBButton;
    @Bind(R.id.start_c) Button mStartCButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_top);

        Log.d(TAG, "onCreate: " + this);
        ButterKnife.bind(this);

        mStartAButton.setOnClickListener(this);
        mStartBButton.setOnClickListener(this);
        mStartCButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_a:
                doStartActivity(SingleInstanceAActivity.class);
                break;
            case R.id.start_b:
                doStartActivity(SingleInstanceBActivity.class);
                break;
            case R.id.start_c:
                doStartActivity(SingleInstanceCActivity.class);
                break;
        }
    }

    private void doStartActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
