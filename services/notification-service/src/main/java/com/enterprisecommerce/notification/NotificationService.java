package com.enterprisecommerce.notification;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Map<String, NotificationEvent> sentNotifications = new ConcurrentHashMap<>();
    private final Set<String> processedEventIds = ConcurrentHashMap.newKeySet();

    public NotificationEvent send(String customerId, NotificationType type, String message) {
        NotificationEvent event = new NotificationEvent(UUID.randomUUID().toString(), customerId, type, message);
        sentNotifications.put(event.id(), event);
        return event;
    }

    public NotificationConsumeResult consumeOrderEvent(OrderNotificationEvent orderEvent) {
        if (orderEvent == null || orderEvent.eventId() == null || orderEvent.eventId().isBlank()) {
            throw new IllegalArgumentException("eventId required");
        }
        if (!processedEventIds.add(orderEvent.eventId())) {
            return new NotificationConsumeResult(orderEvent.eventId(), false, "DUPLICATE_EVENT", null);
        }

        try {
            NotificationEvent notification = send(orderEvent.customerId(), orderEvent.type(), orderEvent.message());
            return new NotificationConsumeResult(orderEvent.eventId(), true, "PROCESSED", notification);
        } catch (RuntimeException ex) {
            processedEventIds.remove(orderEvent.eventId());
            throw ex;
        }
    }

    public int sentNotificationsCount() {
        return sentNotifications.size();
    }
}
