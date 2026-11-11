# ADR-006 - Saga orchestrée pour le parcours commande

## Contexte
Création de commande = stock + paiement + confirmation.
## Problème
Pas de transaction ACID distribuée inter-services.
## Contraintes
Cohérence métier, compensation explicite, observabilité.
## Options évaluées
2PC, saga chorégraphiée, saga orchestrée.
## Décision retenue
Saga orchestrée via Order Service.
## Justification
Visibilité centrale de l’état de workflow et règles de compensation.
## Avantages
Lisibilité, auditabilité, gouvernance.
## Inconvénients
Risque de centralisation et complexité d’orchestrateur.
## Conséquences
Machine d’état explicite + retries bornés + DLQ.
## Révision possible
Évoluer vers chorégraphie partielle si couplage orchestration trop fort.
