package com.baio.money_minder.notifications;


import com.baio.money_minder.notifications.dtos.CreateNotificationRequest;
import com.baio.money_minder.notifications.dtos.NotificationResponse;
import com.baio.money_minder.notifications.dtos.UpdateNotificationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "user.id" , source = "userId")
    Notification toEntity(CreateNotificationRequest createNotificationRequest);

    @Mapping(target = "userId" , source = "user.id")
    NotificationResponse toDto(Notification notification);

    void update(UpdateNotificationRequest updatedNotification, @MappingTarget Notification notification);
}
