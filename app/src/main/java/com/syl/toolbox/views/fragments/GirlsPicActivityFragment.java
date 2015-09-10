package com.syl.toolbox.views.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.syl.toolbox.BaiduApiRequest;
import com.syl.toolbox.R;
import com.syl.toolbox.models.GirlsPicInfoResp;
import com.syl.toolbox.utils.NetworkUtil;
import com.syl.toolbox.views.adapters.GirlsPicFragmentAdapter;
import com.viewpagerindicator.LinePageIndicator;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class GirlsPicActivityFragment extends Fragment {

    public static final String TAG = GirlsPicActivityFragment.class.getSimpleName();

    public static final String REQUEST_URL = "http://apis.baidu.com/txapi/mvtp/meinv";

    @Bind(R.id.indicator) LinePageIndicator mIndicator;
    @Bind(R.id.pager) ViewPager mPager;

    // TODO: 占位图
    public static String[] IMAGE_URL = new String[] {"", "", "", "", "", "", "", ""};

    private FragmentStatePagerAdapter mAdapter;

    public GirlsPicActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_girls_pic, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        //FragmentStatePagerAdapter adapter = new GirlsPicFragmentAdapter(getActivity().getSupportFragmentManager());
        mAdapter = new GirlsPicFragmentAdapter(getChildFragmentManager());
        mPager.setAdapter(mAdapter);

        mIndicator.setViewPager(mPager);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        String url = REQUEST_URL + "?" + "num=10";

        Map<String, String> header = new HashMap<>();
        header.put("apikey", "07b662d06be1eb930c67d26159e30e1b");

        Request<GirlsPicInfoResp> request = new BaiduApiRequest<>(url, GirlsPicInfoResp.class, header,
                new Response.Listener<GirlsPicInfoResp>() {
                    @Override
                    public void onResponse(GirlsPicInfoResp response) {
                        if(response.getCode() == 200) {
                            IMAGE_URL[0] = response.getPic0().getPicUrl();
                            IMAGE_URL[1] = response.getPic1().getPicUrl();
                            IMAGE_URL[2] = response.getPic2().getPicUrl();
                            IMAGE_URL[3] = response.getPic3().getPicUrl();
                            IMAGE_URL[4] = response.getPic4().getPicUrl();
                            IMAGE_URL[5] = response.getPic5().getPicUrl();
                            IMAGE_URL[6] = response.getPic6().getPicUrl();
                            IMAGE_URL[7] = response.getPic7().getPicUrl();

                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), ""+response.getMsg(), Toast.LENGTH_SHORT).show();
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
}
