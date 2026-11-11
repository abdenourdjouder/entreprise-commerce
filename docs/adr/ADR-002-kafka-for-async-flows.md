# ADR-002 - Kafka pour les flux asynchrones

## Contexte
Le cycle de commande implique plusieurs services et états intermédiaires.
## Problème
Éviter le couplage temporel fort du tout-synchrone.
## Contraintes
Rejouabilité, débit, ordering par clé métier.
## Options évaluées
REST synchrone uniquement, RabbitMQ, Kafka.
## Décision retenue
Kafka pour événements métier inter-services.
## Justification
Bon compromis throughput, rétention, reprocessing, consumer groups.
## Avantages
Découplage, reprise, DLQ, scaling horizontal.
## Inconvénients
Complexité opérationnelle et gouvernance des schémas.
## Conséquences
Idempotence obligatoire et monitoring des offsets/lag.
## Révision possible
Si les flux deviennent peu nombreux et strictement command/reply, simplification vers REST ciblé.
