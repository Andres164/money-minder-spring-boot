package com.baio.money_minder.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePasswordRequest {
    @Size(min = 8)
    private String oldPassword;
    @Size(min = 8)
    private String newPassword;
}
