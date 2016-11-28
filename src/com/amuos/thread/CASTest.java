package com.amuos.thread;

import org.junit.Test;
import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by juan.wang on 11/28/16.
 * CAS 指的是现代 CPU 广泛支持的一种对内存中的共享数据进行操作的一种特殊指令。这个指令会对内存中的共享数据做原子的读写操作。
 * CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则返回V。
 * 这是一种乐观锁的思路，它相信在它修改之前，没有其它线程去修改它；
 * 而Synchronized是一种悲观锁，它认为在它修改之前，一定会有其它线程去修改它，悲观锁效率很低。
 *
 * AtomicInteger利用CAS实现原子性操作。
 * java.lang.Error: java.lang.SecurityException: Unsafe
 */
public class CASTest {

    // volatile保证了变量的内存可见性，也就是所有工作线程中同一时刻都可以得到一致的值。
    private volatile int value;
    private static final Unsafe unsafe;
    private static final long valueOffset;// 注意是静态的

    static {
        try {
            // setup to use Unsafe.compareAndSwapInt for updates
            unsafe = Unsafe.getUnsafe();
            valueOffset = unsafe.objectFieldOffset
                    (AtomicInteger.class.getDeclaredField("value"));// 反射出value属性，获取其在内存中的位置
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    public final int get() {
        return value;
    }

    public final int incrementAndGet() {
        for (;;) { // 这样优于while(true)
            int current = get();
            int next = current + 1;
            if (compareAndSet(current, next))
                return next;
        }
    }
    public final int decrementAndGet() {
        for (;;) {
            int current = get();
            int next = current - 1;
            if (compareAndSet(current, next))
                return next;
        }
    }

    @Test
    public void main(){
        for (int i = 0; i < 100; i++){
            if (i % 3 == 0){
                decrementAndGet();
            }
            incrementAndGet();
        }
    }

}
