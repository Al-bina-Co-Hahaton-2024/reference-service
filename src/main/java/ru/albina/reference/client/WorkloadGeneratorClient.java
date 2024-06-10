package ru.albina.reference.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.albina.reference.domain.Modality;
import ru.albina.reference.domain.TypeModality;
import ru.albina.reference.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WorkloadGeneratorClient {


    private final WebClient webClient;

    public WorkloadGeneratorClient(WebClient.Builder libWebClientBuilder) {
        this.webClient = libWebClientBuilder
                .baseUrl(Optional.ofNullable(System.getenv("ML_HOST")).orElse("http://localhost:5000"))
                .build();
    }


    public Long getByYearAndWeek(long year, long week, Modality modality, TypeModality typeModality) {
        final var result = this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/{year}/{week}").build(year, week))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map.Entry<String, BigDecimal>>>() {
                })
                .blockOptional();

        final var modalityStr = modality.toString().toUpperCase() +
                (typeModality != TypeModality.DEFAULT ? "_" + typeModality.toString().toUpperCase() : "");

        return result
                .map(v-> v.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .map(v -> v.get(modalityStr))
                .map(BigDecimal::longValue)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find modality " + modality + " " + typeModality)
                );
    }
}
