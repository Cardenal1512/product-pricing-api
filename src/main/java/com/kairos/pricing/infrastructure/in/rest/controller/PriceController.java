package com.kairos.pricing.infrastructure.in.rest.controller;

import com.kairos.pricing.application.usecase.GetApplicablePriceUseCase;
import com.kairos.pricing.application.validator.PriceRequestValidator;
import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.infrastructure.in.rest.dto.PriceResponse;
import com.kairos.pricing.infrastructure.in.rest.mapper.PriceResponseMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final GetApplicablePriceUseCase getApplicablePriceUseCase;
    private final PriceResponseMapper priceResponseMapper;
    private final PriceRequestValidator priceRequestValidator;

    @GetMapping
    public PriceResponse getApplicablePrice(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long brandId
    ) throws BadRequestException {
        priceRequestValidator.validatePriceRequestParams(productId, brandId, date);
        LocalDateTime parsedDate = priceRequestValidator.parseDate(date);
        
        Price price = getApplicablePriceUseCase.execute(parsedDate, productId, brandId);
        return priceResponseMapper.toDto(price);
    }
}
