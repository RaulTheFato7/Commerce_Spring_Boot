package com.devsuperior.commerce.dto;

import com.devsuperior.commerce.entities.Payment;

import java.time.Instant;

public class PaymentDTO {

    private Long id;
    private Instant moment;

    public PaymentDTO() {}

    public PaymentDTO(Long id, Instant moment) {
        this.id = id;
        this.moment = moment;
    }
    public PaymentDTO(Payment entity) {
        id = entity.getId();
        moment = entity.getMoment();
    }
}
