package com.baio.money_minder.users.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotEmpty
    private String oldPassword;
    @Size(min = 8, message = "La contraseña debe contener al menos 8 caracteres")
    private String newPassword;
}
