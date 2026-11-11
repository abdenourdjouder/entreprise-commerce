package com.enterprisecommerce.inventory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class InventoryServiceTest {

    @Test
    void reserveRemovesAvailableQuantity() {
        InventoryService service = new InventoryService();

        InventoryReservation reservation = service.reserve("SKU-100", 2, "ORD-1");

        assertThat(reservation.reserved()).isTrue();
        assertThat(service.getStock("SKU-100").quantity()).isEqualTo(10);
    }

    @Test
    void reserveRejectsUnavailableQuantity() {
        InventoryService service = new InventoryService();

        assertThatThrownBy(() -> service.reserve("SKU-300", 99, "ORD-2"))
            .isInstanceOf(IllegalStateException.class);
    }
}
