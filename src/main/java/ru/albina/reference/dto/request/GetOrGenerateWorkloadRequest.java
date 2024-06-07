package ru.albina.reference.dto.request;

import lombok.Data;

import java.util.Set;
@Data
public class GetOrGenerateWorkloadRequest {
    private Set<Integer> weeks;
}
