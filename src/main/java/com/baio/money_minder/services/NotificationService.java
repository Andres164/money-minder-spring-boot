package com.baio.money_minder.services;

import com.baio.money_minder.dtos.NotificationRequest;
import com.baio.money_minder.entities.Notification;
import com.baio.money_minder.mappers.NotificationMapper;
import com.baio.money_minder.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public List<Notification> findAll() {
        return this.notificationRepository.findAll();
    }

    public Optional<Notification> findById(int id) {
        return notificationRepository.findById(id);
    }

    public Notification createUser(NotificationRequest notification) {
        var newNotification = this.notificationMapper.toEntity(notification);
        this.notificationRepository.save(newNotification);
        return newNotification;
    }

    // TODO: Check if .map is needed, could the readability be improved ?
    public Optional<Notification> updateNotification(int id, NotificationRequest updatedNotification) {
        return notificationRepository.findById(id)
                .map(notification -> {
                    notificationMapper.update(updatedNotification, notification);
                    notificationRepository.save(notification);
                    return notification;
                });
    }

    public boolean deleteNotification(int id) {
        var notification = notificationRepository.findById(id).orElse(null);
        if(notification == null) {
            return false;
        }

        notificationRepository.delete(notification);
        return true;
    }
}
