package com.enterprisecommerce.product;

import java.math.BigDecimal;

public record Product(String id, String sku, String name, BigDecimal price, String currency) {
}
