// src/main/java/com/carrefour/kata/repository/TimeSlotRepository.java
package com.carrefour.kata.repository;

import com.carrefour.kata.domain.DeliveryMethod;
import com.carrefour.kata.domain.TimeSlot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByMethodAndDateAndReservedFalse(DeliveryMethod method, LocalDate date);
    // pour la réservation avec verrouillage
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from TimeSlot t where t.id = :id")
    TimeSlot findByIdLock(@Param("id") Long id);
}
