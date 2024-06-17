package com.switchwon.payments.service;

import com.switchwon.payments.domain.Payment;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Primary
@Component
public class PayingApprovalPgClient implements PgClient {

    @Retryable(retryFor = HttpClientErrorException.class, backoff = @Backoff(delay = 3000L))
    @Override
    public Payment pay(Payment payment) {
        payment.processed();
        return payment;
    }
}