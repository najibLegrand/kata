package com.vente.achat.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data @AllArgsConstructor @NoArgsConstructor
public class DiscountAppliedEvent {
    private String cartId;
    private String code;
    private BigDecimal discountAmount;
    private Instant timestamp = Instant.now();
}