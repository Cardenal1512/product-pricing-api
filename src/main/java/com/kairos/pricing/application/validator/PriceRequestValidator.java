package com.kairos.pricing.application.validator;

import com.kairos.pricing.domain.expetion.RequestParamException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PriceRequestValidator {

    public void validatePriceRequestParams(Long productId, Long brandId, String date) {
        List<String> missing = new ArrayList<>();

        if (Objects.isNull(productId)) {
            missing.add("productId");
        }

        if (Objects.isNull(brandId)) {
            missing.add("brandId");
        }

        if (Objects.isNull(date) || date.isBlank()) {
            missing.add("date");
        }

        if (!missing.isEmpty()) {
            String message = "Missing required " +
                    (Integer.valueOf(1).equals(missing.size()) ? "parameter: " : "parameters: ") +
                    String.join(", ", missing);
            throw new RequestParamException(message);
        }
    }

    public LocalDateTime parseDate(String dateString) {
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME); // ISO-8601 por defecto
        } catch (DateTimeParseException e) {
            throw new RequestParamException("Invalid date format. Expected format: yyyy-MM-dd'T'HH:mm:ss");
        }
    }
}
