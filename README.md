# Job Application System

A production‑ready **Job Application Management System** built with **Spring Boot**, **JWT authentication**, and **PostgreSQL**, following clean architecture, test‑driven development, and real‑world Git workflows.

This project demonstrates how a modern backend system is designed, secured, tested, and prepared for cloud deployment.

---

## ✨ Key Features

### 🔐 Authentication & Security

* JWT‑based authentication (stateless)
* Role‑based authorization (`CANDIDATE`, `RECRUITER`)
* Secure password hashing using BCrypt
* Protected endpoints via Spring Security

### 💼 Job Management

* Recruiters can create and manage job postings
* Candidates can view available jobs

### 📄 Application Management

* Candidates can apply to jobs
* Prevents duplicate applications per job
* Candidates can withdraw applications
* Pagination support for application listings

### 🧪 Testing (Production‑Grade)

* **Unit tests** with JUnit 5 + Mockito
* **Repository tests** with `@DataJpaTest`
* **Security integration tests** with MockMvc
* High coverage of business logic and edge cases

### 🧩 Clean Architecture

* Layered design (Controller → Service → Repository)
* DTO‑based request/response handling
* Centralized exception handling
* Environment‑specific configuration

---

## 🛠️ Tech Stack

| Category         | Technology                  |
| ---------------- | --------------------------- |
| Language         | Java 17                     |
| Framework        | Spring Boot 3               |
| Security         | Spring Security + JWT       |
| Database         | PostgreSQL                  |
| ORM              | Spring Data JPA (Hibernate) |
| Testing          | JUnit 5, Mockito, MockMvc   |
| Build Tool       | Maven                       |
| Cloud            | AWS (EC2, RDS – planned)    |
| Containerization | Docker (planned)            |
| Version Control  | Git + GitHub                |

---

## 📐 Architecture Overview

```
Client (Browser / Postman)
        |
        v
Spring Boot REST API
        |
        v
PostgreSQL Database
```

* Stateless REST API
* JWT token used for authentication
* Role‑based access enforced at controller level

---

## 📂 Project Structure

```
src/main/java/com/example/jobapplicationsystem
│
├── controller        # REST controllers
├── service           # Business logic
├── repository        # JPA repositories
├── entity            # JPA entities
├── enums             # Domain enums
├── dto               # Request / Response DTOs
├── security          # JWT & Spring Security config
├── exception         # Global exception handling
└── config             # Application configuration
```

---

## 🔑 Authentication Flow

1. User logs in via `/auth/login`
2. Server returns a JWT token
3. Client sends token in `Authorization` header:

   ```
   Authorization: Bearer <token>
   ```
4. Security filter validates token and sets authentication context

---

## 🧪 Testing Strategy

### Unit Tests

* Services tested in isolation
* Dependencies mocked using Mockito

### Repository Tests

* Uses in‑memory database
* Verifies queries, constraints, pagination

### Integration Tests

* Full security flow tested
* JWT authentication + authorization verified

All tests can be run using:

```bash
mvn test
```

---

## ⚙️ Configuration

### application‑prod.yml (Safe to Commit)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

> ⚠️ No secrets are hard‑coded. All sensitive values are injected via environment variables.

---

## 🚀 Deployment (Planned / In Progress)

**Phase 1**

* AWS EC2 (Spring Boot application)
* AWS RDS (PostgreSQL)

**Phase 2 (Optional Enhancements)**

* Dockerization
* AWS ECS
* Load Balancer
* CI/CD with GitHub Actions

---

## 🔒 Git & Workflow Practices

* `main` branch as source of truth
* Branch protection rules enabled
* Feature‑branch based development
* Clean commit history

---

## 📌 Why This Project?

This project was built to:

* Simulate a **real production backend**
* Follow **industry best practices**
* Demonstrate **security, testing, and architecture skills**
* Serve as a strong **portfolio & interview project**

---

## ⭐️ Future Improvements

* API documentation (Swagger / OpenAPI)
* Refresh token support
* Admin role
* Application analytics dashboard

---

> If you’re reviewing this as a recruiter or interviewer: this project reflects how I approach backend systems in real‑world environments — secure, testable, and scalable.
