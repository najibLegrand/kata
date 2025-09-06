// src/test/java/com/carrefour/kata/api/DeliveryControllerIT.java
package com.carrefour.kata.api;

import com.carrefour.kata.DeliverySchedulerApplication;
import com.carrefour.kata.domain.DeliveryMethod;
import com.carrefour.kata.domain.TimeSlot;
import com.carrefour.kata.repository.ReservationRepository;
import com.carrefour.kata.repository.TimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(classes = DeliverySchedulerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DeliveryControllerIT {

    @Autowired MockMvc mockMvc;
    @Autowired ReservationRepository reservationRepository;
    @Autowired TimeSlotRepository timeSlotRepository;
    private Long slotId;
    private LocalDate testDay;


    @BeforeEach
    void prepare() {
        reservationRepository.deleteAllInBatch();
        timeSlotRepository.deleteAllInBatch();
        testDay = LocalDate.now().plusDays(1);

        TimeSlot slot = timeSlotRepository.save(new TimeSlot(
                testDay, LocalTime.of(9, 0), LocalTime.of(11, 0), DeliveryMethod.DELIVERY));
        slotId = slot.getId();
    }

    @Test
    void shouldReturnDeliveryMethods() throws Exception {
        mockMvc.perform(get("/api/delivery-methods"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("DRIVE"));
    }

    @Test
    void shouldListFreeTimeSlots() throws Exception {
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        mockMvc.perform(get("/api/time-slots")
                        .param("method", DeliveryMethod.DELIVERY.name())
                        .param("date", tomorrow.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].method").value("DELIVERY"));
    }

    @Test
    void shouldReserveAndRejectSecondReservation() throws Exception {
        // 1. réserve le slot 1
        mockMvc.perform(post("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerRef\":\"cust-123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slotId").value(1));

        // 2. même slot une 2ᵉ fois => 409
        mockMvc.perform(post("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerRef\":\"cust-456\"}"))
                .andExpect(status().isConflict());
    }
}
