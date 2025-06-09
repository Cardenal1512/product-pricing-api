package com.kairos.pricing.infrastructure.out.persistence;

import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.domain.port.out.PriceRepository;
import com.kairos.pricing.infrastructure.out.persistence.specification.PriceSpecifications;
import com.kairos.pricing.infrastructure.out.persistence.repository.SpringDataPriceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class PriceRepositoryJpa implements PriceRepository {
    private final SpringDataPriceRepository springDataPriceRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Price> findByProductIdAndBrandIdAndDate(long productId, long brandId, LocalDateTime date) {
        return this.springDataPriceRepository.findAll(PriceSpecifications.isApplicable(date, productId, brandId))
                .stream()
                .map(priceEntity -> modelMapper.map(priceEntity, Price.class))
                .toList();
    }
}
