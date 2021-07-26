package com.lanweihong.springdoc.swagger.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/23 13:47
 */
@Getter
@Setter
@Schema(description = "用户参数实体")
public class UserParam {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String userName;

    @Schema(name = "roles", description = "角色id列表")
    @NotNull(message = "角色id列表不能为空")
    @Size(min = 1)
    private Set<String> roleList;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码，6-18位，包含大小写、数字及特殊字符")
    @Size(min = 6, max = 18)
    private String password;

    @Schema(example = "zhangsan@lanweihong.com", description = "邮箱")
    private String email;

    @Schema(description = "年龄")
    @Min(value = 1, message = "最小年龄为1")
    @Max(value = 150, message = "最大年龄为150")
    private Integer age;
}
