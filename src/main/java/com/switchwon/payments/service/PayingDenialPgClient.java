package com.switchwon.payments.service;

import com.switchwon.payments.domain.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class PayingDenialPgClient implements PgClient {

    @Retryable(retryFor = HttpClientErrorException.class, backoff = @Backoff(delay = 3000L))
    @Override
    public Payment pay(Payment payment) {
        throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}