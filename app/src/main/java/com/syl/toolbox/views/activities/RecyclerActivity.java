package com.syl.toolbox.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.syl.toolbox.R;
import com.syl.toolbox.views.adapters.RecyclerAdapter;

/**
 * RecyclerView Demo Activity
 *
 * Created by shenyunlong on 12/18/15.
 */
public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

//        mLayoutManager = new LinearLayoutManager(this);  // ListView
//        mLayoutManager = new GridLayoutManager(this, 2);  // GridView
        mLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);  // 瀑布流
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 测试数据
        String[] data = new String[20];
        for(int i=0; i<data.length; i++) {
            data[i] = "Facebook " + i;
        }

        mAdapter = new RecyclerAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }
}
