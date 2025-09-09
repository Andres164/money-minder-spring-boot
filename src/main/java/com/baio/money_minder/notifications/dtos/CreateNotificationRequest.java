package com.baio.money_minder.notifications.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNotificationRequest {
    @NotEmpty
    private String content;
    @Future(message = "La fecha de notificacion debe ser una fecha en el futuro")
    private Date notifyDate;
    @NotNull
    private boolean hasBeenRead;
    @Positive(message = "Debe ser mayor a 0")
    private Long userId;
}
