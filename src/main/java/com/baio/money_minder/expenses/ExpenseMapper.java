package com.baio.money_minder.expenses;

import com.baio.money_minder.expenses.dtos.CreateExpenseRequest;
import com.baio.money_minder.expenses.dtos.ExpenseResponse;
import com.baio.money_minder.expenses.dtos.UpdateExpenseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    Expense toEntity(CreateExpenseRequest expenseResponse);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "category.id", source = "category.id")
    @Mapping(target = "category.name", source = "category.name")
    ExpenseResponse toDto(Expense expense);

    void update(UpdateExpenseRequest updatedExpense, @MappingTarget Expense expense);
}
