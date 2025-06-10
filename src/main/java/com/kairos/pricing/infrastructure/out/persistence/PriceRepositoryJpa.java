package com.kairos.pricing.infrastructure.out.persistence;

import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.domain.port.out.PriceRepository;
import com.kairos.pricing.infrastructure.out.persistence.mapper.PricePersistenceMapper;
import com.kairos.pricing.infrastructure.out.persistence.specification.PriceSpecifications;
import com.kairos.pricing.infrastructure.out.persistence.repository.SpringDataPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceRepositoryJpa implements PriceRepository {
    private final SpringDataPriceRepository springDataPriceRepository;
    private final PricePersistenceMapper pricePersistenceMapper;

    @Override
    public List<Price> findByProductIdAndBrandIdAndDate(long productId, long brandId, LocalDateTime date) {
        return this.springDataPriceRepository.findAll(PriceSpecifications.isApplicable(date, productId, brandId))
                .stream()
                .map(pricePersistenceMapper::toDto)
                .toList();
    }
}
