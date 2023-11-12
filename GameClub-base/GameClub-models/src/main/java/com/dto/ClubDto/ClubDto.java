package com.dto.ClubDto;

import lombok.Data;

@Data
public class ClubDto {
    private Integer id;
    private String uuid;
    private String clubName;
    private String iconUrl;
    private int hotCount;
    private String description;
}
