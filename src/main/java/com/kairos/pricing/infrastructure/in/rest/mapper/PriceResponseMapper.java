package com.kairos.pricing.infrastructure.in.rest.mapper;

import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.infrastructure.in.rest.dto.PriceResponse;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface PriceResponseMapper extends BaseMapper<PriceResponse, Price>{
}
