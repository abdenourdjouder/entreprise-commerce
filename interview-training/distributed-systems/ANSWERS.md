# Distributed Systems - Answers

## 1) 2PC augmente couplage et fragilité opérationnelle.
2PC peut sembler “propre” théoriquement, mais coûte cher en disponibilité et en opérations.  
En microservices, saga + compensation est souvent plus pragmatique et plus observable.

## 2) ACID local + événements + orchestration + idempotence.
Tu garantis fort dans chaque service, puis tu coordonnes globalement par workflow distribué.  
C’est un changement de modèle mental: cohérence immédiate locale, cohérence globale convergente.

## 3) Revenir à un état métier valide après échec partiel.
La compensation ne “supprime” pas le passé; elle applique une action inverse métier traçable.  
Réponse senior: compensation idempotente, auditée, et testée sur scénarios de répétition.

## 4) CAP impose un compromis explicite.
En cas de partition réseau, tu choisis entre disponibilité et cohérence forte selon le besoin métier.  
En entretien, évite CAP théorique pur: donne un exemple concret (checkout, paiement, stock).
