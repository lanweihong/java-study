package com.lanweihong.springdoc.swagger.service;

import com.lanweihong.springdoc.swagger.dto.UserDTO;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/26 12:03
 */
public interface IUserService {

    /**
     * 通过用户名查询用户
     * @param userName 用户名
     * @return
     */
    UserDTO getUserByName(String userName);
}
