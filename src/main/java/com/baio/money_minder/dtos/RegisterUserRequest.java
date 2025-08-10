package com.baio.money_minder.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @Email
    private String email;
    @NotEmpty
    private String username;
    @Size(min = 8)
    private String password;
}