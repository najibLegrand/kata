package com.vente.achat.repository;

import com.vente.achat.domain.model.DiscountCode;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DiscountCodeRepository {
    private final Map<String, DiscountCode> store = new ConcurrentHashMap<>();

    public Optional<DiscountCode> findByCode(String code) { return Optional.ofNullable(store.get(code)); }
    public void save(DiscountCode code)                   { store.put(code.getCode(), code); }
    public Collection<DiscountCode> findAll()             { return store.values(); }
}
