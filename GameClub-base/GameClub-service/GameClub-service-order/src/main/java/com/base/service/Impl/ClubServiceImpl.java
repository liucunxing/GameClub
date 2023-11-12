package com.base.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.ClubMapper;
import com.base.mapper.ClubUserMapper;
import com.base.mapper.UserRoleMapper;
import com.base.service.AuthenticationFacade;
import com.base.service.IClubService;
import com.base.utils.common.FileStorageUtils;
import com.base.utils.common.RedisUtils;
import com.common.PagedResult;
import com.common.ResponseResult;
import com.dto.ClubDto.*;
import com.pojos.Club;
import com.pojos.ClubUserMap;
import com.pojos.UserRole;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements IClubService {
    private final AuthenticationFacade authenticationFacade;
    private final ClubUserMapper clubUserMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private FileStorageUtils fileStorageUtils;
    @Autowired
    private RedisUtils redisUtils;

    public ClubServiceImpl(AuthenticationFacade authenticationFacade, ClubUserMapper clubUserMapper) {
        this.authenticationFacade = authenticationFacade;
        this.clubUserMapper = clubUserMapper;
    }

    @Override
    public ResponseResult<List<ClubDto>> getHotClub() {
        Object hotClub = redisUtils.get("hotClub");
        if(hotClub != null){
            return ResponseResult.success((List<ClubDto>)hotClub);
        }
        LambdaQueryWrapper<Club> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Club::getStatus,1).orderByDesc(Club::getHotCount).last("Limit 6");
        List<ClubDto> res = new ArrayList<>();
        List<Club> clubs = baseMapper.selectList(wrapper);
        clubs.stream().map(c->{
            ClubDto d = new ClubDto();
            BeanUtils.copyProperties(c,d);
            return res.add(d);
        }).collect(Collectors.toList());
        redisUtils.set("hotClub",res,24);
        return ResponseResult.success(res);
    }

    @Override
    @Transactional
    public ResponseResult createClub(CreateClubDto dto) {
        Claims userClaims = authenticationFacade.getUserClaims();
        Long userId = Long.parseLong(userClaims.get("userId").toString());
        if(dto.getClubName().isBlank()){
            return ResponseResult.error("club名称不可为空");
        }
        if(dto.getIconUrl().isBlank()){
            return ResponseResult.error("club图标不可为空");
        }
        Club one = getOne(Wrappers.<Club>lambdaQuery().eq(Club::getClubName, dto.getClubName()));
        if(one != null){
            return ResponseResult.error("club名称已存在，请重新输入名称");
        }
        Club club = new Club();
        BeanUtils.copyProperties(dto,club);
        String uuid = UUID.randomUUID().toString().replace("-","");
        club.setUuid(uuid);
        baseMapper.insert(club);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,userId);
        UserRole userRole1 = userRoleMapper.selectOne(wrapper);
        UserRole userRole = new UserRole(userId,2);
        clubUserMapper.insert(new ClubUserMap(uuid,userId,2,club.getCreateTime()));
        userRoleMapper.update(userRole,wrapper);
        return ResponseResult.success("创建成功",club);
    }

    /**
     * 验证是否可以创建俱乐部：只有普通角色可以创建
     * 返回为true时，前端才从创建按钮跳转
     * @return
     */
    @Override
    public ResponseResult<Boolean> verifyCreateClub() {
        Claims claims = authenticationFacade.getUserClaims();
        Object roleId = claims.get("roleId");
        if(roleId != "1" || roleId != "7"){
            return ResponseResult.error("您已有俱乐部，请先退出原俱乐部");
        }
        return ResponseResult.success(true);
    }

    @Override
    public ResponseResult<List<ClubBannerDto>> showClubBanners() {
        Object clubBanners = redisUtils.get("clubBanners");
        if(clubBanners != null){
            return ResponseResult.success((List<ClubBannerDto>) clubBanners);
        }
        LambdaQueryWrapper<Club> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Club::getStatus,1).eq(Club::getIfBanner,true);
        List<Club> clubs = baseMapper.selectList(wrapper);
        List<ClubBannerDto> dtos = clubs.stream().map( club -> {
            ClubBannerDto dto = new ClubBannerDto();
            BeanUtils.copyProperties(club,dto);
            return dto;
        }).collect(Collectors.toList());
        redisUtils.set("clubBanners",dtos,60);
        return ResponseResult.success(dtos);
    }

    /**
     * 设置club展示轮播图，最多5个,判断俱乐部状态为已创建
     * @param clubId
     * @return
     */
    @Override
    public ResponseResult setClubToBanner(Integer clubId) {
        Club club = getOne(Wrappers.<Club>lambdaQuery().eq(Club::getId, clubId));
        if(club.getStatus() != 2){
            return ResponseResult.error("该俱乐部在审核中或已解散");
        }
        LambdaQueryWrapper<Club> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Club::getIfBanner,true);
        Integer integer = baseMapper.selectCount(wrapper);
        if(integer == 5){
            return ResponseResult.error("最多支持5个俱乐部轮播图");
        }
        club.setIfBanner(true);
        baseMapper.updateById(club);
        return ResponseResult.success("设置轮播成功");
    }

    @Override
    public PagedResult<ClubListDto> getClubList(ClubQueryDto dto) {
        var wrapper = getWrapper(dto);
        Page<ClubListDto> page = new Page<>(dto.getPageIndex(),dto.getPageSize());
        IPage<ClubListDto> pageResult = baseMapper.queryClubList(page,wrapper);
        SimpleDateFormat formatt = new SimpleDateFormat("yyyy-mm-dd");

        return new PagedResult<>(pageResult.getRecords(), pageResult.getTotal(), dto.getPageIndex(), dto.getPageSize());
    }

    private QueryWrapper<ClubListDto> getWrapper(ClubQueryDto dto){
        var wrapper = new QueryWrapper<ClubListDto>();
        wrapper.eq("c.status",1);
        if(!StringUtils.isEmpty(dto.getClubName())){
            wrapper.like("c.clubName",dto.getClubName());
        }
        return wrapper;
    }

}
