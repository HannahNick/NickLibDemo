package com.nick.nicklibdemo.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nick on 2022/9/20 15:15.
 */
public class User implements Parcelable {

    private String name;

    public User(){}

    public User(Parcel in) {
        name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
