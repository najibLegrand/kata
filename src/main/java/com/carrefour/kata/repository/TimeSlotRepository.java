// src/main/java/com/carrefour/kata/repository/TimeSlotRepository.java
package com.carrefour.kata.repository;

import com.carrefour.kata.domain.DeliveryMethod;
import com.carrefour.kata.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByMethodAndDateAndReservedFalse(DeliveryMethod method, LocalDate date);
}
