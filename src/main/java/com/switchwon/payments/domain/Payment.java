package com.switchwon.payments.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private PaymentStatus status;
    private PaymentMethod method;
    private PaymentDetails details;
    @ManyToOne
    private User user;
    private String merchantId;
    private Money amount;
    private boolean isProcessed = false;
    private LocalDateTime createdAt;

    protected Payment() {}

    public Payment(
            PaymentMethod method,
            PaymentDetails details,
            PaymentStatus status,
            User user,
            String merchantId,
            Money amount,
            LocalDateTime createdAt
    ) {
        this.method = method;
        this.details = details;
        this.status = status;
        this.user = user;
        this.merchantId = merchantId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public PaymentDetails getDetails() {
        return details;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public Money getAmount() {
        return amount;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void processed() {
        this.isProcessed = true;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
