# ADR-004 - Redis cache-aside ciblé

## Contexte
Le catalogue produit est lecture-dominant.
## Problème
Réduire latence et charge base sans briser cohérence métier.
## Contraintes
Simplicité d’invalidation, coût ops limité.
## Options évaluées
Pas de cache, cache local, Redis distribué.
## Décision retenue
Redis en cache-aside uniquement sur lectures produit ciblées.
## Justification
Gains rapides sur endpoints chauds, complexité contenue.
## Avantages
Performance, absorption des pics.
## Inconvénients
Données potentiellement périmées, invalidation délicate.
## Conséquences
TTL + invalidation événementielle; ne pas cacher les invariants critiques de commande.
## Révision possible
Si stampede ou incohérences fréquentes, réduire périmètre cache.
