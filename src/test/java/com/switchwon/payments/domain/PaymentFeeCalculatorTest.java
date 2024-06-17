package com.switchwon.payments.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentFeeCalculatorTest {

    @Test
    @DisplayName("150원의_원금에_대한_결제_수수료는_4.5원이다")
    void calculatePaymentFee() {
        BigDecimal amount = new BigDecimal("150");
        BigDecimal paymentFee = new BigDecimal("4.5");

        BigDecimal calculatePaymentFee = PaymentFeeCalculator.calculatePaymentFee(amount);

        assertTrue(paymentFee.compareTo(calculatePaymentFee) == 0);
    }


}