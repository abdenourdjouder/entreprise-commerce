package com.enterprisecommerce.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ProductCatalogTest {

    @Test
    void catalogContainsSeededProducts() {
        ProductCatalog catalog = new ProductCatalog();

        assertThat(catalog.findAll()).hasSizeGreaterThan(0);
        assertThat(catalog.findBySku("SKU-100")).isPresent();
    }

    @Test
    void canCreateProduct() {
        ProductCatalog catalog = new ProductCatalog();

        Product created = catalog.create("SKU-777", "USB-C Hub", new BigDecimal("54.90"), "EUR");

        assertThat(created.sku()).isEqualTo("SKU-777");
        assertThat(catalog.findBySku("SKU-777")).isPresent();
    }
}
