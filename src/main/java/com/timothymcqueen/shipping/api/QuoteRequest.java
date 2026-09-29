package com.timothymcqueen.shipping.api;

import com.timothymcqueen.shipping.domain.ServiceLevel;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record QuoteRequest(
    @NotNull
    @DecimalMin(value = "0.01", message = "weightPounds must be greater than zero")
    @DecimalMax(value = "150.00", message = "weightPounds must be 150 or less")
    BigDecimal weightPounds,

    @NotNull
    ServiceLevel serviceLevel
) {}
