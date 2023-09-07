package com.base.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.ClubMapper;
import com.base.mapper.ClubUserMapper;
import com.base.service.AuthenticationFacade;
import com.base.service.IClubService;
import com.common.ResponseResult;
import com.dto.ClubDto.CreateClubDto;
import com.pojos.Club;
import com.pojos.ClubUserMap;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements IClubService {
    private final AuthenticationFacade authenticationFacade;
    private final ClubUserMapper clubUserMapper;

    public ClubServiceImpl(AuthenticationFacade authenticationFacade, ClubUserMapper clubUserMapper) {
        this.authenticationFacade = authenticationFacade;
        this.clubUserMapper = clubUserMapper;
    }

    @Override
    @Transactional
    public ResponseResult createClub(CreateClubDto dto) {
        Claims userClaims = authenticationFacade.getUserClaims();
        Object category = userClaims.get("category");
        if(category==null || !category.toString().equals("2")){
            return ResponseResult.error("您暂无权限创建club");
        }
        if(dto.getClubName().isBlank()){
            return ResponseResult.error("club名称不可为空");
        }
        if(dto.getIconUrl().isBlank()){
            return ResponseResult.error("club图标不可为空");
        }
        Club one = getOne(Wrappers.<Club>lambdaQuery().eq(Club::getClubName, dto.getClubName()));
        if(one != null){
            return ResponseResult.error("club已名称已存在，请重新输入名称");
        }
        Club club = new Club();
        BeanUtils.copyProperties(dto,club);
        String uuid = UUID.randomUUID().toString().replace("-","");
        club.setUuid(uuid);
        baseMapper.insert(club);
        ClubUserMap cu = new ClubUserMap();
        cu.setClubUUID(uuid);
        cu.setUserId(Long.parseLong(userClaims.get("userId").toString()));
        cu.setCategory(Integer.parseInt(category.toString()));
        clubUserMapper.insert(cu);
        return ResponseResult.success("创建成功",club);
    }
}
