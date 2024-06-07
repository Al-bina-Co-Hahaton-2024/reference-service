package ru.albina.reference.service.hour;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.albina.reference.domain.HoursPerMonthEntity;
import ru.albina.reference.dto.response.HoursPerMonthValue;
import ru.albina.reference.mapper.HoursPerMonthMapper;
import ru.albina.reference.repository.HoursPerMonthRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class HoursPerMonthService {

    private final HoursPerMonthRepository hoursPerMonthRepository;

    private final HoursPerMonthMapper hoursPerMonthMapper;

    @Transactional(readOnly = true)
    public List<HoursPerMonthValue> getByYear(int year) {
        return this.hoursPerMonthRepository.findAllById(IntStream.range(1, 12).mapToObj(month -> this.generateId(year, month)).toList())
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

    private LocalDate generateId(int year, int month) {
        return LocalDate.of(year, month, 1);
    }
}
