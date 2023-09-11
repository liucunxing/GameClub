package com.dto.ClubUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubUserQueryDto {
    private int category;
    private String name;
    private String clubUUID;
    private int pageIndex;
    private int pageSize;
}
