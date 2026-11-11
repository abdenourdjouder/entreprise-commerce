package com.enterprisecommerce.payment;

import java.math.BigDecimal;

public record Payment(String id, String orderId, BigDecimal amount, String currency, PaymentStatus status) {
}
