package ru.albina.reference.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "hours_per_month")
@Accessors(chain = true)
public class HoursPerMonthEntity {
    @Id
    @Column(name = "month_date", nullable = false)
    private LocalDate id;

    @NotNull
    @Column(name = "hours", nullable = false)
    private Double hours;

}