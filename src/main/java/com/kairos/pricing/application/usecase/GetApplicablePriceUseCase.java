package com.kairos.pricing.application.usecase;

import com.kairos.pricing.domain.expetion.PriceNotFoundException;
import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.domain.port.out.PriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetApplicablePriceUseCase {
    private final PriceRepository repository;

    public Price execute(LocalDateTime applicationDate, long productId, long brandId) {
        log.info("Searching applicable price for date={}, productId={}, brandId={}",
                applicationDate, productId, brandId);
        List<Price> candidates = repository.findByProductIdAndBrandIdAndDate(productId, brandId, applicationDate);
        return this.selectApplicablePrice(candidates)
                .map(selected -> {
                    log.info("Selected price: priceList={}, priority={}, price={}",
                            selected.getPriceList(), selected.getPriority(), selected.getPrice());
                    return selected;
                })
                .orElseThrow(() -> {
                    log.error("No applicable price could be selected from candidates.");
                    return new PriceNotFoundException("No applicable price found");
                });
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
