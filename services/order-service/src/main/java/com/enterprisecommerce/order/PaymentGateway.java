package com.enterprisecommerce.order;

import java.math.BigDecimal;

public interface PaymentGateway {
    String authorize(String orderId, BigDecimal amount, String currency);
    void refund(String paymentId);
}
