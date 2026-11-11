package com.enterprisecommerce.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "security.api-key=test-order-key")
@AutoConfigureMockMvc
class OrderSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectRequestsWithoutApiKey() throws Exception {
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validOrderRequest()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowRequestsWithApiKey() throws Exception {
        mockMvc.perform(post("/api/orders")
                .header("X-API-KEY", "test-order-key")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validOrderRequest()))
            .andExpect(status().isOk());
    }

    private String validOrderRequest() {
        return """
            {
              "customerId": "C-SEC-1",
              "items": [
                {
                  "sku": "SKU-100",
                  "quantity": 1,
                  "unitPrice": 99.90
                }
              ]
            }
            """;
    }
}
