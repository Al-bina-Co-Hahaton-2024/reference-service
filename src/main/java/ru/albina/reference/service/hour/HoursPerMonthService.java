package ru.albina.reference.service.hour;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.albina.reference.domain.HoursPerMonthEntity;
import ru.albina.reference.dto.response.HoursPerMonthValue;
import ru.albina.reference.mapper.HoursPerMonthMapper;
import ru.albina.reference.repository.HoursPerMonthRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HoursPerMonthService {

    private final HoursPerMonthRepository hoursPerMonthRepository;

    private final HoursPerMonthMapper hoursPerMonthMapper;

    @Transactional(readOnly = true)
    public List<HoursPerMonthValue> getByYear(int year) {
        List<LocalDate> list = new ArrayList<>();
        for (int month = 1; month < 13; month++) {
            LocalDate localDate = this.generateId(year, month);
            list.add(localDate);
        }
        return this.hoursPerMonthRepository.findAllById(list)
                .stream().map(this.hoursPerMonthMapper::to)
                .toList();
    }

    @Transactional
    public void createOrUpdate(int year, int month, double hours) {
        final var id = this.generateId(year, month);
        this.hoursPerMonthRepository.findById(id)
                .orElseGet(() -> this.hoursPerMonthRepository.save(new HoursPerMonthEntity().setId(id).setHours(hours)))
                .setHours(hours);
    }

    @Transactional(readOnly = true)
    public HoursPerMonthValue getByYearAndMonth(int year, int month) {
        return this.hoursPerMonthRepository.findById(this.generateId(year, month))
                .map(this.hoursPerMonthMapper::to)
                .orElseThrow(
                        () -> new IllegalArgumentException("Year: " + year + " Month: " + month + "; not generated!")
                );
    }

    private LocalDate generateId(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

}
