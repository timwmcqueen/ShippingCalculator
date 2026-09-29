package com.timothymcqueen.shipping;

import com.timothymcqueen.shipping.api.QuoteRequest;
import com.timothymcqueen.shipping.api.QuoteResponse;
import com.timothymcqueen.shipping.domain.ServiceLevel;
import com.timothymcqueen.shipping.domain.ShippingQuoteRepository;
import com.timothymcqueen.shipping.service.ShippingQuoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ShippingQuoteServiceTest {
    @Autowired ShippingQuoteService service;
    @Autowired ShippingQuoteRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void createsAndPersistsStandardQuote() {
        QuoteResponse result = service.createQuote(
            new QuoteRequest(new BigDecimal("5.00"), ServiceLevel.STANDARD)
        );

        assertThat(result.baseCost()).isEqualByComparingTo("14.32");
        assertThat(result.tax()).isEqualByComparingTo("2.15");
        assertThat(result.total()).isEqualByComparingTo("16.47");
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void appliesExpressMultiplierBeforeTax() {
        QuoteResponse result = service.createQuote(
            new QuoteRequest(new BigDecimal("12.00"), ServiceLevel.EXPRESS)
        );

        assertThat(result.baseCost()).isEqualByComparingTo("44.63");
        assertThat(result.tax()).isEqualByComparingTo("6.69");
        assertThat(result.total()).isEqualByComparingTo("51.32");
    }
}
