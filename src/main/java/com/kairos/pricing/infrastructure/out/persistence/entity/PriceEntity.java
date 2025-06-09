package com.kairos.pricing.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Data
@NoArgsConstructor
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long brandId;
    private Long productId;
    private Integer priceList;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer priority;
    private BigDecimal price;
    private String currency;
}
