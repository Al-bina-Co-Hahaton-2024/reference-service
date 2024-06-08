package ru.albina.reference.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeekNumberResult {

    private LocalDate startDate;

    private LocalDate endDate;

    private int weekNumber;
}
