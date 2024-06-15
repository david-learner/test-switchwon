package com.switchwon.payments.service.dto;

import static com.switchwon.payments.service.dto.MoneyDecimalPointHandler.handleDecimalPoint;

import com.switchwon.payments.domain.Money;
import java.math.BigDecimal;

public class BalanceRetrievalResponse {

    private String userId;
    private BigDecimal balance;
    private String currency;

    private BalanceRetrievalResponse(String userId, BigDecimal balance, String currency) {
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }

    public static BalanceRetrievalResponse of(String userId, Money money) {
        return new BalanceRetrievalResponse(
                userId,
                handleDecimalPoint(money),
                money.getCurrency().getCurrencyCode()
        );
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }
}
