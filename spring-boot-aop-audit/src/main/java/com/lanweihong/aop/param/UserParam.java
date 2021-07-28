package com.lanweihong.aop.param;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 用户参数对象
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/28 13:52
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserParam {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;
}
