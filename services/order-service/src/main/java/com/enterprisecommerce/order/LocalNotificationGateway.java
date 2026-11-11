package com.enterprisecommerce.order;

import org.springframework.stereotype.Component;

@Component
public class LocalNotificationGateway implements NotificationGateway {

    @Override
    public void notifyOrderCreated(String customerId, String orderId) {
        System.out.println("Order created: customerId=" + customerId + ", orderId=" + orderId);
    }

    @Override
    public void notifyOrderCancelled(String customerId, String orderId) {
        System.out.println("Order cancelled: customerId=" + customerId + ", orderId=" + orderId);
    }
}
