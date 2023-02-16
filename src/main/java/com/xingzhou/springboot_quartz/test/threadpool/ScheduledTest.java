package com.xingzhou.springboot_quartz.test.threadpool;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
}
