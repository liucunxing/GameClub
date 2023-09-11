package com.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.PagedResult;
import com.dto.ClubUser.ClubUserListDto;
import com.dto.ClubUser.ClubUserQueryDto;
import com.pojos.ClubUserMap;

public interface IClubUserService extends IService<ClubUserMap> {
    PagedResult<ClubUserListDto> queryClubUserList(ClubUserQueryDto dto);
}
