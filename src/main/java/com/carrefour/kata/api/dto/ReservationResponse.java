// src/main/java/com/carrefour/kata/api/dto/ReservationResponse.java
package com.carrefour.kata.api.dto;

public record ReservationResponse(
        Long reservationId,
        Long slotId,
        String customerRef
) { }
