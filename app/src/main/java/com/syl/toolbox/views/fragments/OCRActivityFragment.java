package com.syl.toolbox.views.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syl.toolbox.R;
import com.syl.toolbox.network.MyNetwork;

import org.apache.http.HttpEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class OCRActivityFragment extends Fragment {

    public static final String TAG = OCRActivityFragment.class.getSimpleName();

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
    }


}
