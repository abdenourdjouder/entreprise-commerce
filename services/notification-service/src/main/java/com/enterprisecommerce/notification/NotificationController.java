package com.enterprisecommerce.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationEvent> send(@RequestBody NotificationMessageRequest request) {
        NotificationEvent event = notificationService.send(request.customerId(), request.type(), request.message());
        return ResponseEntity.ok(event);
    }

    @PostMapping("/consume/order-event")
    public ResponseEntity<NotificationConsumeResult> consumeOrderEvent(@RequestBody OrderNotificationEvent request) {
        try {
            return ResponseEntity.ok(notificationService.consumeOrderEvent(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}

record NotificationMessageRequest(String customerId, NotificationType type, String message) {
}
