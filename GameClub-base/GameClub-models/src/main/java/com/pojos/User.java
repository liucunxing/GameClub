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
    @TableField("avaterUrl")
    private String avaterUrl;
    @TableField("role")
    private Integer role;  //1.系统管理员  2.club创始人 3.club考官 4.club技术陪  5.club娱乐陪  6.点单用户
}

