package com.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.ResponseResult;
import com.dto.UserDto.CreateUserDto;
import com.dto.UserDto.loginDto;
import com.pojos.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IUserService extends IService<User> {
    ResponseResult<User> register(CreateUserDto dto);
    ResponseResult login(loginDto dto);
    ResponseResult getAvaterUrl(MultipartFile multipartFile) throws IOException;
    User getUser(String telNumber);

    ResponseResult logout();
}
