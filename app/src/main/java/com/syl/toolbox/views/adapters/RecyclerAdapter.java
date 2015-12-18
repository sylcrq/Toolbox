package com.syl.toolbox.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.syl.toolbox.R;

/**
 * RecyclerView Demo Adapter
 *
 * Created by shenyunlong on 12/18/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] mData;

    public RecyclerAdapter(String[] data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mData[position]);
        holder.age.setText("" + (1024 + position));
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.length;
    }

    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView age;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.name);
            this.age = (TextView) view.findViewById(R.id.age);
        }
    }
}
