package com.dto.ClubDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubQueryDto {
    private String clubName;
    private int pageIndex;
    private int pageSize;
}
