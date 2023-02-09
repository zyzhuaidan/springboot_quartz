package com.xingzhou.springboot_quartz.test;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        System.out.println(time + "===> 正在下载最新更新的爱情动作片...");
    }
}

