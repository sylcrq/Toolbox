package com.syl.toolbox.views.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.syl.toolbox.BaiduApiRequest;
import com.syl.toolbox.R;
import com.syl.toolbox.models.WeatherInfo;
import com.syl.toolbox.models.WeatherInfoResp;
import com.syl.toolbox.utils.NetworkUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherActivityFragment extends Fragment {

    public static final String TAG = WeatherActivityFragment.class.getSimpleName();

    public static final String REQUEST_URL = "http://apis.baidu.com/apistore/weatherservice/cityname";

    @Bind(R.id.city_tv) TextView mCity;
    @Bind(R.id.weather_tv) TextView mWeather;
    @Bind(R.id.temperature_tv) TextView mTemperature;
    @Bind(R.id.time_tv) TextView mTime;
    @Bind(R.id.search_text) EditText mSearchCity;

    @BindString(R.string.search_error) String mSearchErrorTips;

    public WeatherActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        mSearchCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if(actionId == EditorInfo.IME_ACTION_GO) {
                    loadData();
                    handled = true;
                }

                return handled;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
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

        // Cancel Network Task
        NetworkUtil.getInstance(getActivity()).cancelAllWithTAG(TAG);
    }

    @OnClick(R.id.search_button)
    public void loadData() {
        String url = REQUEST_URL + "?" + "cityname" + "=";

        try {
            url += URLEncoder.encode(mSearchCity.getText().toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", "07b662d06be1eb930c67d26159e30e1b");

        Request<WeatherInfoResp> request = new BaiduApiRequest<>(url, WeatherInfoResp.class, headers,
                new Response.Listener<WeatherInfoResp>() {
                    @Override
                    public void onResponse(WeatherInfoResp response) {

                        if(response.getErrNum() == 0) {
                            WeatherInfo info = response.getRetData();

                            mCity.setText(info.getCity());
                            mWeather.setText(info.getWeather());
                            mTemperature.setText(info.getTemp());
                            mTime.setText(info.getDate() + " " + info.getTime());
                        } else {
                            Toast.makeText(getActivity(), mSearchErrorTips, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error
                Toast.makeText(getActivity(), "Error: "+mSearchErrorTips, Toast.LENGTH_SHORT).show();
            }
        });

        request.setTag(TAG);

        NetworkUtil.getInstance(getActivity()).addToRequestQueue(request);
    }
}
