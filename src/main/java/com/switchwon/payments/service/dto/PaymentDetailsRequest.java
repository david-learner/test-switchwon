package com.switchwon.payments.service.dto;

import com.switchwon.payments.domain.PaymentDetails;

public class PaymentDetailsRequest {

    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public PaymentDetailsRequest(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public PaymentDetails toPaymentDetails() {
        return new PaymentDetails(cardNumber, expiryDate, cvv);
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
