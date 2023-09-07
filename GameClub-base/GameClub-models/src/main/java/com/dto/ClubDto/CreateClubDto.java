package com.dto.ClubDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClubDto {
    private String clubName;
    private String iconUrl;

}
