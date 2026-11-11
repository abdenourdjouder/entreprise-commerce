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
    private final OrderEventStore orderEventStore;
    private final OrderOutbox orderOutbox;
    private final PaymentAuthorizationRetrier paymentAuthorizationRetrier;

    public OrderService(
        InventoryGateway inventoryGateway,
        PaymentGateway paymentGateway,
        NotificationGateway notificationGateway,
        OrderEventStore orderEventStore,
        OrderOutbox orderOutbox,
        PaymentAuthorizationRetrier paymentAuthorizationRetrier
    ) {
        this.inventoryGateway = inventoryGateway;
        this.paymentGateway = paymentGateway;
        this.notificationGateway = notificationGateway;
        this.orderEventStore = orderEventStore;
        this.orderOutbox = orderOutbox;
        this.paymentAuthorizationRetrier = paymentAuthorizationRetrier;
    }

    public Order createOrder(CreateOrderRequest request) {
        validateRequest(request);

        List<OrderItem> reservedItems = new ArrayList<>();
        String orderId = "ORD-" + UUID.randomUUID();
        appendOrderEvent(orderId, OrderEventType.ORDER_CREATED, "Order request accepted");

        try {
            for (OrderItem item : request.items()) {
                inventoryGateway.reserve(item.sku(), item.quantity(), orderId);
                reservedItems.add(item);
                appendOrderEvent(
                    orderId,
                    OrderEventType.STOCK_RESERVED,
                    "Reserved " + item.quantity() + " of " + item.sku()
                );
            }

            BigDecimal total = request.items().stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            String paymentId = paymentAuthorizationRetrier.authorize(
                () -> paymentGateway.authorize(orderId, total, "EUR")
            );
            appendOrderEvent(orderId, OrderEventType.PAYMENT_AUTHORIZED, "PaymentId " + paymentId + " authorised");
            Order order = new Order(orderId, request.customerId(), request.items(), total, OrderStatus.PAID, paymentId);
            orders.put(orderId, order);
            appendOrderEvent(orderId, OrderEventType.ORDER_CONFIRMED, "Order confirmed and paid");
            notificationGateway.notifyOrderCreated(request.customerId(), orderId);
            return order;
        } catch (IllegalStateException | IllegalArgumentException ex) {
            for (OrderItem item : reservedItems) {
                inventoryGateway.release(item.sku(), item.quantity(), orderId);
                appendOrderEvent(
                    orderId,
                    OrderEventType.STOCK_RELEASED,
                    "Released " + item.quantity() + " of " + item.sku()
                );
            }
            Order failed = new Order(orderId, request.customerId(), request.items(), BigDecimal.ZERO, OrderStatus.FAILED, null);
            orders.put(orderId, failed);
            appendOrderEvent(orderId, OrderEventType.ORDER_FAILED, ex.getMessage());
            throw ex;
        }
    }

    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    public List<Order> listOrders() {
        return List.copyOf(orders.values());
    }

    public Order cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        for (OrderItem item : order.items()) {
            inventoryGateway.release(item.sku(), item.quantity(), orderId);
            appendOrderEvent(
                orderId,
                OrderEventType.STOCK_RELEASED,
                "Released " + item.quantity() + " of " + item.sku()
            );
        }

        if (order.paymentId() != null) {
            paymentGateway.refund(order.paymentId());
            appendOrderEvent(orderId, OrderEventType.PAYMENT_REFUNDED, "PaymentId " + order.paymentId() + " refunded");
        }

        Order cancelled = new Order(order.id(), order.customerId(), order.items(), order.totalAmount(), OrderStatus.CANCELLED, order.paymentId());
        orders.put(orderId, cancelled);
        appendOrderEvent(orderId, OrderEventType.ORDER_CANCELLED, "Order cancelled by user");
        notificationGateway.notifyOrderCancelled(order.customerId(), orderId);
        return cancelled;
    }

    public List<OrderEvent> getOrderEvents(String orderId) {
        return orderEventStore.findByOrderId(orderId);
    }

    public List<OutboxEvent> getOutboxEvents(String orderId) {
        return orderOutbox.listByAggregateId(orderId);
    }

    public List<OutboxEvent> getPendingOutboxEvents(int limit) {
        int boundedLimit = Math.max(1, limit);
        return orderOutbox.listPending(boundedLimit);
    }

    public void markOutboxEventAsPublished(String eventId) {
        orderOutbox.markPublished(eventId);
    }

    private void appendOrderEvent(String orderId, OrderEventType type, String details) {
        orderEventStore.append(orderId, type, details);
        orderOutbox.append(orderId, type.name(), details);
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
