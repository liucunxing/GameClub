package com.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@TableName("clubusermap")
@Data
public class ClubUserMap {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "clubUUID")
    private String clubUUID;
    @TableField(value = "userId")
    private Long userId;
    @TableField(value = "category")
    private Integer category;
    @TableField(value = "joinTime")
    private Date joinTime;
    @TableField(value = "orderCount")
    private int orderCount;
}
