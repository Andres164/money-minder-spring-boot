package com.baio.money_minder.controllers;

import com.baio.money_minder.entities.Category;
import com.baio.money_minder.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Access the expenses' categories catalog")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get category by ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all categories", responses = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(summary = "Create a new category", responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "409", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            UriComponentsBuilder uriBuilder
    ) {
        var savedCategory = categoryService.createCategory(category);

        var location = uriBuilder.path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();
        return ResponseEntity.created(location).body(savedCategory);
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
        return categoryService.updateCategory(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete category by ID", responses = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
