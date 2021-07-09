package com.lanweihong.quartz.controller;

import com.lanweihong.quartz.dto.ScheduleJobDTO;
import com.lanweihong.quartz.param.ScheduleJobParam;
import com.lanweihong.quartz.service.IJobService;
import com.lanweihong.quartz.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:26
 */
@RestController
@RequestMapping("/jobs")
public class MainController {

    private final IJobService jobService;

    @Autowired
    public MainController(IJobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/all")
    public JsonResult<List<ScheduleJobDTO>> queryAllJobs() {
        List<ScheduleJobDTO> list = jobService.listAllJob();
        return JsonResult.ok(list);
    }

    @GetMapping("/running")
    public JsonResult<List<ScheduleJobDTO>> queryRunningJobs() {
        List<ScheduleJobDTO> list = jobService.listRunningJob();
        return JsonResult.ok(list);
    }

    @PostMapping("")
    public JsonResult<String> addJob(@RequestBody @Valid ScheduleJobParam param) {
        boolean flag = jobService.addJob(param);
        return flag ? JsonResult.ok() : JsonResult.error("添加任务失败");
    }

    @PutMapping("/pause")
    public JsonResult<String> pauseJob(@RequestBody @Valid ScheduleJobParam param) {
        boolean flag = jobService.pauseJob(param.getJobName(), param.getJobGroup());
        return flag ? JsonResult.ok() : JsonResult.error("停止任务失败");
    }

    @PutMapping("/resume")
    public JsonResult<String> resumeJob(@RequestBody @Valid ScheduleJobParam param) {
        boolean flag = jobService.resumeJob(param.getJobName(), param.getJobGroup());
        return flag ? JsonResult.ok() : JsonResult.error("停止任务失败");
    }

    @DeleteMapping("")
    public JsonResult<String> deleteJob(@RequestBody @Valid ScheduleJobParam param) {
        boolean flag = jobService.deleteJob(param.getJobName(), param.getJobGroup());
        return flag ? JsonResult.ok() : JsonResult.error("删除任务失败");
    }

}
