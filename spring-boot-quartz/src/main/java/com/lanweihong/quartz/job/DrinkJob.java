package com.lanweihong.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:29
 */
public class DrinkJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + " 正在喝东西......");
    }
}
