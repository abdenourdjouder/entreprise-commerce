# LEARNING GUIDE - Order Service

- **Responsabilité métier**: orchestration du cycle de commande.
- **Concepts illustrés**: orchestration de saga locale, machine d’état explicite, journal d’événements métier en mémoire.
- **Flux clé**: create order -> reserve stock -> payment -> order confirmed; échec -> compensation stock + order failed.
- **Classes clés**:
  - `OrderService`: orchestration métier et transitions d’état.
  - `OrderEventStore`: historique des événements par commande (`ORDER_CREATED`, `STOCK_RESERVED`, `PAYMENT_AUTHORIZED`, etc.).
  - `OrderController`: endpoints `/api/orders`, `/api/orders/{id}`, `/api/orders/{id}/events`, `/api/orders/{id}/cancel`.
- **Tests associés**:
  - `OrderServiceTest#createOrderShouldReserveInventoryAndAuthorisePayment`
  - `OrderServiceTest#createOrderShouldFailWhenInventoryNotAvailableAndEmitFailureEvents`
  - `OrderServiceTest#cancelOrderShouldRefundAndReleaseStock`
- **Questions entretien**: "Pourquoi pas un 2PC pour commande distribuée ?"
