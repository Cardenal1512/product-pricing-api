package com.kairos.pricing.infrastructure.in.rest.controller;

import com.kairos.pricing.application.usecase.GetApplicablePriceUseCase;
import com.kairos.pricing.application.validator.PriceRequestValidator;
import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.infrastructure.in.rest.dto.PriceResponse;
import com.kairos.pricing.infrastructure.in.rest.mapper.PriceResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PriceController implements PriceApi {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;

    private final PriceResponseMapper priceResponseMapper;

    private final PriceRequestValidator priceRequestValidator;

    @Override
    public ResponseEntity<PriceResponse> getApplicablePrice(String date, Long productId, Long brandId) {
        priceRequestValidator.validatePriceRequestParams(productId, brandId, date);
        LocalDateTime parsedDate = priceRequestValidator.parseDate(date);

        Price price = getApplicablePriceUseCase.execute(parsedDate, productId, brandId);
        return ResponseEntity.ok(priceResponseMapper.toDto(price));
    }
}
