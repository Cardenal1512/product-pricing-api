# Pricing Service – Hexagonal Architecture

**Purpose**  
Given a **date**, **product id**, and **brand id**, return the **applicable price**
according to business rules (date range + highest priority).

---

## 1. Tech Stack

| Layer        | Choice                                      | Notes                              |
|--------------|---------------------------------------------|------------------------------------|
| Language     | **Java 21**                                 |                                    |
| Framework    | **Spring Boot 3**                           |                                    |
| Mapping      | **MapStruct 1.5** (`componentModel=spring`) |                                    |
| Persistence  | Spring Data JPA + H2 (dev/tests)            |      |
| Testing      | JUnit 5, Mockito, AssertJ                   |                                    |


## 2. Architecture Overview

```
           ┌──────────────┐
           │   REST API   │         (infrastructure / input)
           └──────▲───────┘
                  │
        DTO       │
<──────────>  Mapper (MapStruct)
                  │
┌─────────────────┴──────────────────┐
│  GetApplicablePriceUseCase (APP)   │  (applitation)
└─────────────────▲──────────────────┘
                  │ Port
         PriceRepository
                  │
        DTO       │
<──────────>  Mapper (MapStruct)
     ┌────────────┴────────────┐
     │  JPA Adapter + Spec     │     (infrastructure / output)
     └─────────────────────────┘
                  │
             H2 / Postgres
```

* **Domain** `Price` (POJO)
* **Application** Use-case
* **Infrastructure** Adapters + mappers

Folder layout mirrors the hexagonal style: `domain/`, `application/`, `infrastructure/`.

---

## 3. Quick Start

```bash
# clone & run
git clone https://github.com/Cardenal1512/pricing-service.git
cd pricing-service
./mvnw spring-boot:run
```

Service is now listening on **`http://localhost:8080`**.

Example request:

```bash
curl -Gs \
  -d date="2020-06-14T16:00:00" \
  -d productId=35455 \
  -d brandId=1 \
  http://localhost:8080/prices
```

Expected response:

```json
{
  "brandId": 1,
  "productId": 35455,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate":   "2020-06-14T18:30:00",
  "priority":  1,
  "price":     25.45,
  "currency":  "EUR"
}
```

---

## 4. Build and Test

```bash
# compile + run tests
./mvnw clean verify
```

Coverage includes:

* **Application** – parameterised tests (`Stream<Arguments>`)
* **API** – `@WebMvcTest` unit for controller
* **Integration** – Spring Boot context + MockMvc

---

## 5. Conventional Commits

This repository follows <https://www.conventionalcommits.org>.


---

## License
MIT © 2025 Adrián Fernández Cardenal