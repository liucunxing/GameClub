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
    private Integer category;  //1.系统管理员  2.club创始人 3.club成员 4.club考官  5.普通用户
}

