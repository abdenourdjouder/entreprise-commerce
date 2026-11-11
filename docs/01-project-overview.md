# 01 - Project Overview

## Objectif
Construire une plateforme e-commerce microservices servant de:
- application fonctionnelle,
- laboratoire d’architecture d’entreprise,
- support de préparation entretiens Senior/Tech Lead.

## Périmètre fonctionnel initial
- Clients, catalogue produits, stock, commandes, paiement simulé, notifications.
- Scénarios nominal + échecs (stock insuffisant, timeout paiement, événement dupliqué).
- Traçabilité de bout en bout (correlation IDs, journaux d’événements, métriques Prometheus).
- Sécurité inter-services par API key (baseline locale avant OAuth2/OIDC).

## Hors périmètre
- Intégration bancaire réelle.
- Déploiement cloud vendor-specific en phase initiale.
- Garanties exactly-once globales (assumées irréalistes sans compromis lourds).
