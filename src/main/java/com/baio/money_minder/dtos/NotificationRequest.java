package com.baio.money_minder.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String content;
    private Date notifyDate;
    private boolean hasBeenRead;
    private Integer userId;
}
