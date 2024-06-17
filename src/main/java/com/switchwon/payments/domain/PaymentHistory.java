package com.switchwon.payments.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PAYMENT_HISTORY")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private PaymentHistoryType type;
    @ManyToOne
    private Payment payment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    protected PaymentHistory() {}

    public PaymentHistory(
            PaymentHistoryType type,
            Payment payment,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.type = type;
        this.payment = payment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public PaymentHistoryType getType() {
        return type;
    }

    public Payment getPayment() {
        return payment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}
