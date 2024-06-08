package ru.albina.reference.service.workload;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.albina.reference.client.WorkloadGeneratorClient;
import ru.albina.reference.domain.Modality;
import ru.albina.reference.domain.TypeModality;
import ru.albina.reference.domain.WorkloadEntity;
import ru.albina.reference.dto.response.Workload;
import ru.albina.reference.mapper.WorkloadMapper;
import ru.albina.reference.repository.WorkloadRepository;
import ru.albina.reference.service.week.WeekService;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkloadService {

    private static final Map<Modality, Set<TypeModality>> REFERENCE = Map.of(
            Modality.DENSITOMETER, Set.of(TypeModality.DEFAULT),
            Modality.FLG, Set.of(TypeModality.DEFAULT),
            Modality.KT, Set.of(TypeModality.DEFAULT, TypeModality.U, TypeModality.U2),
            Modality.MMG, Set.of(TypeModality.DEFAULT),
            Modality.MRT, Set.of(TypeModality.DEFAULT, TypeModality.U, TypeModality.U2),
            Modality.RG, Set.of(TypeModality.DEFAULT)
    );

    private final WorkloadRepository workloadRepository;

    private final WorkloadMapper workloadMapper;

    private final WorkloadGeneratorClient workloadGeneratorClient;

    private final WeekService weekService;

    @Transactional(readOnly = true)
    public List<Workload> getWorkloads() {
        return this.workloadRepository.findAll().stream()
                .map(this.workloadMapper::from)
                .toList();
    }

    @Transactional
    public List<Workload> getOrCreate(int year, Set<Integer> weeks) {
        final var result = new LinkedList<Workload>();
        weeks.forEach(week->{
            final var list = REFERENCE.keySet().stream()
                    .map(modality -> REFERENCE.get(modality).stream().map(typeModality -> this.getOrCreateEntity(year, week, modality, typeModality)).toList())
                    .flatMap(Collection::stream)
                    .map(this.workloadMapper::from)
                    .toList();

            result.addAll(list);
        });
        return result;
    }

    @Transactional
    public void editWorkload(int year, int week, Modality modality, TypeModality typeModality, Long value) {
        this.getOrCreateEntity(year, week, modality, typeModality)
                .setManualValue(value);
    }

    public WorkloadEntity getOrCreateEntity(int year, int week, Modality modality, TypeModality typeModality) {

        if (week <= 0 || this.maxWeeksAtYear(year) < week) {
            throw new IllegalArgumentException("Week out of range, " + this.maxWeeksAtYear(year) + " less than " + week);
        }

        final var id = this.generate(year, week, modality, typeModality);

        return this.workloadRepository.findById(id)
                .orElseGet(() -> this.workloadRepository.save(
                        new WorkloadEntity()
                                .setId(id)
                                .setGeneratedValue(this.workloadGeneratorClient.getByYearAndWeek(year, week, modality, typeModality))
                ));
    }


    private long maxWeeksAtYear(int year) {
        LocalDate date = LocalDate.of(year,1,1).with(TemporalAdjusters.lastDayOfYear());
        return this.weekService.find(date).getWeekNumber();
    }


    private WorkloadEntity.WorkloadId generate(int year, int week, Modality modality, TypeModality typeModality) {
        return new WorkloadEntity.WorkloadId()
                .setYear(year)
                .setWeek(week)
                .setModality(modality)
                .setTypeModality(typeModality);
    }
}
