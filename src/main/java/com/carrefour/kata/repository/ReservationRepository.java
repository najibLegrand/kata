// src/main/java/com/carrefour/kata/repository/ReservationRepository.java
package com.carrefour.kata.repository;

import com.carrefour.kata.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByTimeSlotId(Long timeSlotId);
}
