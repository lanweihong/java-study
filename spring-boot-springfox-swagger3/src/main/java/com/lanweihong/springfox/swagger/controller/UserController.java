package com.lanweihong.springfox.swagger.controller;

import com.lanweihong.springfox.swagger.param.UserParam;
import com.lanweihong.springfox.swagger.vo.JsonResult;
import com.lanweihong.springfox.swagger.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理 Controller
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/23 11:15
 */
@RestController
@RequestMapping("/api/users")
// @Tag 注解不生效，似乎是 BUG，相关 issues：https://github.com/springfox/springfox/issues/3668，因此这里使用 2.X 的注解 @Api
// @Tag(name = "用户管理", description = "用户数据增删改查")
@Api(tags = "用户管理")
public class UserController {

    @Operation(summary = "查询用户列表", description = "返回所有用户数据")
    @GetMapping("")
    public JsonResult<List<UserVO>> getUserList(@Parameter(description = "用户名") @RequestParam(value = "username", required = false) String userName) {
        List<UserVO> result = new ArrayList<>();
        result.add(new UserVO("zhangsan", "zhangsan@lanweihong.com"));
        result.add(new UserVO("lisi", "lisi@lanweihong.com"));
        return JsonResult.ok(result);
    }

    @Operation(summary = "通过用户名查询用户", description = "根据用户名查询用户详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "请求成功"),
            @ApiResponse(responseCode = "404", description = "用户不存在", content = @Content)
    })
    @GetMapping("/{username}")
    public JsonResult<UserVO> getUserByName(@Parameter(description = "用户名", required = true) @PathVariable("username") String userName) {
        // TODO
        return JsonResult.ok();
    }

    @Operation(summary = "新增用户")
    @PostMapping("")
    public JsonResult<UserVO> addUser(@Parameter(required = true) @Valid @RequestBody UserParam param) {
        // TODO
        return JsonResult.ok();
    }

    @Operation(summary = "修改用户")
    @PutMapping("")
    public JsonResult<UserVO> updateUser(@Parameter(description = "用户参数", required = true) @Valid @RequestBody UserParam param) {
        // TODO
        return JsonResult.ok();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{username}")
    public JsonResult<UserVO> deleteUserByName(@Parameter(name = "username", in = ParameterIn.PATH, description = "用户名", required = true) @PathVariable("username") String userName) {
        // TODO
        return JsonResult.ok();
    }

    @Hidden
    @PostMapping("/test")
    public JsonResult<UserVO> testAddUser(@Valid @RequestBody UserParam userParam) {
        // TODO
        return JsonResult.ok();
    }
}
