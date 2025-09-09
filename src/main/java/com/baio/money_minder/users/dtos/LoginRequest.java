package com.baio.money_minder.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @Email
    private String email;
    @NotEmpty
    private String password;
}
