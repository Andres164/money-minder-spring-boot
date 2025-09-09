package com.baio.money_minder.notifications.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Long id;
    private String content;
    private Date notifyDate;
    private boolean hasBeenRead;
    private Long userId;
}
