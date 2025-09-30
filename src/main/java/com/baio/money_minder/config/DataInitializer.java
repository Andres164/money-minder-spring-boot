package com.baio.money_minder.config;

import com.baio.money_minder.categories.Category;
import com.baio.money_minder.categories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if(this.categoryRepository.count() > 0) {
            return;
        }

        this.categoryRepository.save(new Category(null, "Alimentos"));
        this.categoryRepository.save(new Category(null, "Transporte"));
        this.categoryRepository.save(new Category(null, "Entretenimiento"));
    }
}
