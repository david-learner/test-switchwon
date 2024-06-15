package com.switchwon.payments.service.dto;

import static com.switchwon.payments.domain.PaymentCurrency.KRW;

import com.switchwon.payments.domain.Money;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyDecimalPointHandler {

    /**
     * 화폐에 따른 소수 표기 정책을 따라 소수를 다룹니다.
     */
    public static BigDecimal handleDecimalPoint(Money money) {
        if (money.getCurrency().equals(KRW)) {
            return trimAsKrwTrimPolicy(money.getAmount());
        }
        return trimAsUsdTrimPolicy(money.getAmount());
    }

    /**
     * KRW에 대한 소수 표기 정책을 따라 소수를 버립니다.
     * @return 소수가 없는 숫자
     */
    private static BigDecimal trimAsKrwTrimPolicy(BigDecimal amount) {
        return amount.setScale(0, RoundingMode.DOWN);
    }

    /**
     * USD에 대한 소수 표기 정책을 따라 소수를 둘째자리 미만은 버립니다.
     * @return 소수 둘째자리까지 있는 숫자
     */
    private static BigDecimal trimAsUsdTrimPolicy(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.DOWN);
    }
}
