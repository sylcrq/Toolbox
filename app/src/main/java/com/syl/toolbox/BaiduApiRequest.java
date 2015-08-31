package com.syl.toolbox;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 百度APIStore服务请求继承此类
 *
 * Created by shenyunlong on 2015/8/31.
 */
public class BaiduApiRequest<T> extends Request<T> {

    public static final String TAG = BaiduApiRequest.class.getSimpleName();

    private final Class<T> mClazz;
    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;

    public BaiduApiRequest(String url, Class<T> clazz, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);

        this.mClazz = clazz;
        this.mHeaders = headers;
        this.mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(JSON.parseObject(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
