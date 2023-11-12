package com.base.controller;

import com.base.service.AuthenticationFacade;
import com.base.service.IMenuService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(value = "user",tags = "user")
public class UserController {
    @Autowired
    private IUserService userService;
    private final AuthenticationFacade authenticationFacade;
    @Autowired
    private IMenuService menuService;

    public UserController(AuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }
    @PostMapping("/register")
    @ApiOperation("register测试")
    public ResponseResult<User> register(@RequestBody CreateUserDto dto){
        return userService.register(dto);
    }
    @GetMapping("/logout")
    public ResponseResult logout(long id){
        return userService.logout(id);
    }

    @PostMapping("/login")
    @ApiOperation("login测试")
    public ResponseResult<String> login(@RequestBody loginDto dto){
        return userService.login(dto);
    }
    @PostMapping("/parseToken")
    @ApiOperation("parseToken测试")
    //@PreAuthorize("hasAuthority('Club:AddClubUser')")
    public ResponseResult getId(String token){
//        Claims claims = authenticationFacade.getUserClaims();
//        Object userId = claims.get("userId");
//        Object category = claims.get("role");
//        Object userName = claims.get("userName");
//        Map<String,Object> map = new HashMap<>();
//        map.put("userId",userId);
//        map.put("role",category);
//        map.put("userName",userName);
        return ResponseResult.success(JwtUtils.parseJwtToClaims(token));
    }
    @PostMapping("/noAuth/testUpload")
    @ApiOperation("测试上传头像")
    public ResponseResult testUpload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return userService.getAvaterUrl(multipartFile);
    }
    @GetMapping("/noAuth/testGetPerms")
    @ApiOperation("测试获取perms")
    public ResponseResult getPerms(Long userId){
        return ResponseResult.success(menuService.getMenuPerms(userId));
    }
    @GetMapping("/noAuth/getUser")
    @ApiOperation("获取用户信息")
    public ResponseResult<User> getUser(String telNumber){
        return ResponseResult.success(userService.getUser(telNumber));
    }
}
