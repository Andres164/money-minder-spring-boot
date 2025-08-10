package com.baio.money_minder.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Email
    public String email;
    @NotEmpty
    public String username;
}
