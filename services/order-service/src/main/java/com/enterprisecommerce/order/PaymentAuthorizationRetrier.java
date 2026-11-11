package com.enterprisecommerce.order;

import java.util.Objects;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentAuthorizationRetrier {

    private final int maxAttempts;
    private final long initialDelayMillis;
    private final long maxDelayMillis;
    private final double jitterFactor;
    private final Supplier<Double> randomRatioSupplier;
    private final Sleeper sleeper;

    @Autowired
    public PaymentAuthorizationRetrier(
        @Value("${resilience.payment.retry.max-attempts:3}") int maxAttempts,
        @Value("${resilience.payment.retry.initial-delay-ms:50}") long initialDelayMillis,
        @Value("${resilience.payment.retry.max-delay-ms:500}") long maxDelayMillis,
        @Value("${resilience.payment.retry.jitter-factor:0.25}") double jitterFactor
    ) {
        this(maxAttempts, initialDelayMillis, maxDelayMillis, jitterFactor, Math::random, Thread::sleep);
    }

    PaymentAuthorizationRetrier(
        int maxAttempts,
        long initialDelayMillis,
        long maxDelayMillis,
        double jitterFactor,
        Supplier<Double> randomRatioSupplier,
        Sleeper sleeper
    ) {
        this.maxAttempts = Math.max(1, maxAttempts);
        this.initialDelayMillis = Math.max(0, initialDelayMillis);
        this.maxDelayMillis = Math.max(this.initialDelayMillis, maxDelayMillis);
        this.jitterFactor = Math.max(0, jitterFactor);
        this.randomRatioSupplier = Objects.requireNonNull(randomRatioSupplier, "randomRatioSupplier");
        this.sleeper = Objects.requireNonNull(sleeper, "sleeper");
    }

    public String authorize(Authorizer authorizer) {
        long delay = initialDelayMillis;
        RuntimeException lastFailure = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return authorizer.authorize();
            } catch (RuntimeException exception) {
                lastFailure = exception;
                if (attempt == maxAttempts) {
                    throw exception;
                }
                backoff(delay);
                delay = Math.min(maxDelayMillis, delay * 2L);
            }
        }

        throw lastFailure == null ? new IllegalStateException("Retry loop finished without outcome") : lastFailure;
    }

    private void backoff(long delayMillis) {
        long jitter = (long) (delayMillis * jitterFactor * randomRatioSupplier.get());
        long sleepMillis = Math.min(maxDelayMillis, delayMillis + jitter);
        try {
            sleeper.sleep(sleepMillis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Payment retry interrupted", exception);
        }
    }

    @FunctionalInterface
    public interface Authorizer {
        String authorize();
    }

    @FunctionalInterface
    interface Sleeper {
        void sleep(long milliseconds) throws InterruptedException;
    }
}
