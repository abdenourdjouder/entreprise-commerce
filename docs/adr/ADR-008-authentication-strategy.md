# ADR-008 - OAuth2/OIDC + JWT via API Gateway

## Contexte
Le projet doit couvrir sécurité moderne API et RBAC.
## Problème
Centraliser authentification tout en gardant autorisation locale fine.
## Contraintes
Simplicité locale, cohérence policy, pédagogie entretien.
## Options évaluées
Session stateful, JWT maison, OAuth2/OIDC standard.
## Décision retenue
OAuth2/OIDC, validation JWT au niveau Gateway et services.
## Justification
Standard industrie, extensible, bonne base pour discussion sécurité.
## Avantages
Interopérabilité, séparation IdP / API.
## Inconvénients
Complexité token lifecycle, clés, rotation, clock skew.
## Conséquences
Gestion stricte des claims, scopes, audiences.
## Révision possible
Si besoin B2B strict, ajouter mTLS/service-to-service hardening.
