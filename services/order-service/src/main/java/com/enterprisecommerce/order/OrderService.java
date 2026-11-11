package com.enterprisecommerce.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final InventoryGateway inventoryGateway;
    private final PaymentGateway paymentGateway;
    private final NotificationGateway notificationGateway;

    public OrderService(InventoryGateway inventoryGateway, PaymentGateway paymentGateway, NotificationGateway notificationGateway) {
        this.inventoryGateway = inventoryGateway;
        this.paymentGateway = paymentGateway;
        this.notificationGateway = notificationGateway;
    }

    public Order createOrder(CreateOrderRequest request) {
        validateRequest(request);

        List<OrderItem> reservedItems = new ArrayList<>();
        String orderId = "ORD-" + UUID.randomUUID();

        try {
            for (OrderItem item : request.items()) {
                inventoryGateway.reserve(item.sku(), item.quantity(), orderId);
                reservedItems.add(item);
            }

            BigDecimal total = request.items().stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            String paymentId = paymentGateway.authorize(orderId, total, "EUR");
            Order order = new Order(orderId, request.customerId(), request.items(), total, OrderStatus.PAID, paymentId);
            orders.put(orderId, order);
            notificationGateway.notifyOrderCreated(request.customerId(), orderId);
            return order;
        } catch (IllegalStateException | IllegalArgumentException ex) {
            reservedItems.forEach(item -> inventoryGateway.release(item.sku(), item.quantity(), orderId));
            Order failed = new Order(orderId, request.customerId(), request.items(), BigDecimal.ZERO, OrderStatus.FAILED, null);
            orders.put(orderId, failed);
            throw ex;
        }
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    public Order cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        for (OrderItem item : order.items()) {
            inventoryGateway.release(item.sku(), item.quantity(), orderId);
        }

        Order cancelled = new Order(order.id(), order.customerId(), order.items(), order.totalAmount(), OrderStatus.CANCELLED, order.paymentId());
        orders.put(orderId, cancelled);
        notificationGateway.notifyOrderCancelled(order.customerId(), orderId);
        return cancelled;
    }

    private void validateRequest(CreateOrderRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request required");
        }
        if (request.customerId() == null || request.customerId().isBlank()) {
            throw new IllegalArgumentException("customerId required");
        }
        if (request.items() == null || request.items().isEmpty()) {
            throw new IllegalArgumentException("at least one item required");
        }
    }
}
