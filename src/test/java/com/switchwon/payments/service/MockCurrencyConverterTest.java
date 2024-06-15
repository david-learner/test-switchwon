package com.switchwon.payments.service;

import static com.switchwon.payments.domain.PaymentCurrency.KRW;
import static com.switchwon.payments.domain.PaymentCurrency.USD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.switchwon.payments.domain.Money;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MockCurrencyConverterTest {

    @Test
    @DisplayName("1000원(KRW)을_USD로_변환하면_0.73달러(USD)가_나와야_한다")
    void when_1000KRW_is_converted_to_USD_then_converted_result_should_be_0_point_73_USD() {
        final var converter = new MockCurrencyConverter();
        final var krwMoney = new Money(KRW, new BigDecimal("1000"));

        final var convertedUsdMoney = converter.convert(krwMoney, USD);

        final var usdMoney = new Money(USD, new BigDecimal("0.73"));
        assertThat(convertedUsdMoney.equals(usdMoney)).isTrue();
    }

    @Test
    @DisplayName("1달러(USD)를_KRW로_변환하면_1374.49원(KRW)가_나와야_한다")
    void when_1USD_is_converted_to_KRW_then_converted_result_should_be_1374_point_49_KRW() {
        final var converter = new MockCurrencyConverter();
        final var usdMoney = new Money(USD, new BigDecimal("1"));

        final var convertedKrwMoney = converter.convert(usdMoney, KRW);

        final var krwMoney = new Money(KRW, new BigDecimal("1374.49"));
        assertThat(convertedKrwMoney.equals(krwMoney)).isTrue();
    }
}
