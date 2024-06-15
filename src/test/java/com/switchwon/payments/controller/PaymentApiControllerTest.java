package com.switchwon.payments.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.switchwon.payments.service.MockCurrencyConverter;
import java.math.BigDecimal;
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
}