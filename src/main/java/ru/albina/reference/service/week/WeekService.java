package ru.albina.reference.service.week;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.albina.reference.domain.WeekNumberEntity;
import ru.albina.reference.dto.response.WeekNumberResult;
import ru.albina.reference.exception.EntityNotFoundException;
import ru.albina.reference.repository.WeekNumberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

        return this.map(week);
    }

    @Transactional(readOnly = true)
    public List<WeekNumberResult> findAll(List<LocalDate> localDateList) {
        return localDateList.stream()
                .map(this.weekNumberRepository::findByDate)
                .map(v -> v.orElse(null))
                .filter(Objects::nonNull)
                .distinct()
                .map(this::map)
                .toList();
    }


    private WeekNumberResult map(WeekNumberEntity week) {
        return WeekNumberResult.builder()
                .startDate(week.getId().getStartDate())
                .endDate(week.getId().getEndDate())
                .weekNumber(week.getWeekNumber())
                .build();
    }
}
