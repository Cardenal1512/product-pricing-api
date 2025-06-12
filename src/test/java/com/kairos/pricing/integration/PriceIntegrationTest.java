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

    @Test
    void should_return_400_when_product_id_is_missing() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T10:00:00")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Missing required parameter: productId"));
    }

    @Test
    void should_return_400_when_brand_id_is_missing() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Missing required parameter: brandId"));
    }

    @Test
    void should_return_400_when_date_is_missing() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Missing required parameter: date"));
    }

    @Test
    void should_return_404_when_no_price_found() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2030-01-01T00:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No applicable price found"));
    }

    @Test
    void should_return_400_when_product_id_is_not_a_number() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "e")  // tipo incorrecto
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid value 'e' for parameter 'productId'. Expected type: Long"));
    }

    @Test
    void should_return_400_when_brand_id_is_not_a_number() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "e"))  // tipo incorrecto
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid value 'e' for parameter 'brandId'. Expected type: Long"));
    }

    @Test
    void should_return_400_when_date_is_malformed() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "12//06//2025")  // mal formato
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid date format: '12//06//2025'. Expected ISO format: yyyy-MM-dd'T'HH:mm:ss"));
    }

}

