package com.carrefour.kata.ai;

import com.carrefour.kata.ai.AiAdviceService;
import com.carrefour.kata.domain.DeliveryMethod;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiAdviceService service;

    public AiController(AiAdviceService service) {
        this.service = service;
    }

    @Operation(summary = "Conseil AI", description = "Fournit un conseil selon le mode et le jour.")
    @GetMapping("/advice")
    public Map<String, String> advice(
            @Parameter(description = "Mode de livraison", required = true)
            @RequestParam("method") DeliveryMethod method,
            @Parameter(description = "Jour (YYYY-MM-DD)", example = "2025-08-10", required = true)
            @RequestParam("day") @DateTimeFormat(iso = ISO.DATE) LocalDate day
    ) {
        return Map.of("advice", service.advice(method, day));
    }
}
