package com.switchwon.payments.service;

import com.switchwon.payments.domain.Payment;
import com.switchwon.payments.domain.PaymentFeeCalculator;
import com.switchwon.payments.domain.PaymentHistory;
import com.switchwon.payments.domain.PaymentHistoryType;
import com.switchwon.payments.domain.PaymentStatus;
import com.switchwon.payments.repository.MerchantRepository;
import com.switchwon.payments.repository.PaymentHistoryRepository;
import com.switchwon.payments.repository.PaymentRepository;
import com.switchwon.payments.repository.PointRepository;
import com.switchwon.payments.repository.UserRepository;
import com.switchwon.payments.service.dto.BalanceRetrievalRequest;
import com.switchwon.payments.service.dto.BalanceRetrievalResponse;
import com.switchwon.payments.service.dto.PaymentApprovalRequest;
import com.switchwon.payments.service.dto.PaymentApprovalResponse;
import com.switchwon.payments.service.dto.PaymentEstimationRequest;
import com.switchwon.payments.service.dto.PaymentEstimationResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private final CurrencyConverter currencyConverter;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final MerchantRepository merchantRepository;
    private final PointService pointService;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(
            CurrencyConverter currencyConverter,
            PointRepository pointRepository,
            UserRepository userRepository,
            MerchantRepository merchantRepository,
            CardService cardService,
            PointService pointService,
            PaymentHistoryRepository paymentHistoryRepository,
            PaymentRepository paymentRepository
    ) {
        this.currencyConverter = currencyConverter;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
        this.merchantRepository = merchantRepository;
        this.pointService = pointService;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * 포인트 잔액을 요청된 화폐 단위로 변환하여 반환한다
     */
    public BalanceRetrievalResponse getBalance(BalanceRetrievalRequest request) {
        final Long userId = request.getUserId();
        final var returningCurrency = createCurrency(request.getReturningCurrencyCode());
        final var userPoint = pointRepository.findPointByUserId(userId);

        final var userMoneyFromPoint = userPoint.toMoney();
        final var convertedUserMoney = currencyConverter.convert(userMoneyFromPoint, returningCurrency);

        return BalanceRetrievalResponse.of(userId.toString(), convertedUserMoney);
    }

    /**
     * 결제 예상 결과를 조회한다
     */
    public PaymentEstimationResponse getEstimationPaymentResult(PaymentEstimationRequest request) {
        validateUser(request.getUserId());
        validateMerchant(request.getMerchantId());
        final BigDecimal paymentFee = PaymentFeeCalculator.calculatePaymentFee(request.getAmount());
        final BigDecimal estimatedTotalAmount = request.getAmount().add(paymentFee);

        return createPaymentEstimationResponse(paymentFee, estimatedTotalAmount, request.getCurrency());
    }

    /**
     * 결제를 승인한다
     */
    @Transactional
    public PaymentApprovalResponse approvePayment(PaymentApprovalRequest request) {
        LOGGER.info("Payment approval request: {}", request);

        validateMerchant(request.getMerchantId());
        final var savedPayment = paymentRepository.save(createPayment(request));
        createPaymentHistory(savedPayment, PaymentHistoryType.APPROVAL);

        final Payment pointPayment = pointService.pay(request.getUserId(), request.toMoney(), savedPayment);

        return new PaymentApprovalResponse(pointPayment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PaymentHistory createPaymentHistory(Payment payment, PaymentHistoryType type) {
        final var paymentHistory = new PaymentHistory(
                type,
                payment,
                LocalDateTime.now(ZoneOffset.UTC),
                LocalDateTime.now(ZoneOffset.UTC)
        );
        return paymentHistoryRepository.save(paymentHistory);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Payment createPayment(PaymentApprovalRequest request) {
        final var user = userRepository.findOneById(request.getUserId());
        final var payment = new Payment(
                request.getPaymentMethod(),
                request.getPaymentDetails().toPaymentDetails(),
                PaymentStatus.PENDING,
                user,
                request.getMerchantId(),
                request.toMoney(),
                LocalDateTime.now(ZoneOffset.UTC)
        );
        return payment;
    }

    private void validateUser(Long userId) {
        userRepository.findOneById(userId);
    }

    private void validateMerchant(String merchantId) {
        merchantRepository.findByUserMerchantId(merchantId)
                .orElseThrow(() -> new IllegalArgumentException("Merchant not found with id: " + merchantId));
    }

    private PaymentEstimationResponse createPaymentEstimationResponse(
            BigDecimal paymentFee,
            BigDecimal estimatedTotalAmount,
            String currencyCode
    ) {
        return new PaymentEstimationResponse(estimatedTotalAmount, paymentFee, currencyCode);
    }

    private Currency createCurrency(String returningCurrencyCode) {
        Currency returningCurrency;
        try {
            returningCurrency = Currency.getInstance(returningCurrencyCode);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency code: " + returningCurrencyCode);
        }
        return returningCurrency;
    }
}
