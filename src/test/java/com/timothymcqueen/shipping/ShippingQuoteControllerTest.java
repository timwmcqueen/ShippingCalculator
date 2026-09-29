package com.timothymcqueen.shipping;

import com.timothymcqueen.shipping.domain.ShippingQuoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ShippingQuoteControllerTest {
    @Autowired MockMvc mockMvc;
    @Autowired ShippingQuoteRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void validatesAndCreatesQuote() throws Exception {
        mockMvc.perform(post("/api/quotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "weightPounds": 5,
                      "serviceLevel": "STANDARD"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.baseCost").value(14.32))
            .andExpect(jsonPath("$.total").value(16.47));
    }

    @Test
    void rejectsInvalidWeight() throws Exception {
        mockMvc.perform(post("/api/quotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "weightPounds": 0,
                      "serviceLevel": "STANDARD"
                    }
                    """))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Validation failed"));
    }
}
