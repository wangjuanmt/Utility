package com.amuos.thread;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by juan.wang on 11/23/16.
 */
public class LockTest {
    @Test
    public void testOutputter1() {
        final Outputter1 output = new Outputter1();
        new Thread() {
            public void run() {
                output.output("zhangsan");
            }
        }.start();
        new Thread() {
            public void run() {
                output.output("lisi");
            }
        }.start();
    }

    /**
     * Lock可以实现和sychronized一样的效果
     * 用sychronized修饰的方法或者语句块在代码执行完之后锁自动释放，而用Lock需要我们手动释放锁，
     * 所以为了保证锁最终被释放(发生异常情况)，要把互斥区放在try内，释放锁放在finally内。
     */
    class Outputter1 {
        private Lock lock = new ReentrantLock();// 锁对象

        public void output(String name) {
            // TODO 线程输出方法
            lock.lock();// 得到锁
            try {
                for (int i = 0; i < name.length(); i++)
                    System.out.print(name.charAt(i));
            } finally {
                lock.unlock();// 释放锁
            }
        }
    }

    @Test
    public void testData(){
        final Data data = new Data();
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        data.set(new Random().nextInt(30));
                    }
                }
            }).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        data.get();
                    }
                }
            }).start();
        }
    }

    class Data {
        private int data;// 共享数据
        public void set(int data) {
            System.out.println(Thread.currentThread().getName() + "准备写入数据");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "写入" + this.data);
        }
        public void get() {
            System.out.println(Thread.currentThread().getName() + "准备读取数据");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "读取" + this.data);
        }
    }
}



