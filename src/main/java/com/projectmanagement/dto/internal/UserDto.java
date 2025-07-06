package com.projectmanagement.dto.internal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String id;
    private String username;
    private char active;

    public UserDto(String id, String username, char active) {
        this.id = id;
        this.username = username;
        this.active = active;
    }

}