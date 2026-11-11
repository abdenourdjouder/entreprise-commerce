package com.enterprisecommerce.order;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;

@Component
public class OrderEventStore {

    private final Map<String, List<OrderEvent>> eventsByOrderId = new ConcurrentHashMap<>();

    public void append(String orderId, OrderEventType type, String details) {
        eventsByOrderId.computeIfAbsent(orderId, ignored -> new CopyOnWriteArrayList<>())
            .add(new OrderEvent(orderId, type, details, Instant.now()));
    }

    public List<OrderEvent> findByOrderId(String orderId) {
        return new ArrayList<>(eventsByOrderId.getOrDefault(orderId, List.of()));
    }
}
