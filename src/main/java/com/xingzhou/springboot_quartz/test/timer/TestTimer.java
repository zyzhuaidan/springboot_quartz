package com.xingzhou.springboot_quartz.test.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 类描述
 *
 * @author xingzhou
 * @version 2023/2/11 09:31
 **/
public class TestTimer {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时任务调度一次!" + "执行任务时间:" + new Date());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // 延迟 1s 打印一次
        // timer.schedule(task, 1000);
        // 延迟 1s 固定时延每隔 1s 周期打印一次
        timer.schedule(task, 1000, 1000);
        /*
        **/
        // 延迟 1s 固定速率每隔 1s 周期打印一次
        // timer.scheduleAtFixedRate(task, 1000, 1000);
    }
}
