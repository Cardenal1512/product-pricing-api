package com.kairos.pricing.infrastructure.out.persistence.specification;

import com.kairos.pricing.infrastructure.out.persistence.entity.PriceEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PriceSpecifications {
    public static Specification<PriceEntity> isApplicable(LocalDateTime date, Long productId, Long brandId) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("productId"), productId),
                cb.equal(root.get("brandId"), brandId),
                cb.lessThanOrEqualTo(root.get("startDate"), date),
                cb.greaterThanOrEqualTo(root.get("endDate"), date)
        );
    }
}
