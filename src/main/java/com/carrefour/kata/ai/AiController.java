package com.carrefour.kata.ai;

import com.carrefour.kata.ai.AiAdviceService;
import com.carrefour.kata.domain.DeliveryMethod;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiAdviceService service;

    public AiController(AiAdviceService service) {
        this.service = service;
    }

    @GetMapping("/advice")
    public Map<String, String> advice(
            @RequestParam("method") DeliveryMethod method,
            @RequestParam("day") @DateTimeFormat(iso = ISO.DATE) LocalDate day
    ) {
        return Map.of("advice", service.advice(method, day));
    }
}
