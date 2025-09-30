package com.baio.money_minder.budgets.dtos;

import com.baio.money_minder.budgets.BudgetState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetResponse {
    private Long id;
    private Long userId;
    private String name;
    private BigDecimal budgetAmount;
    private String currency;
    private BigDecimal amountUsed = BigDecimal.ZERO;
    private int durationDays;
    private BudgetState state = BudgetState.ACTIVE;
    private LocalDate startDate;
}
