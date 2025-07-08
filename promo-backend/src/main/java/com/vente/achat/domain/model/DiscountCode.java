package com.vente.achat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class DiscountCode {
    private String code;
    private BigDecimal percentage;
    private Set<String> applicableCategories;

    // règles avancées
    private LocalDate expiryDate;
    private int maxUses;                // 0 = illimité
    private final AtomicInteger uses = new AtomicInteger(0);

    public boolean isApplicable(Product p) { return applicableCategories.contains(p.getCategory()); }
    public boolean isExpired()            { return expiryDate != null && LocalDate.now().isAfter(expiryDate); }
    public boolean hasRemainingUses()     { return maxUses == 0 || uses.get() < maxUses; }
    public void   incrementUses()         { uses.incrementAndGet(); }
}
