package com.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("category")
public class category {
    @TableField("id")
    private Integer id;
    @TableField("categoryName")
    private String categoryName;
}
