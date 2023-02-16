package com.xingzhou.springboot_quartz.test.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Job2 任务2
 *
 * @author xingzhou
 * @version 2023/02/16 10:51
 **/
public class Job2 implements Job {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void before() {
        System.out.println("任务2：开始执行-" + dateFormat.format(new Date()));
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        before();
        System.out.println("任务2：业务逻辑。。。");
        after();
    }

    private void after() {
        System.out.println("任务2：执行结束");
        System.out.println();
    }
}