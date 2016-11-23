package com.amuos.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer是一种线程设施，用于安排以后在后台线程中执行的任务。可安排任务执行一次，或者定期重复执行，可以看成一个定时器，可以调度TimerTask。
 * TimerTask是一个抽象类，实现了Runnable接口，所以具备了多线程的能力。
 * 一个Timer可以调度任意多个TimerTask，它会将TimerTask存储在一个队列中，顺序调度
 * Created by juan.wang on 11/23/16.
 */
public class TimerTest {
    static class MyTimerTask1 extends TimerTask {
        public void run() {
            System.out.println("爆炸1！！！");
            new Timer().schedule(new MyTimerTask2(), 2000);
        }
    }
    static class MyTimerTask2 extends TimerTask {
        public void run() {
            System.out.println("爆炸2！！！");
            new Timer().schedule(new MyTimerTask1(), 3000);
        }
    }
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask2(), 2000);
        while(true) {
            System.out.println(new Date().getSeconds());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
