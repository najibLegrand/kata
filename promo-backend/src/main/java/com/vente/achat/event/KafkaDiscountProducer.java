package com.vente.achat.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class KafkaDiscountProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "discounts";

    public void publishAppliedEvent(String cartId, String code, java.math.BigDecimal amount) {
        kafkaTemplate.send(TOPIC, new DiscountAppliedEvent(cartId, code, amount, java.time.Instant.now()));
    }

    public void publishRejectedEvent(String cartId, String code) {
        kafkaTemplate.send(TOPIC, new DiscountAppliedEvent(cartId, code, java.math.BigDecimal.ZERO, java.time.Instant.now()));
    }
}
