package com.vente.achat.service;

import com.vente.achat.domain.model.Cart;
import com.vente.achat.domain.model.DiscountCode;
import com.vente.achat.domain.model.Product;
import com.vente.achat.dto.DiscountRequest;
import com.vente.achat.dto.DiscountResponse;
import com.vente.achat.event.KafkaDiscountProducer;
import com.vente.achat.exception.CodeExpiredException;
import com.vente.achat.exception.CodeMaxUsesExceededException;
import com.vente.achat.exception.CodeNotApplicableException;
import com.vente.achat.exception.DiscountException;
import com.vente.achat.repository.CartRepository;
import com.vente.achat.repository.DiscountCodeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountService {

    private final KafkaDiscountProducer kafka;
    private final DiscountCodeRepository codes;
    private final CartRepository carts;

    public DiscountService(KafkaDiscountProducer k, DiscountCodeRepository c, CartRepository carts) {
        this.kafka = k; this.codes = c; this.carts = carts;
    }

    public DiscountResponse applyDiscount(DiscountRequest req) {

        Cart cart = carts.findById(req.getCart().getCartId())
                .orElseThrow(() -> new DiscountException("Panier introuvable"));

        DiscountCode code = codes.findByCode(req.getCode())
                .orElseThrow(() -> new DiscountException("Code introuvable"));

        // ✅ validations avancées
        if (code.isExpired())
            throw new CodeExpiredException(code.getCode());

        if (!code.hasRemainingUses())
            throw new CodeMaxUsesExceededException(code.getCode());

        BigDecimal discount = computeDiscount(cart, code);
        if (discount.compareTo(BigDecimal.ZERO) == 0)
            throw new CodeNotApplicableException(code.getCode());

        // mise à jour usage + event
        code.incrementUses();
        kafka.publishAppliedEvent(cart.getCartId(), code.getCode(), discount);
        return new DiscountResponse(true, discount);
    }

    private BigDecimal computeDiscount(Cart cart, DiscountCode code) {
        return cart.getProducts().stream()
                .filter(code::isApplicable)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO,
                        (acc, price) -> acc.add(price.multiply(code.getPercentage())));
    }
}
