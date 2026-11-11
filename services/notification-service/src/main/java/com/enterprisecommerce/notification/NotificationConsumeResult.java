package com.enterprisecommerce.notification;

public record NotificationConsumeResult(
    String eventId,
    boolean processed,
    String reason,
    NotificationEvent notification
) {
}
