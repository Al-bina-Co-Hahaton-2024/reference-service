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
import ru.albina.reference.domain.Modality;
import ru.albina.reference.domain.TypeModality;
import ru.albina.reference.dto.request.GetOrGenerateWorkloadRequest;
import ru.albina.reference.dto.request.WorkloadEditRequest;
import ru.albina.reference.dto.response.Workload;
import ru.albina.reference.service.workload.WorkloadService;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(WebConstants.FULL_WEB + "/workloads")
@RequiredArgsConstructor
public class WorkloadController {

    private final WorkloadService workloadService;

    @Operation(
            summary = "Получить нагрузки по недельно",
            security = @SecurityRequirement(name = OpenApiConfiguration.JWT),
            responses = {
                    @ApiResponse(
                            description = "ОК",
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Workload.class)))
                    )
            }
    )
    //TODO @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<Workload> getWorkload() {
        return this.workloadService.getWorkloads();
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
    //TODO @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/years/{year}/calculate")
    public LinkedList<Workload> getWorkload(
            @PathVariable("year") int year,
            @RequestBody GetOrGenerateWorkloadRequest getOrGenerateWorkloadRequest
    ) {
        return this.workloadService.getOrCreate(year, getOrGenerateWorkloadRequest.getWeeks());
    }


    @Operation(
            summary = "Указать нагрузку за неделю",
            security = @SecurityRequirement(name = OpenApiConfiguration.JWT),
            responses = {
                    @ApiResponse(
                            description = "ОК",
                            responseCode = "200"
                    )
            }
    )
    //TODO @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/years/{year}/weeks/{week}/modalities/{modality}/types/{typeModality}")
    public void getWorkload(
            @PathVariable("year") int year,
            @PathVariable("week") int week,
            @PathVariable("modality") Modality modality,
            @PathVariable("typeModality") TypeModality typeModality,
            @RequestBody WorkloadEditRequest workloadEditRequest
    ) {
        this.workloadService.editWorkload(year, week, modality, typeModality, workloadEditRequest.getValue());
    }

}
