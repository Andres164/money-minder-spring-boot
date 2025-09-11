package com.baio.money_minder.budgets;

import com.baio.money_minder.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty(message = "Nombre del presupuesto requerido")
    @Size(max = 60, message = "El nombre debe contener un maximo de 60 caracteres")
    @Column(length = 60, nullable = false, unique = true)
    private String name;

    @NotNull(message = "Debes proveer una cantidad")
    @Digits(integer = 12, fraction = 2, message = "Formato de cantidad invalido")
    private BigDecimal budgetAmount;

    private String currency;

    private BigDecimal amountUsed;

    // recurrence

    private Date startDate;

    private String state;
}
