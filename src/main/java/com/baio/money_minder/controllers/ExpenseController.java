package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.CreateExpenseRequest;
import com.baio.money_minder.dtos.ExpenseResponse;
import com.baio.money_minder.dtos.UpdateExpenseRequest;
import com.baio.money_minder.services.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/expenses")
@Tag(name = "Expenses", description = "Operations related to user expenses")
@AllArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @Operation(
            summary = "Retrieve all expenses",
            description = "Fetches a list of all registered expenses.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of expenses")
            }
    )
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> findAll() {
        return ResponseEntity.ok(expenseService.findAll());
    }

    @Operation(
            summary = "Retrieve an expense by ID",
            description = "Fetches the details of a specific expense by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expense found"),
                    @ApiResponse(responseCode = "404", description = "Expense not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> findById(
            @Parameter(description = "ID of the expense to retrieve", example = "1")
            @PathVariable Long id
    ) {
        return expenseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new expense",
            description = "Registers a new expense in the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Expense successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
            @Parameter(description = "Expense details to create")
            @Valid @RequestBody CreateExpenseRequest expenseRequest,
            UriComponentsBuilder uriBuilder
    ) {
        var created = expenseService.createExpense(expenseRequest);
        var location = uriBuilder.path("/expenses/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @Operation(
            summary = "Update an existing expense",
            description = "Updates the details of an existing expense identified by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Expense successfully updated"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Expense not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @Parameter(description = "ID of the expense to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated expense details")
            @Valid @RequestBody UpdateExpenseRequest expenseRequest
    ) {
        return expenseService.updateExpense(id, expenseRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete an expense",
            description = "Deletes a specific expense identified by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Expense successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Expense not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @Parameter(description = "ID of the expense to delete", example = "1")
            @PathVariable Long id
    ) {
        boolean deleted = expenseService.deleteExpense(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

