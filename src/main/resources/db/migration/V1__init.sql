
CREATE TYPE delivery_method AS ENUM ('DRIVE','DELIVERY','DELIVERY_TODAY', 'DELIVERY_ASAP');

CREATE TABLE time_slots (
    id BIGSERIAL PRIMARY KEY,
    slot_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    method delivery_method NOT NULL,
    reserved BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT uk_slot UNIQUE (slot_date, method, start_time)
);

CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    slot_id BIGINT NOT NULL REFERENCES time_slots(id) ON DELETE CASCADE,
    customer_ref VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT uk_reservation UNIQUE (slot_id)
);
