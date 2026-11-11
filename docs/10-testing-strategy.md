# 10 - Testing Strategy

## Principe

La stratégie privilégie la **fiabilité des scénarios critiques** plutôt que la simple couverture.

## Pyramide de tests appliquée

- **Unitaires**: logique pure (status, retry, outbox, idempotence).
- **Intégration Spring**: sécurité API key, contexte, endpoints clés.
- **Intégration système locale**: build multi-modules Maven + comportements inter-services simulés.
- **Contrat/résilience/kafka réel**: prévu pour itérations d’industrialisation.

## Références actuelles

- `OrderServiceTest`: saga locale, compensation, outbox.
- `PaymentAuthorizationRetrierTest`: retry exponentiel + jitter.
- `OrderSecurityIntegrationTest`: endpoints protégés via `X-API-KEY`.
- `OrderCorrelationIdIntegrationTest`: propagation `X-Correlation-Id`.
- `NotificationServiceTest`: idempotence consumer.
- `NotificationSecurityIntegrationTest`: sécurité notification.
- `NotificationCorrelationIdIntegrationTest`: corrélation côté notification.

## Critères de qualité d’un test

1. Assertion sur un comportement métier observable.
2. Test déterministe (pas d’aléatoire non contrôlé).
3. Échec lisible.
4. Coût d’exécution compatible CI.

## Anti-patterns évités

- Assertion uniquement sur “pas d’exception”.
- Mocking excessif qui invalide la valeur métier du test.
- Couplage du test à des détails d’implémentation sans valeur contractuelle.

## Entretien: message clé

> “Je couple stratégie de test et risque business: sécurité, cohérence de commande et résilience de paiement sont testés en priorité, avant de viser du volume de couverture.”
