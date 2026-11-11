package com.enterprisecommerce.order;

import java.time.Instant;

public record OrderEvent(String orderId, OrderEventType type, String details, Instant occurredAt) {
}
