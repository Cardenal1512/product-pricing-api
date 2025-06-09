package com.kairos.pricing.infrastructure.out.persistence.repository;

import com.kairos.pricing.infrastructure.out.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long>, JpaSpecificationExecutor<PriceEntity> {
}
