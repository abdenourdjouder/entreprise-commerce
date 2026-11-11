package com.enterprisecommerce.common;

import java.util.UUID;

public record CorrelationId(String value) {
    public CorrelationId() {
        this(UUID.randomUUID().toString());
    }
}
