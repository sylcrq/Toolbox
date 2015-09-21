package com.syl.toolbox.views.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.syl.toolbox.R;
import com.syl.toolbox.views.activities.GirlsPicActivity;
import com.syl.toolbox.views.activities.IPAddressActivity;
import com.syl.toolbox.views.activities.IdentityActivity;
import com.syl.toolbox.views.activities.ListViewActivity;
import com.syl.toolbox.views.activities.LocalServiceTestActivity;
import com.syl.toolbox.views.activities.WeatherActivity;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String TAG = MainActivityFragment.class.getSimpleName();

    @Bind(R.id.toolbox_list) ListView mToolBoxList;

    @BindString(R.string.title_activity_weather) String mWeatherTitle;
    @BindString(R.string.title_activity_identity) String mIdentityTitle;
    @BindString(R.string.title_activity_ipaddress) String mIPAddressTitle;
    @BindString(R.string.title_activity_girls_pic) String mGirlsPicTitle;
    @BindString(R.string.title_activity_list_view) String mListViewTitle;
    @BindString(R.string.title_activity_local_service_test) String mLocalServiceTitle;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        // TODO: adapter
        final String[] array = {mWeatherTitle,
                                mIdentityTitle,
                                mIPAddressTitle,
                                mGirlsPicTitle,
                                mListViewTitle,
                                mLocalServiceTitle};

        final Class[] clazz = {WeatherActivity.class,
                                IdentityActivity.class,
                                IPAddressActivity.class,
                                GirlsPicActivity.class,
                                ListViewActivity.class,
                                LocalServiceTestActivity.class};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);

        mToolBoxList.setAdapter(adapter);
        mToolBoxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), clazz[position]);
                startActivity(intent);
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
    }
}
