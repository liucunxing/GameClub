package com.base.controller;

import com.base.myInterface.Authorize;
import com.base.myInterface.NoAuthorize;
import com.base.service.IClubService;
import com.common.ResponseResult;
import com.dto.ClubDto.ClubBannerDto;
import com.dto.ClubDto.CreateClubDto;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/club")
@Api(value = "club",tags = "club")
@Authorize
public class ClubController {
    @Autowired
    private IClubService clubService;
    @PostMapping("/createClub")
    public ResponseResult createClub(@RequestBody CreateClubDto dto){
        return clubService.createClub(dto);
    }
    @PostMapping("/verifyCreateClub")
    @PreAuthorize("hasAuthority('Club:CreateClub')")
    public ResponseResult<Boolean> verifyCreateClub(){
        return clubService.verifyCreateClub();
    }
    @GetMapping("/showClubBanners")
    @NoAuthorize
    public ResponseResult<List<ClubBannerDto>> showClubBanners(){
        return clubService.showClubBanners();
    }
    @PostMapping("/setClubToBanner")
    @PreAuthorize("hasAuthority('Club:SetClubBanner')")
    public ResponseResult setClubToBanner(@RequestBody Integer id){
        return clubService.setClubToBanner(id);
    }
}
