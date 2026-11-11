package com.enterprisecommerce.order;

import java.math.BigDecimal;
import java.util.List;

public record Order(String id, String customerId, List<OrderItem> items, BigDecimal totalAmount, OrderStatus status, String paymentId) {
}
