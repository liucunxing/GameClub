package com.base.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.filter.LoginUser;
import com.base.mapper.UserMapper;
import com.base.service.IUserService;
//import com.base.service.LoginUser;
import com.base.utils.common.JwtUtils;
import com.base.utils.common.RedisUtils;
import com.common.ResponseResult;
import com.dto.UserDto.CreateUserDto;
import com.dto.UserDto.loginDto;
import com.pojos.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Value("${ss3Prefix.userAvater}")
    private String avaterUrl;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtils redisUtils;

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
        System.out.println(passwordEncoder.encode(dto.getPassword()));
        String pswd = passwordEncoder.encode(dto.getPassword());
        user.setPassword(pswd);
        //UploadUtils.uploadFile("D://" + dto.getUserAvater(), "/var/www/html/GameClubPic/userAvater/" + dto.getUserAvater());
        user.setAvaterUrl(avaterUrl + dto.getUserAvater());
        baseMapper.insert(user);
        return ResponseResult.success("注册成功", user);
    }

    @Override
    public ResponseResult login(loginDto dto) {
        if (StringUtils.isNotBlank(dto.getTelNumber()) && StringUtils.isNotBlank(dto.getPassword())) {
            //首先进行Authenticate方法认证，根据过滤器流程，当调用该方法后，会向后调用UserDetailService中的查询用户，并进行密码比对
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getName(),dto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            if(Objects.isNull(authenticate)){
                return ResponseResult.error("登陆失败");
            }
            //比对成功后会返回loginUser（UserDetail实现类）对象，里面封装了user的全部信息
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            //认证通过生成jwt，并把用户信息存入redis
            Integer id = loginUser.getUser().getId();
            Map<String, String> claims = new HashMap<>();
            claims.put("userId", id.toString());
            claims.put("userName", loginUser.getUser().getName());
            String token = JwtUtils.createJwt(claims);
            String key = "userId:"+id;
            redisUtils.set(key,loginUser);
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

    @Override
    public User getUser(String name) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getName,name));
    }

    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder信息中的id
        Authentication authentication = (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer id = loginUser.getUser().getId();
        //删除redis的值
        redisUtils.delete("userId:"+id);
        return ResponseResult.success("注销成功");
    }
}

