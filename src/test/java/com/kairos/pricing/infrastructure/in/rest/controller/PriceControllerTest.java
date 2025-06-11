package com.kairos.pricing.infrastructure.in.rest.controller;

import com.kairos.pricing.application.usecase.GetApplicablePriceUseCase;
import com.kairos.pricing.application.validator.PriceRequestValidator;
import com.kairos.pricing.domain.model.Price;
import com.kairos.pricing.infrastructure.in.rest.dto.PriceResponse;
import com.kairos.pricing.infrastructure.in.rest.mapper.PriceResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceControllerTest {

    @InjectMocks
    private PriceController controller;

    @Mock
    private GetApplicablePriceUseCase useCase;

    @Mock
    private PriceRequestValidator priceRequestValidator;

    @Spy
    private PriceResponseMapper mapper = Mappers.getMapper(PriceResponseMapper.class);

    @Test
    void should_return_expected_price_response() throws Exception {

        Price price = mockPrice();
        when(useCase.execute(any(), eq(35425L), eq(1L))).thenReturn(price);
        when(priceRequestValidator.parseDate(Mockito.anyString())).thenReturn(LocalDateTime.parse("2020-06-14T16:00:00"));
        PriceResponse response = controller.getApplicablePrice(
                "2020-06-14T16:00:00",
                35425L,
                1L);
        assertEquals(2, response.getPriceList());

        assertEquals(25.50, response.getPrice().doubleValue());
    }

    private Price mockPrice() {
        return Price.builder()
                .brandId(1L)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(2)
                .productId(35455L)
                .priority(0)
                .price(new BigDecimal("25.50"))
                .currency("EUR")
                .build();
    }

    private PriceResponse mockPriceResponse() {
        return PriceResponse.builder()
                .brandId(1L)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(2)
                .productId(35455L)
                .priority(0)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();
    }

}
