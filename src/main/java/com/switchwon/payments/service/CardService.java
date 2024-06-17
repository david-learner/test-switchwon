package com.switchwon.payments.service;

import com.switchwon.payments.domain.Payment;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final PgClient pgClient;

    public CardService(PgClient pgClient) {
        this.pgClient = pgClient;
    }

    public Payment pay(Payment payment) {
        return pgClient.pay(payment);
    }
}
