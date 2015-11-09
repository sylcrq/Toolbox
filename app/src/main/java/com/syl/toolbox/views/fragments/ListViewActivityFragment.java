package com.syl.toolbox.views.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.syl.toolbox.R;
import com.syl.toolbox.models.PersonInfo;
import com.syl.toolbox.views.adapters.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListViewActivityFragment extends Fragment {

    public static final String TAG = ListViewActivityFragment.class.getSimpleName();

    @Bind(R.id.listview) ListView mListView;

    public ListViewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        // 添加测试数据
        List<PersonInfo> data = new ArrayList<>();
        for(int i=0; i<100; i++) {
            data.add(new PersonInfo("Jake Wharton "+i, 1024+i, "", i%2));
        }

        ListViewAdapter adapter = new ListViewAdapter(getActivity(), data);
        mListView.setAdapter(adapter);
    }
}
