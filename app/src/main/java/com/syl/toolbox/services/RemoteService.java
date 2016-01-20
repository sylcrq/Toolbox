package com.syl.toolbox.services;

import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class RemoteService extends Service {

    public static final String TAG = RemoteService.class.getSimpleName();

    private List<Client> mClients = new ArrayList<>();

    private RemoteCallbackList<IClientCallback> mCallbackList = new RemoteCallbackList<>();

    public IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            // Do Nothing
        }

        @Override
        public int getPid() throws RemoteException {
            Log.d(TAG, "[" + Process.myPid() + "-" + Thread.currentThread().getId() + "]" + "getPid");

            return Process.myPid();
        }

        @Override
        public List<String> getClients() throws RemoteException {
            Log.d(TAG, "[" + Process.myPid() + "-" + Thread.currentThread().getId() + "]" + "getClients # " + mClients);

            List<String> userList = new ArrayList<>();

            for(Client client : mClients) {
                userList.add(client.mName);
            }
            return userList;
        }

        @Override
        public void join(IBinder token, String user) throws RemoteException {
            if(findClient(token) != null) {
                Log.d(TAG, "[" + Process.myPid() + "-" + Thread.currentThread().getId() + "]" + " join # has joined");
                return;
            }

            Client client = new Client(user, token);
            Log.d(TAG, "[" + Process.myPid() + "-" + Thread.currentThread().getId() + "]" + " join # " + client);
            token.linkToDeath(client, 0);
            mClients.add(client);

            notifyClient(user, true);
        }

        @Override
        public void leave(IBinder token, String user) throws RemoteException {
            Client client = findClient(token);
            if(client != null) {
                Log.d(TAG, "[" + Process.myPid() + "-" + Thread.currentThread().getId() + "]" + " leave # " + client);

                mClients.remove(client);
                client.mToken.unlinkToDeath(client, 0);
            } else {
                Log.d(TAG, "[" + Process.myPid() + "-" + Thread.currentThread().getId() + "]" + " leave # has left");
            }

            notifyClient(user, false);
        }

        @Override
        public void registerClientCallback(IClientCallback cb) throws RemoteException {
            mCallbackList.register(cb);
        }

        @Override
        public void unregisterClientCallback(IClientCallback cb) throws RemoteException {
            mCallbackList.unregister(cb);
        }
    };

    private void notifyClient(String user, boolean isJoin) {
        int n = mCallbackList.beginBroadcast();

        try {
            for(int i=0; i<n; i++) {
                if(isJoin) {
                    mCallbackList.getBroadcastItem(i).onJoin(user);
                } else {
                    mCallbackList.getBroadcastItem(i).onLeave(user);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        mCallbackList.finishBroadcast();
    }

    private Client findClient(IBinder token) {
        for(Client client : mClients) {
            if(client.mToken == token) {
                return client;
            }
        }

        return null;
    }

    /**
     * 远程Service保存的用户Model
     */
    public final class Client implements IBinder.DeathRecipient {
        private String mName;
        private IBinder mToken;

        public Client(String name, IBinder token) {
            this.mName = name;
            this.mToken = token;
        }

        @Override
        public String toString() {
            return "Client{" +
                    "mName='" + mName + '\'' +
                    ", mToken=" + mToken +
                    '}';
        }

        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied # " + this);

            int index = mClients.indexOf(this);
            if(index < 0) {
                return;
            }
            Log.d(TAG, "Died # Client=" + mClients.get(index));
            mClients.remove(this);
        }
    }

    public RemoteService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        mCallbackList.kill();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");

        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

        Log.d(TAG, "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");

        return super.onUnbind(intent);
    }
}
