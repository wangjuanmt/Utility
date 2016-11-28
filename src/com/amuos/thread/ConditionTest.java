package com.amuos.thread;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by juan.wang on 11/28/16.
 * Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用.
 */
public class ConditionTest {

    @Test
    public void testCondition() {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadExecute(business, "sub");
            }
        }).start();
        threadExecute(business, "main");
    }

    public static void threadExecute(Business business, String threadType) {
        for(int i = 0; i < 100; i++) {
            try {
                if("main".equals(threadType)) {
                    business.main(i);
                } else {
                    business.sub(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在Condition中，用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()，
     * 传统线程的通信方式，Condition都可以实现，
     * 这里注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
     */
    class Business {
        private boolean bool = true;
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        public /*synchronized*/ void main(int loop) throws InterruptedException {
            lock.lock();
            try {
                while(bool) {
                    condition.await();//this.wait();
                }
                for(int i = 0; i < 100; i++) {
                    System.out.println("main thread seq of " + i + ", loop of " + loop);
                }
                bool = true;
                condition.signal();//this.notify();
            } finally {
                lock.unlock();
            }
        }
        public /*synchronized*/ void sub(int loop) throws InterruptedException {
            lock.lock();
            try {
                while(!bool) {
                    condition.await();//this.wait();
                }
                for(int i = 0; i < 10; i++) {
                    System.out.println("sub thread seq of " + i + ", loop of " + loop);
                }
                bool = false;
                condition.signal();//this.notify();
            } finally {
                lock.unlock();
            }
        }
    }

}
