// src/test/java/com/carrefour/kata/api/DeliveryControllerIT.java
package com.carrefour.kata.api;

import com.carrefour.kata.domain.DeliveryMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryControllerIT {

    @Autowired MockMvc mockMvc;

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
