# 07 - Security

## Objectif de la phase courante

Mettre en place une sécurité **exécutable et testable** avant OAuth2/OIDC complet:
- authentification machine-to-machine par API key,
- endpoints métier protégés,
- endpoints d’observabilité accessibles sans authentification.

## Implémentation actuelle

### `order-service`
- Dépendance `spring-boot-starter-security`.
- Filtre `ApiKeyAuthenticationFilter`:
  - exige l’en-tête `X-API-KEY` sur `/api/**`,
  - renvoie `401` si la clé est absente ou invalide.
- `SecurityConfig`:
  - `permitAll` pour `/`, `/actuator/health`, `/actuator/info`,
  - authentification obligatoire sur `/api/**`,
  - session stateless.

### `notification-service`
- Même pattern API key + session stateless.
- Protection des routes de notification, y compris la consommation idempotente.

## Configuration

Clé locale de développement:
```yaml
security:
  api-key: local-dev-api-key
```

## Tests de sécurité

- `OrderSecurityIntegrationTest`:
  - sans clé -> `401`,
  - avec clé -> `200`.
- `NotificationSecurityIntegrationTest`:
  - sans clé -> `401`,
  - avec clé -> `200`.

## Limites et suite prévue

- Ce mécanisme API key est pédagogique, utile pour sécuriser rapidement les flux inter-services en local.
- La cible de production reste OAuth2/OIDC + JWT, gestion de secrets (Vault/KMS), rotation de clés et RBAC fin.
