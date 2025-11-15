package com.baio.money_minder.budgets.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBudgetRequest {
    private Long userId;
    private String name;
    private BigDecimal budgetAmount;
    private String currency;
    private int durationDays;
    private LocalDate startDate;
}
