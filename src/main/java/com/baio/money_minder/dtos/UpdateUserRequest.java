package com.baio.money_minder.dtos;

import lombok.Data;

@Data
public class UpdateUserRequest {
    public String email;
    public String username;
}
