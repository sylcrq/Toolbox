package com.syl.toolbox.utils;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 将Volley网络库封装在单例模式中
 *
 * 1. 单例线程安全？
 *
 * Created by shenyunlong on 2015/8/31.
 */
public class NetworkUtil {

    public static final String TAG = NetworkUtil.class.getSimpleName();

    private static NetworkUtil mInstance;
    private RequestQueue mRequestQueue;
    private Context mContext;

    private NetworkUtil(Context context) {
        this.mContext = context;
        // getApplicationContext() is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        this.mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    /**
     * 获取单例
     *
     * @param context
     * @return
     */
    public static synchronized NetworkUtil getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new NetworkUtil(context);
        }

        return mInstance;
    }

    /**
     * 获取volley请求队列
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * 添加Request到volley请求队列
     *
     * @param request
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> request) {
        mRequestQueue.add(request);
    }
}
