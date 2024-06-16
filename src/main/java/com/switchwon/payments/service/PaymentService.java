package com.switchwon.payments.service;

import com.switchwon.payments.domain.PaymentFeeCalculator;
import com.switchwon.payments.repository.MerchantRepository;
import com.switchwon.payments.repository.PointRepository;
import com.switchwon.payments.repository.UserRepository;
import com.switchwon.payments.service.dto.BalanceRetrievalRequest;
import com.switchwon.payments.service.dto.BalanceRetrievalResponse;
import com.switchwon.payments.service.dto.PaymentEstimationRequest;
import com.switchwon.payments.service.dto.PaymentEstimationResponse;
import java.math.BigDecimal;
import java.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private final CurrencyConverter currencyConverter;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;

    public PaymentService(
            CurrencyConverter currencyConverter,
            PointRepository pointRepository,
            UserRepository userRepository,
            MerchantRepository merchantRepository
    ) {
        this.currencyConverter = currencyConverter;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
        this.merchantRepository = merchantRepository;
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

    /**
     * 결제 예상 결과를 조회한다
     */
    public PaymentEstimationResponse getEstimationPaymentResult(PaymentEstimationRequest request) {
        userRepository.findOneById(request.getUserId());
        merchantRepository.findByUserMerchantId(request.getMerchantId())
                .orElseThrow(() -> new IllegalArgumentException("Merchant not found with id: " + request.getMerchantId()));
        // todo: 결제 금액 정책에 따른 결제 금액 검증(예: 최소 결제 금액, 최대 결제 금액 등)
        final BigDecimal paymentFee = PaymentFeeCalculator.calculatePaymentFee(request.getAmount());
        final BigDecimal estimatedTotalAmount = request.getAmount().add(paymentFee);

        return createPaymentEstimationResponse(paymentFee, estimatedTotalAmount, request.getCurrency());
    }

    private PaymentEstimationResponse createPaymentEstimationResponse(
            BigDecimal paymentFee,
            BigDecimal estimatedTotalAmount,
            String currencyCode
    ) {
        return new PaymentEstimationResponse(estimatedTotalAmount, paymentFee, currencyCode);
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
