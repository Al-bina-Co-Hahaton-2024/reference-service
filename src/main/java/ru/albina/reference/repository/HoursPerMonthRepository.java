package ru.albina.reference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.albina.reference.domain.HoursPerMonthEntity;

import java.time.LocalDate;

@Repository
public interface HoursPerMonthRepository extends JpaRepository<HoursPerMonthEntity, LocalDate> {

}
