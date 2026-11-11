# 17 - Glossary (FR/EN)

Glossaire vivant des termes architecture, Java/JVM, Spring, Kafka, SRE et leadership technique.

- **Bounded Context**: frontière métier autonome / *business boundary with explicit ownership*.
- **Aggregate**: cluster cohérent modifié de manière atomique / *consistency boundary*.
- **Saga**: orchestration de transactions locales avec compensation / *distributed transaction pattern*.
- **Transactional Outbox**: écriture atomique business + événement à publier / *reliable event publication bridge*.
- **Idempotence**: même effet malgré répétition / *safe reprocessing*.
- **Eventually Consistent**: convergence différée mais déterministe / *asynchronous consistency model*.
- **Optimistic Locking**: contrôle de version sans verrou bloquant / *version-based concurrency control*.
- **Pessimistic Locking**: verrou explicite des lignes / *lock-first concurrency control*.
- **Retry with Jitter**: relance avec délai randomisé / *backoff to prevent synchronized retries*.
- **Circuit Breaker**: coupure temporaire d’un appel instable / *fail-fast protective pattern*.
- **Bulkhead**: isolation de ressources / *fault isolation boundary*.
- **Correlation ID**: identifiant de trace de bout en bout / *cross-service request trace key*.
- **SLI/SLO/SLA**: indicateur/objectif/engagement de service / *service reliability contract layers*.
- **Runbook**: procédure opérationnelle standard / *operational playbook*.
- **Postmortem**: analyse d’incident orientée amélioration système / *blameless incident review*.
