package com.lanweihong.springdoc.swagger.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/25 21:54
 */
@Schema(description = "角色参数对象")
@Getter
@Setter
public class RoleParam {

    @Schema(description = "id")
    private int id;

    @Schema(required = true, description = "角色名称")
    private String roleName;

    @Schema(required = true, description = "角色状态", example = "1")
    private int status;
}
