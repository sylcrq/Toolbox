package com.syl.toolbox.views.activities;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.syl.toolbox.R;

public class ToastActivity extends AppCompatActivity {

    public static final String TAG = ToastActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ToastViewController viewController = new ToastViewController(getApplicationContext());
                viewController.showPopView();
            }
        });
    }

    /**
     * 控制悬浮窗的显示
     *
     */
    public static class ToastViewController implements View.OnClickListener, View.OnTouchListener {

        private Context mContext;
        private WindowManager mWindowManager;
        private View mContentView;
        private View mWholeView;

        public ToastViewController(Context context) {
            this.mContext = context;
            this.mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }

        public void showPopView() {
            Log.d(TAG, "showPopView");

            View view = View.inflate(mContext, R.layout.toast_view, null);

            mContentView = view.findViewById(R.id.content);
            mWholeView = view;

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_TOAST,
                    0,
                    PixelFormat.TRANSLUCENT);
//            params.gravity = Gravity.TOP;

            mContentView.setOnClickListener(this);
            mWholeView.setOnTouchListener(this);

            mWindowManager.addView(mWholeView, params);
        }

        public void removePopView() {
            Log.d(TAG, "removePopView");

            mWindowManager.removeView(mWholeView);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick");

            removePopView();
            Toast.makeText(mContext, "On Click", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(TAG, "onTouch");

            int x = (int) event.getX();
            int y = (int) event.getY();

            Rect rect = new Rect();
            mContentView.getGlobalVisibleRect(rect);

            if(!rect.contains(x, y)) {
                removePopView();
            }

            return false;
        }
    }
}
