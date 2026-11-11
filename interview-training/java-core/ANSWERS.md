# Java Core - Answers

## 1) `record` est idéal pour les objets data-carrier immuables.
En entretien: explique que `record` exprime une intention de **donnée** (value object) avec immutabilité, `equals/hashCode` et `toString` générés.  
Bon choix pour des payloads (`Order`, `OutboxEvent`), moins pour des objets avec cycle de vie complexe et invariants évolutifs.

## 2) Un mauvais contrat `equals/hashCode` casse la logique de déduplication.
Le point clé: `HashMap`/`HashSet` reposent sur ce contrat pour retrouver les objets dans les bons buckets.  
Si le contrat est incohérent, tu obtiens des doublons invisibles, des suppressions ratées, et une idempotence qui “semble” marcher mais échoue en production.

## 3) `Optional` est surtout un type de retour, pas un champ de domaine.
`Optional` est excellent pour rendre l’absence explicite à l’appelant.  
Comme champ d’objet métier, il ajoute souvent du bruit (sérialisation, mapping, lisibilité) sans bénéfice réel; mieux vaut un modèle métier clair avec invariants explicites.

## 4) L’atomicité technique ne remplace pas l’atomicité métier.
Une opération atomique sur une structure (`ConcurrentHashMap`) ne garantit pas qu’un **workflow métier** complet est atomique.  
La réponse senior: distinguer atomicité locale, cohérence métier globale, et compléter avec saga, compensation et idempotence.
