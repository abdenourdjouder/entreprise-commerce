# 12 - Design Decisions (synthèse)

## Décisions clés Phase 1

1. **Microservices bornés** plutôt que monolithe modulaire.
2. **Saga orchestrée** pour le parcours commande.
3. **Transactional Outbox** pour fiabiliser la publication d’événements.
4. **Kafka** pour flux asynchrones métier.
5. **PostgreSQL par service** pour isolation des données.
6. **Redis ciblé** (cache-aside catalogue), pas "cache partout".
7. **Hexagonal architecture** dans chaque service pour testabilité.
8. **OAuth2/OIDC + JWT** via Gateway pour sécurité API.

Les détails d’arbitrage sont documentés dans `docs/adr/`.
