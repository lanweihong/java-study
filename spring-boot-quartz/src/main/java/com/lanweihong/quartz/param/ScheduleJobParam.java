package com.lanweihong.quartz.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:09
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ScheduleJobParam {

    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @NotBlank(message = "任务分组不能为空")
    private String jobGroup;

    @NotBlank(message = "执行类名不能为空")
    private String jobClass;

    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    private String jobDescription;

    /**
     * 额外的数据
     */
    private List<Map<String, Object>> jobMapData;
}
