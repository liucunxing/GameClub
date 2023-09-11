package com.dto.ClubUser;

import lombok.Data;

import java.util.Date;

@Data
public class ClubUserListDto {
    private String name;
    private String telNumber;
    private Integer category;
    private Date joinTime;
    private int orderCount;
}
