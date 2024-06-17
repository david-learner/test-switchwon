package com.switchwon.payments.repository;

import com.switchwon.payments.domain.Payment;
import com.switchwon.payments.domain.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
