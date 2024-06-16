package com.switchwon.payments.service.dto;

import java.math.BigDecimal;

public class PaymentEstimationRequest {

    private BigDecimal amount;
    private String currency;
    private String merchantId;
    private Long userId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PaymentEstimationRequest{" +
                "amount=" + amount +
                ", currencyCode='" + currency + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
