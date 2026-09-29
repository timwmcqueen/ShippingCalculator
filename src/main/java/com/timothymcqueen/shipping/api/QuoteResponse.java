package com.timothymcqueen.shipping.api;

import com.timothymcqueen.shipping.domain.ServiceLevel;
import com.timothymcqueen.shipping.domain.ShippingQuote;
import java.math.BigDecimal;
import java.time.Instant;

public record QuoteResponse(
    Long id,
    BigDecimal weightPounds,
    ServiceLevel serviceLevel,
    BigDecimal baseCost,
    BigDecimal tax,
    BigDecimal total,
    Instant createdAt
) {
    public static QuoteResponse from(ShippingQuote quote) {
        return new QuoteResponse(
            quote.getId(),
            quote.getWeightPounds(),
            quote.getServiceLevel(),
            quote.getBaseCost(),
            quote.getTax(),
            quote.getTotal(),
            quote.getCreatedAt()
        );
    }
}
