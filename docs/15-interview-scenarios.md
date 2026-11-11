# 15 - Interview Scenarios

Scénarios d’entretien orientés incident/réalité production.  
Format: **Contexte | Raisonnement attendu | Réponse modèle | Pièges | Relances**.

1. **Paiement distribué en échec intermittent** | Identifier transitoire vs permanent, vérifier retries et saturation aval. | “Je borne retries avec jitter, j’ajoute breaker/timeout, je protège la confirmation de commande.” | Retry infini sans télémétrie. | Comment éviter surcharge fournisseur ?
2. **Panne Kafka partielle (broker indisponible)** | Évaluer backlog, impact commandes, stratégie degradation. | “Je bascule sur outbox locale durable et reprends publication après rétablissement.” | Arrêter tous les flows inutilement. | Comment préserver ordre métier ?
3. **Lenteur SQL brutale** | EXPLAIN ANALYZE, locks, plan regressions. | “Je valide index/plans avant rollback version query.” | Ajouter index aveugle. | Quelle métrique DB prioritaire ?
4. **Deadlock récurrent sur stock** | Identifier ordre de verrous et réduire fenêtre transactionnelle. | “Je rends l’ordre d’acquisition déterministe + retry borné.” | Augmenter timeouts seulement. | Quand passer optimistic locking ?
5. **OutOfMemoryError en prod** | Distinguer heap/metaspace, dump + JFR. | “Je stabilise, capture preuves, corrige fuite puis test de non-régression.” | Redémarrer sans preuve. | Quel guardrail mettre ensuite ?
6. **Double réservation de stock** | Vérifier idempotence commande et verrouillage réservation. | “Je protège la clé métier (`orderId`,`sku`) et rends compensation idempotente.” | Corriger uniquement côté UI. | Comment prouver le fix ?
7. **Service inventory indisponible** | Dégrader checkout ou queue commande selon SLA. | “Je priorise expérience client avec état pending explicite et reprise async.” | Hard fail global immédiat. | Quel timeout acceptable ?
8. **Migration de base sans downtime** | Compatibilité backward schema-first. | “Expand/contract, dual-read éventuel, puis cleanup.” | Changement destructif direct. | Rollback de migration ?
9. **Incident production après release** | Triage impact, rollback/canary stop, postmortem. | “Je limite blast radius avant RCA complète.” | Chercher coupable. | Quels indicateurs déclenchent rollback ?
10. **Refactoring monolithe vers microservices** | Domain boundaries d’abord, pas extraction technique. | “Je pars d’un monolithe modulaire puis extrais les contextes à forte friction.” | Découpage par couches techniques. | Signaux de readiness ?
11. **Désaccord architecture fort** | Critères de décision explicites, ADR. | “Je timebox l’analyse et tranche sur risque/coût/réversibilité.” | Débat d’autorité. | Quand escalader ?
12. **Dette technique critique vs roadmap produit** | Chiffrer risque business de la dette. | “J’utilise error budget et coût d’inaction pour prioriser.” | Opposer produit vs tech de façon binaire. | Exemple concret de compromis ?
13. **Mentoring développeur senior bloqué** | Coaching sur raisonnement et ownership. | “Je demande alternatives argumentées + plan d’expérimentation court.” | Donner la solution directement. | Comment mesurer progression ?
14. **Consumer Kafka duplique des notifications** | Vérifier clé idempotence persistante. | “Je stocke `eventId` durablement et je rends send side-effect safe.” | Dépendre de l’ordre broker seul. | TTL de déduplication ?
15. **Pic trafic x5 en 10 minutes** | Backpressure, autoscaling, priorisation endpoints. | “Je protège checkout, limite non-critique, augmente capacité contrôlée.” | Scale aveugle sans goulot identifié. | Quel composant scale d’abord ?
16. **Rotation de clé de sécurité** | Dual-key window, observabilité erreur auth. | “Je supporte ancienne+nouvelle clé pendant fenêtre de transition.” | Rotation big-bang. | Comment auditer adoption ?
17. **Prometheus montre hausse erreurs sans logs clairs** | Corréler métriques/traces/correlation ID. | “Je pivote par `X-Correlation-Id` pour reconstruire parcours.” | Lire logs isolément. | Quelles dimensions ajouter ?
18. **Canary release dégrade P99** | Stop canary, comparer diff config/runtime. | “Je rollback ciblé puis j’investigue profile CPU/IO.” | Continuer rollout “pour confirmer”. | Seuil auto-stop ?
19. **Conflit entre conformité sécurité et time-to-market** | Classifier risques bloquants vs mitigables. | “Je sécurise le non-négociable et planifie compensations datées.” | Accepter risque sans trace. | Qui signe l’acceptation de risque ?
20. **Préparer pitch 5 minutes de ce projet** | Structurer problème, choix, compromis, résultats. | “Je raconte phase par phase: saga/outbox/idempotence/sécurité/observabilité et limites assumées.” | Démo code brute sans message business. | Quelle preuve d’impact fournir ?
