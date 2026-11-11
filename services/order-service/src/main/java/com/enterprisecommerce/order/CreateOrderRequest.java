package com.enterprisecommerce.order;

import java.util.List;

public record CreateOrderRequest(String customerId, List<OrderItem> items) {
}
