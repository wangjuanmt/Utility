package com.amuos.thread;

import org.junit.Test;

/**
 * Created by juan.wang on 11/21/16.
 */
public class ThreadVolatile {
    /**
     * volatile是一种弱的同步手段,
     */
    static volatile int i = 0, j = 0;
    @Test
    public void test(){
        Runnable runnable = () -> {
            one();
            two();
        };
        for (int i = 0; i < 10; i++){
            new Thread(runnable).start();
        }
    }

    static void one() {
        i++;
        j++;
    }
    static void two() {
        System.out.println("i=" + i + " j=" + j);
    }
}
