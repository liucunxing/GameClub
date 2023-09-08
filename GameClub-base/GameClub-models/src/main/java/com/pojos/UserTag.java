package com.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
@NoArgsConstructor
@TableName("usertag")
public class UserTag {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("userId")
    private Integer userId;
    @TableField("tagName")
    private String tagName;
    @TableField("clubUUID")
    private String clubUUID;
}
