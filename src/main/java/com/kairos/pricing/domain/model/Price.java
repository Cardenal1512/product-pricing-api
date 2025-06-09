package com.kairos.pricing.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Price {
    private final long productId;
    private final long brandId;
    private final int priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final int priority;
    private final BigDecimal price;
    private final String currency;
}
