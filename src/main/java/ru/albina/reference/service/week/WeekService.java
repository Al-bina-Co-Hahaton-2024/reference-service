package ru.albina.reference.service.week;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.albina.reference.dto.response.WeekNumberResult;
import ru.albina.reference.exception.EntityNotFoundException;
import ru.albina.reference.repository.WeekNumberRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeekService {

    private final WeekNumberRepository weekNumberRepository;

    @Transactional(readOnly = true)
    public WeekNumberResult find(LocalDate localDate) {
        final var week = this.weekNumberRepository.findByDate(localDate)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find week number for " + localDate)
                );

        return WeekNumberResult.builder()
                .startDate(week.getId().getStartDate())
                .endDate(week.getId().getEndDate())
                .weekNumber(week.getWeekNumber())
                .build();
    }

}
