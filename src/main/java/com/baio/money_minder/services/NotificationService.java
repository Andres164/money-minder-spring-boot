package com.baio.money_minder.services;

import com.baio.money_minder.dtos.CreateNotificationRequest;
import com.baio.money_minder.dtos.NotificationResponse;
import com.baio.money_minder.dtos.UpdateNotificationRequest;
import com.baio.money_minder.entities.Notification;
import com.baio.money_minder.entities.User;
import com.baio.money_minder.mappers.NotificationMapper;
import com.baio.money_minder.repositories.NotificationRepository;
import com.baio.money_minder.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public List<NotificationResponse> findAll() {
        return this.notificationRepository.findAll()
                .stream()
                .map(this.notificationMapper::toDto)
                .toList();
    }

    public Optional<NotificationResponse> findById(Long id) {
        return notificationRepository.findById(id)
                .map(this.notificationMapper::toDto);
    }

    /**
     * Create a notification
     * @param notification notification to create
     * @return the created notification
     * @throws IllegalArgumentException if the notification's userId cannot be found
     */
    public Optional<NotificationResponse> createNotification(CreateNotificationRequest notification) {
        var newNotification = notificationMapper.toEntity(notification);

        var user = userRepository.findById(notification.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + notification.getUserId()));

        // TODO: Move the mapping logic if possible
        newNotification.setUser(user);

        notificationRepository.save(newNotification);
        return Optional.of(this.notificationMapper.toDto(newNotification));
    }

    public Optional<NotificationResponse> updateNotification(Long id, UpdateNotificationRequest updatedNotification) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notificationMapper.update(updatedNotification, notification);
                    notificationRepository.save(notification);
                    return this.notificationMapper.toDto(notification);
                });
    }

    public boolean deleteNotification(Long id) {
        var notification = notificationRepository.findById(id).orElse(null);
        if(notification == null) {
            return false;
        }

        notificationRepository.delete(notification);
        return true;
    }
}
