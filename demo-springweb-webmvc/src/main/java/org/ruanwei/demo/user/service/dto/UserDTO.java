package org.ruanwei.demo.user.service.dto;

import org.ruanwei.demo.user.dao.entity.UserEntity;


public class UserDTO extends UserEntity {
    public UserDTO() {
    }


    public UserDTO(String name, int age) {
        super(name, age);
    }
}
