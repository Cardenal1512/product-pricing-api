package com.kairos.pricing.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PriceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private void assertPrice(String date, int expectedPriceList, double expectedPrice) throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", date)
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.price").value(expectedPrice));
    }

    @Test
    void test1_price_at_2020_06_14_10_00() throws Exception {
        assertPrice("2020-06-14T10:00:00", 1, 35.50);
    }

    @Test
    void test2_price_at_2020_06_14_16_00() throws Exception {
        assertPrice("2020-06-14T16:00:00", 2, 25.45);
    }

    @Test
    void test3_price_at_2020_06_14_21_00() throws Exception {
        assertPrice("2020-06-14T21:00:00", 1, 35.50);
    }

    @Test
    void test4_price_at_2020_06_15_10_00() throws Exception {
        assertPrice("2020-06-15T10:00:00", 3, 30.50);
    }

    @Test
    void test5_price_at_2020_06_16_21_00() throws Exception {
        assertPrice("2020-06-16T21:00:00", 4, 38.95);
    }
}

