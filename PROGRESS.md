# PROGRESS - Enterprise Commerce Platform

## Phase 1 - Architecture et cadrage

### Statut global
- État: Terminé
- Date: 2026-11-11

### Réalisé
- Squelette Maven multi-modules initial.
- Découpage microservices et bounded contexts documentés.
- Diagrammes Mermaid de contexte et flux métier.
- ADR initiaux créés.
- Guides d’apprentissage par service initialisés.

## Phase 2 - Parent Maven + infrastructure locale

### Statut global
- État: Terminé
- Date: 2026-11-11

### Réalisé
- Parent Maven complet avec BOM Spring et versions de référence.
- Structure de modules alignée sur l’architecture cible.
- Fichier `infra/docker-compose.yml` avec PostgreSQL, Redis, ZooKeeper et Kafka.
- Répertoires `k8s/` et `interview-training/` préparés.

## Phase 3 - Bootstraps métier et parcours de commande

### Statut global
- État: En cours
- Date: 2026-11-11

### Réalisé
- Domain model minimal et fonctionnel par service.
- Contrôleurs REST pour clients, produits, stock, paiements, notifications et commandes.
- Gestion mémoire des données pour illustrer le flux de commande sans dépendance externe.
- Tests unitaires de domaine et de service exécutés.

### Flux démontrés
- Création client.
- Consultation catalogue produits.
- Réservation stock.
- Paiement simulé.
- Commande confirmée.
- Annulation avec libération de stock.

### Validation exécutée
- `mvn -q test` ✅

### Limites connues
- Les services restent en mode mémoire pour garder le projet simple et testable sans dépendances externes.
- L’intégration PostgreSQL, Kafka et Redis sera ajoutée dans la phase suivante.
