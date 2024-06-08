package ru.albina.reference.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.albina.backlib.configuration.WebConstants;
import ru.albina.backlib.configuration.auto.OpenApiConfiguration;
import ru.albina.reference.dto.response.WeekNumberResult;
import ru.albina.reference.service.week.WeekService;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping(WebConstants.FULL_PRIVATE + "/week-numbers")
@RequiredArgsConstructor
public class WeekNumberInternalController {

    private final WeekService weekService;

    @Operation(
            summary = "Найти номер недели по дате",
            security = @SecurityRequirement(name = OpenApiConfiguration.JWT),
            responses = {
                    @ApiResponse(
                            description = "ОК",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = WeekNumberResult.class))
                    )
            }
    )
    @GetMapping
    public WeekNumberResult getWorkload(@RequestParam("date") LocalDate localDate) {
        return this.weekService.find(localDate);
    }
}
