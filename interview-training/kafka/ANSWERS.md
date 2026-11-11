# Kafka - Answers

## 1) Gérer les doublons via idempotence applicative.
En at-least-once, le doublon est normal, pas une anomalie.  
La vraie robustesse vient d’un traitement idempotent (clé métier stable, vérification de déjà-traité, side effects sûrs).

## 2) Isoler messages irrécupérables sans bloquer le flux principal.
La DLQ évite de figer une partition à cause d’un message poison.  
Point entretien important: une DLQ n’est utile que si un process de triage/replay existe.

## 3) Selon invariant d’ordre métier (souvent `orderId`).
La clé de partition doit refléter où l’ordre est indispensable.  
Tu arbitres entre ordre local strict et parallélisme: une clé trop large réduit le throughput, trop fine casse l’ordre utile.

## 4) Éviter divergence entre commit DB et publication d’événement.
Sans outbox, crash entre “write DB” et “publish” crée une incohérence durable.  
Avec outbox, tu sécurises l’intention métier localement puis publies de façon rejouable.
