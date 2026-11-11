package com.enterprisecommerce.inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final Map<String, Integer> stock = new ConcurrentHashMap<>();

    public InventoryService() {
        stock.put("SKU-100", 12);
        stock.put("SKU-200", 25);
        stock.put("SKU-300", 7);
    }

    public InventoryEntry getStock(String sku) {
        return new InventoryEntry(sku, stock.getOrDefault(sku, 0));
    }

    public InventoryReservation reserve(String sku, int quantity, String orderId) {
        validateRequest(sku, quantity, orderId);

        while (true) {
            int current = stock.getOrDefault(sku, 0);
            if (current < quantity) {
                throw new IllegalStateException("Insufficient stock for sku " + sku);
            }
            if (stock.replace(sku, current, current - quantity)) {
                return new InventoryReservation(orderId, sku, quantity, true);
            }
        }
    }

    public InventoryEntry release(String sku, int quantity, String orderId) {
        validateRequest(sku, quantity, orderId);
        int current = stock.getOrDefault(sku, 0);
        stock.put(sku, current + quantity);
        return new InventoryEntry(sku, stock.get(sku));
    }

    private void validateRequest(String sku, int quantity, String orderId) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("sku required");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("orderId required");
        }
    }
}
