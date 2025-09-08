package com.baio.money_minder.mappers;

import com.baio.money_minder.dtos.CreateExpenseRequest;
import com.baio.money_minder.dtos.ExpenseResponse;
import com.baio.money_minder.dtos.UpdateExpenseRequest;
import com.baio.money_minder.entities.Expense;
import org.hibernate.sql.Update;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    Expense toEntity(CreateExpenseRequest expenseResponse);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "category.id", source = "category.id")
    @Mapping(target = "category.name", source = "category.name")
    ExpenseResponse toDto(Expense expense);

    void update(UpdateExpenseRequest updatedExpense, @MappingTarget Expense expense);
}
