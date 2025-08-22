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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        var notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "Get notification by ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable Long id) {
        return notificationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new notification", responses = {
            @ApiResponse(responseCode = "201", content = @Content),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody CreateNotificationRequest notification,
            UriComponentsBuilder uriBuilder
    ) {
        return notificationService.createNotification(notification)
                .map(newNotification -> {
                    var location = uriBuilder.path("/{id}").buildAndExpand(newNotification.getId()).toUri();
                    return ResponseEntity.created(location).body(newNotification);
                })
                .orElse(ResponseEntity.notFound().build());

    }

    @Operation(summary = "Update an existing notification", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNotificationRequest updatedNotification
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
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        return notificationService.deleteNotification(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}