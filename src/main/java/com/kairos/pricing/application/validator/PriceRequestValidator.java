package com.kairos.pricing.application.validator;

import com.kairos.pricing.domain.expetion.RequestParamException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Service
public class PriceRequestValidator {

    public void validatePriceRequestParams(Long productId, Long brandId, String date) throws BadRequestException {
        if (Objects.isNull(productId)) {
            throw new RequestParamException("Missing required parameter: productId");
        }
        if (Objects.isNull(brandId)) {
            throw new RequestParamException("Missing required parameter: brandId");
        }
        if (Objects.isNull(date) || date.isBlank()) {
            throw new RequestParamException("Missing required parameter: date");
        }
    }

    public LocalDateTime parseDate(String dateString) throws BadRequestException {
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME); // ISO-8601 por defecto
        } catch (DateTimeParseException e) {
            throw new RequestParamException("Invalid date format. Expected format: yyyy-MM-dd'T'HH:mm:ss");
        }
    }
}
