# LEARNING GUIDE - Order Service

- **Responsabilité métier**: orchestration du cycle de commande.
- **Concepts illustrés (à implémenter)**: saga orchestrée, machine d’état, outbox.
- **Flux clé**: create order -> reserve stock -> payment -> confirm/cancel.
- **Questions entretien**: "Pourquoi pas un 2PC pour commande distribuée ?"
