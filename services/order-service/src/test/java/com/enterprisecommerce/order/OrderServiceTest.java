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
        OrderService service = new OrderService(inventoryGateway, paymentGateway, notificationGateway);

        Order order = service.createOrder(new CreateOrderRequest("C-1", List.of(
            new OrderItem("SKU-100", 1, new BigDecimal("1499.00"))
        )));

        assertThat(order.status()).isEqualTo(OrderStatus.PAID);
        assertThat(order.paymentId()).isNotNull();
    }

    @Test
    void createOrderShouldFailWhenInventoryNotAvailable() {
        InventoryGateway inventoryGateway = new LocalInventoryGateway();
        PaymentGateway paymentGateway = new LocalPaymentGateway();
        NotificationGateway notificationGateway = new LocalNotificationGateway();
        OrderService service = new OrderService(inventoryGateway, paymentGateway, notificationGateway);

        assertThatThrownBy(() -> service.createOrder(new CreateOrderRequest("C-1", List.of(
            new OrderItem("SKU-100", 99, new BigDecimal("10.00"))
        ))))
            .isInstanceOf(IllegalStateException.class);
    }
}
