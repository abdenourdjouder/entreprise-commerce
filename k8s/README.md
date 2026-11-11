# Kubernetes (pédagogique)

## Manifests actuellement fournis

- `order-service-configmap.yaml`
- `order-service-deployment.yaml`
- `order-service-service.yaml`
- `notification-service-configmap.yaml`
- `notification-service-deployment.yaml`
- `notification-service-service.yaml`

## Concepts illustrés

- ConfigMap pour la configuration externe (API key et paramètres de retry).
- Readiness et liveness probes sur `/actuator/health`.
- Requests/limits de ressources.
- Séparation Deployment/Service.

## Application locale

```bash
kubectl apply -f k8s/order-service-configmap.yaml
kubectl apply -f k8s/order-service-deployment.yaml
kubectl apply -f k8s/order-service-service.yaml
kubectl apply -f k8s/notification-service-configmap.yaml
kubectl apply -f k8s/notification-service-deployment.yaml
kubectl apply -f k8s/notification-service-service.yaml
```

## Limites assumées

- Manifests orientés démonstration (pas de Secret manager, HPA ni canary actif).
- Les images référencées (`ghcr.io/...`) sont des placeholders tant que la publication registry n’est pas branchée.
