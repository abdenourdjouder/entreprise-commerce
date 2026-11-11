# ADR-009 - Stratégie de déploiement progressive

## Contexte
Objectif: local reproducible + chemin vers production.
## Problème
Livrer vite sans masquer les enjeux de prod.
## Contraintes
Pédagogie, coût setup raisonnable.
## Options évaluées
Kubernetes-only, Docker Compose-only, progression Compose -> K8s.
## Décision retenue
Docker Compose en local, manifests K8s pédagogiques ensuite.
## Justification
Accélère onboarding tout en préparant CI/CD et pratiques production.
## Avantages
Itération rapide, reproductibilité.
## Inconvénients
Écart local/prod possible.
## Conséquences
Documenter explicitement les écarts et hypothèses d’exploitation.
## Révision possible
Si environnement d’équipe standardisé K8s, basculer plus tôt sur cluster dev.
