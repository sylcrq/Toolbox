package com.syl.toolbox.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;


public class MessengerService extends Service {

    public static final String TAG = MessengerService.class.getSimpleName();

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     *
     */
    private Messenger messenger = new Messenger(new IncomingHandler());

    public MessengerService() {
    }

    /**
     * Handler of incoming messages from clients.
     *
     */
    public class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == 1024) {
                Toast.makeText(MessengerService.this, "Hello ?", Toast.LENGTH_SHORT).show();
            } else {
                super.handleMessage(msg);
            }
        }
    }

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
