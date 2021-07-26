package com.lanweihong.springdoc.swagger.controller.user;

import com.lanweihong.springdoc.swagger.dto.UserDTO;
import com.lanweihong.springdoc.swagger.exception.NotFoundException;
import com.lanweihong.springdoc.swagger.param.UserParam;
import com.lanweihong.springdoc.swagger.service.IUserService;
import com.lanweihong.springdoc.swagger.vo.JsonResult;
import com.lanweihong.springdoc.swagger.vo.UserVO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理 Controller
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/23 11:15
 */
@Tag(name = "用户管理", description = "用户数据增删改查")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "查询用户列表", description = "返回所有用户数据")
    @GetMapping("")
    public JsonResult<List<UserVO>> getUserList(@Parameter(description = "用户名") @RequestParam(value = "username", required = false) String userName) {
        List<UserVO> result = new ArrayList<>();
        // TODO
        return JsonResult.ok(result);
    }

    @Operation(summary = "通过用户名查询用户", description = "根据用户名查询用户详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "请求成功"),
            @ApiResponse(responseCode = "404", description = "用户不存在", content = @Content)
    })
    @GetMapping("/{username}")
    public JsonResult<UserVO> getUserByName(@Parameter(description = "用户名", required = true) @PathVariable("username") String userName) throws NotFoundException {
        UserDTO userDTO = userService.getUserByName(userName);
        if (null == userDTO) {
            throw new NotFoundException(404, "用户信息未找到");
        }
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

    @Operation(summary = "测试新增用户接口", hidden = true)
    @PostMapping("/test")
    @Hidden
    public JsonResult<UserVO> testAddUser(@Parameter(required = true) @Valid @RequestBody UserParam param) {
        // TODO
        return JsonResult.ok();
    }
}
