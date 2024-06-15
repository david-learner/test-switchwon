package com.switchwon.payments.service;

import com.switchwon.payments.domain.Money;
import java.math.BigDecimal;
import java.util.Currency;

public interface CurrencyConverter {
    Money convert(Money money, Currency targetCurrency);
    BigDecimal getExchangeRate(Currency fromCurrency, Currency toCurrency);
}
