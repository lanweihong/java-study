package com.lanweihong.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:28
 */
public class EatJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // 通过 jobExecutionContext 可获取到任务的相关信息
        // JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + " 正在吃饭......");
    }
}
