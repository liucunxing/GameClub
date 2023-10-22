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
    @TableField("ifBanner")
    private Boolean ifBanner = false;
    @TableField("status")
    private int status = 2; //1-已创建 2-审核中 3-已解散
}
