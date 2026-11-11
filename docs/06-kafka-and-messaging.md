# 06 - Kafka and Messaging

## Objectif de la phase courante

Préparer le passage à Kafka réel en implémentant d’abord les mécanismes de fiabilité **dans le code métier**:
- outbox transactionnelle (version pédagogique locale),
- publication différée (pending -> published),
- consommation idempotente côté notification.

## Implémentation actuelle (sans broker externe)

- `order-service`:
  - `OrderOutbox` stocke les événements à publier avec statut `PENDING` / `PUBLISHED`.
  - chaque transition de saga appelle `appendOrderEvent(...)` qui écrit:
    1. le journal métier (`OrderEventStore`),
    2. l’outbox (`OrderOutbox`).
  - endpoints:
    - `GET /api/orders/{orderId}/outbox`
    - `GET /api/orders/outbox/pending`
    - `POST /api/orders/outbox/{eventId}/published`

- `notification-service`:
  - `consumeOrderEvent` rejette les doublons via `eventId`.
  - endpoint: `POST /api/notifications/consume/order-event`.

## Mapping vers l’implémentation Kafka future

- `OrderOutbox#listPending` -> poller qui publiera vers Kafka.
- `markPublished` -> accusé de publication broker.
- `processedEventIds` du notification-service -> stockage durable de déduplication (DB/Redis) en production.

## Limites assumées

- stockage en mémoire: pas de durabilité au redémarrage.
- pas de retry/backoff/DLQ réels dans cette phase.
- pas de garantie inter-processus (single instance pédagogique).
