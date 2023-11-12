package com.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.PagedResult;
import com.common.ResponseResult;
import com.dto.ClubDto.*;
import com.pojos.Club;

import java.util.List;

public interface IClubService extends IService<Club> {
    ResponseResult<List<ClubDto>> getHotClub();
    ResponseResult createClub(CreateClubDto dto);
    ResponseResult<Boolean> verifyCreateClub();
    ResponseResult<List<ClubBannerDto>> showClubBanners();
    ResponseResult setClubToBanner(Integer clubId);
    PagedResult<ClubListDto> getClubList(ClubQueryDto dto);

}
