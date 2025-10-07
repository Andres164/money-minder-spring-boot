package com.baio.money_minder.budgets;

import com.baio.money_minder.budgets.dtos.BudgetResponse;
import com.baio.money_minder.budgets.dtos.CreateBudgetRequest;
import com.baio.money_minder.budgets.dtos.UpdateBudgetRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
    Budget toEntity(CreateBudgetRequest budgetRequest);

    @Mapping(target = "userId", source = "user.id")
    BudgetResponse toDto(Budget budget);

    void update(UpdateBudgetRequest updateBudget, @MappingTarget Budget budget);
}
