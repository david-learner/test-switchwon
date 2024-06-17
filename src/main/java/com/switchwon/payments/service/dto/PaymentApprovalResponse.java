package com.switchwon.payments.service.dto;

import com.switchwon.payments.domain.Payment;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class PaymentApprovalResponse {

    private String paymentId;
    private String status;
    private BigDecimal amountTotal;
    private String currency;
    private String timestamp;

    public PaymentApprovalResponse(
            String paymentId,
            String status,
            BigDecimal amountTotal,
            String currency,
            String timestamp
    ) {
        this.paymentId = paymentId;
        this.status = status;
        this.amountTotal = amountTotal;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    public PaymentApprovalResponse(Payment payment) {
        this.paymentId = payment.getId().toString();
        this.status = payment.getStatus().name();
        this.amountTotal = payment.getAmount().getAmount();
        this.currency = payment.getAmount().getCurrency().getCurrencyCode();
        this.timestamp = payment.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
