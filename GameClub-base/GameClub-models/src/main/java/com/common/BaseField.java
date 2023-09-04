package com.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class BaseField {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "createUser" ,fill = FieldFill.INSERT)
    private String createUser;
    @TableField(value = "createTime" ,fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "updateUser" ,fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    @TableField(value = "updateTime" ,fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
