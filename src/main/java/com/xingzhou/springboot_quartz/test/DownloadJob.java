package com.xingzhou.springboot_quartz.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DownloadJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        System.out.println(time + "===>...");
    }
}
