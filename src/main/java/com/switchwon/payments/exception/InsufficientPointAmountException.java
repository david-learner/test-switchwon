package com.switchwon.payments.exception;

/**
 * 포인트가 부족할 때 발생하는 예외
 */
public class InsufficientPointAmountException extends RuntimeException {

    public InsufficientPointAmountException() {
        super("Point is not enough to do something.");
    }
}
