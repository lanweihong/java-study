package com.lanweihong.springdoc.swagger.controller.role;

import com.lanweihong.springdoc.swagger.param.RoleParam;
import com.lanweihong.springdoc.swagger.vo.JsonResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/25 15:31
 */
@RestController
@RequestMapping("/api/roles")
@Tag(name = "角色管理")
public class RoleController {

    @GetMapping("/api/roles")
    public JsonResult<String> queryRoleList() {
        return JsonResult.ok();
    }

    @PostMapping("/api/roles")
    public JsonResult<String> addRole(@Valid @RequestBody RoleParam param) {
        // TODO...
        return JsonResult.ok();
    }
}
