package com.lanweihong.quartz.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/9 05:08
 */
@Data
@Accessors(chain = true)
public class ScheduleJobDTO {

    private String jobName;
    private String jobGroup;
    private String jobDescription;
    private Integer triggerStatus;
    private String triggerStatusName;

    /**
     * 额外的数据
     */
    private List<Map<String, Object>> jobMapData;
}
