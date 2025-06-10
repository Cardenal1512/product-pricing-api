package com.kairos.pricing.domain.port.out;

import com.kairos.pricing.domain.model.Price;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository {
    List<Price> findByProductIdAndBrandIdAndDate(long productId, long brandId, LocalDateTime date);
}
