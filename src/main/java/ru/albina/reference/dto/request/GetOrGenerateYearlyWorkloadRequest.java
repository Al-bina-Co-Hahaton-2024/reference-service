package ru.albina.reference.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class GetOrGenerateYearlyWorkloadRequest {

    private int year;

    private Set<Integer> weeks;
}
