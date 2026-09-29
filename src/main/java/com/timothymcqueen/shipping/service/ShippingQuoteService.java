package com.timothymcqueen.shipping.service;

import com.timothymcqueen.shipping.api.QuoteRequest;
import com.timothymcqueen.shipping.api.QuoteResponse;
import com.timothymcqueen.shipping.domain.ServiceLevel;
import com.timothymcqueen.shipping.domain.ShippingQuote;
import com.timothymcqueen.shipping.domain.ShippingQuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class ShippingQuoteService {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.15");
    private static final BigDecimal EXPRESS_MULTIPLIER = new BigDecimal("1.75");

    private final ShippingQuoteRepository repository;
    private final Clock clock;

    @Autowired
    public ShippingQuoteService(ShippingQuoteRepository repository) {
        this(repository, Clock.systemUTC());
    }

    ShippingQuoteService(ShippingQuoteRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public QuoteResponse createQuote(QuoteRequest request) {
        BigDecimal baseCost = calculateBaseCost(request.weightPounds());
        if (request.serviceLevel() == ServiceLevel.EXPRESS) {
            baseCost = money(baseCost.multiply(EXPRESS_MULTIPLIER));
        }

        BigDecimal tax = money(baseCost.multiply(TAX_RATE));
        BigDecimal total = money(baseCost.add(tax));

        ShippingQuote saved = repository.save(new ShippingQuote(
            request.weightPounds().setScale(2, RoundingMode.HALF_UP),
            request.serviceLevel(),
            baseCost,
            tax,
            total,
            Instant.now(clock)
        ));

        return QuoteResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<QuoteResponse> recentQuotes() {
        return repository.findTop20ByOrderByCreatedAtDesc()
            .stream()
            .map(QuoteResponse::from)
            .toList();
    }

    BigDecimal calculateBaseCost(BigDecimal weightPounds) {
        if (weightPounds.compareTo(new BigDecimal("1.00")) < 0) {
            return new BigDecimal("7.88");
        }
        if (weightPounds.compareTo(new BigDecimal("6.00")) < 0) {
            return new BigDecimal("14.32");
        }
        if (weightPounds.compareTo(new BigDecimal("10.00")) < 0) {
            return new BigDecimal("21.11");
        }
        return new BigDecimal("25.50");
    }

    private BigDecimal money(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
