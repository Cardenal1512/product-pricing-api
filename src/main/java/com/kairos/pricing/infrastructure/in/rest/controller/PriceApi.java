package com.kairos.pricing.infrastructure.in.rest.controller;

import com.kairos.pricing.infrastructure.in.rest.dto.PriceResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/prices")
public interface PriceApi {
    @Operation(summary = "Get price by productId, brandId and date")
    @GetMapping
    ResponseEntity<PriceResponse> getApplicablePrice(
            @Parameter(description = "Date in ISO format", required = true) @RequestParam String date,
            @Parameter(description = "Product ID", required = true) @RequestParam Long productId,
            @Parameter(description = "Brand ID", required = true) @RequestParam Long brandId
    );
}
