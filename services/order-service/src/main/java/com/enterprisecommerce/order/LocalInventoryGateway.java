package com.enterprisecommerce.order;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class LocalInventoryGateway implements InventoryGateway {

    private final Map<String, Integer> stock = new ConcurrentHashMap<>();

    public LocalInventoryGateway() {
        stock.put("SKU-100", 12);
        stock.put("SKU-200", 25);
        stock.put("SKU-300", 7);
    }

    @Override
    public void reserve(String sku, int quantity, String orderId) {
        while (true) {
            int current = stock.getOrDefault(sku, 0);
            if (current < quantity) {
                throw new IllegalStateException("Insufficient stock for " + sku);
            }
            if (stock.replace(sku, current, current - quantity)) {
                return;
            }
        }
    }

    @Override
    public void release(String sku, int quantity, String orderId) {
        stock.merge(sku, quantity, Integer::sum);
    }
}
