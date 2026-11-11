# 09 - Observability and Production

## Objectif de la phase courante

Rendre les services traçables et mesurables en local avant industrialisation complète.

## Implémentation actuelle

### Correlation IDs

- Filtre `CorrelationIdFilter` ajouté dans:
  - `order-service`
  - `notification-service`
- Comportement:
  - lit `X-Correlation-Id` si présent,
  - génère un UUID sinon,
  - renvoie systématiquement `X-Correlation-Id` en réponse.
- Le `correlationId` est poussé dans le MDC et exposé dans le pattern de logs.

### Logs structurés (console)

- Ajout de `logback-spring.xml` avec pattern homogène:
  - timestamp,
  - level,
  - logger,
  - `corr=<correlationId>`.

### Métriques Prometheus

- Ajout de `micrometer-registry-prometheus` sur order/notification services.
- Exposition Actuator:
  - `/actuator/health`
  - `/actuator/info`
  - `/actuator/prometheus`
- Tag applicatif configuré via `management.metrics.tags.application`.

## Tests d’observabilité

- `OrderCorrelationIdIntegrationTest`
- `NotificationCorrelationIdIntegrationTest`

## Limites et suite

- Tracing distribué OpenTelemetry non branché dans cette itération.
- SLI/SLO, alerting et postmortem templates restent à compléter en phase d’industrialisation.
