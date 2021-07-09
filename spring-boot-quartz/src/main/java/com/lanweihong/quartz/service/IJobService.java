package com.lanweihong.quartz.service;

import com.lanweihong.quartz.dto.ScheduleJobDTO;
import com.lanweihong.quartz.param.ScheduleJobParam;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:08
 */
public interface IJobService {

    /**
     * 查询所有任务列表
     * @return
     */
    List<ScheduleJobDTO> listAllJob();

    /**
     * 获取正在运行的任务列表
     * @return
     */
    List<ScheduleJobDTO> listRunningJob();

    /**
     * 新增任务
     * @param job job
     * @return
     */
    boolean addJob(ScheduleJobParam job);

    /**
     * 执行 job
     * @param jobName jobName
     * @param jobGroupName jobGroupName
     * @return
     */
    boolean triggerJob(String jobName, String jobGroupName);

    /**
     * 启动所有定时任务
     * @return
     */
    boolean startJobs();

    /**
     * 删除任务
     * @param jobName jobName
     * @param jobGroupName jobGroupName
     * @return
     */
    boolean deleteJob(String jobName, String jobGroupName);

    /**
     * 暂停任务
     * @param jobName jobName
     * @param jobGroupName jobGroupName
     * @return
     */
    boolean pauseJob(String jobName, String jobGroupName);

    /**
     * 恢复任务
     * @param jobName jobName
     * @param jobGroupName jobGroupName
     * @return
     */
    boolean resumeJob(String jobName, String jobGroupName);
}
