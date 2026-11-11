# 13 - Tech Lead Handbook

## 1) Arbitrage architecture vs delivery

- Démarrer simple mais extensible.
- Différer la complexité tant que le risque n’est pas prouvé.
- Garder une trace des compromis (ADR + PROGRESS).

## 2) Dette technique

- Dette assumée: in-memory pour apprendre vite.
- Plan de remboursement explicite: persistance durable, Kafka réel, DLQ, tracing distribué.
- Éviter la dette “invisible”: documenter ce qui est non production-ready.

## 3) Code review (niveau Staff)

Checklist:
1. Lisibilité métier (intention claire).
2. Robustesse (erreurs explicites, pas de silent fallback).
3. Exploitabilité (logs, métriques, corrélation).
4. Testabilité.
5. Impact ops (coût runtime, blast radius).

## 4) Mentoring

- Exiger l’explication du “pourquoi”, pas juste du “comment”.
- Demander une alternative rejetée + justification.
- Transformer chaque PR en capsule d’apprentissage réutilisable.

## 5) Incident management

- Contenir rapidement l’impact client.
- Restaurer le service avant l’optimisation parfaite.
- Produire un postmortem orienté système (pas blâme individuel).

## 6) Décision en désaccord d’architecture

1. Clarifier les contraintes non négociables.
2. Formuler 2-3 options comparables.
3. Décider sur critères explicites (risque, coût, délai, réversibilité).
4. Timebox de réévaluation.

## 7) Message entretien Tech Lead

> “Un Tech Lead ne choisit pas la solution la plus élégante en absolu, mais celle qui maximise la valeur sous contraintes et reste opérable en production.”
