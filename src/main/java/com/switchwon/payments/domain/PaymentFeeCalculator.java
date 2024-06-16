package com.switchwon.payments.domain;

import java.math.BigDecimal;

public class PaymentFeeCalculator {

    // todo: payment fee 정책별로 다르게 적용할 수 있도록 변경
    private static final BigDecimal DEFAULT_PAYMENT_FEE_RATE = new BigDecimal("0.03");

    public static BigDecimal calculatePaymentFee(BigDecimal amount) {
        return amount.multiply(DEFAULT_PAYMENT_FEE_RATE);
    }
}
