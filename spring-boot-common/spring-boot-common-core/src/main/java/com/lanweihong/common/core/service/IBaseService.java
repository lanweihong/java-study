package com.lanweihong.common.core.service;

/**
 * @author lanweihong
 * @date 2022/1/4 22:19
 */
public interface IBaseService<T> {

    /**
     * 通过 id 查询
     *
     * @param key id
     * @return
     */
    T getByKey(Object key);

    /**
     * 添加记录
     *
     * @param record 记录
     * @return
     */
    int insert(T record);

    /**
     * 修改记录
     *
     * @param record 记录
     * @return
     */
    int updateSelective(T record);

    /**
     * 选择性添加
     *
     * @param record 记录
     * @return
     */
    int insertSelective(T record);
}
