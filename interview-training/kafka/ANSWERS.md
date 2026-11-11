# Kafka - Answers

1. Gérer les doublons via idempotence applicative.
2. Isoler messages irrécupérables sans bloquer le flux principal.
3. Selon invariant d’ordre métier (souvent `orderId`).
4. Éviter divergence entre commit DB et publication d’événement.

