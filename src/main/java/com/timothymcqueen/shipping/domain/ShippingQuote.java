package com.timothymcqueen.shipping.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "shipping_quotes")
public class ShippingQuote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight_pounds", nullable = false, precision = 8, scale = 2)
    private BigDecimal weightPounds;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_level", nullable = false, length = 20)
    private ServiceLevel serviceLevel;

    @Column(name = "base_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseCost;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected ShippingQuote() {}

    public ShippingQuote(BigDecimal weightPounds, ServiceLevel serviceLevel, BigDecimal baseCost,
                         BigDecimal tax, BigDecimal total, Instant createdAt) {
        this.weightPounds = weightPounds;
        this.serviceLevel = serviceLevel;
        this.baseCost = baseCost;
        this.tax = tax;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public BigDecimal getWeightPounds() { return weightPounds; }
    public ServiceLevel getServiceLevel() { return serviceLevel; }
    public BigDecimal getBaseCost() { return baseCost; }
    public BigDecimal getTax() { return tax; }
    public BigDecimal getTotal() { return total; }
    public Instant getCreatedAt() { return createdAt; }
}
