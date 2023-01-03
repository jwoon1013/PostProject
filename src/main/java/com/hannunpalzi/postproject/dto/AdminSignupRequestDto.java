package com.hannunpalzi.postproject.dto;

import lombok.Getter;

@Getter
public class AdminSignupRequestDto {
    public String username;
    public String password;
    public String adminToken;
}
