package com.syl.toolbox.views.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.syl.toolbox.views.fragments.GirlsPicFragment;
import com.syl.toolbox.views.fragments.GirlsPicActivityFragment;

/**
 * Created by shenyunlong on 2015/9/10.
 */
public class GirlsPicFragmentAdapter extends FragmentStatePagerAdapter {

    public static final String TAG = GirlsPicFragmentAdapter.class.getSimpleName();

    public GirlsPicFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem:  position="+position);

        GirlsPicFragment fragment = new GirlsPicFragment();

        Bundle bundle = new Bundle();
        bundle.putString("arg", GirlsPicActivityFragment.IMAGE_URL[position]);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: count="+GirlsPicActivityFragment.IMAGE_URL.length);

        return GirlsPicActivityFragment.IMAGE_URL.length;
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d(TAG, "getItemPosition");

        return POSITION_NONE;
    }
}
