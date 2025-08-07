// src/main/java/com/carrefour/kata/api/dto/TimeSlotDto.java
package com.carrefour.kata.api.dto;

import com.carrefour.kata.domain.DeliveryMethod;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeSlotDto(
        Long id,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        DeliveryMethod method
) { }
