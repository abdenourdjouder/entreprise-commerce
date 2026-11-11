package com.enterprisecommerce.order;

public interface InventoryGateway {
    void reserve(String sku, int quantity, String orderId);
    void release(String sku, int quantity, String orderId);
}
