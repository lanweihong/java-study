package com.lanweihong.springfox.swagger.param;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/23 13:47
 */
@Getter
@Setter
// @Schema 注解用在类上不生效，使用 @ApiModel 替代
// @Schema(title = "用户实体标题", description = "用户参数实体")
@ApiModel(description = "用户参数对象")
public class UserParam {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码，6-18位，包含大小写、数字及特殊字符")
    private String password;

    @Schema(example = "zhangsan@lanweihong.com", description = "邮箱")
    private String email;
}
