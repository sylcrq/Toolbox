package com.syl.toolbox.views.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.syl.toolbox.R;
import com.syl.toolbox.network.MyNetwork;
import com.syl.toolbox.receivers.UploadServiceReceiver;
import com.syl.toolbox.services.UploadService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class OCRActivityFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = OCRActivityFragment.class.getSimpleName();

    @Bind(R.id.upload_button) Button mUploadButton;
    @Bind(R.id.upload_progress_bar) ProgressBar mUploadProgress;

    private Activity mActivity;

    private UploadServiceReceiver mUploadReceiver = new UploadServiceReceiver() {

        @Override
        public void onUploadStart() {
            Toast.makeText(mActivity, "Upload Start", Toast.LENGTH_SHORT).show();
            mUploadProgress.setProgress(0);
        }

        @Override
        public void onUploadStop() {
            Toast.makeText(mActivity, "Upload Stop", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUploadComplete() {
            Toast.makeText(mActivity, "Upload OK", Toast.LENGTH_SHORT).show();
            mUploadProgress.setProgress(100);
        }

        @Override
        public void onUploadError() {
            Toast.makeText(mActivity, "Upload Error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateUploadProgress(int percent) {
            mUploadProgress.setProgress(percent);
        }
    };

    public OCRActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_ocr, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        mUploadButton.setOnClickListener(this);

        mUploadProgress.setMax(100);
        mUploadProgress.setProgress(0);
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        mUploadReceiver.register(getActivity());

        new Thread() {
            @Override
            public void run() {
                super.run();

                //MyNetwork.getInstance().request4Get("http://www.android.com/");

                getWeatherInfo();
                //getOCR();
            }
        }.start();
    }

    private void getWeatherInfo() {
        String cityname = "";

        try {
            cityname = URLEncoder.encode("北京", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "http://apis.baidu.com/apistore/weatherservice/cityname?cityname=" + cityname;

        Map<String, String> header = new HashMap<String, String>();
        header.put("apikey", "07b662d06be1eb930c67d26159e30e1b");

        MyNetwork.getInstance().request4Get(url, header);
    }

    private void getOCR() {
        String url = "http://apis.baidu.com/apistore/idlocr/ocr";

        Map<String, String> header = new HashMap<String, String>();
        header.put("apikey", "07b662d06be1eb930c67d26159e30e1b");

        MyNetwork.getInstance().request4Post(url, header);
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");

        mUploadReceiver.unregister(getActivity());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(TAG, "onAttach");

        mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_button:
                Log.d(TAG, "UI Thread="+Thread.currentThread().getId());
                UploadService.startActionUpload(getActivity());
                break;
        }
    }
}
