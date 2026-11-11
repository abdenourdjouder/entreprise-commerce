# LEARNING GUIDE - Notification Service

- **Responsabilité métier**: notifications asynchrones (email simulé).
- **Concepts illustrés**: consommation idempotente, déduplication par `eventId`, séparation consume/send.
- **Flux clé**: consommer un événement de commande -> vérifier duplicat -> envoyer notification.
- **Classes clés**:
  - `NotificationService#consumeOrderEvent`: point d’entrée idempotent du consumer.
  - `NotificationController#consumeOrderEvent`: endpoint de simulation consumer (`POST /api/notifications/consume/order-event`).
  - `security/ApiKeyAuthenticationFilter` + `security/SecurityConfig`: protection des endpoints `/api/**` via `X-API-KEY`.
- **Tests associés**:
  - `NotificationServiceTest#consumeOrderEventShouldBeIdempotent`
  - `NotificationServiceTest#consumeOrderEventShouldRejectMissingEventId`
  - `NotificationSecurityIntegrationTest`
- **Questions entretien**: "Comment prouver l’idempotence d’un consumer ?"
