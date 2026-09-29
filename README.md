# Shipping Quote API

![CI](https://github.com/timwmcqueen/ShippingCalculator/actions/workflows/ci.yml/badge.svg)

This started as a small Java shipping calculator and was rebuilt as a Spring Boot API with request validation, database storage, migrations, tests, and Docker.

The original console version is kept in `legacy/ShippingCost.java`.

## Stack

- Java 21
- Spring Boot
- Spring Web
- Jakarta Bean Validation
- Spring Data JPA
- H2 for local development/testing
- PostgreSQL-ready production configuration
- Flyway
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

Returns the 20 most recent saved quotes.

## Design

The application is split into API, service, and persistence layers.

Money is calculated with `BigDecimal`. Incoming requests are validated before calculations run, and invalid requests return structured error responses.

Flyway manages the database schema. The default profile uses H2 in PostgreSQL compatibility mode. The `prod` profile reads PostgreSQL connection settings from environment variables.

## Run locally

Requirements: Java 21 and Maven.

```bash
mvn spring-boot:run
```

Create a quote:

```bash
curl -X POST http://localhost:8080/api/quotes \
  -H "Content-Type: application/json" \
  -d '{"weightPounds":5,"serviceLevel":"STANDARD"}'
```

## Test

```bash
mvn verify
```

The tests cover quote calculations, persistence, request validation, and the HTTP layer.

## Docker

```bash
docker build -t shipping-quote-api .
docker run -p 8080:8080 shipping-quote-api
```

## PostgreSQL configuration

Run with the `prod` profile and provide:

- `DATABASE_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

```bash
java -jar app.jar --spring.profiles.active=prod
```
