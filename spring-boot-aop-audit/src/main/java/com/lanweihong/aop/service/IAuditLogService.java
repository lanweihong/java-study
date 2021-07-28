package com.lanweihong.aop.service;

import com.lanweihong.aop.entity.AuditLogDO;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 17:30
 */
public interface IAuditLogService {

    /**
     * 添加日志
     * @param operationLogDO 操作日志
     * @return
     */
    int addOperationLog(AuditLogDO operationLogDO);

    /**
     * 查询操作日志
     * @return
     */
    List<AuditLogDO> listOperationLogs();
}
