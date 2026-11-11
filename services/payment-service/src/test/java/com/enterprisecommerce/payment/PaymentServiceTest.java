package com.enterprisecommerce.payment;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

    @Test
    void authorizeAndCaptureShouldTrackStatus() {
        PaymentService paymentService = new PaymentService();

        Payment authorized = paymentService.authorize("ORD-1", new BigDecimal("49.90"), "EUR");
        Payment captured = paymentService.capture(authorized.id());

        assertThat(authorized.status()).isEqualTo(PaymentStatus.AUTHORIZED);
        assertThat(captured.status()).isEqualTo(PaymentStatus.CAPTURED);
    }
}
