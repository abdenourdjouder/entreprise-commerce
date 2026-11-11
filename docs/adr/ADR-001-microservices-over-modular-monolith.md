# ADR-001 - Microservices plutôt que monolithe modulaire

## Contexte
Le projet cible la préparation Tech Lead sur systèmes distribués.

## Problème
Choisir une architecture qui expose les vrais compromis de production.

## Contraintes
Lisibilité pédagogique, coût opérationnel acceptable en local, traçabilité des décisions.

## Options évaluées
1. Monolithe modulaire.
2. Microservices ciblés.

## Décision retenue
Microservices ciblés (7 services métier + gateway).

## Justification
Permet de démontrer saga, outbox, cohérence éventuelle, idempotence, observabilité distribuée.

## Avantages
Frontières explicites, indépendance de déploiement, support entretien réaliste.

## Inconvénients
Complexité ops et tests E2E plus élevée.

## Conséquences
Nécessite standards forts: contrats, logs corrélés, stratégie de résilience.

## Révision possible
Si le coût de maintenance dépasse la valeur pédagogique, retour vers monolithe modulaire démonstratif.
