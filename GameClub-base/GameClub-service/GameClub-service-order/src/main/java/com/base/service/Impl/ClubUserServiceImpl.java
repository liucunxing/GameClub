package com.base.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.ClubUserMapper;
import com.base.service.IClubUserService;
import com.common.PagedResult;
import com.dto.ClubUser.ClubUserListDto;
import com.dto.ClubUser.ClubUserQueryDto;
import com.pojos.ClubUserMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class ClubUserServiceImpl extends ServiceImpl<ClubUserMapper, ClubUserMap> implements IClubUserService {
    @Override
    public PagedResult<ClubUserListDto> queryClubUserList(ClubUserQueryDto dto) {
        var wrapper = getWrapper(dto);
        Page<ClubUserListDto> page = new Page<>(dto.getPageIndex(),dto.getPageSize());
        IPage<ClubUserListDto> pageResult = baseMapper.queryUserList(page,wrapper);
        return new PagedResult<>(pageResult.getRecords(), pageResult.getTotal(), dto.getPageIndex(), dto.getPageSize());
    }
    private QueryWrapper<ClubUserListDto> getWrapper(ClubUserQueryDto dto){
        var wrapper = new QueryWrapper<ClubUserListDto>();
        wrapper.eq("c.clubUUID",dto.getClubUUID());
        if(!StringUtils.isEmpty(dto.getName())){
            wrapper.like("u.name",dto.getName());
        }
        if(dto.getCategory() != 0){
            wrapper.eq("u.category",dto.getCategory());
        }else{
            List<Integer> categories = Arrays.asList(2,3,4,5);
            wrapper.in("u.category", categories);
        }
        return wrapper;
    }
}
