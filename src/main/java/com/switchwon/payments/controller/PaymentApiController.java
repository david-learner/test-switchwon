package com.switchwon.payments.controller;

import com.switchwon.payments.service.PaymentService;
import com.switchwon.payments.service.dto.BalanceRetrievalRequest;
import com.switchwon.payments.service.dto.BalanceRetrievalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentApiController.class);

    private final PaymentService paymentService;

    public PaymentApiController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * 잔액을 조회한다
     */
    @GetMapping("/api/payment/balance")
    public BalanceRetrievalResponse retrieveBalance(BalanceRetrievalRequest request) {
        return paymentService.getBalance(request);
    }
}
