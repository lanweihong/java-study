package com.lanweihong.common.core.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 13:18
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
