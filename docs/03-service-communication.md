# 03 - Service Communication

## Stratégie

- **Synchrone REST**: requêtes de consultation et commandes nécessitant feedback immédiat.
- **Asynchrone Kafka**: transitions de saga, notifications, découplage temporel.

## Contrats

- API REST versionnées (`/api/v1/...`) pour exposition externe via Gateway.
- Événements de domaine versionnés (`eventType`, `eventVersion`, `occurredAt`, `correlationId`).

## Garanties réalistes

- Livraison **at-least-once** côté Kafka consommateur.
- Idempotence obligatoire côté consommateurs.
- Ordering garanti **par partition**, pas global.

## Erreurs classiques en entretien

- Confondre "exactly-once Kafka" avec garantie métier end-to-end.
- Oublier le traitement des doublons applicatifs.
- Coupler fortement payload événementiel et modèle de persistance interne.
