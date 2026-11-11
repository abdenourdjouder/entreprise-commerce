package com.enterprisecommerce.order;

import java.time.Instant;

public record OutboxEvent(
    String id,
    String aggregateId,
    String type,
    String payload,
    Instant occurredAt,
    Instant publishedAt,
    OutboxStatus status
) {
}
