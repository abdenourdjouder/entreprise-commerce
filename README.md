# Enterprise Commerce Platform

Projet **professionnel et pédagogique** pour préparation entretiens **Java Senior / Tech Lead** via une plateforme e-commerce microservices.

## État actuel

- **Phase active**: Phase 1 (architecture et cadrage).
- **Implémentation métier**: non démarrée.
- **Objectif**: figer l’architecture, les frontières, la stack et les ADR avant développement.

## Architecture cible

```mermaid
flowchart LR
    C[Client Web/Mobile]
    G[API Gateway]
    CS[Customer Service]
    PS[Product Service]
    OS[Order Service]
    IS[Inventory Service]
    PAY[Payment Service]
    NS[Notification Service]
    K[(Kafka)]
    R[(Redis)]
    DB1[(PostgreSQL Customer)]
    DB2[(PostgreSQL Product)]
    DB3[(PostgreSQL Order)]
    DB4[(PostgreSQL Inventory)]
    DB5[(PostgreSQL Payment)]
    DB6[(PostgreSQL Notification)]

    C --> G
    G --> CS
    G --> PS
    G --> OS
    OS --> IS
    OS --> PAY
    OS --> K
    IS --> K
    PAY --> K
    NS --> K
    CS --> DB1
    PS --> DB2
    OS --> DB3
    IS --> DB4
    PAY --> DB5
    NS --> DB6
    PS --- R
```

## Arborescence

```text
.
├── docs/
│   ├── adr/
│   └── *.md
├── infra/
│   └── docker-compose.yml
├── interview-training/
├── k8s/
├── services/
│   ├── api-gateway/
│   ├── customer-service/
│   ├── product-service/
│   ├── order-service/
│   ├── inventory-service/
│   ├── payment-service/
│   └── notification-service/
├── shared/
│   └── common-kernel/
├── PROGRESS.md
└── pom.xml
```

## Stack retenue (référence Phase 1)

- Java 21 (compilation), Java 25 (comparaison LTS moderne).
- Spring Boot 3.5.x (ligne stable), Maven multi-modules.
- PostgreSQL, Kafka, Redis, Docker Compose, Kubernetes.
- JUnit 5, Mockito, AssertJ, Testcontainers, WireMock, JaCoCo.

## Documents clés

- `PROGRESS.md`
- `docs/00-learning-roadmap.md`
- `docs/02-architecture-overview.md`
- `docs/03-service-communication.md`
- `docs/04-order-lifecycle.md`
- `docs/12-design-decisions.md`
- `docs/16-java-version-guide.md`
- `docs/adr/*.md`

## Lancement

Le code applicatif n’est pas encore implémenté. Pour l’instant:

```bash
mvn -q validate
```
