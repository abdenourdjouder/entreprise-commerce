# 05 - Database and Transactions

## Objectif

Expliquer comment garder une cohérence métier forte dans chaque service, tout en assumant une cohérence éventuelle au niveau inter-services.

## Modèle actuel (phase pédagogique)

- Les services sont majoritairement in-memory pour accélérer l’apprentissage.
- Le design est toutefois aligné avec une persistance PostgreSQL par bounded context:
  - `customer-service` (clients),
  - `product-service` (catalogue),
  - `order-service` (commande + outbox),
  - `inventory-service` (stock),
  - `payment-service` (paiements),
  - `notification-service` (journal de notifications).

## Transactions: règle d’or

- **ACID local** dans le service propriétaire.
- **Pas de transaction distribuée 2PC** entre services.
- **Cohérence globale** obtenue par saga + compensation + idempotence.

## Isolation et verrous (cible PostgreSQL)

- `READ COMMITTED` par défaut pour la majorité des flows.
- `REPEATABLE READ` ciblé pour les lectures critiques à stabilité de snapshot.
- `SELECT ... FOR UPDATE` uniquement sur sections sensibles (ex: réservation stock), avec timeout court.
- Préférer optimistic locking (`version`) pour réduire la contention.

## Deadlocks: prévention

1. Ordre d’acquisition stable des ressources.
2. Transactions courtes.
3. Requêtes indexées pour éviter les scans sous verrou.
4. Retry borné côté application quand deadlock détecté.

## Outbox et transaction locale

Le pattern outbox (`order-service`) formalise:
1. écriture métier,
2. écriture d’événement outbox,
dans la même unité de cohérence locale (conceptuellement, puis transaction SQL en phase DB réelle).

Références code:
- `services/order-service/src/main/java/com/enterprisecommerce/order/OrderOutbox.java`
- `services/order-service/src/main/java/com/enterprisecommerce/order/OrderService.java`

## Entretien: message clé

> “Je maximise la cohérence forte localement et je rends l’incohérence globale explicite, observable et compensable. C’est plus réaliste en microservices que de tenter un ACID distribué coûteux et fragile.”
