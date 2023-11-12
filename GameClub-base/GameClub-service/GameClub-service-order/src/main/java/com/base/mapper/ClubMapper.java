package com.base.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dto.ClubDto.ClubListDto;
import com.pojos.Club;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClubMapper extends BaseMapper<Club> {
    @Select({"select c.uuid,c.clubName,c.iconUrl,c.hotCount,c.createTime,c.orderCount,c.status FROM `club` c ",
            "${ew.customSqlSegment} ORDER BY hotCount DESC"})
    IPage<ClubListDto> queryClubList(Page<ClubListDto> page, @Param(Constants.WRAPPER) Wrapper<ClubListDto> wrapper);
}
