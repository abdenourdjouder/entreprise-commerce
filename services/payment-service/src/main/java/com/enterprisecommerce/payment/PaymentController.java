package com.enterprisecommerce.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/authorize")
    public ResponseEntity<Payment> authorize(@RequestBody PaymentRequest request) {
        try {
            return ResponseEntity.ok(paymentService.authorize(request.orderId(), request.amount(), request.currency()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{paymentId}/capture")
    public ResponseEntity<Payment> capture(@PathVariable String paymentId) {
        try {
            return ResponseEntity.ok(paymentService.capture(paymentId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<Payment> refund(@PathVariable String paymentId) {
        try {
            return ResponseEntity.ok(paymentService.refund(paymentId));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
