package com.baio.money_minder.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull(message = "Cantidad requerida")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cantidad debe ser mayor a 0")
    @Digits(integer = 12, fraction = 2, message = "Formato de cantidad invalido")
    private BigDecimal amount;

    @NotBlank(message = "Divisa requerida")
    @Size(min = 3, max = 3, message = "La divisa debe ser un codigo de 3 caracteres (ISO)")
    private String currency;

    @Size(max = 255, message = "La descripcion debe ser maximo 255 caracteres")
    private String description;

    @NotNull(message = "Fecha requerida")
    private LocalDate date;
}
