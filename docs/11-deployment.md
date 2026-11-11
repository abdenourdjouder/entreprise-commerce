# 11 - Deployment

## Objectif de la phase courante

Poser une chaîne CI/CD minimale cohérente avec l’état actuel du code.

## Implémentation actuelle

### Docker

- Dockerfiles ajoutés:
  - `services/order-service/Dockerfile`
  - `services/notification-service/Dockerfile`
- Packaging Maven puis build des images de service.

### Kubernetes

- Manifests fournis pour `order-service` et `notification-service`:
  - ConfigMap
  - Deployment
  - Service
- Probes readiness/liveness sur `/actuator/health`.
- Requests/limits CPU/Mémoire.

### GitHub Actions

- Workflow ajouté: `.github/workflows/ci.yml`
- Étapes:
  1. Setup Java 21 + cache Maven
  2. `mvn -q test`
  3. package des services order/notification
  4. build Docker images CI (`ecp/order-service:ci`, `ecp/notification-service:ci`)

## Limites assumées

- Pas de push registry automatique dans cette phase.
- Pas encore de stratégie Blue/Green / Canary active.
- Pas d’environnement Kubernetes dédié provisionné automatiquement.
