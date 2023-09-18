package com.base.service.Impl;

import com.base.filter.LoginUser;
import com.base.service.IMenuService;
import com.base.service.IUserService;
import com.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义userDetails
 */

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;
    @Override
    public UserDetails loadUserByUsername(String telNumber) throws UsernameNotFoundException {
        User user = userService.getUser(telNumber);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        //查询对应的权限信息
        List<String> list = menuService.getMenuPerms(Long.valueOf(user.getId()));
        return new LoginUser(user,list);
    }


}
