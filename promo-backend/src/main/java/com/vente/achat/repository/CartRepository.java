package com.vente.achat.repository;

import com.vente.achat.domain.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CartRepository {
    private final Map<String, Cart> store = new ConcurrentHashMap<>();

    public Optional<Cart> findById(String id)      { return Optional.ofNullable(store.get(id)); }
    public void save(Cart cart)                    { store.put(cart.getCartId(), cart); }
    public void clear()                            { store.clear(); }
}
