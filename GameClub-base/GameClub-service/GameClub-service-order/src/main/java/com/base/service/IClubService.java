package com.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.ResponseResult;
import com.dto.ClubDto.CreateClubDto;
import com.pojos.Club;

public interface IClubService extends IService<Club> {
    ResponseResult createClub(CreateClubDto dto);
}
