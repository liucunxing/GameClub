package com.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.common.BaseField;
import lombok.Data;

@Data
@TableName("club")
public class Club extends BaseField {
    @TableField("uuid")
    private String uuid;
    @TableField("clubName")
    private String clubName;
    @TableField("iconUrl")
    private String iconUrl;

}
