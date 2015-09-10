package com.syl.toolbox.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.syl.toolbox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shenyunlong on 2015/9/10.
 */
public class GirlsPicFragment extends Fragment {

    public static final String TAG = GirlsPicFragment.class.getSimpleName();

    @Bind(R.id.image) ImageView mImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        String content = bundle.getString("arg");

        if(!content.isEmpty()) {
            // TODO:
            ImageLoader.getInstance().displayImage(content, mImage);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");
    }
}
