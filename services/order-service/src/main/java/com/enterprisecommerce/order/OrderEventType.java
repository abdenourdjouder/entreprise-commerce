package com.enterprisecommerce.order;

public enum OrderEventType {
    ORDER_CREATED,
    STOCK_RESERVED,
    PAYMENT_AUTHORIZED,
    ORDER_CONFIRMED,
    STOCK_RELEASED,
    PAYMENT_REFUNDED,
    ORDER_CANCELLED,
    ORDER_FAILED
}
