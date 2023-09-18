package com.base.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.MenuMapper;
import com.base.mapper.RoleMenuMapper;
import com.base.mapper.UserRoleMapper;
import com.base.service.IMenuService;
import com.pojos.Menu;
import com.pojos.RoleMenu;
import com.pojos.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public List<String> getMenuPerms(Long userId) {
        List<String> menuPerms = baseMapper.getMenuPerms(userId);
        return menuPerms;
    }
}
