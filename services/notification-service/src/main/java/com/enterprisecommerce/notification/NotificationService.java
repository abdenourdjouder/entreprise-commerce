package com.enterprisecommerce.notification;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final Map<String, NotificationEvent> sentNotifications = new ConcurrentHashMap<>();

    public NotificationEvent send(String customerId, NotificationType type, String message) {
        NotificationEvent event = new NotificationEvent(UUID.randomUUID().toString(), customerId, type, message);
        sentNotifications.put(event.id(), event);
        return event;
    }
}
