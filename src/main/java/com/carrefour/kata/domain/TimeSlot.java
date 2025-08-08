package com.carrefour.kata.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Créneau horaire pour un mode de livraison donné.
 */
@Entity
@Table(
        name = "time_slots",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_slot",
                columnNames = {"slot_date", "method", "start_time"}
        )
)
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slot_date")
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private DeliveryMethod method;

    /** Indique si le créneau est déjà réservé. */
    private boolean reserved = false;

    /** Verrouillage optimiste. */
    @Version
    private Long version;

    /* ---------- constructeurs ---------- */
    protected TimeSlot() { }  // JPA

    public TimeSlot(LocalDate date,
                    LocalTime startTime,
                    LocalTime endTime,
                    DeliveryMethod method) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.method = method;
    }

    /* ---------- getters / setters ---------- */
    public Long getId() { return id; }
    public LocalDate getDay() { return date; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public DeliveryMethod getMethod() { return method; }
    public boolean isReserved() { return reserved; }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
    void setId(Long id) { this.id = id; }
}
