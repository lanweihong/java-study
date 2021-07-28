package com.lanweihong.aop.controller;

import com.lanweihong.aop.annotation.AuditLog;
import com.lanweihong.aop.enums.AuditLogModuleEnum;
import com.lanweihong.aop.enums.AuditLogOperationTypeEnum;
import com.lanweihong.aop.param.UserParam;
import com.lanweihong.common.base.vo.JsonResult;
import com.sun.javaws.exceptions.InvalidArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 14:53
 */
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @GetMapping("/{userid}")
    public JsonResult<String> getUserById(@PathVariable(value = "userid") Long userId) {
        // TODO...
        return JsonResult.ok();
    }

    @AuditLog(description = "新增用户，用户名：#{[0].userName}", type = AuditLogOperationTypeEnum.INSERT)
    @PostMapping
    public JsonResult<String> addUser(@Valid @RequestBody UserParam param) {
        // TODO...
        return JsonResult.ok();
    }

    @AuditLog(description = "修改用户，用户Id：#{[0]}，用户名：#{[1].userName}")
    @PutMapping("/{userid}")
    public JsonResult<String> updateUser(@PathVariable(value = "userid", required = false) Long userId, @Valid @RequestBody UserParam param) {
        // TODO...
        return JsonResult.ok();
    }

    @AuditLog(description = "删除用户，用户 id：#{[0]}", type = AuditLogOperationTypeEnum.DELETE)
    @DeleteMapping("/{userid}")
    public JsonResult<String> deleteUser(@PathVariable(value = "userid") Long userId) {
        // TODO...
        return JsonResult.ok();
    }
}
