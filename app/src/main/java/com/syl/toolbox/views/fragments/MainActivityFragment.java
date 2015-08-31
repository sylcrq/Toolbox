package com.syl.toolbox.views.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.syl.toolbox.BaiduApiRequest;
import com.syl.toolbox.R;
import com.syl.toolbox.models.WeatherInfo;
import com.syl.toolbox.models.WeatherInfoResp;
import com.syl.toolbox.utils.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String TAG = MainActivityFragment.class.getSimpleName();

    private TextView mCity;
    private TextView mWeather;
    private TextView mTemperature;
    private TextView mTime;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        // TODO: DataBinding
        mCity = (TextView) view.findViewById(R.id.city_tv);
        mWeather = (TextView) view.findViewById(R.id.weather_tv);
        mTemperature = (TextView) view.findViewById(R.id.temperature_tv);
        mTime = (TextView) view.findViewById(R.id.time_tv);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        final String url = "http://apis.baidu.com/apistore/weatherservice/weather" + "?" + "citypinyin" + "=" + "beijing";

//        Request<String> request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", "07b662d06be1eb930c67d26159e30e1b");

        Request<WeatherInfoResp> request = new BaiduApiRequest<>(url, WeatherInfoResp.class, headers,
                new Response.Listener<WeatherInfoResp>() {
                    @Override
                    public void onResponse(WeatherInfoResp response) {
                        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();

                        WeatherInfo info = response.getRetData();

                        mCity.setText(info.getCity());
                        mWeather.setText(info.getWeather());
                        mTemperature.setText(info.getTemp());
                        mTime.setText(info.getDate()+" "+info.getTime());
                    }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Error
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                }
        });

        NetworkUtil.getInstance(getActivity()).addToRequestQueue(request);
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

        // TODO: Cancel Network Task
    }
}
