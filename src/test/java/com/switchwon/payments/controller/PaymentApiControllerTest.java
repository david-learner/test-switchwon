package com.switchwon.payments.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.switchwon.payments.service.MockCurrencyConverter;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(MockCurrencyConverter.class)
class PaymentApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 잔액조회시_returningCurrencyCode를_전달하지_않으면_USD를_기준으로_잔액결과과_반환된다() throws Exception {
        mockMvc.perform(get("/api/payment/balance?userId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value("1"))
                .andExpect(jsonPath("balance").value(BigDecimal.valueOf(0.73)))
                .andExpect(jsonPath("currency").value("USD"));
    }

    @Test
    void 잔액조회시_returningCurrencyCode를_USD로_전달하면_USD를_기준으로_잔액결과과_반환된다() throws Exception {
        mockMvc.perform(get("/api/payment/balance?userId=1&returningCurrencyCode=USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value("1"))
                .andExpect(jsonPath("balance").value(BigDecimal.valueOf(0.73)))
                .andExpect(jsonPath("currency").value("USD"));
    }

    @Test
    void 잔액조회시_returningCurrencyCode를_KRW로_전달하면_KRW를_기준으로_잔액결과과_반환된다() throws Exception {
        mockMvc.perform(get("/api/payment/balance?userId=1&returningCurrencyCode=KRW"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value("1"))
                .andExpect(jsonPath("balance").value(BigDecimal.valueOf(1000)))
                .andExpect(jsonPath("currency").value("KRW"));
    }

    @Test
    @DisplayName("지불금액_150달러에_대한_결제예상결과_조회시_예상결제금액은_154.50달러이며_결제수수료는_4.50달러이다")
    void when_get_estimation_payment_result_then_return_estimated_payment_amount_is_154_point_50_and_payment_fee_is_4_point_50() throws Exception {
        String estimationPaymentRequestBody = """
                {
                    "amount": 150,
                    "currency": "USD",
                    "merchantId": "Starbucks-001",
                    "userId": 1
                }
                """;
        
        mockMvc.perform(post("/api/payment/estimate")
                        .contentType("application/json")
                        .content(estimationPaymentRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("estimatedTotal").value(BigDecimal.valueOf(154.50)))
                .andExpect(jsonPath("fees").value(BigDecimal.valueOf(4.50)))
                .andExpect(jsonPath("currency").value("USD"));
    }
}