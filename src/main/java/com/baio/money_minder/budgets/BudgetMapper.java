package com.baio.money_minder.budgets;

import com.baio.money_minder.budgets.dtos.BudgetResponse;
import com.baio.money_minder.budgets.dtos.CreateBudgetRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
    Budget toEntity(CreateBudgetRequest budgetRequest);

    @Mapping(target = "userId", source = "user.id")
    BudgetResponse toDto(Budget budget);
}
