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
import ru.albina.reference.dto.request.HoursPerMonthValueRequest;
import ru.albina.reference.dto.response.HoursPerMonthValue;
import ru.albina.reference.service.hour.HoursPerMonthService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(WebConstants.FULL_WEB + "/hours-per-month")
@RequiredArgsConstructor
public class HoursPerMonthController {

    private final HoursPerMonthService hoursPerMonthService;

    @Operation(
            summary = "Получить рабочие часы за год",
            security = @SecurityRequirement(name = OpenApiConfiguration.JWT),
            responses = {
                    @ApiResponse(
                            description = "ОК",
                            responseCode = "200",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HoursPerMonthValue.class)))
                    )
            }
    )
    @GetMapping("/{year}")
    public List<HoursPerMonthValue> getByYear(
            @PathVariable("year") int year
    ) {
        return this.hoursPerMonthService.getByYear(year);
    }

    @Operation(
            summary = "Добавить рабочие часы для месяца в году",
            security = @SecurityRequirement(name = OpenApiConfiguration.JWT),
            responses = {
                    @ApiResponse(
                            description = "ОК",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/{year}/months/{month}")
    public void createOrUpdate(
            @PathVariable("year") int year,
            @PathVariable("month") int month,
            @RequestBody HoursPerMonthValueRequest request
    ) {
        this.hoursPerMonthService.createOrUpdate(year, month, request.getHours());
    }
}
