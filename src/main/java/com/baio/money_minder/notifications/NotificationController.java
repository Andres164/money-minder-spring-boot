package com.baio.money_minder.notifications;

import com.baio.money_minder.notifications.dtos.CreateNotificationRequest;
import com.baio.money_minder.notifications.dtos.NotificationResponse;
import com.baio.money_minder.notifications.dtos.UpdateNotificationRequest;
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
@CrossOrigin
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "Manage and access users' notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(
            summary = "Get all notifications",
            description = "Retrieve the complete list of notifications",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of notifications"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        var notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    @Operation(
            summary = "Get notification by ID",
            description = "Retrieve a single notification by its unique identifier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the notification"
                    ),
                    @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(
            @Parameter(description = "Notification ID", example = "1")
            @PathVariable Long id
    ) {
        return notificationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new notification",
            description = "Create and persist a new notification record",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Notification successfully created"
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid notification data provided", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Parameter(description = "Notification creation payload")
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

    @Operation(
            summary = "Update an existing notification",
            description = "Update a notification's details by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification successfully updated"
                    ),
                    @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(
            @Parameter(description = "Notification ID", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Notification update payload")
            @Valid @RequestBody UpdateNotificationRequest updatedNotification
    ) {
        return notificationService.updateNotification(id, updatedNotification)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete notification by ID",
            description = "Delete an existing notification using its unique identifier",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Notification successfully deleted"),
                    @ApiResponse(responseCode = "404", description = "Notification not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
            @Parameter(description = "Notification ID", example = "1")
            @PathVariable Long id
    ) {
        return notificationService.deleteNotification(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
