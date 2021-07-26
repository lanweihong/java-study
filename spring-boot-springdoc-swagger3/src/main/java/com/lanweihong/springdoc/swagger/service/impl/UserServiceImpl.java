package com.lanweihong.springdoc.swagger.service.impl;

import com.lanweihong.springdoc.swagger.dto.UserDTO;
import com.lanweihong.springdoc.swagger.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/26 12:04
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Override
    public UserDTO getUserByName(String userName) {
        // TODO ...
        return null;
    }
}
