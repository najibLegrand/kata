package com.carrefour.kata.api;

import com.carrefour.kata.api.dto.*;
import com.carrefour.kata.api.mapper.TimeSlotMapper;
import com.carrefour.kata.domain.DeliveryMethod;
import com.carrefour.kata.domain.Reservation;
import com.carrefour.kata.repository.TimeSlotRepository;
import com.carrefour.kata.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DeliveryController {

    private final TimeSlotRepository slotRepo;
    private final ReservationService reservationService;

    public DeliveryController(TimeSlotRepository slotRepo, ReservationService reservationService) {
        this.slotRepo = slotRepo;
        this.reservationService = reservationService;
    }

    @GetMapping("/delivery-methods")
    public DeliveryMethod[] methods() {
        return DeliveryMethod.values();
    }

    @GetMapping("/time-slots")
    public List<TimeSlotDto> freeSlots(@RequestParam("method") DeliveryMethod method,
                                       @RequestParam("date") LocalDate date) {
        return slotRepo.findByMethodAndDateAndReservedFalse(method, date)
                .stream()
                .map(TimeSlotMapper::toDto)
                .toList();
    }

    @PostMapping("/reservations/{slotId}")
    public ResponseEntity<ReservationResponse> reserve(@PathVariable("slotId") Long slotId,
                                                       @RequestBody ReservationRequest req) {
        Reservation res = reservationService.reserveSlot(slotId, req.customerRef());
        return ResponseEntity.ok(
                new ReservationResponse(res.getId(), res.getTimeSlot().getId(), res.getCustomerRef())
        );
    }
}
