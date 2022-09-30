package com.nick.nicklibdemo;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testCallAble(){
        CallableImpl callable = new CallableImpl();
        FutureTask<Integer> ft = new FutureTask<>(callable);
        for(int i = 0;i < 100;i++) {
            System.out.println(Thread.currentThread().getName()+" 的循环变量i的值"+i);
            if(i==20) {
                new Thread(ft,"有返回值的线程").start();
            }
        }
        try {
            System.out.println("----------------------");
            System.out.println("子线程的返回值："+ft.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}