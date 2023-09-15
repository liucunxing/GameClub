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
        //获得roleIds
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,userId);
        List<Integer> roleIds = userRoleMapper.selectList(wrapper).stream().map(s->s.getRoleId()).collect(Collectors.toList());
        //获取menuIds
        List<Integer> menuIds = new ArrayList<>();
        for (Integer roleId: roleIds) {
            LambdaQueryWrapper<RoleMenu> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(RoleMenu::getRoleId,roleId);
            List<Integer> collect = roleMenuMapper.selectList(wrapper1).stream().map(s -> s.getMenuId()).collect(Collectors.toList());
            menuIds.addAll(collect);
        }

        //通过menuIds获取menu的perms
        List<String> perms = new ArrayList<>();
        for (Integer menuId : menuIds) {
            LambdaQueryWrapper<Menu> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(Menu::getId,menuId);
            List<String> collect = menuMapper.selectList(wrapper1).stream().map(s -> s.getPerms()).collect(Collectors.toList());
            perms.addAll(collect);
        }
        return perms;
    }
}
