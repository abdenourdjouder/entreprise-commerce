package com.enterprisecommerce.notification;

public record NotificationEvent(String id, String customerId, NotificationType type, String message) {
}
