package com.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tag")
public class Tag {
    @TableField("id")
    private Integer id;
    @TableField("tagName")
    private String tagName;
}
