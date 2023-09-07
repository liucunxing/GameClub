package com.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.common.BaseField;
import lombok.Data;

@Data
@TableName("user")
public class User extends BaseField {
    @TableField("name")
    private String name;
    @TableField("salt")
    private String salt;
    @TableField("telNumber")
    private String telNumber;
    @TableField("password")
    private String password;
    @TableField("age")
    private int age;
    @TableField("sex")
    private boolean sex;
    @TableField("category")
    private Integer category;
}
