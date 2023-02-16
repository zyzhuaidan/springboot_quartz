package com.xingzhou.springboot_quartz.test.quartz;

import javax.annotation.Resource;

import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * QuartzController 控制层
 *
 * @author xingzhou
 * @version 2023/02/16 10:58
 **/
@RestController
@RequestMapping("")
public class QuartzController {
    @Resource
    private QuartzSchedulerManager quartzSchedulerManager;

    // @Description: 获取定时器信息
    @GetMapping("/info")
    public String getQuartzJob(String name, String group) {
        String info = null;
        try {
            info = quartzSchedulerManager.getJobInfo(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return info;
    }

    // @Description: 修改定时器的 执行时间
    @PostMapping("/modify")
    public boolean modifyQuartzJob(String name, String group, String time) {
        boolean flag = true;

        if (!CronExpression.isValidExpression(time)) {
            throw new RuntimeException("非法的cron表达式");
        }

        try {
            flag = quartzSchedulerManager.modifyJob(name, group, time);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    // @Description: 启动所有定时器
    @PostMapping("/start")
    public void startQuartzJob() {
        try {
            quartzSchedulerManager.startJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // @Description: 暂停指定 定时器
    @PostMapping(value = "/pause")
    public void pauseQuartzJob(String name, String group) {
        try {
            quartzSchedulerManager.pauseJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 暂停所有定时器
    @PostMapping(value = "/pauseAll")
    public void pauseAllQuartzJob() {
        try {
            quartzSchedulerManager.pauseAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // 删除指定定时器
    @PostMapping(value = "/delete")
    public void deleteJob(String name, String group) {
        try {
            quartzSchedulerManager.deleteJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}