package com.enterprisecommerce.inventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{sku}")
    public ResponseEntity<InventoryEntry> getStock(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.getStock(sku));
    }

    @PostMapping("/reserve")
    public ResponseEntity<InventoryReservation> reserve(@RequestBody InventoryReservationRequest request) {
        try {
            InventoryReservation reservation = inventoryService.reserve(request.sku(), request.quantity(), request.orderId());
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(new InventoryReservation(request.orderId(), request.sku(), request.quantity(), false));
        }
    }

    @PostMapping("/release")
    public ResponseEntity<InventoryEntry> release(@RequestBody InventoryReservationRequest request) {
        try {
            return ResponseEntity.ok(inventoryService.release(request.sku(), request.quantity(), request.orderId()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
