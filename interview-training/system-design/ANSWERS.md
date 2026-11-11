# System Design - Answers

## 1) Par responsabilité métier et cadence de changement.
Un découpage utile minimise le couplage et aligne les frontières sur les équipes.  
Signal d’un bon design: chaque service peut évoluer sans coordination permanente avec tous les autres.

## 2) Les validations critiques user-facing immédiates.
Garde en synchrone ce qui impacte directement l’expérience instantanée utilisateur.  
Le reste peut être asynchrone pour absorber la charge et réduire le couplage temporel.

## 3) Latence P95/P99, taux d’erreur, confirmation commande.
Choisis des indicateurs qui reflètent la valeur métier, pas seulement l’infra.  
Exemple: “taux de commande confirmée” parle mieux au produit qu’un CPU moyen.

## 4) Contrats stables, ADR, ownership clair, observabilité standardisée.
Scalabilité organisationnelle = autant un problème de gouvernance que de code.  
Réponse entretien attendue: stabilité des interfaces, décisions tracées et exploitation homogène.
