package com.enterprisecommerce.order;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class LocalPaymentGateway implements PaymentGateway {

    private final Map<String, String> payments = new ConcurrentHashMap<>();

    @Override
    public String authorize(String orderId, BigDecimal amount, String currency) {
        String paymentId = "PAY-" + UUID.randomUUID();
        payments.put(paymentId, orderId);
        return paymentId;
    }

    @Override
    public void refund(String paymentId) {
        payments.remove(paymentId);
    }
}
