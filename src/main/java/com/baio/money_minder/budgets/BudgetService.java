package com.baio.money_minder.budgets;

import com.baio.money_minder.budgets.dtos.BudgetResponse;
import com.baio.money_minder.budgets.dtos.CreateBudgetRequest;
import com.baio.money_minder.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
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

    public BudgetResponse createBudget(CreateBudgetRequest request) {
        var budget = this.budgetMapper.toEntity(request);

        var user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        budget.setUser(user);

        return this.budgetMapper.toDto( budgetRepository.save(budget) );
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
