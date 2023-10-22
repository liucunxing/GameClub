package com.base.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.ClubMapper;
import com.base.mapper.ClubUserMapper;
import com.base.mapper.UserRoleMapper;
import com.base.service.AuthenticationFacade;
import com.base.service.IClubService;
import com.base.utils.common.FileStorageUtils;
import com.common.ResponseResult;
import com.dto.ClubDto.ClubBannerDto;
import com.dto.ClubDto.CreateClubDto;
import com.pojos.Club;
import com.pojos.ClubUserMap;
import com.pojos.UserRole;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClubServiceImpl extends ServiceImpl<ClubMapper, Club> implements IClubService {
    private final AuthenticationFacade authenticationFacade;
    private final ClubUserMapper clubUserMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private FileStorageUtils fileStorageUtils;

    public ClubServiceImpl(AuthenticationFacade authenticationFacade, ClubUserMapper clubUserMapper) {
        this.authenticationFacade = authenticationFacade;
        this.clubUserMapper = clubUserMapper;
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
        List<ClubBannerDto> dtos = new ArrayList<>();
        LambdaQueryWrapper<Club> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Club::getIfBanner,true);
        List<Club> clubs = baseMapper.selectList(wrapper);
        BeanUtils.copyProperties(clubs,dtos);
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

    public ResponseResult getAvaterUrl(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            InputStream inputStream = multipartFile.getInputStream();
            String s = fileStorageUtils.uploadImgFile("", originalFilename , inputStream);
            return ResponseResult.success(s);
        }
        return ResponseResult.error("图像上传失败");
    }

}
