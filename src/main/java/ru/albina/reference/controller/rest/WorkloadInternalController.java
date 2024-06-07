package ru.albina.reference.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.albina.backlib.configuration.WebConstants;
import ru.albina.backlib.configuration.auto.OpenApiConfiguration;
import ru.albina.reference.dto.request.GetOrGenerateWorkloadRequest;
import ru.albina.reference.dto.request.GetOrGenerateYearlyWorkloadRequest;
import ru.albina.reference.dto.response.Workload;
import ru.albina.reference.service.workload.WorkloadService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(WebConstants.FULL_PRIVATE + "/workloads")
@RequiredArgsConstructor
public class WorkloadInternalController {

    private final WorkloadService workloadService;


    @Operation(
            summary = "Получить нагрузку за год по неделям",
            security = @SecurityRequirement(name = OpenApiConfiguration.JWT),
            responses = {
                    @ApiResponse(
                            description = "ОК",
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Workload.class)))
                    )
            }
    )
    @PostMapping("/years/{year}/calculate")
    public List<Workload> getWorkload(
            @PathVariable("year") int year,
            @RequestBody GetOrGenerateWorkloadRequest getOrGenerateWorkloadRequest
    ) {
        return this.workloadService.getOrCreate(year, getOrGenerateWorkloadRequest.getWeeks());
    }


    @Operation(
            summary = "Получить нагрузку за год по неделям",
            security = @SecurityRequirement(name = OpenApiConfiguration.JWT),
            responses = {
                    @ApiResponse(
                            description = "ОК",
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Workload.class)))
                    )
            }
    )
    @PostMapping("/calculate")
    public List<Workload> getWorkload(
            @RequestBody List<GetOrGenerateYearlyWorkloadRequest> getOrGenerateYearlyWorkloadRequests
    ) {
        return getOrGenerateYearlyWorkloadRequests.stream()
                .map(request -> this.workloadService.getOrCreate(request.getYear(), request.getWeeks()))
                .flatMap(Collection::stream)
                .toList();
    }

}
