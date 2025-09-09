package com.baio.money_minder.categories;

import com.baio.money_minder.categories.dtos.CategoryRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(
        name = "Categories",
        description = "Operations related to the categories catalog used to classify expenses"
)
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Get category by ID",
            description = "Retrieve a category by its unique identifier.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Unique identifier of the category to retrieve",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category found and returned successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category with the given ID was not found",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get all categories",
            description = "Retrieve the complete list of available categories.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of categories returned successfully"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @Operation(
            summary = "Create a new category",
            description = "Create and persist a new category. Category names must be unique.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Category details for the new category",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "A category with the same name already exists",
                            content = @Content
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest,
            UriComponentsBuilder uriBuilder
    ) {
        var savedCategory = categoryService.createCategory(categoryRequest);

        var location = uriBuilder.path("/{id}")
                .buildAndExpand(savedCategory.getId()).toUri();
        return ResponseEntity.created(location).body(savedCategory);
    }

    @Operation(
            summary = "Update an existing category",
            description = "Update the details of an existing category identified by its ID.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Unique identifier of the category to update",
                            required = true,
                            example = "1"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated category details",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Category updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category with the given ID was not found",
                            content = @Content
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        return categoryService.updateCategory(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete category by ID",
            description = "Delete an existing category identified by its ID.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Unique identifier of the category to delete",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Category deleted successfully (no content returned)"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category with the given ID was not found",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
