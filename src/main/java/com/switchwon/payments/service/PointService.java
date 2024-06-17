package com.switchwon.payments.service;

import com.switchwon.payments.domain.Money;
import com.switchwon.payments.domain.Payment;
import com.switchwon.payments.domain.PaymentFeeCalculator;
import com.switchwon.payments.domain.PaymentHistory;
import com.switchwon.payments.domain.PaymentHistoryType;
import com.switchwon.payments.domain.Point;
import com.switchwon.payments.exception.InsufficientPointAmountException;
import com.switchwon.payments.repository.PaymentHistoryRepository;
import com.switchwon.payments.repository.PaymentRepository;
import com.switchwon.payments.repository.PointRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointService {

    private final CardService cardService;
    private final PointRepository pointRepository;
    private final CurrencyConverter currencyConverter;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentRepository paymentRepository;

    public PointService(
            CardService cardService,
            PointRepository pointRepository,
            CurrencyConverter currencyConverter,
            PaymentHistoryRepository paymentHistoryRepository,
            PaymentRepository paymentRepository
    ) {
        this.cardService = cardService;
        this.pointRepository = pointRepository;
        this.currencyConverter = currencyConverter;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.paymentRepository = paymentRepository;
    }

    @Retryable(retryFor = {InsufficientPointAmountException.class}, backoff = @Backoff(delay = 3000))
    @Transactional
    public Payment pay(Long userId, Money purchaseAmount, Payment payment) {
        final var point = pointRepository.findPointByUserId(userId);
        final var convertedPurchaseAmount = convertForMatchingSameCurrency(purchaseAmount);

        final BigDecimal paymentFee = PaymentFeeCalculator.calculatePaymentFee(convertedPurchaseAmount.getAmount());
        final BigDecimal estimatedTotalAmount = convertedPurchaseAmount.getAmount().add(paymentFee);

        Payment cardPayment = null;
        if (!point.hasSufficientPointToPay(new Money(Point.PEGGED_CURRENCY, estimatedTotalAmount))) {
            cardPayment = cardService.pay(payment);
            final Payment savedCardPayment = paymentRepository.save(cardPayment);
            paymentHistoryRepository.save(createPaymentHistory(savedCardPayment));
        }
        point.subtract(purchaseAmount);
        pointRepository.save(point);
        payment.processed();
        return payment;
    }

    private PaymentHistory createPaymentHistory(Payment payment) {
        return new PaymentHistory(
                PaymentHistoryType.APPROVAL,
                payment,
                LocalDateTime.now(ZoneOffset.UTC),
                LocalDateTime.now(ZoneOffset.UTC));
    }

    private Money convertForMatchingSameCurrency(Money purchaseAmount) {
        return currencyConverter.convert(purchaseAmount, Point.PEGGED_CURRENCY);
    }
}
