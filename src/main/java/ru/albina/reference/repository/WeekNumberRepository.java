package ru.albina.reference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.albina.reference.domain.WeekNumberEntity;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeekNumberRepository extends JpaRepository<WeekNumberEntity, WeekNumberEntity.WeekNumberEntityId> {

    @Query(
            value = """
                        select *
                        from week_number where start_date <= :date AND :date <= end_date;
                    """,
            nativeQuery = true
    )
    Optional<WeekNumberEntity> findByDate(@Param("date") LocalDate date);

    @Query(
            value = """
                    SELECT MAX(week_number.week_number) FROM week_number WHERE date_part('year', start_date) = :year
                    """,
            nativeQuery = true
    )
    Optional<Long> getMaxWeekNumber(@Param("year") long year);
}
