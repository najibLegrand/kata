package com.vente.achat.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class Cart {
    private String cartId;
    private List<Product> products;
    private BigDecimal total;
}
