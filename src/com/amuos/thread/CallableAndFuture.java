package com.amuos.thread;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Callable接口类似于Runnable，但是Runnable不会返回结果，并且无法抛出返回结果的异常，而Callable功能更强大一些，被线程执行后，可以返回值，
 * 这个返回值可以被Future拿到，也就是说，Future可以拿到异步执行任务的返回值
 * Created by juan.wang on 11/23/16.
 */
public class CallableAndFuture {

    @Test
    public void test1() {
        Callable callable = new Callable() {
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        };
        /**
         * FutureTask实现了两个接口，Runnable和Future，所以它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值
         */
        FutureTask future = new FutureTask(callable);
        new Thread(future).start();
        try {
            Thread.sleep(5000);// 可能做一些事情
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        // 通过ExecutorService的submit方法执行Callable，并返回Future
        Future future = threadPool.submit(new Callable() {
            public Integer call() throws Exception {
                return new Random().nextInt(100);
            }
        });
        try {
            Thread.sleep(5000);// 可能做一些事情
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService cs = new ExecutorCompletionService(threadPool);
        for(int i = 1; i < 5; i++) {
            final int taskID = i;
            cs.submit(new Callable() {
                public Integer call() throws Exception {
                    return taskID;
                }
            });
        }
        // 可能做一些事情
        for(int i = 1; i < 5; i++) {
            try {
                System.out.println(cs.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
