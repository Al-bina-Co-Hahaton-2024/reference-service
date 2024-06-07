package ru.albina.reference.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.albina.reference.configuration.MapperConfiguration;
import ru.albina.reference.domain.HoursPerMonthEntity;
import ru.albina.reference.dto.response.HoursPerMonthValue;

@Mapper(config = MapperConfiguration.class)
public interface HoursPerMonthMapper {

    @Mapping(target = "year", source = "id.year")
    @Mapping(target = "month", source = "id.monthValue")
    HoursPerMonthValue to(HoursPerMonthEntity hoursPerMonthEntity);
}
