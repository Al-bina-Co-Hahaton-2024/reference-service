package ru.albina.reference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.albina.reference.domain.Modality;
import ru.albina.reference.domain.TypeModality;
import ru.albina.reference.domain.WorkloadEntity;

import java.util.Optional;

@Repository
public interface WorkloadRepository extends JpaRepository<WorkloadEntity, WorkloadEntity.WorkloadId> {
}
