package com.switchwon.payments.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    @DisplayName("첫번째 금액과 두번째 금액을 비교하여 첫번째 금액이 두번째 금액보다 크거나 같다면 참을 반환한다")
    void isEqualOrBiggerThan_WhenFirstAmountIsBiggerThanSecondAmount_ThenReturnTrue() {
        Money twoThousandWon = new Money(PaymentCurrency.KRW, BigDecimal.valueOf(2000));
        Money oneThousandWon = new Money(PaymentCurrency.KRW, BigDecimal.valueOf(1000));

        boolean result = twoThousandWon.isEqualOrBiggerThan(oneThousandWon);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("첫번째 금액과 두번째 금액을 비교하여 첫번째 금액이 두번째 금액보다 작다면 거짓을 반환한다")
    void isEqualOrBiggerThan_WhenFirstAmountIsSmallerThanSecondAmount_ThenReturnFalse() {
        Money oneThousandWon = new Money(PaymentCurrency.KRW, BigDecimal.valueOf(1000));
        Money twoThousandWon = new Money(PaymentCurrency.KRW, BigDecimal.valueOf(2000));

        boolean result = oneThousandWon.isEqualOrBiggerThan(twoThousandWon);

        assertThat(result).isFalse();
    }
}
