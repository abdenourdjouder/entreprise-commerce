# ADR-007 - Transactional Outbox

## Contexte
Publier un événement juste après commit métier.
## Problème
Éviter la perte d’événement en cas de crash entre DB commit et publish Kafka.
## Contraintes
Fiabilité, simplicité pédagogique, rejouabilité.
## Options évaluées
Dual-write naïf, CDC Debezium, transactional outbox.
## Décision retenue
Transactional outbox (poller applicatif en première étape).
## Justification
Garantit "write business + write outbox" atomique localement.
## Avantages
Robustesse supérieure au dual-write.
## Inconvénients
Complexité de polling, duplication potentielle à la publication.
## Conséquences
Consommateurs idempotents obligatoires.
## Révision possible
Passage à CDC si montée en volume et besoin d’industrialisation.
