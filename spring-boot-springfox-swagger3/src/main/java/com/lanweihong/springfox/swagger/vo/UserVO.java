package com.lanweihong.springfox.swagger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/23 13:18
 */
@Getter
@Setter
@ApiModel(description = "用户实体")
@ToString
public class UserVO {

    public UserVO() {

    }

    public UserVO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    @Schema(name = "用户名", description = "用户名")
    private String userName;

    @Schema(description = "邮箱")
    private String email;
}
