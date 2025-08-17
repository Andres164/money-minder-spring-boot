package com.baio.money_minder.services;

import com.baio.money_minder.entities.Category;
import com.baio.money_minder.exceptions.UniqueFieldViolationException;
import com.baio.money_minder.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsCategoryByName(category.getName())) {
            throw new UniqueFieldViolationException("name", "Una categoria con este nombre ya existe");
        }
        return categoryRepository.save(category);

    }

    public Optional<Category> updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(updatedCategory.getName());
            return categoryRepository.save(category);
        });
    }

    public boolean deleteCategory(Long id) {
        var category = categoryRepository.findById(id).orElse(null);
        if(category == null) {
            return false;
        }

        categoryRepository.delete(category);
        return true;
    }
}
