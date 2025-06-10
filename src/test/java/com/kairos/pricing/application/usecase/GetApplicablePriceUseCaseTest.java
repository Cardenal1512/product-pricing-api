package com.kairos.pricing.application.usecase;

import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.domain.port.out.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetApplicablePriceUseCaseTest {

    private static final long PROD = 35455L;
    private static final long BRAND = 1L;

    @InjectMocks
    private GetApplicablePriceUseCase useCase;

    @Mock
    private PriceRepository repository;

    @Test
    void should_return_single_price_when_only_one_candidate() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        Price only = createPrice(1, "2020-06-14T09:00:00", "2020-06-14T11:00:00", 0, "35.50");

        when(repository.findByProductIdAndBrandIdAndDate(PROD, BRAND, date))
                .thenReturn(List.of(only));

        Price result = useCase.execute(date, PROD, BRAND);

        assertEquals(only, result);
    }

    @Test
    void should_return_price_with_highest_priority_for_matching_date() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price low  = createPrice(1, "2020-06-14T11:00:00", "2020-06-14T21:00:00", 0, "35.50");
        Price high = createPrice(2, "2020-06-14T14:00:00", "2020-06-14T18:30:00", 1, "25.45");

        when(repository.findByProductIdAndBrandIdAndDate(PROD, BRAND, date))
                .thenReturn(List.of(low, high));

        Price result = useCase.execute(date, PROD, BRAND);

        assertEquals(high, result);
        assertEquals(2, result.getPriceList());
    }

    @Test
    void should_throw_when_no_applicable_price_found() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 18, 10, 0);

        when(repository.findByProductIdAndBrandIdAndDate(PROD, BRAND, date))
                .thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> useCase.execute(date, PROD, BRAND));
    }




    /* ------------ Parametric test ------------- */

    @ParameterizedTest
    @MethodSource("cases")
    void should_return_expected_price(LocalDateTime date,
                                      List<Price> repoData,
                                      int expectedList,
                                      BigDecimal expectedPrice) {

        when(repository.findByProductIdAndBrandIdAndDate(35455L, 1L, date))
                .thenReturn(repoData);

        Price result = useCase.execute(date, 35455L, 1L);

        assertEquals(expectedList, result.getPriceList());
    }

    private static Stream<Arguments> cases() {
        //  (rows Excel)
        Price t1 = createPrice(1, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 0, "35.50");
        Price t2 = createPrice(2, "2020-06-14T15:00:00", "2020-06-14T18:30:00", 1, "25.45");
        Price t3 = createPrice(3, "2020-06-15T00:00:00", "2020-06-15T11:00:00", 1, "30.50");
        Price t4 = createPrice(4, "2020-06-15T16:00:00", "2020-12-31T23:59:59", 1, "38.95");

        return Stream.of(
                Arguments.of(LocalDateTime.parse("2020-06-14T10:00:00"), List.of(t1), 1, new BigDecimal("35.50")),
                Arguments.of(LocalDateTime.parse("2020-06-14T17:00:00"), List.of(t1, t2), 2, new BigDecimal("25.45")),
                Arguments.of(LocalDateTime.parse("2020-06-14T18:30:00"), List.of(t1, t2), 2, new BigDecimal("25.45")),
                Arguments.of(LocalDateTime.parse("2020-06-15T10:00:00"), List.of(t1, t3), 3, new BigDecimal("30.50")),
                Arguments.of(LocalDateTime.parse("2020-06-16T21:00:00"), List.of(t1, t4), 4, new BigDecimal("38.95"))
        );
    }

    private static Price createPrice(int list, String from, String to, int priority, String amount) {
        return Price.builder()
                .productId(PROD)
                .brandId(BRAND)
                .priceList(list)
                .priority(priority)
                .startDate(LocalDateTime.parse(from))
                .endDate(LocalDateTime.parse(to))
                .price(new BigDecimal(amount))
                .currency("EUR")
                .build();
    }


}
