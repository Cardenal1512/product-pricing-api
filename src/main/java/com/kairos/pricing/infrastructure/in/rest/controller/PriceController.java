package com.kairos.pricing.infrastructure.in.rest.controller;

import com.kairos.pricing.application.usecase.GetApplicablePriceUseCase;
import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.infrastructure.in.rest.dto.PriceResponse;
import com.kairos.pricing.infrastructure.in.rest.mapper.PriceResponseMapper;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public PriceResponse getApplicablePrice(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam Long productId,
            @RequestParam Long brandId
    ) {
        Price price = getApplicablePriceUseCase.execute(date, productId, brandId);
        return priceResponseMapper.toDto(price);
    }
}
