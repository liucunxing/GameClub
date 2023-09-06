package com.dto.UserDto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String name;
    private String telNumber;
    private String password;
    private int age;
    private boolean sex;
    private int category;
}
