package com.enterprisecommerce.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class PaymentAuthorizationRetrierTest {

    @Test
    void shouldRetryWithBackoffAndSucceed() {
        List<Long> sleeps = new ArrayList<>();
        AtomicInteger attempts = new AtomicInteger();
        PaymentAuthorizationRetrier retrier = new PaymentAuthorizationRetrier(
            3,
            100,
            500,
            0.0,
            () -> 0.0,
            sleeps::add
        );

        String paymentId = retrier.authorize(() -> {
            if (attempts.incrementAndGet() < 3) {
                throw new IllegalStateException("Transient failure");
            }
            return "PAY-42";
        });

        assertThat(paymentId).isEqualTo("PAY-42");
        assertThat(attempts.get()).isEqualTo(3);
        assertThat(sleeps).containsExactly(100L, 200L);
    }

    @Test
    void shouldThrowAfterMaxAttempts() {
        PaymentAuthorizationRetrier retrier = new PaymentAuthorizationRetrier(
            2,
            0,
            0,
            0.0,
            () -> 0.0,
            ignored -> {
            }
        );

        assertThatThrownBy(() -> retrier.authorize(() -> {
            throw new IllegalStateException("Permanent failure");
        })).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Permanent failure");
    }
}
