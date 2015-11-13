package com.syl.toolbox.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by shenyunlong on 11/12/15.
 */
public class SkinManager {

    public static final String TAG = SkinManager.class.getSimpleName();

    private static SkinManager instance;
    private Context context;
    private Resources resources;

    private SkinManager(Context context) {
        this.context = context;
    }

    public static SkinManager getInstance(Context context) {
        if(instance == null) {
            instance = new SkinManager(context);
        }

        return instance;
    }

    public void init(String skinPath) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method method = assetManager.getClass().getMethod("addAssetPath", String.class);
        method.invoke(assetManager, skinPath);

        Resources res = context.getResources();
        resources = new Resources(assetManager, res.getDisplayMetrics(), res.getConfiguration());
    }

    public Drawable getDrawableByName(String name, String packageName) {
        int resId = resources.getIdentifier(name, "drawable", packageName);

        Log.d(TAG, ""+packageName+":"+name+" # resId="+resId);

        return resources.getDrawable(resId);
    }
}
