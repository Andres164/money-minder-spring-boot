package com.baio.money_minder.budgets;

import com.baio.money_minder.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotBlank(message = "Divisa requerida")
    @Size(min = 3, max = 3, message = "La divisa debe ser un codigo de 3 caracteres (ISO)")
    private String currency;

    @NotNull(message = "Debes proveer una cantidad")
    @Digits(integer = 12, fraction = 2, message = "Formato de cantidad invalido")
    private BigDecimal amountUsed = BigDecimal.ZERO;

    /**
     * Duration of the budget in days (e.g. 1 = daily, 7 = weekly, 30 = monthly).
     */
    @Min(value = 1, message = "La duración mínima es 1 día")
    @Column(nullable = false)
    private int durationDays;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private BudgetState state = BudgetState.ACTIVE;

    @NotNull(message = "Fecha requerida")
    private LocalDate startDate;
}
