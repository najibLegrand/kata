package com.carrefour.kata.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_ref", nullable = false)
    private String customerRef;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "slot_id",                 // <— IMPORTANT : doit matcher le DDL Flyway
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_reservation_slot")
    )
    private TimeSlot timeSlot;

    protected Reservation() { }  // JPA

    public Reservation(String customerRef, TimeSlot timeSlot) {
        this.customerRef = customerRef;
        this.timeSlot = timeSlot;
    }

    public Long getId() { return id; }
    public String getCustomerRef() { return customerRef; }
    public TimeSlot getTimeSlot() { return timeSlot; }
}
