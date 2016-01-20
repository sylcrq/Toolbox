// IClientCallback.aidl
package com.syl.toolbox.services;

// Declare any non-default types here with import statements

interface IClientCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void onJoin(String user);
    void onLeave(String user);
}
