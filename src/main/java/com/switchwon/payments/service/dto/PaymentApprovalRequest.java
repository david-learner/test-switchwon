package com.switchwon.payments.service.dto;

import com.switchwon.payments.domain.Money;
import com.switchwon.payments.domain.PaymentMethod;
import java.math.BigDecimal;

public class PaymentApprovalRequest {

    private Long userId;
    private BigDecimal amount;
    private String currency;
    private String merchantId;
    private PaymentMethod paymentMethod;
    private PaymentDetailsRequest paymentDetails;

    public PaymentApprovalRequest(
            Long userId,
            BigDecimal amount,
            String currency,
            String merchantId,
            PaymentMethod paymentMethod,
            PaymentDetailsRequest paymentDetails
    ) {
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.merchantId = merchantId;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentDetailsRequest getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetailsRequest paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Money toMoney() {
        return new Money(currency, amount);
    }
}
