package com.carrefour.kata.domain;

import jakarta.persistence.*;

/**
 * Réservation d'un créneau par un client.
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identifiant fonctionnel du client (simple pour le kata). */
    private String customerRef;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private TimeSlot timeSlot;

    /* ---------- constructeurs ---------- */
    protected Reservation() { }  // JPA

    public Reservation(String customerRef, TimeSlot timeSlot) {
        this.customerRef = customerRef;
        this.timeSlot = timeSlot;
    }

    /* ---------- getters ---------- */
    public Long getId() { return id; }
    public String getCustomerRef() { return customerRef; }
    public TimeSlot getTimeSlot() { return timeSlot; }
}
