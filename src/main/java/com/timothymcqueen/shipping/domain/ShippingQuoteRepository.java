package com.timothymcqueen.shipping.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShippingQuoteRepository extends JpaRepository<ShippingQuote, Long> {
    List<ShippingQuote> findTop20ByOrderByCreatedAtDesc();
}
