package com.enterprisecommerce.notification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "security.api-key=test-notification-key")
@AutoConfigureMockMvc
class NotificationSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectSendWithoutApiKey() throws Exception {
        mockMvc.perform(post("/api/notifications/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validNotificationRequest()))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowSendWithApiKey() throws Exception {
        mockMvc.perform(post("/api/notifications/send")
                .header("X-API-KEY", "test-notification-key")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validNotificationRequest()))
            .andExpect(status().isOk());
    }

    private String validNotificationRequest() {
        return """
            {
              "customerId": "C-SEC-1",
              "type": "ORDER_CONFIRMED",
              "message": "Order confirmed"
            }
            """;
    }
}
