package com.switchwon.payments.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Money {

    private final Currency currency;
    private final BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Money(String currencyCode, BigDecimal amount) {
        this(Currency.getInstance(currencyCode), amount);
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return Objects.equals(currency, money.currency) && equalsAmount(amount, money.amount);
    }

    private boolean equalsAmount(BigDecimal amount1, BigDecimal amount2) {
        return amount1.compareTo(amount2) == 0 ? true : false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
