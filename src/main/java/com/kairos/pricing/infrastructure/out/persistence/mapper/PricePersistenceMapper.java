package com.kairos.pricing.infrastructure.out.persistence.mapper;

import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.infrastructure.in.rest.mapper.BaseMapper;
import com.kairos.pricing.infrastructure.out.persistence.entity.PriceEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PricePersistenceMapper extends BaseMapper<Price, PriceEntity> {
}
