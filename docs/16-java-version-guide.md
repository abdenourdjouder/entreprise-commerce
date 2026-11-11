# 16 - Java Version Guide (8/11/17/21/25)

## Position projet

- **Compilation de référence**: Java 21 (LTS mature).
- **Comparatif complémentaire**: Java 25 (LTS récente) pour évaluer impacts.
- **Règle**: pas de preview par défaut.

## Différences pédagogiques à couvrir

- Java 8 -> streams, lambdas, Optional.
- Java 11 -> HTTP client, améliorations GC/JVM.
- Java 17 -> sealed classes, records finalisés.
- Java 21 -> virtual threads, pattern matching/switch consolidés.
- Java 25 -> évolutions de performance/JIT/GC et APIs finalisées (à documenter avec impact réel dans le projet).

## Discipline d’adoption

- Introduire une nouveauté seulement si elle résout un problème concret.
- Documenter compatibilité, coût de migration, observabilité et risques production.
