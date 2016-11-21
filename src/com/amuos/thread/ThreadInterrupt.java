package com.amuos.thread;

import org.junit.Test;

/**
 * Created by juan.wang on 11/21/16.
 * Thread interrupt() and yield()
 */
public class ThreadInterrupt {

    @Test
    public void testMyThread() throws InterruptedException {
        MyThread t = new MyThread("MyThread");
        t.start();
        Thread.sleep(10);// 睡眠100毫秒
        t.interrupt();// 中断t线程
    }

    @Test
    public void testMyThread2() throws InterruptedException {
        MyThread2 t = new MyThread2("MyThread");
        t.start();
        Thread.sleep(10);// 睡眠100毫秒
        t.interrupt();// 中断t线程
    }

    @Test
    public void yieldTest() throws InterruptedException {
        // 创建线程对象
        YieldThread t1 = new YieldThread("t1");
        YieldThread t2 = new YieldThread("t2");
        // 启动线程
        t1.start();
        t2.start();
        // 主线程休眠100毫秒
        Thread.sleep(100);
        // 终止线程
        t1.interrupt();
        t2.interrupt();
    }


    class MyThread extends Thread {
        int i = 0;
        public MyThread(String name) {
            super(name);
        }
        public void run() {
            while(true) {// 死循环，等待被中断
                System.out.println(getName() + getId() + "执行了" + ++i + "次");
            }
        }
    }

    class MyThread2 extends Thread {
        int i = 0;
        public MyThread2(String name) {
            super(name);
        }
        public void run() {
            while(!isInterrupted()) {// 当前线程没有被中断，则执行
                System.out.println(getName() + getId() + "执行了" + ++i + "次");
            }
        }
    }

    class YieldThread extends Thread {
        int i = 0;
        public YieldThread(String name) {
            super(name);
        }
        public void run() {
            while(!isInterrupted()) {
                System.out.println(getName() + "执行了" + ++i + "次");
                if(i % 10 == 0) {// 当i能对10整除时，则让步
                    Thread.yield();
                }
            }
        }
    }


}
