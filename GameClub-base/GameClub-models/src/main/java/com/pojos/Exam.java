package com.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("exam")
public class Exam {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    @TableField("userId")
    private long userId;
    @TableField("examCategory")
    private Integer examCategory;
    @TableField("gameId")
    private String gameId;
    @TableField("gameArea")
    private String gameArea;
    @TableField("device")
    private String device;
    @TableField("examTime")
    private Date examTime;
    @TableField("examinerId")
    private long examinerId;
    @TableField("status")
    private int status; //考核状态  1-等待考核  2-考核通过  3-考核未通过
}
