package ru.albina.reference.dto.response;

import lombok.Data;
import ru.albina.reference.domain.Modality;
import ru.albina.reference.domain.TypeModality;


@Data
public class Workload {

    private int year;

    private int week;

    private Modality modality;

    private TypeModality typeModality;

    private Long manualValue;

    private Long generatedValue;
}
