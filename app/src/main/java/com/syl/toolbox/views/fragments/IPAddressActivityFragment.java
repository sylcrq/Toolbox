package com.syl.toolbox.views.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.syl.toolbox.BaiduApiRequest;
import com.syl.toolbox.R;
import com.syl.toolbox.models.IPAddressInfo;
import com.syl.toolbox.models.IPAddressInfoResp;
import com.syl.toolbox.utils.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class IPAddressActivityFragment extends Fragment {

    public static final String TAG = IPAddressActivityFragment.class.getSimpleName();

    public static final String REQUEST_URL = "http://apis.baidu.com/apistore/iplookupservice/iplookup";

    @Bind(R.id.ip_tv) TextView mIP;
    @Bind(R.id.country_tv) TextView mCountry;
    @Bind(R.id.city_tv) TextView mCity;
    @Bind(R.id.carrier_tv) TextView mCarrier;
    @Bind(R.id.search_text) EditText mSearchIPAddress;

    @BindString(R.string.ipaddress_search_error) String mSearchErrorTips;

    public IPAddressActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_ipaddress, container, false);
        ButterKnife.bind(this, view);

        return view;
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
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");
    }

    @OnClick(R.id.search_button)
    public void loadData() {

        String url = REQUEST_URL + "?" + "ip" + "=";
        url += mSearchIPAddress.getText();

        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", "07b662d06be1eb930c67d26159e30e1b");

        final Request<IPAddressInfoResp> request = new BaiduApiRequest<>(url, IPAddressInfoResp.class, headers,
                new Response.Listener<IPAddressInfoResp>() {
                    @Override
                    public void onResponse(IPAddressInfoResp response) {
                        if(response.getErrNum() == 0) {
                            IPAddressInfo info = response.getRetData();

                            mIP.setText(info.getIp());
                            mCountry.setText(info.getCountry());
                            mCity.setText(info.getCity());
                            mCarrier.setText(info.getCarrier());
                        } else {
                            // Error
                            Toast.makeText(getActivity(), "Error: "+mSearchErrorTips, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();
                }
        });

        request.setTag(TAG);

        NetworkUtil.getInstance(getActivity()).addToRequestQueue(request);
    }
}
