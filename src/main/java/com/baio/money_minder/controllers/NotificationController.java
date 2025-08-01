package com.baio.money_minder.controllers;

import com.baio.money_minder.dtos.*;
import com.baio.money_minder.entities.Notification;
import com.baio.money_minder.mappers.NotificationMapper;
import com.baio.money_minder.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    private static final String BASE_URI = "/notifications";

    @GetMapping
    public ResponseEntity<Iterable<Notification>> getAllNotifications() {
        var notifications = this.notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable int id) {
        var notification = this.notificationRepository.findById(id).orElse(null);
        if(notification == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notification);
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(
        @RequestBody NotificationRequest notification,
        UriComponentsBuilder uriBuilder
    ) {
        /* TODO: Add validation to prevent the creation of notifications with notify date in the past */
        var newNotification = this.notificationMapper.toEntity(notification);
        this.notificationRepository.save(newNotification);

        var notificationUri = uriBuilder.path(BASE_URI + "/{id}").buildAndExpand(newNotification.getId()).toUri();
        return ResponseEntity.created(notificationUri).body(newNotification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
        @PathVariable(name = "id") int id,
        @RequestBody NotificationRequest updatedNotification
    ) {
        var notification = this.notificationRepository.findById(id).orElse(null);
        if(notification == null) {
            return ResponseEntity.notFound().build();
        }

        notificationMapper.update(updatedNotification, notification);
        notificationRepository.save(notification);

        return ResponseEntity.ok(notification);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(
        @PathVariable(name = "id") int id
    ) {
        var notification = this.notificationRepository.findById(id).orElse(null);
        if(notification == null) {
            return ResponseEntity.notFound().build();
        }

        this.notificationRepository.delete(notification);
        return ResponseEntity.noContent().build();
    }
}