package ru.albina.reference.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class WeekNumberResult {

    private LocalDate startDate;

    private LocalDate endDate;

    private int weekNumber;
}
