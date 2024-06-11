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

    @Query(
            value = """
                    SELECT * FROM week_number
                        WHERE (
                                date_part('year', end_date) = :year OR
                                ((date_part('year', start_date) = :yearMinusOne) AND (date_part('day', end_date) - date_part('day', start_date)) >= 7)
                            ) AND week_number.week_number = :week
                    """,
            nativeQuery = true
    )
    Optional<WeekNumberEntity> _findByYearAndWeek(@Param("year") int year, @Param("yearMinusOne") int yearMinusOne, @Param("week") int week);


    default Optional<WeekNumberEntity> findByYearAndWeek(@Param("year") int year, @Param("week") int week) {
        return this._findByYearAndWeek(year, year - 1, week);
    }
}
