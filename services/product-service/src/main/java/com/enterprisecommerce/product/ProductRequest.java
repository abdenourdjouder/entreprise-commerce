package com.enterprisecommerce.product;

import java.math.BigDecimal;

public record ProductRequest(String sku, String name, BigDecimal price, String currency) {
}
