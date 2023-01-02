package com.hannunpalzi.postproject.dto;

import com.hannunpalzi.postproject.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UsernameAndRoleResponseDto {
    private String username;
    private UserRoleEnum role;

    public UsernameAndRoleResponseDto(String username, UserRoleEnum role) {
        this.username = username;
        this.role = role;
    }
}
