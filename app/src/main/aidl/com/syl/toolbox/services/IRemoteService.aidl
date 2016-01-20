// IRemoteService.aidl
package com.syl.toolbox.services;

// Declare any non-default types here with import statements

import com.syl.toolbox.services.IClientCallback;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int getPid();

    void join(IBinder token, String user);
    void leave(IBinder token, String user);
    List<String> getClients();

    void registerClientCallback(IClientCallback cb);
    void unregisterClientCallback(IClientCallback cb);
}
