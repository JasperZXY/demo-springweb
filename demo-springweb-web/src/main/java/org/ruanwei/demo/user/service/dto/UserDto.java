package org.ruanwei.demo.user.service.dto;

import org.ruanwei.demo.user.dao.entity.UserEntity;


public class UserDto extends UserEntity {
    public UserDto() {
    }


    public UserDto(String name, int age) {
        super(name, age);
    }

}
