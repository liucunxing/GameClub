package com.dto.UserDto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String name;
    private String telNumber;
    private String password;
    private int age;
    private boolean sex;
    private String userAvater;
    //private List<Integer> roleIds;
}
