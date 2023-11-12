package com.base.controller;

import com.base.service.IClubService;
import com.common.PagedResult;
import com.common.ResponseResult;
import com.dto.ClubDto.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/club")
@Api(value = "club",tags = "club")
public class ClubController {
    @Autowired
    private IClubService clubService;
    @GetMapping("/noAuth/getHotClub")
    public ResponseResult<List<ClubDto>> getHotClub(){
        return clubService.getHotClub();
    }
    @PostMapping("/createClub")
    public ResponseResult createClub(@RequestBody CreateClubDto dto){
        return clubService.createClub(dto);
    }
    @PostMapping("/verifyCreateClub")
    @PreAuthorize("hasAuthority('Club:CreateClub')")
    public ResponseResult<Boolean> verifyCreateClub(){
        return clubService.verifyCreateClub();
    }
    @GetMapping("/noAuth/showClubBanners")
    public ResponseResult<List<ClubBannerDto>> showClubBanners(){
        return clubService.showClubBanners();
    }
    @PostMapping("/setClubToBanner")
    @PreAuthorize("hasAuthority('Club:SetClubBanner')")
    public ResponseResult setClubToBanner(@RequestBody Integer id){
        return clubService.setClubToBanner(id);
    }
    @PostMapping("/noAuth/getClubList")
    public PagedResult<ClubListDto> getClubList(@RequestBody ClubQueryDto dto){
        return clubService.getClubList(dto);
    }
}
