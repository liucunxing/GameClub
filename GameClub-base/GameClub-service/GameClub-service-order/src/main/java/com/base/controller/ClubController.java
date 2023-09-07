package com.base.controller;

import com.base.myInterface.Authorize;
import com.base.service.IClubService;
import com.common.ResponseResult;
import com.dto.ClubDto.CreateClubDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
