package com.xingzhou.springboot_quartz.test.timer;

import java.util.Date;
import java.util.TaskQueue;
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
                System.out.println("定时任务调度一次!" + new Date(System.currentTimeMillis()));
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

class TimerThread extends Thread {
    boolean newTasksMayBeScheduled = true;
    private TaskQueue queue;

    TimerThread(TaskQueue queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            mainLoop();
        } finally {
            // Someone killed this Thread, behave as if Timer cancelled
            synchronized (queue) {
                newTasksMayBeScheduled = false;
                queue.clear();  // Eliminate obsolete references
            }
        }

    /**
     * The main timer loop. (See class comment.)
     */
    private void mainLoop() {
        while (true) {
            try {
                TimerTask task;
                boolean taskFired;
                synchronized (queue) {
                    // Wait for queue to become non-empty
                    while (queue.isEmpty() && newTasksMayBeScheduled)
                        queue.wait();
                    if (queue.isEmpty())
                        break; // Queue is empty and will forever remain; die

                    // Queue nonempty; look at first evt and do the right thing
                    long currentTime, executionTime;
                    task = queue.getMin();
                    synchronized (task.lock) {
                        if (task.state == TimerTask.CANCELLED) {
                            queue.removeMin();
                            continue; // No action required, poll queue again
                        }
                        currentTime = System.currentTimeMillis();
                        executionTime = task.nextExecutionTime;
                        if (taskFired = (executionTime <= currentTime)) {
                            if (task.period == 0) { // Non-repeating, remove
                                queue.removeMin();
                                task.state = TimerTask.EXECUTED;
                            } else { // Repeating task, reschedule
                                queue.rescheduleMin(
                                    task.period < 0 ? currentTime - task.period : executionTime + task.period);
                            }
                        }
                    }
                    if (!taskFired) // Task hasn't yet fired; wait
                        queue.wait(executionTime - currentTime);
                }
                if (taskFired) // Task fired; run it, holding no locks
                    task.run();
            } catch (InterruptedException e) {
            }
        }
    }
}
