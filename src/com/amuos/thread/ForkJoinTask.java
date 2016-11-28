package com.amuos.thread;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by juan.wang on 11/28/16.
 * Java在JDK7之后加入了并行计算的框架Fork/Join，可以解决我们系统中大数据计算的性能问题。
 * Fork/Join采用的是分治法，Fork是将一个大任务拆分成若干个子任务，子任务分别去计算，而Join是获取到子任务的计算结果，然后合并，这个是递归的过程。
 * 子任务被分配到不同的核上执行时，效率最高。
 *
 * 计算一个超大数组所有元素的和。
 */
public class ForkJoinTask extends RecursiveTask<Integer> {
    private static final long serialVersionUID = -6196480027075657316L;
    private static final int THRESHOLD = 500000;
    private long[] array;
    private int low;
    private int high;

    public ForkJoinTask(long[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (high - low <= THRESHOLD) {
            // 小于阈值则直接计算
            for (int i = low; i < high; i++) {
                sum += array[i];
            }
        } else {
            // 1\. 一个大任务分割成两个子任务
            int mid = (low + high) >>> 1;
            ForkJoinTask left = new ForkJoinTask(array, low, mid);
            ForkJoinTask right = new ForkJoinTask(array, mid + 1, high);

            // 2\. 分别计算
            left.fork();
            right.fork();

            // 3\. 合并结果
            sum = left.join() + right.join();
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] array = genArray(10000000);
        System.out.println(Arrays.toString(array));

        // to compare
        long begin_c = System.currentTimeMillis();
        int sum_c = 0;
        for (int i = 0; i < array.length; i++){
            sum_c += array[i];
        }
        long end_c = System.currentTimeMillis();
        System.out.println(String.format("Compared result is %s, time needed %sms.", sum_c, end_c - begin_c));


        // 1\. 创建任务
        ForkJoinTask sumTask = new ForkJoinTask(array, 0, array.length - 1);
        long begin = System.currentTimeMillis();

        // 2\. 创建线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 3\. 提交任务到线程池
        forkJoinPool.submit(sumTask);

        // 4\. 获取结果
        Integer result = sumTask.get();

        long end = System.currentTimeMillis();

        System.out.println(String.format("结果 %s 耗时 %sms", result, end - begin));
    }

    private static long[] genArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Random().nextLong();
        }
        return array;
    }
}
