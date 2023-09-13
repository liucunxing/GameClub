package com.pojos;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rolemenu")
public class RoleMenu {
    private Integer id;
    private Integer roleId;
    private Integer menuId;
}
