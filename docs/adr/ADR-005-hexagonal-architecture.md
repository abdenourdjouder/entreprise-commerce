# ADR-005 - Architecture hexagonale par service

## Contexte
Le projet vise testabilité et clarté des frontières.
## Problème
Éviter que le domaine dépende des frameworks.
## Contraintes
Code pédagogique, évolutif, testable.
## Options évaluées
Layered classique, clean architecture stricte, hexagonal.
## Décision retenue
Hexagonal architecture pragmatique.
## Justification
Ports/adapters explicites, isolation du domaine, facilité de tests unitaires.
## Avantages
Remplaçabilité des adapters, lisibilité des dépendances.
## Inconvénients
Boilerplate supplémentaire.
## Conséquences
Conventions strictes de packages et mapping DTO.
## Révision possible
Si sur-abstraction sans gain réel, simplifier certains adapters.
