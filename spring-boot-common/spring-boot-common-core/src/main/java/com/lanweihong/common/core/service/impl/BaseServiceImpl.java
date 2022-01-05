package com.lanweihong.common.core.service.impl;

import com.lanweihong.common.core.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author lanweihong
 * @date 2022/1/4 22:19
 */
public class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    protected Mapper<T> mapper;

    @Override
    public T getByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int insert(T record) {
        return mapper.insert(record);
    }

    @Override
    public int updateSelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertSelective(T record) {
        return this.mapper.insertSelective(record);
    }
}
