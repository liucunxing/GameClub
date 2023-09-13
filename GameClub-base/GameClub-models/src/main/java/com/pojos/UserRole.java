package com.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("userrole")
public class UserRole {
    private long id;
    private long userId;
    private Integer roleId;
}
