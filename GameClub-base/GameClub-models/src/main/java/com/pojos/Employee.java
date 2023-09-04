package com.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.common.BaseField;
import lombok.Data;

@Data
@TableName("employee")
public class Employee extends BaseField {
    @TableField("employeeName")
    private String employeeName;
}
