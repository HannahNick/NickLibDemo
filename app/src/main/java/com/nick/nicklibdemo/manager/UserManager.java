package com.nick.nicklibdemo.manager;

/**
 * Created by Nick on 2022/9/20 13:22.
 */
public class UserManager {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private UserManager(){}

    public static UserManager getInstance(){
        return Holder.sManager;
    }

    public static class Holder{
        public static final UserManager sManager = new UserManager();
    }
}
