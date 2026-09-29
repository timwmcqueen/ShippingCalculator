package com.timothymcqueen.shipping.api;

import com.timothymcqueen.shipping.service.ShippingQuoteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class ShippingQuoteController {
    private final ShippingQuoteService service;

    public ShippingQuoteController(ShippingQuoteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<QuoteResponse> create(@Valid @RequestBody QuoteRequest request) {
        QuoteResponse response = service.createQuote(request);
        return ResponseEntity
            .created(URI.create("/api/quotes/" + response.id()))
            .body(response);
    }

    @GetMapping
    public List<QuoteResponse> recent() {
        return service.recentQuotes();
    }
}
