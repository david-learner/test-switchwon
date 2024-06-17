package com.switchwon.payments.service;

import com.switchwon.payments.domain.Payment;

public interface PgClient {

    Payment pay(Payment payment);
}
