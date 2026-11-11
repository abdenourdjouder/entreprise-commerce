package com.enterprisecommerce.payment;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final Map<String, Payment> payments = new ConcurrentHashMap<>();

    public Payment authorize(String orderId, BigDecimal amount, String currency) {
        validate(orderId, amount, currency);
        Payment payment = new Payment(UUID.randomUUID().toString(), orderId, amount, currency, PaymentStatus.AUTHORIZED);
        payments.put(payment.id(), payment);
        return payment;
    }

    public Payment capture(String paymentId) {
        Payment payment = payments.get(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found: " + paymentId);
        }
        Payment updated = new Payment(payment.id(), payment.orderId(), payment.amount(), payment.currency(), PaymentStatus.CAPTURED);
        payments.put(paymentId, updated);
        return updated;
    }

    public Payment refund(String paymentId) {
        Payment payment = payments.get(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found: " + paymentId);
        }
        Payment updated = new Payment(payment.id(), payment.orderId(), payment.amount(), payment.currency(), PaymentStatus.REFUNDED);
        payments.put(paymentId, updated);
        return updated;
    }

    private void validate(String orderId, BigDecimal amount, String currency) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("orderId required");
        }
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency required");
        }
    }
}
