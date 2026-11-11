package com.enterprisecommerce.inventory;

public record InventoryReservationRequest(String sku, int quantity, String orderId) {
}
