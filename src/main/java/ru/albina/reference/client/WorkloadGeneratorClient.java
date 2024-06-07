package ru.albina.reference.client;

import org.springframework.stereotype.Component;
import ru.albina.reference.domain.Modality;
import ru.albina.reference.domain.TypeModality;

@Component
public class WorkloadGeneratorClient {


    public Long getByYearAndWeek(long year, long week, Modality modality, TypeModality typeModality) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
