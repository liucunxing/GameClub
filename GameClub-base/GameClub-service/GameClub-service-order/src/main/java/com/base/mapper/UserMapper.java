package com.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.common.ResponseResult;
import com.dto.UserDto.loginDto;
import com.pojos.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
