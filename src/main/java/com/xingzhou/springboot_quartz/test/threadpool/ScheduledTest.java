package com.xingzhou.springboot_quartz.test.threadpool;

import java.util.Date;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * ScheduledTest 定时任务测试类
 *
 * @author xingzhou
 * @version 2023/02/13 17:30
 **/
public class ScheduledTest {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);

        // 按计划执行
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("执行任务1，线程名:" + Thread.currentThread().getName() + "执行时间" + new Date());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MILLISECONDS);
    }

    public RunnableScheduledFuture<?> take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                RunnableScheduledFuture<?> first = queue[0];
                if (first == null)
                    available.await();
                else {
                    long delay = first.getDelay(NANOSECONDS);
                    if (delay <= 0L)
                        return finishPoll(first);
                    first = null; // don't retain ref while waiting
                    if (leader != null)
                        available.await();
                    else {
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;
                        try {
                            available.awaitNanos(delay);
                        } finally {
                            if (leader == thisThread)
                                leader = null;
                        }
                    }
                }
            }
        } finally {
            if (leader == null && queue[0] != null)
                available.signal();
            lock.unlock();
        }
    }

    public RunnableScheduledFuture<?> take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                RunnableScheduledFuture<?> first = queue[0];
                // 若是沒有任務，就讓線程在available條件下等待。
                if (first == null)
                    available.await();
                else {
                    // 獲取任務的剩餘延時時間
                    long delay = first.getDelay(NANOSECONDS);
                    // 若是延時時間到了，就返回這個任務，用來執行。
                    if (delay <= 0)
                        return finishPoll(first);
                    // 將first設置爲null，當線程等待時，不持有first的引用
                    first = null; // don't retain ref while waiting

                    // 若是仍是原來那個等待隊列頭任務的線程，
                    // 說明隊列頭任務的延時時間尚未到，繼續等待。
                    if (leader != null)
                        available.await();
                    else {
                        // 記錄一下當前等待隊列頭任務的線程
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;
                        try {
                            // 當任務的延時時間到了時，可以自動超時喚醒。
                            available.awaitNanos(delay);
                        } finally {
                            if (leader == thisThread)
                                leader = null;
                        }
                    }
                }
            }
        } finally {
            if (leader == null && queue[0] != null) // 喚醒等待任務的線程
                available.signal();
            ock.unlock();
        }
    }

    public RunnableScheduledFuture<?> take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (;;) {
                RunnableScheduledFuture<?> first = queue[0];
                // 若是没有任务，就让线程在available条件下等待。
                if (first == null)
                    available.await();
                else {
                    // 获取任务的剩余延时时间
                    long delay = first.getDelay(NANOSECONDS);
                    // 若是延时时间到了，就返回这个任务，用来执行。
                    if (delay <= 0)
                        return finishPoll(first);
                    // 将first设置爲null，当线程等待时，不持有first的引用
                    first = null; // don't retain ref while waiting

                    // 若是仍是原来那个等待队列头任务的线程，
                    // 说明队列头任务的延时时间尚未到，继续等待。
                    if (leader != null)
                        available.await();
                    else {
                        // 记录一下当前等待队列头任务的线程
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;
                        try {
                            // 当任务的延时时间到了时，可以自动超时唤醒。
                            available.awaitNanos(delay);
                        } finally {
                            if (leader == thisThread)
                                leader = null;
                        }
                    }
                }
            }
        } finally {
            if (leader == null && queue[0] != null) // 唤醒等待任务的线程
                available.signal();
            ock.unlock();
        }
    }

}
