package com.baio.money_minder.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
