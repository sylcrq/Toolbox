package com.syl.toolbox.views.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.syl.toolbox.services.LocalService;
import com.syl.toolbox.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class LocalServiceTestActivityFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = LocalServiceTestActivityFragment.class.getSimpleName();

    @Bind(R.id.start_service) Button mStartButton;
    @Bind(R.id.stop_service) Button mStopButton;
    @Bind(R.id.bind_service) Button mBindButton;
    @Bind(R.id.unbind_service) Button mUnbindButton;

    private boolean mIsBound;
    private LocalService mBoundService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + name);

            mBoundService = ((LocalService.LocalBinder)service).getService();

            Toast.makeText(getActivity(), R.string.local_service_connected, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: " + name);

            mBoundService = null;
            Toast.makeText(getActivity(), R.string.local_service_disconnected, Toast.LENGTH_SHORT).show();
        }
    };

    public LocalServiceTestActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_local_service_test, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated");

        mStartButton.setOnClickListener(this);
        mStopButton.setOnClickListener(this);
        mBindButton.setOnClickListener(this);
        mUnbindButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        doUnbindService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                doStartService();
                break;
            case R.id.stop_service:
                doStopService();
                break;
            case R.id.bind_service:
                doBindService();
                break;
            case R.id.unbind_service:
                doUnbindService();
                break;
            default:
                break;
        }
    }

    public void doStartService() {
        getActivity().startService(new Intent(getActivity(), LocalService.class));
    }

    public void doStopService() {
        getActivity().stopService(new Intent(getActivity(), LocalService.class));
    }

    public void doBindService() {
        getActivity().bindService(new Intent(getActivity(), LocalService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void doUnbindService() {
        if(mIsBound) {
            getActivity().unbindService(mConnection);
            mIsBound = false;
        }
    }
}
