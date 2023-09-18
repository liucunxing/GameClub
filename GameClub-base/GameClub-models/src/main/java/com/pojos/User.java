package com.pojos;

import com.baomidou.mybatisplus.annotation.*;
import com.common.BaseField;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User{
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
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
    @TableField(value = "createUser" ,fill = FieldFill.INSERT)
    private String createUser;
    @TableField(value = "createTime" ,fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "updateUser" ,fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    @TableField(value = "updateTime" ,fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}

