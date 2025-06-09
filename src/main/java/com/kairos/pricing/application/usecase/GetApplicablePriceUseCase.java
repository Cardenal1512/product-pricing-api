package com.kairos.pricing.application.usecase;

import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.domain.port.out.PriceRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GetApplicablePriceUseCase {
    private final PriceRepository repository;

    public Price execute(LocalDateTime applicationDate, long productId, long brandId) {
        List<Price> candidates = repository.findByProductIdAndBrandIdAndDate(productId, brandId, applicationDate);
        return this.selectApplicablePrice(candidates)
                .orElseThrow(() -> new RuntimeException("No applicable price found"));
    }

    /**
     * Selects the applicable price from the list of candidates by comparing their priority.
     * In case of overlapping entries, the one with the highest priority is returned.
     */
    private Optional<Price> selectApplicablePrice(List<Price> prices) {
        return prices.stream()
                .reduce((p1, p2) -> p1.getPriority() >= p2.getPriority() ? p1 : p2);
    }
}
