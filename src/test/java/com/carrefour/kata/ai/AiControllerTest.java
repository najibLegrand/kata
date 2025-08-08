// src/test/java/com/carrefour/kata/ai/AiControllerTest.java
package com.carrefour.kata.ai;

import com.carrefour.kata.domain.DeliveryMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AiController.class, properties = "app.seed.enabled=false")
class AiControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    AiAdviceService ai;

    @Test
    void advice_endpoint_returns_json() throws Exception {
        when(ai.advice(DeliveryMethod.DELIVERY, LocalDate.parse("2025-08-08")))
                .thenReturn("Conseil_fake");

        mvc.perform(get("/ai/advice")
                        .param("method", "DELIVERY")
                        .param("day", "2025-08-08"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.advice").value("Conseil_fake"));
    }

    @Test
    void bad_enum_returns_400() throws Exception {
        mvc.perform(get("/ai/advice")
                        .param("method", "NOT_A_METHOD")
                        .param("day", "2025-08-08"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void missing_param_returns_400() throws Exception {
        mvc.perform(get("/ai/advice")
                        .param("method", "DELIVERY")) // 'day' manquant
                .andExpect(status().isBadRequest());
    }
}
