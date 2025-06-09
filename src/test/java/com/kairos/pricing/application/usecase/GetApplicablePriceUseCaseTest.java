package com.kairos.pricing.application.usecase;

import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.domain.port.out.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.AssertionsKt.assertNotNull;

public class GetApplicablePriceUseCaseTest {
    private GetApplicablePriceUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetApplicablePriceUseCase(new FakePriceRepository());
    }

    @Test
    void should_return_price_with_highest_priority_for_matching_date() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        long productId = 35455L;
        long brandId = 1L;

        // When
        Price price = useCase.execute(date, productId, brandId);

        // Then
        assertNotNull(price);
        assertEquals(BigDecimal.valueOf(25.45), price.getPrice());
        assertEquals(2, price.getPriceList());
    }

    // Fake repo hardcoded
    private static class FakePriceRepository implements PriceRepository {
        @Override
        public List<Price> findByProductIdAndBrandIdAndDate(long productId, long brandId, LocalDateTime date) {
            return List.of(
                    standardTariff(productId, brandId),
                    specialTariff(productId, brandId)
            );
        }

        private Price standardTariff(long productId, long brandId) {
            return Price.builder()
                    .productId(productId)
                    .brandId(brandId)
                    .priceList(1)
                    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                    .priority(0)
                    .price(BigDecimal.valueOf(35.50))
                    .currency("EUR")
                    .build();
        }

        private Price specialTariff(long productId, long brandId) {
            return Price.builder()
                    .productId(productId)
                    .brandId(brandId)
                    .priceList(2)
                    .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                    .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                    .priority(1)
                    .price(BigDecimal.valueOf(25.45))
                    .currency("EUR")
                    .build();
        }
    }
}
