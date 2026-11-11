package com.enterprisecommerce.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    @Test
    void createOrderShouldReserveInventoryAndAuthorisePayment() {
        InventoryGateway inventoryGateway = new LocalInventoryGateway();
        PaymentGateway paymentGateway = new LocalPaymentGateway();
        NotificationGateway notificationGateway = new LocalNotificationGateway();
        OrderEventStore orderEventStore = new OrderEventStore();
        OrderOutbox orderOutbox = new OrderOutbox();
        OrderService service = new OrderService(inventoryGateway, paymentGateway, notificationGateway, orderEventStore, orderOutbox);

        Order order = service.createOrder(new CreateOrderRequest("C-1", List.of(
            new OrderItem("SKU-100", 1, new BigDecimal("1499.00"))
        )));

        assertThat(order.status()).isEqualTo(OrderStatus.PAID);
        assertThat(order.paymentId()).isNotNull();
        assertThat(service.getOrderEvents(order.id()))
            .extracting(OrderEvent::type)
            .containsExactly(
                OrderEventType.ORDER_CREATED,
                OrderEventType.STOCK_RESERVED,
                OrderEventType.PAYMENT_AUTHORIZED,
                OrderEventType.ORDER_CONFIRMED
            );
        assertThat(service.getPendingOutboxEvents(100))
            .extracting(OutboxEvent::type)
            .containsExactly(
                OrderEventType.ORDER_CREATED.name(),
                OrderEventType.STOCK_RESERVED.name(),
                OrderEventType.PAYMENT_AUTHORIZED.name(),
                OrderEventType.ORDER_CONFIRMED.name()
            );
    }

    @Test
    void createOrderShouldFailWhenInventoryNotAvailableAndEmitFailureEvents() {
        InventoryGateway inventoryGateway = new LocalInventoryGateway();
        PaymentGateway paymentGateway = new LocalPaymentGateway();
        NotificationGateway notificationGateway = new LocalNotificationGateway();
        OrderEventStore orderEventStore = new OrderEventStore();
        OrderOutbox orderOutbox = new OrderOutbox();
        OrderService service = new OrderService(inventoryGateway, paymentGateway, notificationGateway, orderEventStore, orderOutbox);

        assertThatThrownBy(() -> service.createOrder(new CreateOrderRequest("C-1", List.of(
            new OrderItem("SKU-100", 1, new BigDecimal("10.00")),
            new OrderItem("SKU-300", 99, new BigDecimal("12.00"))
        ))))
            .isInstanceOf(IllegalStateException.class);

        Order actual = service.listOrders().stream()
            .filter(order -> order.status() == OrderStatus.FAILED)
            .findFirst()
            .orElseThrow();
        assertThat(service.getOrderEvents(actual.id()))
            .extracting(OrderEvent::type)
            .contains(OrderEventType.STOCK_RELEASED, OrderEventType.ORDER_FAILED);
        assertThat(service.getOutboxEvents(actual.id()))
            .extracting(OutboxEvent::type)
            .contains(OrderEventType.ORDER_FAILED.name());
    }

    @Test
    void cancelOrderShouldRefundAndReleaseStock() {
        InventoryGateway inventoryGateway = new LocalInventoryGateway();
        PaymentGateway paymentGateway = new LocalPaymentGateway();
        NotificationGateway notificationGateway = new LocalNotificationGateway();
        OrderEventStore orderEventStore = new OrderEventStore();
        OrderOutbox orderOutbox = new OrderOutbox();
        OrderService service = new OrderService(inventoryGateway, paymentGateway, notificationGateway, orderEventStore, orderOutbox);

        Order order = service.createOrder(new CreateOrderRequest("C-1", List.of(
            new OrderItem("SKU-100", 1, new BigDecimal("1499.00"))
        )));

        Order cancelled = service.cancelOrder(order.id());
        assertThat(cancelled.status()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(service.getOrderEvents(order.id()))
            .extracting(OrderEvent::type)
            .contains(OrderEventType.PAYMENT_REFUNDED, OrderEventType.ORDER_CANCELLED);
    }

    @Test
    void shouldMarkOutboxEventsAsPublished() {
        InventoryGateway inventoryGateway = new LocalInventoryGateway();
        PaymentGateway paymentGateway = new LocalPaymentGateway();
        NotificationGateway notificationGateway = new LocalNotificationGateway();
        OrderEventStore orderEventStore = new OrderEventStore();
        OrderOutbox orderOutbox = new OrderOutbox();
        OrderService service = new OrderService(inventoryGateway, paymentGateway, notificationGateway, orderEventStore, orderOutbox);

        service.createOrder(new CreateOrderRequest("C-1", List.of(
            new OrderItem("SKU-100", 1, new BigDecimal("1499.00"))
        )));

        OutboxEvent firstEvent = service.getPendingOutboxEvents(1).get(0);
        service.markOutboxEventAsPublished(firstEvent.id());

        assertThat(service.getPendingOutboxEvents(100))
            .allMatch(event -> !event.id().equals(firstEvent.id()));
    }
}
