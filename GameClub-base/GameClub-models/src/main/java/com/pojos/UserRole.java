package com.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("userrole")
public class UserRole {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("userId")
    private Long userId;
    @TableField("roleId")
    private Integer roleId;

    public UserRole(Long userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
