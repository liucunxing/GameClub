package com.dto.ClubUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor@NoArgsConstructor
public class AppointExamDto {
    private Integer examCategory;
    private String gameId;
    private String gameArea;
    private String device;
    private Date examTime;
    private long examinerId;
    private String clubUUID;
}
