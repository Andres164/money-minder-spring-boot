package com.baio.money_minder.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsCategoryByName(String name);
}
