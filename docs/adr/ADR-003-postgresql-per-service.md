# ADR-003 - PostgreSQL par service

## Contexte
Chaque bounded context doit contrôler ses invariants métier.
## Problème
Éviter le couplage via base partagée.
## Contraintes
Isolation logique, transactions locales robustes, simplicité locale.
## Options évaluées
Base partagée, schémas partagés, base/schéma par service.
## Décision retenue
PostgreSQL avec séparation des données par service.
## Justification
Réduit les effets de bord inter-domaines et force les contrats explicites.
## Avantages
Autonomie, sécurité des changements, ownership clair.
## Inconvénients
Reporting transversal plus complexe.
## Conséquences
Besoin d’événements d’intégration et de vues matérialisées de lecture si nécessaire.
## Révision possible
Si surcoût local trop fort, mutualiser l’instance en conservant séparation stricte par schéma.
