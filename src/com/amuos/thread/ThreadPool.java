package com.amuos.thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用并发包java.util.concurrent包
 * Created by juan.wang on 11/23/16.
 */
public class ThreadPool {

    /**
     *创建了一个固定大小的线程池，容量为3，然后循环执行了4个任务，由输出结果可以看到，前3个任务首先执行完，然后空闲下来的线程去执行第4个任务，
     * 在FixedThreadPool中，有一个固定大小的池，如果当前需要执行的任务超过了池大小，那么多于的任务等待状态，直到有空闲下来的线程执行任务，
     * 而当执行的任务小于池大小，空闲的线程也不会去销毁。
     */
    @Test
    public void testFixedThreadPool(){
        //创建一个可重用固定线程集合的线程池，以共享的无界队列方式来运行这些线程。
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        runTask(threadPool);
    }

    /**
     * 4个任务是交替执行的，CachedThreadPool会创建一个缓存区，将初始化的线程缓存起来，
     * 如果线程有可用的，就使用之前创建好的线程，如果没有可用的，就新创建线程，终止并且从缓存中移除已有60秒未被使用的线程。
     */
    @Test
    public void testCachedThreadPool(){
        //创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
        ExecutorService threadPool = Executors.newCachedThreadPool();
        runTask(threadPool);
    }

    /**
     * 4个任务是顺序执行的，SingleThreadExecutor得到的是一个单个的线程，这个线程会保证你的任务执行完成，
     * 如果当前线程意外终止，会创建一个新线程继续执行任务，这和我们直接创建线程不同，也和newFixedThreadPool(1)不同。
     */
    @Test
    public void testSingleThreadPool(){
        //创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        runTask(threadPool);
    }

    /**
     * ScheduledThreadPool可以定时的或延时的执行任务。
     */
    @Test
    public void testScheduledThreadPool(){
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(3);
        // 5秒后执行任务
        scheduledPool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("爆炸1");
            }
        }, 5, TimeUnit.SECONDS);
        // 5秒后执行任务，以后每2秒执行一次
        scheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("爆炸2");
            }
        }, 5, 2, TimeUnit.SECONDS);
        try{
            boolean completed = scheduledPool.awaitTermination(60L, TimeUnit.SECONDS);
            if (!completed) {
                System.out.println("At least one task timed out from GavelTask.");
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {
            scheduledPool.shutdown();// 任务执行完毕，关闭线程池
        }
    }

    public void runTask(ExecutorService threadPool){
        for(int i = 1; i < 5; i++) {
            final int taskID = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i = 1; i < 5; i++) {
                        try {
                            Thread.sleep(20);// 为了测试出效果，让每次任务执行都需要一定时间
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第" + taskID + "次任务的第" + i + "次执行");
                    }
                }
            });
        }
        try{
            threadPool.shutdown();// 任务执行完毕，关闭线程池
            boolean completed = threadPool.awaitTermination(10L, TimeUnit.SECONDS);
            if (!completed) {
                System.out.println("At least one task timed out from GavelTask.");
            }
        }catch (Exception e){
            System.out.println(e);
        }finally {

        }
    }

}

