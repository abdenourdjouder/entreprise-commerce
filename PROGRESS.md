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

## Phase 6 - Sécurité, tests et résilience

### Statut global
- État: Terminé (version pédagogique locale)
- Date: 2026-11-11

### Réalisé
- `order-service`:
  - Activation Spring Security avec API key (`X-API-KEY`) sur `/api/**`.
  - Ajout d’un retry exponentiel avec jitter (`PaymentAuthorizationRetrier`) sur l’autorisation de paiement.
  - Paramétrage `resilience.payment.retry.*` dans `application.yml`.
- `notification-service`:
  - Activation Spring Security avec API key (`X-API-KEY`) sur `/api/**`.
- Tests ajoutés:
  - `OrderSecurityIntegrationTest`
  - `NotificationSecurityIntegrationTest`
  - `PaymentAuthorizationRetrierTest`
  - scénario retry appliqué dans `OrderServiceTest`.

### Validation exécutée
- `mvn -q -pl services/order-service,services/notification-service -am test` ✅
- `mvn -q test` ✅

### Limites connues
- Authentification API key uniquement (pas encore OAuth2/OIDC/JWT).
- Retry local intra-processus uniquement (pas encore circuit breaker / timeout distribué / DLQ).

## Phase 7 - Observabilité et déploiement

### Statut global
- État: Terminé (baseline exécutable)
- Date: 2026-11-11

### Réalisé
- Observabilité:
  - Correlation IDs sur `order-service` et `notification-service` (header + MDC + propagation réponse).
  - Pattern de logs homogène avec `corr=<correlationId>`.
  - Exposition Prometheus (`/actuator/prometheus`) via Micrometer registry.
- Déploiement:
  - Dockerfiles pour `order-service` et `notification-service`.
  - Manifests Kubernetes pédagogiques (ConfigMap, Deployment, Service, probes, resources).
  - Pipeline CI GitHub Actions (`.github/workflows/ci.yml`) avec tests Maven + build images Docker.

### Validation exécutée
- `mvn -q -pl services/order-service,services/notification-service -am test` ✅
- `mvn -q test` ✅

### Limites connues
- Pas encore de tracing OpenTelemetry distribué ni dashboards Grafana packagés.
- Pas de push d’images vers registry dans le workflow CI.

## Phase 8 - Finalisation documentaire et préparation entretien

### Statut global
- État: Terminé
- Date: 2026-11-11

### Réalisé
- Complétion des documents stratégiques:
  - `docs/05-database-and-transactions.md`
  - `docs/10-testing-strategy.md`
  - `docs/13-tech-lead-handbook.md`
  - `docs/17-glossary.md`
- Banque d’entretien consolidée:
  - `docs/14-interview-question-bank.md` (100 questions)
  - `docs/15-interview-scenarios.md` (20 scénarios)
- Pack `interview-training/` complété pour les 9 catégories avec:
  - `CONCEPTS.md`
  - `QUESTIONS.md`
  - `ANSWERS.md`
  - `TRAPS.md`
  - `PROJECT-REFERENCES.md`
- Mise à jour de cohérence:
  - `README.md`
  - `docs/00-learning-roadmap.md`
  - `docs/01-project-overview.md`
  - `docs/12-design-decisions.md`
  - `interview-training/README.md`

### Validation exécutée
- Relecture structurelle et cohérence inter-docs (phase documentaire).

### Limites connues
- Certains contenus avancés (OpenTelemetry distribué complet, runbooks d’incident détaillés par service) restent à approfondir dans une phase d’industrialisation supplémentaire.
