# Hibernate - Answers

## 1) Profiling SQL + traces de requêtes répétitives.
Un N+1 se voit dans les logs SQL: une requête parent suivie de nombreuses requêtes quasi identiques.  
Réponse entretien: prouver avec métriques/trace, puis corriger par fetch join/entity graph selon le cas d’usage.

## 2) Charges lecture-dominantes et collisions rares.
L’optimistic locking est efficace quand les conflits sont exceptionnels et qu’on veut maximiser le débit.  
Il faut prévoir la gestion métier des conflits (`OptimisticLockException`) et une stratégie de retry ou de message utilisateur.

## 3) Car surcharge mémoire et requêtes inutiles.
`EAGER` global charge trop de données, augmente la pression mémoire et peut masquer des problèmes de design de requêtes.  
Approche senior: `LAZY` par défaut, puis chargement explicite selon contrat d’API.

## 4) Batching JDBC/Hibernate avec tailles contrôlées.
Le batch réduit les allers-retours DB et améliore le throughput d’écriture.  
Il faut calibrer la taille de batch, gérer les flush/clear, et surveiller mémoire + temps de transaction.
