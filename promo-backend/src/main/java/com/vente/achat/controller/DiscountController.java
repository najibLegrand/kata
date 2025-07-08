package com.vente.achat.controller;

import com.vente.achat.dto.DiscountRequest;
import com.vente.achat.dto.DiscountResponse;
import com.vente.achat.domain.model.Cart;
import com.vente.achat.service.DiscountService;
import com.vente.achat.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount")
@RequiredArgsConstructor      // génère le constructeur pour les final
@CrossOrigin(origins = "*")   // utile pour le front Next.js en dev
public class DiscountController {

    private final DiscountService service;
    private final CartRepository  cartRepository;

    /** POST /api/v1/discount/apply
     *  Applique un code promo au panier envoyé dans la requête */
    @PostMapping("/apply")
    public ResponseEntity<DiscountResponse> applyDiscount(@RequestBody DiscountRequest request) {
        DiscountResponse resp = service.applyDiscount(request);
        return ResponseEntity.ok(resp);
    }

    /** GET /api/v1/discount/cart/{id}
     *  Retourne le contenu d’un panier par son id */
    @GetMapping("/cart/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable String id) {
        return cartRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
