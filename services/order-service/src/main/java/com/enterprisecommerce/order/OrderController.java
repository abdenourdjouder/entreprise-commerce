package com.enterprisecommerce.order;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            return ResponseEntity.ok(orderService.createOrder(request));
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        return order == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}/events")
    public ResponseEntity<List<OrderEvent>> getOrderEvents(@PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.getOrderEvents(orderId));
    }

    @GetMapping("/{orderId}/outbox")
    public ResponseEntity<List<OutboxEvent>> getOrderOutboxEvents(@PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderService.getOutboxEvents(orderId));
    }

    @GetMapping("/outbox/pending")
    public ResponseEntity<List<OutboxEvent>> getPendingOutboxEvents() {
        return ResponseEntity.ok(orderService.getPendingOutboxEvents(100));
    }

    @PostMapping("/outbox/{eventId}/published")
    public ResponseEntity<Void> markOutboxPublished(@PathVariable String eventId) {
        orderService.markOutboxEventAsPublished(eventId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId) {
        try {
            return ResponseEntity.ok(orderService.cancelOrder(orderId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
