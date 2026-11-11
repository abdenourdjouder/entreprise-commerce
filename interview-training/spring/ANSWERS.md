# Spring - Answers

## 1) Configuration explicite et composable.
`SecurityFilterChain` rend les règles lisibles (matchers, filtres, ordre).  
En entretien, explique que cette explicitation réduit les surprises en prod et facilite les revues sécurité.

## 2) L’appel interne contourne le proxy transactionnel.
`@Transactional` est appliqué par proxy Spring; un appel `this.method()` ne passe pas par le proxy.  
Réponse senior: extraire la méthode dans un autre bean ou repenser la frontière transactionnelle.

## 3) Auth explicite (API key/JWT), session stateless, règles endpoint strictes.
Pour des APIs M2M, stateless + politique d’accès explicite réduit le risque de dérive.  
Ensuite on fait évoluer vers OAuth2/OIDC selon besoin de scopes, identité utilisateur et audit.

## 4) Minimum utile: health/info/prometheus, plus via réseau interne.
Actuator apporte de la valeur ops, mais augmente la surface d’exposition.  
Bonne pratique: exposer le strict nécessaire publiquement, et réserver les endpoints sensibles à un réseau d’exploitation.
