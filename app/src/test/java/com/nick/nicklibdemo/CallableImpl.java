package com.nick.nicklibdemo;

import java.util.concurrent.Callable;

/**
 * Created by Nick on 2022/9/28 15:17.
 */
public class CallableImpl implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int i = 0;
        for(;i<100;i++)
        {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
        return i;
    }
}
