package com.kairos.pricing.application.validator;

import com.kairos.pricing.domain.expetion.RequestParamException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PriceRequestValidatorTest {

    @InjectMocks
    private PriceRequestValidator validator;

    @Test
    void should_not_throw_when_all_parameters_are_valid() {
        assertDoesNotThrow(() -> validator.validatePriceRequestParams(35455L, 1L, "2020-06-14T10:00:00"));
    }

    @Test
    void should_throw_when_product_id_is_missing() {
        RequestParamException ex = assertThrows(
                RequestParamException.class,
                () -> validator.validatePriceRequestParams(null, 1L, "2020-06-14T10:00:00")
        );
        assertEquals("Missing required parameter: productId", ex.getMessage());
    }

    @Test
    void should_throw_when_brand_id_is_missing() {
        RequestParamException ex = assertThrows(
                RequestParamException.class,
                () -> validator.validatePriceRequestParams(35455L, null, "2020-06-14T10:00:00")
        );
        assertEquals("Missing required parameter: brandId", ex.getMessage());
    }

    @Test
    void should_throw_when_date_is_null() {
        RequestParamException ex = assertThrows(
                RequestParamException.class,
                () -> validator.validatePriceRequestParams(35455L, 1L, null)
        );
        assertEquals("Missing required parameter: date", ex.getMessage());
    }

    @Test
    void should_throw_when_date_is_blank() {
        RequestParamException ex = assertThrows(
                RequestParamException.class,
                () -> validator.validatePriceRequestParams(35455L, 1L, " ")
        );
        assertEquals("Missing required parameter: date", ex.getMessage());
    }

    @Test
    void should_parse_date_correctly_when_format_is_valid() throws BadRequestException {
        LocalDateTime result = validator.parseDate("2020-06-14T10:00:00");
        assertEquals(LocalDateTime.parse("2020-06-14T10:00:00"), result);
    }

    @Test
    void should_throw_when_date_format_is_invalid() {
        RequestParamException ex = assertThrows(
                RequestParamException.class,
                () -> validator.parseDate("14/06/2020 10:00")
        );
        assertEquals("Invalid date format. Expected format: yyyy-MM-dd'T'HH:mm:ss", ex.getMessage());
    }
}
