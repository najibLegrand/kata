package com.carrefour.kata;

import com.carrefour.kata.domain.DeliveryMethod;
import com.carrefour.kata.domain.TimeSlot;
import com.carrefour.kata.repository.TimeSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumSet;

/**
 * Point d’entrée de l’application Spring Boot.
 * <p>
 * – La méthode {@code main} démarre le contexte. <br>
 * – Le bean {@code CommandLineRunner} pré-charge une semaine de créneaux
 *   dans la base H2 afin de rendre l’API utilisable immédiatement.
 */
@SpringBootApplication
public class DeliverySchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliverySchedulerApplication.class, args);
    }

    /**
     * Initialise la base avec 3 créneaux (09-11, 11-13, 14-16) pour chaque
     * mode de livraison et pour les 7 prochains jours, uniquement si la table
     * est encore vide.
     */
    @Bean
    @ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
    CommandLineRunner seedDatabase(TimeSlotRepository repo) {
        return args -> {
            if (repo.count() > 0) return;      // déjà peuplé ? on sort.

            LocalDate today = LocalDate.now();

            EnumSet.allOf(DeliveryMethod.class).forEach(method -> {
                for (int d = 1; d <= 7; d++) {                // J+1 à J+7
                    LocalDate date = today.plusDays(d);

                    repo.save(new TimeSlot(date,
                            LocalTime.of(9, 0),
                            LocalTime.of(11, 0),
                            method));

                    repo.save(new TimeSlot(date,
                            LocalTime.of(11, 0),
                            LocalTime.of(13, 0),
                            method));

                    repo.save(new TimeSlot(date,
                            LocalTime.of(14, 0),
                            LocalTime.of(16, 0),
                            method));
                }
            });
        };
    }
}
