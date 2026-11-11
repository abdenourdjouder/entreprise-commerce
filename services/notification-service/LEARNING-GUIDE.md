# LEARNING GUIDE - Notification Service

- **Responsabilité métier**: notifications asynchrones (email simulé).
- **Concepts illustrés**: consommation idempotente, déduplication par `eventId`, séparation consume/send.
- **Flux clé**: consommer un événement de commande -> vérifier duplicat -> envoyer notification.
- **Classes clés**:
  - `NotificationService#consumeOrderEvent`: point d’entrée idempotent du consumer.
  - `NotificationController#consumeOrderEvent`: endpoint de simulation consumer (`POST /api/notifications/consume/order-event`).
- **Tests associés**:
  - `NotificationServiceTest#consumeOrderEventShouldBeIdempotent`
  - `NotificationServiceTest#consumeOrderEventShouldRejectMissingEventId`
- **Questions entretien**: "Comment prouver l’idempotence d’un consumer ?"
