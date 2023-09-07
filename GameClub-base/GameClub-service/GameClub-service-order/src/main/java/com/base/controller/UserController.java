package com.base.controller;

import com.base.myInterface.Authorize;
import com.base.myInterface.NoAuthorize;
import com.base.service.AuthenticationFacade;
import com.base.service.IUserService;
import com.base.utils.common.JwtUtils;
import com.common.ResponseResult;
import com.dto.UserDto.CreateUserDto;
import com.dto.UserDto.loginDto;
import com.pojos.User;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(value = "user",tags = "user")
@Authorize
public class UserController {
    @Autowired
    private IUserService userService;
    private final AuthenticationFacade authenticationFacade;

    public UserController(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/registe")
    @ApiOperation("registe测试")
    public ResponseResult<User> login(@RequestBody CreateUserDto dto){
        return userService.registe(dto);
    }

    @PostMapping("/login")
    @ApiOperation("login测试")
    @NoAuthorize
    public ResponseResult<String> login(@RequestBody loginDto dto){
        return userService.login(dto);
    }
    @PostMapping("/parseToken")
    @ApiOperation("parseToken测试")
    public ResponseResult getId(){
        Claims claims = authenticationFacade.getUserClaims();
        Object userId = claims.get("userId");
        Object category = claims.get("category");
        Object userName = claims.get("userName");
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("category",category);
        map.put("userName",userName);
        return ResponseResult.success(map);
    }
}
