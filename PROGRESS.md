# PROGRESS - Enterprise Commerce Platform

## Phase 1 - Architecture et cadrage

### Statut global
- État: Terminé
- Date: 2026-11-11

### Réalisé
- Squelette Maven multi-modules initial.
- Découpage microservices et bounded contexts documentés.
- Diagrammes Mermaid de contexte et flux métier.
- ADR initiaux créés.
- Guides d’apprentissage par service initialisés.

## Phase 2 - Parent Maven + infrastructure locale

### Statut global
- État: Terminé
- Date: 2026-11-11

### Réalisé
- Parent Maven complet avec BOM Spring et versions de référence.
- Structure de modules alignée sur l’architecture cible.
- Fichier `infra/docker-compose.yml` avec PostgreSQL, Redis, ZooKeeper et Kafka.
- Répertoires `k8s/` et `interview-training/` préparés.

## Phase 3 - Bootstraps métier et parcours de commande

### Statut global
- État: Terminé
- Date: 2026-11-11

### Réalisé
- Domain model minimal et fonctionnel par service.
- Contrôleurs REST pour clients, produits, stock, paiements, notifications et commandes.
- Gestion mémoire des données pour illustrer le flux de commande sans dépendance externe.
- Tests unitaires de domaine et de service exécutés.

### Flux démontrés
- Création client.
- Consultation catalogue produits.
- Réservation stock.
- Paiement simulé.
- Commande confirmée.
- Annulation avec libération de stock.

### Validation exécutée
- `mvn -q test` ✅

### Limites connues
- Les services restent en mode mémoire pour garder le projet simple et testable sans dépendances externes.
- L’intégration PostgreSQL, Kafka et Redis sera ajoutée dans la phase suivante.

## Phase 4 - Parcours de commande orienté événements

### Statut global
- État: Terminé (version pédagogique locale)
- Date: 2026-11-11

### Réalisé
- Ajout d’un journal d’événements métier sur `order-service` (`OrderEventStore`).
- Instrumentation des transitions de commande (création, réservation, paiement, confirmation, compensation, annulation).
- Exposition de l’historique via `GET /api/orders/{orderId}/events`.
- Renforcement des tests `OrderServiceTest` pour couvrir les scénarios succès, échec avec compensation, et annulation.

### Validation exécutée
- `mvn -q -pl services/order-service test` ✅

### Limites connues
- Les événements sont en mémoire et non durables.
- Pas encore de publication Kafka ni de transactional outbox (prévu phase suivante).

## Phase 5 - Outbox, Saga et idempotence

### Statut global
- État: Terminé (version pédagogique locale)
- Date: 2026-11-11

### Réalisé
- `order-service`:
  - Ajout d’une outbox applicative en mémoire (`OrderOutbox`, `OutboxEvent`, `OutboxStatus`).
  - Écriture duale journal métier + outbox pour chaque transition saga.
  - Endpoints d’inspection/pilotage outbox:
    - `GET /api/orders/{orderId}/outbox`
    - `GET /api/orders/outbox/pending`
    - `POST /api/orders/outbox/{eventId}/published`
- `notification-service`:
  - Ajout d’un consumer idempotent (`consumeOrderEvent`) avec déduplication par `eventId`.
  - Endpoint de consommation simulée: `POST /api/notifications/consume/order-event`.

### Validation exécutée
- `mvn -q -pl services/order-service,services/notification-service -am test` ✅
- `mvn -q test` ✅

### Limites connues
- Outbox et déduplication non durables (in-memory).
- Pas encore de publication Kafka, retry exponentiel ni DLQ (phase suivante).
