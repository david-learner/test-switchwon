package com.switchwon.payments.service;

import com.switchwon.payments.repository.PointRepository;
import com.switchwon.payments.service.dto.BalanceRetrievalRequest;
import com.switchwon.payments.service.dto.BalanceRetrievalResponse;
import java.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private final PointRepository pointRepository;
    private final CurrencyConverter currencyConverter;

    public PaymentService(PointRepository pointRepository, CurrencyConverter currencyConverter) {
        this.pointRepository = pointRepository;
        this.currencyConverter = currencyConverter;
    }

    /**
     * 포인트 잔액을 요청된 화폐 단위로 변환하여 반환한다
     */
    public BalanceRetrievalResponse getBalance(BalanceRetrievalRequest request) {
        final Long userId = request.getUserId();
        final var returningCurrency = createCurrencyOf(request.getReturningCurrencyCode());
        final var userPoint = pointRepository.findPointByUserId(userId);

        final var userMoneyFromPoint = userPoint.toMoney();
        final var convertedUserMoney = currencyConverter.convert(userMoneyFromPoint, returningCurrency);

        return BalanceRetrievalResponse.of(userId.toString(), convertedUserMoney);
    }

    private Currency createCurrencyOf(String returningCurrencyCode) {
        Currency returningCurrency;
        try {
            returningCurrency = Currency.getInstance(returningCurrencyCode);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency code: " + returningCurrencyCode);
        }
        return returningCurrency;
    }
}
