package com.base.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.base.mapper.ClubUserMapper;
import com.base.mapper.ExamMapper;
import com.base.mapper.UserMapper;
import com.base.service.IClubUserService;
import com.common.PagedResult;
import com.common.ResponseResult;
import com.dto.ClubUser.AppointExamDto;
import com.dto.ClubUser.ClubUserListDto;
import com.dto.ClubUser.ClubUserQueryDto;
import com.dto.ClubUser.UpdateExamStatusDto;
import com.pojos.ClubUserMap;
import com.pojos.Exam;
import com.pojos.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ClubUserServiceImpl extends ServiceImpl<ClubUserMapper, ClubUserMap> implements IClubUserService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private ClubUserMapper clubUserMapper;
    @Autowired
    private AuthenticationFacadeImpl authenticationFacade;
    @Override
    public PagedResult<ClubUserListDto> queryClubUserList(ClubUserQueryDto dto) {
        var wrapper = getWrapper(dto);
        Page<ClubUserListDto> page = new Page<>(dto.getPageIndex(),dto.getPageSize());
        IPage<ClubUserListDto> pageResult = baseMapper.queryUserList(page,wrapper);
        return new PagedResult<>(pageResult.getRecords(), pageResult.getTotal(), dto.getPageIndex(), dto.getPageSize());
    }

    @Override
    public ResponseResult appointExam(AppointExamDto dto) {
        Long userId = Long.parseLong(authenticationFacade.getUserClaims().get("userId").toString());
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Exam::getUserId,userId).eq(Exam::getClubUUID,dto.getClubUUID());
        if(examMapper.selectOne(wrapper)!=null){
            return ResponseResult.error("您与预约过该俱乐部考核");
        }
        if(StringUtils.isEmpty(dto.getDevice()) || StringUtils.isEmpty(dto.getGameArea()) || StringUtils.isEmpty(dto.getGameId())){
            return ResponseResult.error("个人信息不可为空");
        }
        if(dto.getExamTime() == null){
            return ResponseResult.error("请预约考核时间");
        }
        Exam exam = new Exam();
        exam.setStatus(1);
        exam.setUserId(userId);
        BeanUtils.copyProperties(dto,exam);
        examMapper.insert(exam);
        return ResponseResult.success("预约考核成功");
    }

    @Override
    @Transactional
    public ResponseResult updateStatus(UpdateExamStatusDto dto) {
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Exam::getUserId,dto.getUserId());
        Exam exam = examMapper.selectOne(wrapper);
        if(exam == null){
            ResponseResult.success("考核用户不存在");
        }
        exam.setStatus(dto.getStatus());
        examMapper.updateById(exam);
        if(dto.getStatus() == 2){
            ClubUserMap clubUserMap = new ClubUserMap();
            BeanUtils.copyProperties(dto,clubUserMap);
            Date date = new Date();
            clubUserMap.setJoinTime(date);
            clubUserMap.setOrderCount(0);
            clubUserMapper.insert(clubUserMap);
        }
        return ResponseResult.success("修改考核状态成功");
    }

    private QueryWrapper<ClubUserListDto> getWrapper(ClubUserQueryDto dto){
        var wrapper = new QueryWrapper<ClubUserListDto>();
        wrapper.eq("c.clubUUID",dto.getClubUUID());
        if(!StringUtils.isEmpty(dto.getName())){
            wrapper.like("u.name",dto.getName());
        }
        if(dto.getCategory() != 0){
            wrapper.eq("u.category",dto.getCategory());
        }else{
            List<Integer> categories = Arrays.asList(2,3,4,5);
            wrapper.in("u.category", categories);
        }
        return wrapper;
    }
}
