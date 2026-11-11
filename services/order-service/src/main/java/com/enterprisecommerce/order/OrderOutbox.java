package com.enterprisecommerce.order;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class OrderOutbox {

    private final Map<String, OutboxEvent> eventsById = new ConcurrentHashMap<>();

    public OutboxEvent append(String aggregateId, String type, String payload) {
        OutboxEvent event = new OutboxEvent(
            UUID.randomUUID().toString(),
            aggregateId,
            type,
            payload,
            Instant.now(),
            null,
            OutboxStatus.PENDING
        );
        eventsById.put(event.id(), event);
        return event;
    }

    public List<OutboxEvent> listPending(int limit) {
        return eventsById.values().stream()
            .filter(event -> event.status() == OutboxStatus.PENDING)
            .sorted(Comparator.comparing(OutboxEvent::occurredAt))
            .limit(limit)
            .toList();
    }

    public void markPublished(String eventId) {
        OutboxEvent event = eventsById.get(eventId);
        if (event == null || event.status() == OutboxStatus.PUBLISHED) {
            return;
        }
        eventsById.put(
            eventId,
            new OutboxEvent(
                event.id(),
                event.aggregateId(),
                event.type(),
                event.payload(),
                event.occurredAt(),
                Instant.now(),
                OutboxStatus.PUBLISHED
            )
        );
    }

    public List<OutboxEvent> listByAggregateId(String aggregateId) {
        return eventsById.values().stream()
            .filter(event -> event.aggregateId().equals(aggregateId))
            .sorted(Comparator.comparing(OutboxEvent::occurredAt))
            .toList();
    }
}
