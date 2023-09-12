package com.dto.ClubUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExamStatusDto {
    private long userId;
    private int status;
    private int category;
    private String clubUUID;
}
