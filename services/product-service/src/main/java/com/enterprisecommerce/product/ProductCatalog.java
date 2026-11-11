package com.enterprisecommerce.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalog {

    private final Map<String, Product> products = new ConcurrentHashMap<>();

    public ProductCatalog() {
        seedCatalog();
    }

    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    public Optional<Product> findBySku(String sku) {
        return products.values().stream()
            .filter(product -> product.sku().equalsIgnoreCase(sku))
            .findFirst();
    }

    public Product save(Product product) {
        products.put(product.id(), product);
        return product;
    }

    public Product create(String sku, String name, BigDecimal price, String currency) {
        String id = UUID.randomUUID().toString();
        Product product = new Product(id, sku, name, price, currency);
        return save(product);
    }

    private void seedCatalog() {
        save(new Product("p-1", "SKU-100", "Laptop Pro 14", new BigDecimal("1499.00"), "EUR"));
        save(new Product("p-2", "SKU-200", "Mechanical Keyboard", new BigDecimal("129.00"), "EUR"));
        save(new Product("p-3", "SKU-300", "4K Monitor", new BigDecimal("399.00"), "EUR"));
    }
}
