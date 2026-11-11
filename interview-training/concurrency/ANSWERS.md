# Concurrency - Answers

## 1) Non, il protège la structure, pas toutes les séquences métier.
`ConcurrentHashMap` évite la corruption de la map, mais pas les races entre plusieurs étapes métier (“check then act”).  
En entretien, insiste sur les invariants métier: parfois il faut combiner opérations atomiques, verrous ciblés, ou redesign du flux.

## 2) Pour éviter les rafales synchronisées après panne.
Sans jitter, tous les clients réessaient au même moment et recréent une surcharge (“thundering herd”).  
Le jitter répartit les retries dans le temps, ce qui améliore stabilité et temps de récupération.

## 3) En rendant l’aléatoire injectable et les assertions déterministes.
Un test concurrent fiable contrôle les sources de non-déterminisme (horloge, random, sleep).  
Réponse senior: privilégier tests reproductibles sur propriétés métier plutôt que timings fragiles.

## 4) Le lock prévient conflit en amont, l’idempotence absorbe répétitions en aval.
Locking = prévention de conflit sur ressource partagée. Idempotence = sécurité face aux retries/doublons.  
Les deux ne s’opposent pas: en systèmes distribués, ils couvrent des risques différents et se complètent.
