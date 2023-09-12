package com.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.PagedResult;
import com.common.ResponseResult;
import com.dto.ClubUser.AppointExamDto;
import com.dto.ClubUser.ClubUserListDto;
import com.dto.ClubUser.ClubUserQueryDto;
import com.dto.ClubUser.UpdateExamStatusDto;
import com.pojos.ClubUserMap;

public interface IClubUserService extends IService<ClubUserMap> {
    PagedResult<ClubUserListDto> queryClubUserList(ClubUserQueryDto dto);
    ResponseResult appointExam(AppointExamDto dto);
    ResponseResult updateStatus(UpdateExamStatusDto dto);
}
