package ru.albina.reference.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.albina.reference.configuration.MapperConfiguration;
import ru.albina.reference.domain.WorkloadEntity;
import ru.albina.reference.dto.response.Workload;

@Mapper(config = MapperConfiguration.class)
public interface WorkloadMapper {


    @Mapping(target = "year", source = "id.year")
    @Mapping(target = "week", source = "id.week")
    @Mapping(target = "modality", source = "id.modality")
    @Mapping(target = "typeModality", source = "id.typeModality")
    Workload from(WorkloadEntity workloadEntity);

}
