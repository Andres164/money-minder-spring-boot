package com.baio.money_minder.mappers;


import com.baio.money_minder.dtos.NotificationRequest;
import com.baio.money_minder.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toEntity(NotificationRequest notificationRequest);

    Notification update(NotificationRequest updatedNotification, @MappingTarget Notification notification);
}
