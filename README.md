# Finance Data Processing and Access Control Backend

A RESTful backend API built with Spring Boot for managing financial records with role-based access control. Built as part of a backend engineering assessment.

---

## Overview

This project implements a finance dashboard backend where different users interact with financial records based on their assigned role. The system supports user management, financial record CRUD operations, dashboard-level analytics, and enforces strict role-based access control across all endpoints.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4 |
| Security | Spring Security (HTTP Basic Auth) |
| Database | H2 (in-memory) |
| ORM | Spring Data JPA / Hibernate |
| Validation | Jakarta Bean Validation |
| Boilerplate | Lombok |

---

## Project Structure

```
src/main/java/com/finance/dashboard/
├── controller/
│   ├── AuthController.java
│   ├── DashboardController.java
│   ├── RecordController.java
│   └── UserController.java
├── exception/
│   ├── CustomException.java
│   └── GlobalExceptionHandler.java
├── model/
│   ├── FinancialRecord.java
│   ├── Role.java
│   └── User.java
├── repository/
│   ├── RecordRepository.java
│   └── UserRepository.java
├── security/
│   └── SecurityConfig.java
├── service/
│   ├── DashboardService.java
│   ├── RecordService.java
│   └── UserService.java
└── DashboardApplication.java
```

---

## Getting Started

### Prerequisites
- Java 25+
- Maven

### Run the application

```bash
git clone <your-repo-url>
cd Finance-Data-Processing-and-Access-Control-Backend
mvn spring-boot:run
```

The app starts on `http://localhost:8080`

### H2 Console (Database Viewer)

Access the in-memory database at:
```
http://localhost:8080/h2-console
```

| Field | Value |
|---|---|
| JDBC URL | `jdbc:h2:mem:app` |
| Username | `SA` |
| Password | *(leave blank)* |

---

## Roles

| Role | Permissions |
|---|---|
| `VIEWER` | No access to records or dashboard |
| `ANALYST` | Read records + view dashboard summaries |
| `ADMIN` | Full access — manage records and users |

---

## Authentication

This project uses HTTP Basic Auth. Send credentials with every request:

```
Authorization: Basic <base64(email:password)>
```

In Postman: Authorization tab → Basic Auth → enter email and `password` as the password.

> Note: All users share the fixed password `password` for simplicity in this assessment setup.

---

## API Reference

### Auth (Public)

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |

**Request body:**
```json
{
  "name": "Alice Admin",
  "email": "alice@finance.com",
  "role": "ADMIN"
}
```

---

### Financial Records

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/records` | ADMIN | Create a record |
| GET | `/api/records` | ANALYST, ADMIN | Get all records |
| GET | `/api/records/{id}` | ANALYST, ADMIN | Get record by ID |
| GET | `/api/records/filter` | ANALYST, ADMIN | Filter by type and/or category |
| PUT | `/api/records/{id}` | ADMIN | Update a record |
| DELETE | `/api/records/{id}` | ADMIN | Delete a record |

**Create/Update request body:**
```json
{
  "amount": 5000.00,
  "type": "income",
  "category": "Salary",
  "date": "2024-04-01",
  "notes": "Monthly salary"
}
```

**Filter examples:**
```
GET /api/records/filter?type=income
GET /api/records/filter?category=Salary
GET /api/records/filter?type=expense&category=Rent
```

---

### Dashboard

| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/dashboard/summary` | ANALYST, ADMIN | Total income, expenses, balance, category breakdown |

**Response:**
```json
{
  "totalIncome": 5000.0,
  "totalExpense": 1500.0,
  "balance": 3500.0,
  "CategoryTotals": {
    "Salary": 5000.0,
    "Rent": 1200.0,
    "Utilities": 300.0
  }
}
```

---

### User Management (ADMIN only)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create a user |
| PUT | `/api/users/{id}` | Update a user |
| DELETE | `/api/users/{id}` | Delete a user |

---

## Error Handling

All errors return a consistent JSON response:

```json
{
  "error": "Record not found"
}
```

Validation errors return field-level messages:

```json
{
  "amount": "Amount must be positive",
  "type": "Type is required"
}
```

| Status | Meaning |
|---|---|
| 200 | Success |
| 201 | Created |
| 400 | Validation error |
| 401 | Unauthorized |
| 403 | Forbidden (wrong role) |
| 404 | Not found |
| 409 | Conflict (email already registered) |
| 500 | Internal server error |

---

## Assumptions

- H2 in-memory database is used for simplicity — data resets on every restart
- All users share a fixed password `password` — in production this would use BCrypt hashing
- New users can self-register with any role — in production, role assignment would be restricted to admins
- `type` field accepts only `income` or `expense` — validated in the service layer
- Soft delete is not implemented — deletes are permanent

---

## What This Project Demonstrates

- Clean layered architecture — Controller → Service → Repository
- Role-based access control using Spring Security
- Input validation using Jakarta Bean Validation
- Global exception handling with meaningful error responses
- JPA entity modeling with proper annotations
- RESTful API design with correct HTTP methods and status codes
- Dashboard-level data aggregation using Java streams
