package com.enterprisecommerce.notification;

public record OrderNotificationEvent(
    String eventId,
    String orderId,
    String customerId,
    NotificationType type,
    String message
) {
}
