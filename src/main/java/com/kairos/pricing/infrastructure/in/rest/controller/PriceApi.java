package com.kairos.pricing.infrastructure.in.rest.controller;

import com.kairos.pricing.infrastructure.in.rest.dto.PriceResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/prices")
public interface PriceApi {
    @Operation(summary = "Get price by productId, brandId and date")
    @GetMapping
    ResponseEntity<PriceResponse> getApplicablePrice(
            @Parameter(description = "Date in ISO format", required = true) @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @Parameter(description = "Product ID", required = true) @RequestParam Long productId,
            @Parameter(description = "Brand ID", required = true) @RequestParam Long brandId
    );
}
