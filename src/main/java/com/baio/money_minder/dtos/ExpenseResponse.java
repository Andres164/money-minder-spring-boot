package com.baio.money_minder.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ExpenseResponse {
    private Long id;
    private Long userId;
    private Long categoryId;
    private BigDecimal amount;
    private String currency;
    private String description;
    private LocalDate date;
}