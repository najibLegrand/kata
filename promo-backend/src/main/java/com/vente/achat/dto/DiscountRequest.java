package com.vente.achat.dto;

import com.vente.achat.domain.model.Cart;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class DiscountRequest {
    private Cart cart;
    private String code;
}