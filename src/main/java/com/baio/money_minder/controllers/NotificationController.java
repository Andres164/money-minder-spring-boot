package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.*;
import com.baio.money_minder.entities.Notification;
import com.baio.money_minder.mappers.NotificationMapper;
import com.baio.money_minder.repositories.NotificationRepository;
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

@RestController
@CrossOrigin
@RequestMapping("/notifications")
@Tag(name = "Notifications")
@AllArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    private static final String BASE_URI = "/notifications";

    @Operation(summary = "Get all notifications", responses = {
        @ApiResponse(responseCode = "200")
    })
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        var notifications = this.notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    @Operation(summary = "Get notification by ID", responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable int id) {
        return notificationRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new notification", responses = {
        @ApiResponse(responseCode = "201"),
    })
    @PostMapping
    public ResponseEntity<Notification> createNotification(
        @Valid @RequestBody NotificationRequest notification,
        UriComponentsBuilder uriBuilder
    ) {
        var newNotification = this.notificationMapper.toEntity(notification);
        this.notificationRepository.save(newNotification);

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
        return notificationRepository.findById(id)
            .map(existing -> {
                notificationMapper.update(updatedNotification, existing);
                notificationRepository.save(existing);
                return ResponseEntity.ok(existing);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete notification by ID", responses = {
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "404", content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        return notificationRepository.findById(id)
            .map(existing -> {
                notificationRepository.delete(existing);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exp
    ) {
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}