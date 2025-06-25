package com.baio.money_minder.controllers;

import com.baio.money_minder.entities.Category;
import com.baio.money_minder.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private CategoryRepository categoryRepository;
    // GET
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable(name = "id") Long id) {
        var category = this.categoryRepository.findById(id).orElse(null);
        if(category == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(category);
    }

    // getAll
    @GetMapping
    public ResponseEntity<Iterable<Category>> getAllCategories() {
        var categories = this.categoryRepository.findAll();

        return ResponseEntity.ok(categories);
    }

    // Create
    @PostMapping
    public ResponseEntity<Category> createCategory(
        @RequestBody Category category,
        UriComponentsBuilder uriBuilder ) {

        boolean isCategoryDuplicate = this.categoryRepository.existsCategoryByName(category.getName());
        if(isCategoryDuplicate) {
            // TODO: Agregar un mensaje de error "Ya existe una categoria con este nombre" y posiblemente devolver el URI a este recurso
            return ResponseEntity.badRequest().build();
        }

        this.categoryRepository.save(category);
        var categoryUri = uriBuilder.path("/categories/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(categoryUri).build();
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
        @PathVariable(name = "id") Long id,
        @RequestBody Category request ) {
        var category = this.categoryRepository.findById(id).orElse(null);
        if(category == null) {
            return ResponseEntity.notFound().build();
        }
        // TODO:  IMPROVE BY NOT HAVING THIS MAPPING LOGIC IN THE CONTROLLER
        category.setName(request.getName());
        this.categoryRepository.save(category);

        return ResponseEntity.ok(category);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
        var category = this.categoryRepository.findById(id).orElse(null);
        if(category == null) {
            return ResponseEntity.notFound().build();
        }

        this.categoryRepository.delete(category);
        return ResponseEntity.noContent().build
    }
}

