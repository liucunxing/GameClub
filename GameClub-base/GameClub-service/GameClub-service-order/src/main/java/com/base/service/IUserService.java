package com.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.ResponseResult;
import com.dto.UserDto.CreateUserDto;
import com.dto.UserDto.loginDto;
import com.pojos.User;

public interface IUserService extends IService<User> {
    ResponseResult<User> registe(CreateUserDto dto);
    ResponseResult login(loginDto dto);
}
