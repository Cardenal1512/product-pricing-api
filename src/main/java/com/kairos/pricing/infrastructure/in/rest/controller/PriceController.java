package com.kairos.pricing.infrastructure.in.rest.controller;

import com.kairos.pricing.application.usecase.GetApplicablePriceUseCase;
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

    @Override
    public ResponseEntity<PriceResponse> getApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
        Price price = getApplicablePriceUseCase.execute(date, productId, brandId);
        return ResponseEntity.ok(priceResponseMapper.toDto(price));
    }
}
