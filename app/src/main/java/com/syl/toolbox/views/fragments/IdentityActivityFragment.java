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
import com.syl.toolbox.models.IdentityInfo;
import com.syl.toolbox.models.IdentityInfoResp;
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
public class IdentityActivityFragment extends Fragment {

    public static final String TAG = IdentityActivityFragment.class.getSimpleName();

    public static final String REQUEST_URL = "http://apis.baidu.com/apistore/idservice/id";

    @Bind(R.id.sex_tv) TextView mSex;
    @Bind(R.id.birthday_tv) TextView mBirthday;
    @Bind(R.id.address_tv) TextView mAddress;
    @Bind(R.id.search_text) EditText mSearchIdentity;

    @BindString(R.string.identity_search_error) String mSearchErrorTips;

    public IdentityActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_identity, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");
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
    }

    @OnClick(R.id.search_button)
    public void loadData() {

        String url = REQUEST_URL + "?" + "id" + "=";
        url += mSearchIdentity.getText();

        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", "07b662d06be1eb930c67d26159e30e1b");

        Request<IdentityInfoResp> request = new BaiduApiRequest<>(url, IdentityInfoResp.class, headers,
                new Response.Listener<IdentityInfoResp>() {
                    @Override
                    public void onResponse(IdentityInfoResp response) {
                        if(response.getErrNum() == 0) {
                            IdentityInfo info = response.getRetData();

                            mSex.setText(info.getSex());
                            mBirthday.setText(info.getBirthday());
                            mAddress.setText(info.getAddress());
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
