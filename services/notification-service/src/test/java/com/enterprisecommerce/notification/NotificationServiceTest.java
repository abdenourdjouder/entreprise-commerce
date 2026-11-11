package com.enterprisecommerce.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class NotificationServiceTest {

    @Test
    void sendNotificationShouldPersistEvent() {
        NotificationService service = new NotificationService();

        NotificationEvent event = service.send("C-123", NotificationType.ORDER_CREATED, "Order created");

        assertThat(event.type()).isEqualTo(NotificationType.ORDER_CREATED);
        assertThat(event.customerId()).isEqualTo("C-123");
    }

    @Test
    void consumeOrderEventShouldBeIdempotent() {
        NotificationService service = new NotificationService();
        OrderNotificationEvent message = new OrderNotificationEvent(
            "evt-100",
            "ORD-100",
            "C-123",
            NotificationType.ORDER_CONFIRMED,
            "Order confirmed"
        );

        NotificationConsumeResult first = service.consumeOrderEvent(message);
        NotificationConsumeResult duplicate = service.consumeOrderEvent(message);

        assertThat(first.processed()).isTrue();
        assertThat(duplicate.processed()).isFalse();
        assertThat(duplicate.reason()).isEqualTo("DUPLICATE_EVENT");
        assertThat(service.sentNotificationsCount()).isEqualTo(1);
    }

    @Test
    void consumeOrderEventShouldRejectMissingEventId() {
        NotificationService service = new NotificationService();
        OrderNotificationEvent invalid = new OrderNotificationEvent(
            "",
            "ORD-100",
            "C-123",
            NotificationType.ORDER_CONFIRMED,
            "Order confirmed"
        );

        assertThatThrownBy(() -> service.consumeOrderEvent(invalid))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
