package com.amuos.thread;

import org.junit.Test;

/**
 * Created by juan.wang on 11/21/16.
 * Use synchronized keyword to make sure operation atomicity.（原子性）
 */
public class TraditionalThreadSynchronized {

    /**
     * There are some mis-ordered characters after printing 100 times
     */
    @Test
    public void testOutputter() {
        for (int i = 0; i < 100; i++){
            final Outputter outputter = new Outputter();
            new Thread() {
                public void run() {
                    outputter.output("zhangsan");
                }
            }.start();
            new Thread() {
                public void run() {
                    outputter.output("lisi");
                }
            }.start();
        }
    }

    class Outputter {
        public void output(String name) {
            // TODO 为了保证对name的输出不是一个原子操作，这里逐个输出name的每个字符
            for(int i = 0; i < name.length(); i++)
            System.out.print(name.charAt(i));
            // Thread.sleep(10);
        }
    }

    /**
     * Results all ordered, 'cause output method is synchronized in class Outputter2.
     */
    @Test
    public void testOutputter2() {
        for (int i = 0; i < 100; i++){
            final Outputter2 outputter = new Outputter2();
            new Thread() {
                public void run() {
                    outputter.output("zhangsan");
                }
            }.start();
            new Thread() {
                public void run() {
                    outputter.output("lisi");
                }
            }.start();
        }
    }

    /**
     * 使用synchronized修饰的方法或者代码块可以看成是一个原子操作。
     */
    class Outputter2 {
        public synchronized void output(String name) {
            // TODO 为了保证对name的输出不是一个原子操作，这里逐个输出name的每个字符
            for(int i = 0; i < name.length(); i++)
                System.out.print(name.charAt(i));
            // Thread.sleep(10);
        }
    }
}
