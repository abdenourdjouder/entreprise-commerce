package com.enterprisecommerce.inventory;

public record InventoryReservation(String orderId, String sku, int quantity, boolean reserved) {
}
