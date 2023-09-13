package com.base.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.UserMapper;
import com.base.service.IUserService;
import com.base.utils.common.JwtUtils;
import com.base.utils.common.UploadUtils;
import com.common.AppHttpCodeEnum;
import com.common.ResponseResult;
import com.dto.UserDto.CreateUserDto;
import com.dto.UserDto.loginDto;
import com.pojos.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Value("${ss3Prefix.userAvater}")
    private String avaterUrl;

    @Override
    public ResponseResult<User> registe(CreateUserDto dto) {
        User one = getOne(Wrappers.<User>lambdaQuery().eq(User::getTelNumber, dto.getTelNumber()));
        if (one != null) {
            return ResponseResult.error("该手机号用户已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);

        //get salt
        byte[] s = new byte[1];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(s);
        String salt = Base64.getEncoder().encodeToString(s);
        user.setSalt(salt);

        String pswd = DigestUtils.md5DigestAsHex((dto.getPassword() + salt).getBytes());
        user.setPassword(pswd);
        UploadUtils.uploadFile("D://" + dto.getUserAvater(), "/var/www/html/GameClubPic/userAvater/" + dto.getUserAvater());
        user.setAvaterUrl(avaterUrl + dto.getUserAvater());
        baseMapper.insert(user);
        return ResponseResult.success("注册成功", user);
    }

    @Override
    public ResponseResult login(loginDto dto) {
        if (StringUtils.isNotBlank(dto.getTelNumber()) && StringUtils.isNotBlank(dto.getPassword())) {
            User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getTelNumber, dto.getTelNumber()));
            if (user == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "用户信息不存在");
            }

            String salt = user.getSalt();
            String password = dto.getPassword();
            String pswd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
            if (!pswd.equals(user.getPassword())) {
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }

            Map<String, String> claims = new HashMap<>();
            claims.put("userId", user.getId().toString());
            claims.put("role", user.getRole().toString());
            claims.put("userName", user.getName());
            String token = JwtUtils.createJwt(claims);

            Map<String, String> resMap = new HashMap<>();
            resMap.put("token", token);
            return ResponseResult.success("登录成功", resMap);
        }
        return ResponseResult.error("请输入验证信息");
    }

    @Override
    public ResponseResult getAvaterUrl(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            byte[] fileBytes = multipartFile.getBytes();
            String localFilePath = "D://" + originalFilename;
            Files.write(Paths.get(localFilePath), fileBytes);
            return ResponseResult.success(originalFilename);

        }
        return null;
    }
}

