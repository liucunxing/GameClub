package com.base.controller;

import com.base.myInterface.Authorize;
import com.base.myInterface.NoAuthorize;
import com.base.service.IClubUserService;
import com.common.PagedResult;
import com.dto.ClubUser.ClubUserListDto;
import com.dto.ClubUser.ClubUserQueryDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubUser")
@Api(value = "clubUser",tags = "clubUser")
@Authorize
public class ClubUserController {
    @Autowired
    private IClubUserService clubUserService;
    @PostMapping("/queryClubUserList")
    @NoAuthorize
    public PagedResult<ClubUserListDto> queryClubUserList(@RequestBody ClubUserQueryDto dto){
        return clubUserService.queryClubUserList(dto);
    }

}
