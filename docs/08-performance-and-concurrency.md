# 08 - Performance and Concurrency

## Objectif de la phase courante

Introduire une première résilience applicative sur le chemin critique de paiement.

## Implémentation actuelle

### Retry exponentiel avec jitter (`order-service`)

- Classe: `PaymentAuthorizationRetrier`.
- Usage: `OrderService#createOrder` encapsule l’autorisation de paiement dans ce retrier.
- Paramètres configurables:
  - `resilience.payment.retry.max-attempts`
  - `resilience.payment.retry.initial-delay-ms`
  - `resilience.payment.retry.max-delay-ms`
  - `resilience.payment.retry.jitter-factor`

### Comportement

1. Tentative d’autorisation.
2. En cas d’échec transitoire (`RuntimeException`), attente avec backoff exponentiel + jitter.
3. Nouvelle tentative jusqu’à `max-attempts`.
4. Si toutes les tentatives échouent, la saga de commande passe en échec avec compensation stock.

## Tests de résilience

- `PaymentAuthorizationRetrierTest`:
  - vérifie la progression de backoff.
  - vérifie l’échec après le nombre maximal de tentatives.
- `OrderServiceTest#createOrderShouldRetryPaymentAuthorizationBeforeSuccess`:
  - simule un gateway paiement instable,
  - confirme la réussite de commande après retries.

## Limites connues

- Retry local intra-processus, sans circuit breaker ni timeout distribué.
- Les politiques de retry ne sont pas encore externalisées par endpoint/consumer.
- L’étape suivante ajoutera timeout, circuit breaker, DLQ et métriques de retry.
