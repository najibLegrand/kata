// src/main/java/com/carrefour/kata/api/mapper/TimeSlotMapper.java
package com.carrefour.kata.api.mapper;

import com.carrefour.kata.api.dto.TimeSlotDto;
import com.carrefour.kata.domain.TimeSlot;

public final class TimeSlotMapper {

    private TimeSlotMapper() { }

    public static TimeSlotDto toDto(TimeSlot slot) {
        return new TimeSlotDto(
                slot.getId(),
                slot.getDay(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getMethod()
        );
    }
}
