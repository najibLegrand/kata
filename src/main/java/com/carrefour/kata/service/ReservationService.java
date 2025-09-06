// src/main/java/com/carrefour/kata/service/ReservationService.java
package com.carrefour.kata.service;

import com.carrefour.kata.domain.Reservation;
import com.carrefour.kata.domain.TimeSlot;
import com.carrefour.kata.repository.ReservationRepository;
import com.carrefour.kata.repository.TimeSlotRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final TimeSlotRepository slotRepo;
    private final ReservationRepository resRepo;

    public ReservationService(TimeSlotRepository slotRepo, ReservationRepository resRepo) {
        this.slotRepo = slotRepo;
        this.resRepo = resRepo;
    }

    @Transactional
    public Reservation reserveSlot(Long slotId, String customerRef) {
        TimeSlot slot = slotRepo.findById(slotId)
                .orElseThrow(() -> new EntityNotFoundException("Slot not found"));

        if (slot.isReserved() || resRepo.existsByTimeSlotId(slotId)) {
            throw new IllegalStateException("Slot already reserved");
        }

        slot.setReserved(true);                       // déclenche la version (optimistic locking)
        Reservation res = new Reservation(customerRef, slot);
        return resRepo.save(res);
    }

}
