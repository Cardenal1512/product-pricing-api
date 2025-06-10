package com.kairos.pricing.infrastructure.in.rest.mapper;

import java.util.List;

public interface BaseMapper<T, K> {
    T toDto(K entity);
    K toEntity(T dto);
    List<T> toDtoList(List<K> entityList);
    List<K> toEntityList(List<T> dtoList);
}
