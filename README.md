# Shipping Quote API

A production-style Java REST service that grew out of an early console shipping calculator exercise.

The original exercise is preserved in `legacy/ShippingCost.java`. The current application demonstrates the practices I would use on a real backend service: layered design, input validation, relational persistence, schema migrations, automated tests, CI, and containerization.

## Why this project exists

I wanted one portfolio project that shows more than syntax. This version turns a small calculation problem into a maintainable service with an HTTP API and durable quote history.

## Stack

- Java 21
- Spring Boot
- Spring Web
- Jakarta Bean Validation
- Spring Data JPA
- H2 for local development/testing
- PostgreSQL-ready production configuration
- Flyway database migrations
- JUnit / Spring Boot Test / MockMvc
- Maven
- Docker
- GitHub Actions

## API

### Create a quote

```http
POST /api/quotes
Content-Type: application/json

{
  "weightPounds": 5,
  "serviceLevel": "STANDARD"
}
```

Example response:

```json
{
  "id": 1,
  "weightPounds": 5.00,
  "serviceLevel": "STANDARD",
  "baseCost": 14.32,
  "tax": 2.15,
  "total": 16.47,
  "createdAt": "2026-09-29T14:00:00Z"
}
```

### Recent quotes

```http
GET /api/quotes
```

Returns the 20 most recent persisted quotes.

## Design notes

The application is split into API, service, and persistence layers. Money is calculated with `BigDecimal` rather than floating-point values. Incoming payloads are validated before business logic executes, and invalid requests return structured HTTP problem responses.

Flyway owns the database schema. The default profile uses H2 in PostgreSQL compatibility mode so the application runs locally without external infrastructure. The `prod` profile accepts PostgreSQL connection settings from environment variables.

## Run locally

Requirements: Java 21 and Maven.

```bash
mvn spring-boot:run
```

Then create a quote:

```bash
curl -X POST http://localhost:8080/api/quotes \
  -H "Content-Type: application/json" \
  -d '{"weightPounds":5,"serviceLevel":"STANDARD"}'
```

## Test

```bash
mvn verify
```

The test suite covers business calculations, persistence, request validation, and the HTTP layer.

## Docker

```bash
docker build -t shipping-quote-api .
docker run -p 8080:8080 shipping-quote-api
```

## Production database

Run with the `prod` profile and provide:

- `DATABASE_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

Example:

```bash
java -jar app.jar --spring.profiles.active=prod
```

## Engineering practices demonstrated

- REST API design
- Layered application architecture
- SQL-backed persistence
- Database migrations
- Validation and error handling
- Automated unit/integration testing
- CI on pull requests and main
- Docker-based packaging
- Git branch / pull-request workflow

## Project history

This repository began as a small Java coursework exercise. I intentionally preserved the original implementation under `legacy/` so the repository shows the progression from basic control flow to a structured backend service.
