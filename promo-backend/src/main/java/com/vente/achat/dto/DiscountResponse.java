package com.vente.achat.dto;

import lombok.*;

import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class DiscountResponse {
    private boolean applied;
    private BigDecimal discountAmount;
}
