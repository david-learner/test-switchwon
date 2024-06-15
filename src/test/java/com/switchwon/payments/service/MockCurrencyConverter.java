package com.switchwon.payments.service;

import static com.switchwon.payments.domain.PaymentCurrency.*;

import com.switchwon.payments.domain.Money;
import java.math.BigDecimal;
import java.util.Currency;

public class MockCurrencyConverter implements CurrencyConverter {

    @Override
    public Money convert(Money money, Currency targetCurrency) {
        final var fromCurrency = money.getCurrency();
        if (fromCurrency.equals(targetCurrency)) {
            return money;
        }

        final BigDecimal exchangeRate = getExchangeRate(money.getCurrency(), targetCurrency);
        final BigDecimal convertedAmount = money.getAmount().multiply(exchangeRate);

        return new Money(targetCurrency, convertedAmount);
    }

    @Override
    public BigDecimal getExchangeRate(Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency.equals(USD) && toCurrency.equals(KRW)) {
            return new BigDecimal("1374.49");
        }
        if (fromCurrency.equals(KRW) && toCurrency.equals(USD)) {
            return new BigDecimal("0.00073");
        }

        throw new UnsupportedOperationException("Only support USD <-> KRW conversion.");
    }
}
