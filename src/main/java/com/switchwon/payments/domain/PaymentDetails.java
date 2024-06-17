package com.switchwon.payments.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class PaymentDetails {

    private String cardNumber;
    private String expiryDate;
    private String cvv;

    protected PaymentDetails() {}

    public PaymentDetails(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }
}
