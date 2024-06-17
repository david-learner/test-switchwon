package com.switchwon.payments.exception;

/**
 * 카드 결제가 처리되지 않았을 때 발생하는 예외
 */
public class CardPaymentNotProcessedException extends RuntimeException {

    public CardPaymentNotProcessedException() {
        super("Card payment has was not processed.");
    }
}
