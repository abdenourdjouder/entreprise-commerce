package com.enterprisecommerce.order;

public interface NotificationGateway {
    void notifyOrderCreated(String customerId, String orderId);
    void notifyOrderCancelled(String customerId, String orderId);
}
