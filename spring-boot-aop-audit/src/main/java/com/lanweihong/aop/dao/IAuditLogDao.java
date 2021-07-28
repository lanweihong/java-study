package com.lanweihong.aop.dao;

import com.lanweihong.aop.entity.AuditLogDO;
import com.lanweihong.common.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 13:35
 */
@Repository
public interface IAuditLogDao extends BaseMapper<AuditLogDO> {

}
