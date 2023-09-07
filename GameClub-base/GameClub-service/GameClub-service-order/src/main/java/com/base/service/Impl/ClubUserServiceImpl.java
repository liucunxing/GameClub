package com.base.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.ClubUserMapper;
import com.base.service.IClubUserService;
import com.pojos.ClubUserMap;
import org.springframework.stereotype.Service;

@Service
public class ClubUserServiceImpl extends ServiceImpl<ClubUserMapper, ClubUserMap> implements IClubUserService {
}
