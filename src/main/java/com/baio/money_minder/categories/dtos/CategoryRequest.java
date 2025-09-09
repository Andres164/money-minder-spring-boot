package com.baio.money_minder.categories.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotEmpty
    private String name;
}
