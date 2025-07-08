package com.vente.achat.config;

import com.vente.achat.domain.model.*;
import com.vente.achat.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class InMemoryDataLoader implements CommandLineRunner {

    private final CartRepository          cartRepo;
    private final DiscountCodeRepository  codeRepo;

    @Override
    public void run(String... args) {

        /* --- Codes promo --- */
        codeRepo.save(new DiscountCode(
                "BOOK20",
                new BigDecimal("0.20"),
                Set.of("books"),
                LocalDate.of(2030, 1, 1),   // pas expiré
                100                          // 100 utilisations max
        ));

        codeRepo.save(new DiscountCode(
                "ELECTRO10",
                new BigDecimal("0.10"),
                Set.of("electronics"),
                LocalDate.of(2030, 12, 31),
                0                            // illimité
        ));

        codeRepo.save(new DiscountCode(
                "OLD10",
                new BigDecimal("0.10"),
                Set.of("books", "electronics"),
                LocalDate.now().minusDays(1), // expiré
                50
        ));

        /* --- Panier exemple --- */
        Product book = new Product("p1", "Clean Code Book", new BigDecimal("40.00"), "books");
        Product tv   = new Product("p2", "Smart TV",    new BigDecimal("300.00"), "electronics");

        Cart cart = new Cart(
                "cart123",
                List.of(book, tv),
                book.getPrice().add(tv.getPrice())       // total 340 €
        );

        cartRepo.save(cart);

        /* Log optionnel */
        System.out.println("▶︎ Demo data loaded (carts & discount codes)");
    }
}
