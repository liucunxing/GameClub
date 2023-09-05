package com.base.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.UserMapper;
import com.base.service.IUserService;
import com.common.ResponseResult;
import com.dto.UserDto.loginDto;
import com.pojos.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public ResponseResult<String> login(loginDto dto) {
        return null;
    }
}
