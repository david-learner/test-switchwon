package com.switchwon.payments.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

@Embeddable
public class Money {

    private Currency currency;
    private BigDecimal amount;

    protected Money() {}

    public Money(Currency currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Money(String currencyCode, BigDecimal amount) {
        this(Currency.getInstance(currencyCode), amount);
    }

    public Money add(Money target) {
        if (!isSameCurrency(target)) {
            throw new IllegalArgumentException("Cannot add different currencies.");
        }

        return new Money(currency, amount.add(target.getAmount()));
    }

    public Money subtract(Money target) {
        if (!isSameCurrency(target)) {
            throw new IllegalArgumentException("Cannot subtract different currencies.");
        }

        if (!isEqualOrBiggerThan(target)) {
            throw new IllegalArgumentException("Cannot subtract bigger amount.");
        }

        return new Money(currency, amount.subtract(target.getAmount()));
    }

    public boolean isSameCurrency(Money target) {
        return this.currency.equals(target.getCurrency());
    }

    public boolean isEqualOrBiggerThan(Money target) {
        if (!isSameCurrency(target)) {
            throw new IllegalArgumentException("Cannot compare different currencies.");
        }

        return this.compareTo(target) >= 0;
    }

    private int compareTo(Money target) {
        return this.amount.compareTo(target.getAmount());
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
