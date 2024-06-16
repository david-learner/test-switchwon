package com.switchwon.payments.service.dto;

import com.switchwon.payments.domain.Money;
import java.math.BigDecimal;

public class PaymentEstimationResponse {

    private BigDecimal estimatedTotal;
    private BigDecimal fees;
    private String currency;

    public PaymentEstimationResponse(BigDecimal estimatedTotal, BigDecimal fees, String currency) {
        this.estimatedTotal = MoneyDecimalPointHandler.handleDecimalPoint(new Money(currency, estimatedTotal));
        this.fees = MoneyDecimalPointHandler.handleDecimalPoint(new Money(currency, fees));
        this.currency = currency;
    }

    public BigDecimal getEstimatedTotal() {
        return estimatedTotal;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public String getCurrency() {
        return currency;
    }
}
