// IMyAidlInterface.aidl
package com.nick.nicklibdemo;
import com.nick.nicklibdemo.dto.User;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    User getUser();
}