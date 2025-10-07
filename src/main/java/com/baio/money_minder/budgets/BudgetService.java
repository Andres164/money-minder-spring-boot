package com.baio.money_minder.budgets;

import com.baio.money_minder.budgets.dtos.BudgetResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public List<BudgetResponse> findAll() {
        return this.budgetRepository.findAll()
                .stream()
                .map(this.budgetMapper::toDto)
                .toList();
    }

    public Optional<BudgetResponse> findById(Long id) {
        return this.budgetRepository.findById(id)
                .map(this.budgetMapper::toDto);
    }

    public Optional<BudgetResponse> createBudget() {

    }

    public Optional<BudgetResponse> updateBudget() {

    }

    public Boolean deleteBudget(Long id) {
        var budgetToDelete = this.budgetRepository.findById(id).orElse(null);
        if(budgetToDelete == null) {
            return false;
        }

        this.budgetRepository.delete(budgetToDelete);
        return true;
    }
}
