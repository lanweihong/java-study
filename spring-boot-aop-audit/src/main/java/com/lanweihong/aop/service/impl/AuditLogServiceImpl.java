package com.lanweihong.aop.service.impl;

import com.lanweihong.aop.dao.IAuditLogDao;
import com.lanweihong.aop.entity.AuditLogDO;
import com.lanweihong.aop.service.IAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 17:32
 */
@Service("operationLogService")
public class AuditLogServiceImpl implements IAuditLogService {

    private final IAuditLogDao operationDao;

    @Autowired
    public AuditLogServiceImpl(IAuditLogDao operationDao) {
        this.operationDao = operationDao;
    }

    @Override
    public int addOperationLog(AuditLogDO operationLogDO) {
        return operationDao.insert(operationLogDO);
    }

    @Override
    public List<AuditLogDO> listOperationLogs() {
        return operationDao.selectAll();
    }
}
