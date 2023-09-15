package com.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rolemenu")
public class RoleMenu {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("roleId")
    private Integer roleId;
    @TableField("menuId")
    private Integer menuId;
}
