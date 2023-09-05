package com.base.controller;

import com.base.service.IUserService;
import com.common.ResponseResult;
import com.dto.UserDto.loginDto;
import com.pojos.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Api(value = "user",tags = "user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    @ApiOperation("login测试")
    public ResponseResult<String> login(@RequestBody loginDto dto){
        return userService.login(dto);
    }
}
