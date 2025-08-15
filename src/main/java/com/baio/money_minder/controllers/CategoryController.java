package com.baio.money_minder.controllers;

import com.baio.money_minder.entities.Category;
import com.baio.money_minder.repositories.CategoryRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Operation(summary = "Get category by ID", responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all categories", responses = {
        @ApiResponse(responseCode = "200")
    })
    @GetMapping
    public ResponseEntity<Iterable<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @Operation(summary = "Create a new category", responses = {
        @ApiResponse(responseCode = "201"),
        @ApiResponse(responseCode = "400", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(
        @RequestBody Category category,
        UriComponentsBuilder uriBuilder
    ) {
        // TODO: Agregar un mensaje de error "Ya existe una categoria con este nombre" y posiblemente devolver el URI a este recurso
        if (categoryRepository.existsCategoryByName(category.getName())) {
            return ResponseEntity.badRequest().build(); // or 409 Conflict
        }

        categoryRepository.save(category);
        var location = uriBuilder.path("/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update an existing category", responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
        @PathVariable Long id,
        @RequestBody Category request
    ) {
        return categoryRepository.findById(id)
        // TODO:  IMPROVE BY NOT HAVING THIS MAPPING LOGIC IN THE CONTROLLER
            .map(existing -> {
                existing.setName(request.getName());
                categoryRepository.save(existing);
                return ResponseEntity.ok(existing);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete category by ID", responses = {
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "404", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
            .map(existing -> {
                categoryRepository.delete(existing);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}



