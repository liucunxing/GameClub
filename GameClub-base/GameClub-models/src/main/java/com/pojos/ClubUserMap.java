package com.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("clubusermap")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubUserMap {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "clubUUID")
    private String clubUUID;
    @TableField(value = "userId")
    private Long userId;
    @TableField(value = "roleId")
    private Integer roleId;
    @TableField(value = "joinTime")
    private Date joinTime;
    @TableField(value = "orderCount")
    private int orderCount;

    public ClubUserMap(String uuid, Long userId, int roleId,Date joinTime) {
        this.clubUUID = uuid;
        this.userId = userId;
        this.roleId = roleId;
        this.joinTime = joinTime;
    }
}
