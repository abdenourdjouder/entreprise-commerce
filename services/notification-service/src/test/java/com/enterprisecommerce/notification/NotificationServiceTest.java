package com.enterprisecommerce.notification;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NotificationServiceTest {

    @Test
    void sendNotificationShouldPersistEvent() {
        NotificationService service = new NotificationService();

        NotificationEvent event = service.send("C-123", NotificationType.ORDER_CREATED, "Order created");

        assertThat(event.type()).isEqualTo(NotificationType.ORDER_CREATED);
        assertThat(event.customerId()).isEqualTo("C-123");
    }
}
