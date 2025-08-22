package com.baio.money_minder.services;

import com.baio.money_minder.dtos.CreateExpenseRequest;
import com.baio.money_minder.dtos.ExpenseResponse;
import com.baio.money_minder.dtos.UpdateExpenseRequest;
import com.baio.money_minder.mappers.ExpenseMapper;
import com.baio.money_minder.repositories.CategoryRepository;
import com.baio.money_minder.repositories.ExpenseRepository;
import com.baio.money_minder.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public List<ExpenseResponse> findAll() {
        return this.expenseRepository.findAll()
                .stream()
                .map(this.expenseMapper::toDto)
                .toList();
    }

    public Optional<ExpenseResponse> findById(Long id) {
        return this.expenseRepository.findById(id)
                .map(this.expenseMapper::toDto);
    }

    // TODO: return Optional<ExpenseResponse>
    public ExpenseResponse createExpense(CreateExpenseRequest request) {
        var expense = expenseMapper.toEntity(request);

        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
// TODO: Mapping logic should go in mapper
        expense.setUser(user);
        expense.setCategory(category);

        return expenseMapper.toDto(expenseRepository.save(expense));
    }

    public Optional<ExpenseResponse> updateExpense(Long id, UpdateExpenseRequest expenseRequest) {
        return this.expenseRepository.findById(id)
                .map(expense ->  {
                    var user = expense.getUser();
                    var category = categoryRepository.findById(expenseRequest.getCategoryId())
                            .orElseThrow(() -> new IllegalArgumentException("Category not found")); // TODO: catch exception in controller and return Bad Request

                    this.expenseMapper.update(expenseRequest, expense);
                // TODO: This mapping logic should be in the mapper
                    expense.setUser(user);
                    expense.setCategory(category);

                    this.expenseRepository.save(expense);
                    return this.expenseMapper.toDto(expense);
                });
    }

    public boolean deleteExpense(Long id) {
        var expense = this.expenseRepository.findById(id).orElse(null);
        if(expense == null) {
            return false;
        }

        this.expenseRepository.delete(expense);
        return true;
    }
}
