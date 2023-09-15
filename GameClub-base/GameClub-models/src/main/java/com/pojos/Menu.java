package com.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("menu")
public class Menu {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("menu")
    private String menu;
    @TableField("perms")
    private String perms;
}
