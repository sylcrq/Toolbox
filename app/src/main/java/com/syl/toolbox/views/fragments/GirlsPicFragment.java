package com.syl.toolbox.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.syl.toolbox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shenyunlong on 2015/9/10.
 */
public class GirlsPicFragment extends Fragment {

    public static final String TAG = GirlsPicFragment.class.getSimpleName();

    private DisplayImageOptions mOptions;

    @Bind(R.id.image) ImageView mImage;

    public GirlsPicFragment() {
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        String content = bundle.getString("arg");

        ImageLoader.getInstance().displayImage(content, mImage, mOptions, new SimpleImageLoadingListener());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");
    }
}
