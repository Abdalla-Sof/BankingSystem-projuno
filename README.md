# Banking System — Account Service

A microservices-based banking system built with Java and Spring Boot.
This repository contains the **Account Service** — the first service in the system.

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok

---

## Project Structure

```
src/
└── main/java/com/banking/accountservice/
    ├── controller/        # REST API endpoints
    ├── dto/               # Request and response objects
    ├── entity/            # Database entities
    ├── exception/         # Custom exceptions and global error handler
    ├── repository/        # Database access layer
    └── service/           # Business logic
```

---

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| `POST` | `/api/accounts` | Create a new account |
| `GET` | `/api/accounts/{id}` | Get account by ID |
| `GET` | `/api/accounts/number/{accountNumber}` | Get account by account number |

### Create Account — Request Body

```json
{
  "ownerName": "Abdullahi Abdulkadir",
  "accountType": "SAVINGS",
  "initialBalance": 1000.00
}
```

### Account Types

- `SAVINGS`
- `CURRENT`

---

## How to Run

1. Make sure PostgreSQL is running and create a database named `banking_db`
2. Update `src/main/resources/application.properties` with your DB credentials
3. Run the application:

```bash
./mvnw spring-boot:run
```

The service starts on `http://localhost:8080`

---

## Author

Abdullahi Abdulkadir