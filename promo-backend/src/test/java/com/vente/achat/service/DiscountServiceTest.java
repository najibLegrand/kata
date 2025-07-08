package com.vente.achat.service;

import com.vente.achat.domain.model.*;
import com.vente.achat.dto.DiscountRequest;
import com.vente.achat.dto.DiscountResponse;
import com.vente.achat.event.KafkaDiscountProducer;
import com.vente.achat.exception.*;
import com.vente.achat.repository.CartRepository;
import com.vente.achat.repository.DiscountCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Tests unitaires de la logique métier DiscountService */
@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock KafkaDiscountProducer kafka;
    @Mock DiscountCodeRepository codeRepo;
    @Mock CartRepository cartRepo;

    @InjectMocks DiscountService service;

    /* ---------- Données communes ---------- */
    private Cart cart;
    private Product book;

    @BeforeEach
    void setUp() {
        book = new Product("p1", "Clean Code",
                new BigDecimal("40.00"), "books");
        cart = new Cart("c1", List.of(book), book.getPrice());
        when(cartRepo.findById("c1")).thenReturn(Optional.of(cart));
    }

    @Test
    void applyDiscount_shouldReturnCorrectAmount() {
        // GIVEN un code valide 20 %
        DiscountCode code = new DiscountCode("BOOK20",
                new BigDecimal("0.20"),
                Set.of("books"),
                LocalDate.now().plusDays(1),
                0);
        when(codeRepo.findByCode("BOOK20")).thenReturn(Optional.of(code));

        // WHEN
        DiscountResponse resp = service.applyDiscount(
                new DiscountRequest(cart, "BOOK20"));

        // THEN
        assertTrue(resp.isApplied());
        assertEquals(new BigDecimal("8.00"), resp.getDiscountAmount());
        verify(kafka).publishAppliedEvent("c1","BOOK20",
                new BigDecimal("8.00"));
    }

    @Test
    void applyDiscount_shouldThrowExpired() {
        DiscountCode expired = new DiscountCode("OLD10",
                new BigDecimal("0.10"),
                Set.of("books"),
                LocalDate.now().minusDays(1),
                10);
        when(codeRepo.findByCode("OLD10")).thenReturn(Optional.of(expired));

        assertThrows(CodeExpiredException.class,
                () -> service.applyDiscount(
                        new DiscountRequest(cart, "OLD10")));
        verify(kafka, never()).publishAppliedEvent(any(), any(), any());
    }

    @Test
    void applyDiscount_shouldThrowMaxUsesExceeded() {
        DiscountCode limited = new DiscountCode("LIMIT1",
                new BigDecimal("0.10"),
                Set.of("books"),
                LocalDate.now().plusDays(1),
                1);
        limited.incrementUses(); // déjà utilisé
        when(codeRepo.findByCode("LIMIT1")).thenReturn(Optional.of(limited));

        assertThrows(CodeMaxUsesExceededException.class,
                () -> service.applyDiscount(
                        new DiscountRequest(cart, "LIMIT1")));
    }

    @Test
    void applyDiscount_shouldThrowNotApplicable() {
        DiscountCode wrongCat = new DiscountCode("SHOES5",
                new BigDecimal("0.05"),
                Set.of("shoes"),
                LocalDate.now().plusDays(1),
                0);
        when(codeRepo.findByCode("SHOES5")).thenReturn(Optional.of(wrongCat));

        assertThrows(CodeNotApplicableException.class,
                () -> service.applyDiscount(
                        new DiscountRequest(cart, "SHOES5")));
    }
}
