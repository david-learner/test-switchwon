package com.switchwon.payments.service.dto;

import com.switchwon.payments.domain.PaymentCurrency;

public class BalanceRetrievalRequest {

    private Long userId;
    private String returningCurrencyCode = PaymentCurrency.USD.getCurrencyCode();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReturningCurrencyCode() {
        return returningCurrencyCode;
    }

    public void setReturningCurrencyCode(String returningCurrencyCode) {
        this.returningCurrencyCode = returningCurrencyCode;
    }

    @Override
    public String toString() {
        return "BalanceRetrievalRequest{" +
                "userId=" + userId +
                ", returningCurrencyCode='" + returningCurrencyCode + '\'' +
                '}';
    }
}