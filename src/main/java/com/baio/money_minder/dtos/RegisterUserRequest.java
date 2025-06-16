package com.baio.money_minder.dtos;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String username;
    private String password;
}