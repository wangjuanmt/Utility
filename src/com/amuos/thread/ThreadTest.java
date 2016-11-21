package com.amuos.thread;

import org.junit.Test;

/**
 * Created by juan.wang on 11/21/16.
 * Expected results in testCount (same as testCount2):
 * Thread-0-55
 * Thread-1-55
 * Thread-2-55
 * Thread-3-55
 * Thread-4-55
 * Thread-5-55
 * Thread-6-55
 * Thread-7-55
 * Thread-8-55
 * Thread-9-55
 */
public class ThreadTest {
    /**
     * Results not as expected, 'cause member variable num in class Count would be stored and used by each thread.
     */
    @Test
    public void testCount() {
        Runnable runnable = new Runnable() {
            Count count = new Count();
            public void run() {
                count.count();
            }
        };
        for(int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

    /**
     * Count is thread-unsafe.
     */
    public class Count {
        private int num;
        public void count() {
            for(int i = 1; i <= 10; i++) {
                num += i;
            }
            System.out.println(Thread.currentThread().getName() + "-" + num);
        }
    }

    /**
     * Results as expected, by defining the old member variable num into method scope.
     */
    @Test
    public void testCount2() {
        Runnable runnable = new Runnable() {
            Count2 count2 = new Count2();
            public void run() {
                count2.count2();
            }
        };
        for(int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

    /**
     * Count2 is thread-safe.
     */
    public class Count2 {
        public void count2() {
            int num = 0;
            for(int i = 1; i <= 10; i++) {
                num += i;
            }
            System.out.println(Thread.currentThread().getName() + "-" + num);
        }
    }


}
