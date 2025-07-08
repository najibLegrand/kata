package com.vente.achat.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class Product {
    private String id;
    private String name;
    private BigDecimal price;
    private String category;
}
