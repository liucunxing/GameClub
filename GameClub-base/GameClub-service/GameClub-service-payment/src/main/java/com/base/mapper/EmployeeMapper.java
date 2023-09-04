package com.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pojos.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
