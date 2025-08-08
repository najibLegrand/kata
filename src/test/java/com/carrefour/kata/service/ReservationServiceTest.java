// src/test/java/com/carrefour/kata/service/ReservationServiceTest.java
package com.carrefour.kata.service;

import com.carrefour.kata.domain.DeliveryMethod;
import com.carrefour.kata.domain.Reservation;
import com.carrefour.kata.domain.TimeSlot;
import com.carrefour.kata.repository.ReservationRepository;
import com.carrefour.kata.repository.TimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock TimeSlotRepository slotRepo;
    @Mock ReservationRepository resRepo;
    @InjectMocks ReservationService service;

    @BeforeEach
    void init() { MockitoAnnotations.openMocks(this); }

    @Test
    void reserveFreeSlot() {
        TimeSlot slot = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(11, 0), DeliveryMethod.DELIVERY);
        // inutile d'avoir un id pour ce test
        when(slotRepo.findById(anyLong())).thenReturn(java.util.Optional.of(slot));
        when(resRepo.save(any(Reservation.class))).thenAnswer(inv -> inv.getArgument(0));

        Reservation res = service.reserveSlot(42L, "c1");

        assertThat(res.getTimeSlot()).isSameAs(slot);
        assertThat(res.getCustomerRef()).isEqualTo("c1");
        assertThat(slot.isReserved()).isTrue();

        verify(slotRepo).findById(42L);
        verify(resRepo).save(any(Reservation.class));
    }

    @Test
    void rejectAlreadyReservedSlot() {
        TimeSlot slot = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(11, 0), DeliveryMethod.DELIVERY);
        slot.setReserved(true);

        when(slotRepo.findById(anyLong())).thenReturn(java.util.Optional.of(slot));

        assertThatThrownBy(() -> service.reserveSlot(99L, "c2"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already reserved");

        verify(resRepo, never()).save(any());
    }
}
