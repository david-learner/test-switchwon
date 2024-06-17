package com.switchwon.payments.domain;

import java.math.BigDecimal;

public class PaymentFeeCalculator {

    private static final BigDecimal DEFAULT_PAYMENT_FEE_RATE = new BigDecimal("0.03");

    public static BigDecimal calculatePaymentFee(BigDecimal amount) {
        return amount.multiply(DEFAULT_PAYMENT_FEE_RATE);
    }
}
