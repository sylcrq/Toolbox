package com.syl.toolbox.views.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.syl.toolbox.services.IRemoteService;
import com.syl.toolbox.services.LocalService;
import com.syl.toolbox.R;
import com.syl.toolbox.services.MessengerService;
import com.syl.toolbox.services.RemoteService;

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
    @Bind(R.id.call) Button mCallButton;
    @Bind(R.id.bind_remote_service) Button mBindRemoteButton;
    @Bind(R.id.unbind_remote_service) Button mUnbindRemoteButton;
    @Bind(R.id.say_hello) Button mHelloButton;
    @Bind(R.id.bind_aidl_service) Button mBindAIDLButton;
    @Bind(R.id.unbind_aidl_service) Button mUnbindAIDLButton;
    @Bind(R.id.call_aidl) Button mCallAIDLButton;

    private Activity mActivity;

    /* Local Service */
    private boolean mIsBound = false;
    private LocalService mBoundService;


    /**
     * Defines callbacks for service binding, passed to bindService()
     *
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + name);

            Toast.makeText(mActivity, R.string.local_service_connected, Toast.LENGTH_SHORT).show();

            mBoundService = ((LocalService.LocalBinder)service).getService();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: " + name);

            Toast.makeText(mActivity, R.string.local_service_disconnected, Toast.LENGTH_SHORT).show();

            mBoundService = null;
            mIsBound = false;
        }
    };

    /* Remote Service */
    private Messenger mRemoteService;
    private boolean mRemoteServiceIsBound = false;

    /**
     * Class for interacting with the main interface of the service.
     *
     */
    private ServiceConnection mRemoteConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected # " + name);

            mRemoteService = new Messenger(service);
            mRemoteServiceIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected # " + name);

            mRemoteService = null;
            mRemoteServiceIsBound = false;
        }
    };

    /* AIDL Remote Service */
    private IRemoteService mAIDLService;
    private boolean mAIDLIsBound = false;

    private ServiceConnection mAIDLConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected # " + name);

            mAIDLService = IRemoteService.Stub.asInterface(service);
            mAIDLIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected # " + name);

            mAIDLService = null;
            mAIDLIsBound = false;
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
        mCallButton.setOnClickListener(this);
        mBindRemoteButton.setOnClickListener(this);
        mUnbindRemoteButton.setOnClickListener(this);
        mHelloButton.setOnClickListener(this);
        mBindAIDLButton.setOnClickListener(this);
        mUnbindAIDLButton.setOnClickListener(this);
        mCallAIDLButton.setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mActivity = activity;
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
            case R.id.call:
                callServiceMethod();
                break;
            case R.id.bind_remote_service:
                bindRemoteService();
                break;
            case R.id.unbind_remote_service:
                unbindRemoteService();
                break;
            case R.id.say_hello:
                sayHello();
                break;
            case R.id.bind_aidl_service:
                bindAIDLService();
                break;
            case R.id.unbind_aidl_service:
                unbindAIDLService();
                break;
            case R.id.call_aidl:
                callAIDL();
                break;
            default:
                break;
        }
    }

    /* Local Service */
    public void doStartService() {
        Intent intent = new Intent(mActivity, LocalService.class);
        mActivity.startService(intent);
    }

    public void doStopService() {
        Intent intent = new Intent(mActivity, LocalService.class);
        mActivity.stopService(intent);
    }

    public void doBindService() {
        Intent intent = new Intent(mActivity, LocalService.class);
        mActivity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//        mIsBound = true;
    }

    public void doUnbindService() {
        if(mIsBound) {
            mActivity.unbindService(mConnection);
            mIsBound = false;
        }
    }

    public void callServiceMethod() {
        if(mIsBound) {
            int num = mBoundService.getRandomNumber();
            Toast.makeText(mActivity, "Random Number = " + num, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mActivity, "还没有绑定到一个Service !!!", Toast.LENGTH_SHORT).show();
        }
    }

    /* Remote Service */
    public void bindRemoteService() {
        Intent intent = new Intent(mActivity, MessengerService.class);
        mActivity.bindService(intent, mRemoteConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindRemoteService() {
        if(mRemoteServiceIsBound) {
            mActivity.unbindService(mRemoteConnection);
            mRemoteServiceIsBound =false;
        }
    }

    public void sayHello() {
        if(mRemoteServiceIsBound) {
            // Create and send a message to the service, using a supported 'what' value
            Message message = Message.obtain(null, 1024, 0, 0);
            try {
                mRemoteService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, "还没有绑定到一个Service !!!", Toast.LENGTH_SHORT).show();
        }
    }

    /* AIDL Remote Service */
    public void bindAIDLService() {
        Intent intent = new Intent(mActivity, RemoteService.class);
        mActivity.bindService(intent, mAIDLConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindAIDLService() {
        if(mAIDLIsBound) {
            mActivity.unbindService(mAIDLConnection);
            mAIDLIsBound = false;
        }
    }

    public void callAIDL() {
        if(mAIDLIsBound) {
            int pid = 0;

            try {
                pid = mAIDLService.getPid();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            Toast.makeText(mActivity, "AIDL Service ProcessId=" + pid, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mActivity, "还没有绑定到一个Service !!!", Toast.LENGTH_SHORT).show();
        }
    }
}
