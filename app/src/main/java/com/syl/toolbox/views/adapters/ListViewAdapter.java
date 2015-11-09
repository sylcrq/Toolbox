package com.syl.toolbox.views.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.syl.toolbox.R;
import com.syl.toolbox.models.PersonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyunlong on 2015/9/16.
 */
public class ListViewAdapter extends BaseAdapter {

    public static final String TAG = ListViewAdapter.class.getSimpleName();

    public static final int VIEW_TYPE_COUNT = 2;

    private List<PersonInfo> mData;
    private Context mContext;

    public ListViewAdapter(Context context, List<PersonInfo> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: position="+position);

        ViewHolder viewHolder;

        if(convertView == null) {
            Log.d(TAG, "getView: convertView is NULL");

            if(getItemViewType(position) == 0) {
                convertView = View.inflate(mContext, R.layout.list_view_item, null);
            } else if(getItemViewType(position) == 1) {
                convertView = View.inflate(mContext, R.layout.list_view_item_2, null);
            }

            viewHolder = new ViewHolder();
            viewHolder.iv_avatar = (ImageView) convertView.findViewById(R.id.avatar);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.tv_age = (TextView) convertView.findViewById(R.id.age);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PersonInfo info = (PersonInfo) getItem(position);
        viewHolder.tv_name.setText(info.getName());
        viewHolder.tv_age.setText(String.valueOf(info.getAge()));

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
//        return super.getViewTypeCount();
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        PersonInfo info = (PersonInfo) getItem(position);

        return info.getType();
    }

    private static class ViewHolder {
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_age;
    }
}
