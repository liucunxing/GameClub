package com.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojos.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    @Select("SELECT m.perms\n" +
            "FROM userrole ur\n" +
            "INNER JOIN rolemenu rm ON ur.roleId = rm.roleId\n" +
            "INNER JOIN menu m ON rm.menuId = m.Id\n" +
            "WHERE ur.userId = #{userId}")
    List<String> getMenuPerms(@Param("userId") Long userId);
}
