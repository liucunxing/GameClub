package com.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pojos.Menu;

import java.util.List;

public interface IMenuService extends IService<Menu> {
    List<String> getMenuPerms(Long userId);
}
