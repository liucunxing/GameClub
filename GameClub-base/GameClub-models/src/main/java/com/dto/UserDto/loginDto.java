package com.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginDto {
    private String name;
    private String telNumber;
    private String password;
}
