package com.base.controller;

import com.base.mapper.EmployeeMapper;
import com.common.AppHttpCodeEnum;
import com.common.ResponseResult;
import com.pojos.Employee;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
@Api(value = "employee",tags = "employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeMapper employeeMapper;
    @PostMapping("/addEmployee")
    public ResponseResult<String> addEmployee(String name){
        try {
            Employee employee = new Employee();
            employee.setEmployeeName(name);
            employeeMapper.insert(employee);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseResult.error(AppHttpCodeEnum.SERVER_ERROR.getErrorMessage());
        }
        return ResponseResult.success("添加成功");
    }
}
