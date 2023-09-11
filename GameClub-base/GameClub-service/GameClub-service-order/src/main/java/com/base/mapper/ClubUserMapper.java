package com.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dto.ClubUser.ClubUserListDto;
import com.pojos.ClubUserMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClubUserMapper extends BaseMapper<ClubUserMap> {
    @Select({"select u.name,u.telNumber,u.category,c.joinTime,c.orderCount FROM `user` u ",
            "JOIN clubusermap c on u.id = c.userId",
            "${ew.customSqlSegment} ORDER BY category "})
    IPage<ClubUserListDto> queryUserList(Page<ClubUserListDto> page, @Param(Constants.WRAPPER)Wrapper<ClubUserListDto> wrapper);
}
