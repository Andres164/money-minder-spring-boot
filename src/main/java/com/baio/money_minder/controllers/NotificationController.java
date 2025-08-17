package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.*;
import com.baio.money_minder.entities.Notification;
import com.baio.money_minder.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "Access to users' notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Get all notifications", responses = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        var notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "Get notification by ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable int id) {
        return notificationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new notification", responses = {
            @ApiResponse(responseCode = "201", content = @Content),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @Valid @RequestBody NotificationRequest notification,
            UriComponentsBuilder uriBuilder
    ) {
        var newNotification = notificationService.createUser(notification);
        var location = uriBuilder.path("/{id}").buildAndExpand(newNotification.getId()).toUri();
        return ResponseEntity.created(location).body(newNotification);
    }

    @Operation(summary = "Update an existing notification", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable int id,
            @Valid @RequestBody NotificationRequest updatedNotification
    ) {
        return notificationService.updateNotification(id, updatedNotification)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete notification by ID", responses = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        return notificationService.deleteNotification(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}