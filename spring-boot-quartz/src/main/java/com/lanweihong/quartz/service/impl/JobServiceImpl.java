package com.lanweihong.quartz.service.impl;

import com.lanweihong.quartz.dto.ScheduleJobDTO;
import com.lanweihong.quartz.param.ScheduleJobParam;
import com.lanweihong.quartz.service.IJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:10
 */
@Service("jobService")
@Slf4j
public class JobServiceImpl implements IJobService {

    public static final String TRIGGER_IDENTITY_PREFIX = "trigger_";

    /**
     * 调度器
     */
    private final Scheduler scheduler;

    @Autowired
    public JobServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public List<ScheduleJobDTO> listAllJob() {
        List<ScheduleJobDTO> result = new ArrayList<>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    ScheduleJobDTO scheduleJob = new ScheduleJobDTO();
                    scheduleJob.setJobName(jobKey.getName())
                            .setJobGroup(jobKey.getGroup())
                            .setJobDescription(trigger.getDescription())
                            .setTriggerStatus(scheduler.getTriggerState(trigger.getKey()).ordinal())
                            .setTriggerStatusName(scheduler.getTriggerState(trigger.getKey()).name());

                    result.add(scheduleJob);
                }
            }
        } catch (SchedulerException e) {
            log.error("获取所有任务列表失败，错误：{}", e.getMessage());
        }
        return result;
    }

    @Override
    public List<ScheduleJobDTO> listRunningJob() {
        List<ScheduleJobDTO> result = new ArrayList<>();
        try {
            // 获取列表
            List<JobExecutionContext> executingJobs =  scheduler.getCurrentlyExecutingJobs();

            result = getJobListData(executingJobs);

        } catch (SchedulerException e) {
            log.error("获取运行任务列表失败，错误：{}", e.getMessage());
        }
        return result;
    }

    @Override
    public boolean addJob(ScheduleJobParam jobParam) {
        try {
            // 加载执行类
            Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(jobParam.getJobClass());
            clazz.newInstance();

            // 1. 创建 job
            JobDetail job = JobBuilder.newJob(clazz)
                    .withIdentity(jobParam.getJobName(), jobParam.getJobGroup())
                    .withDescription(jobParam.getJobDescription())
                    .build();

            JobDataMap jobDataMap = job.getJobDataMap();

            List<Map<String, Object>> data = jobParam.getJobMapData();
            if (data != null && data.size() > 0) {
                data.forEach(jobDataItem -> {
                    jobDataItem.keySet().forEach((key) -> {
                        jobDataMap.put(key, jobDataItem.get(key));
                    });
                });
            }

            // 配置cron运行规则，即执行时间
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobParam.getCronExpression());

            // 2. 创建 Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(TRIGGER_IDENTITY_PREFIX + jobParam.getJobName(), jobParam.getJobGroup())
                    .startNow()
                    .withSchedule(cronScheduleBuilder)
                    .build();

            // 3. 注册任务和定时器
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SchedulerException e) {
            log.error("添加任务 {} 失败，错误： {}", jobParam.getJobName(), e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean triggerJob(String jobName, String jobGroupName) {
        JobKey key = new JobKey(jobName, jobGroupName);
        try {
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            log.error("任务 {} 触发失败", jobName);
            return false;
        }
        return true;
    }

    @Override
    public boolean startJobs() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("启动所有任务失败：", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteJob(String jobName, String jobGroupName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        try {
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 删除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            log.error("删除任务 {} 失败，错误：{}", jobName, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean pauseJob(String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("停止任务 {} 失败，错误：{}", jobName, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean resumeJob(String jobName, String jobGroupName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("恢复任务 {} 失败，错误：{}", jobName, e.getMessage());
            return false;
        }
        return true;
    }

    private List<ScheduleJobDTO> getJobListData(List<JobExecutionContext> executingJobs) {
        List<ScheduleJobDTO> result = new ArrayList<>();
        try {
            for (JobExecutionContext jobItem : executingJobs) {
                ScheduleJobDTO  scheduleJobItem = getJobDataByJobExecutionContext(jobItem);
                result.add(scheduleJobItem);
            }
        } catch (SchedulerException e) {
            log.error("获取任务状态失败，错误：{}", e.getMessage());
        }
        return result;
    }

    /**
     * 从 JobExecutionContext 中解析获取数据
     * @param jobContext JobExecutionContext
     * @return
     * @throws SchedulerException
     */
    private ScheduleJobDTO getJobDataByJobExecutionContext(JobExecutionContext jobContext) throws SchedulerException {
        JobDetail jobDetail = jobContext.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        Trigger trigger = jobContext.getTrigger();
        // 封装 ScheduleJobDTO 返回，想要获取更多的数据（如 JobDataMap）可自行添加
        ScheduleJobDTO scheduleJob = new ScheduleJobDTO();
        scheduleJob.setJobName(jobKey.getName())
                .setJobGroup(jobKey.getGroup())
                .setJobDescription(trigger.getDescription())
                .setTriggerStatus(scheduler.getTriggerState(trigger.getKey()).ordinal())
                .setTriggerStatusName(scheduler.getTriggerState(trigger.getKey()).name());
        return scheduleJob;
    }
}
