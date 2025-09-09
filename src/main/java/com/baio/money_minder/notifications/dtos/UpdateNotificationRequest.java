package com.baio.money_minder.notifications.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNotificationRequest {
    @NotEmpty
    private String content;
    @Future(message = "La fecha de notificacion debe ser una fecha en el futuro")
    private Date notifyDate;
    @NotNull
    private boolean hasBeenRead;
}
