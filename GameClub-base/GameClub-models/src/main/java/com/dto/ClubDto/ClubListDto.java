package com.dto.ClubDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ClubListDto {
    private int status;
    private String uuid;
    private String clubName;
    private String iconUrl;
    private int hotCount;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    private Integer orderCount;
}
