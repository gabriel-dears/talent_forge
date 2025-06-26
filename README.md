# 🧠 Talent Forge – AI-Powered Job Matching Platform

**Talent Forge** is a modern recruitment backend platform built with Spring Boot 3.5 and Java 21, designed to streamline
hiring through intelligent AI integration. It enables companies to manage job listings, candidate profiles, and
leverages asynchronous Kafka-based communication with downstream services like an AI matcher.

This backend supports resume parsing, observability, secure REST APIs with OAuth2, event-driven messaging, and scalable
architecture for microservices.

---

### 🔍 Key Features

- ✅ Job & Candidate CRUD operations
- 📄 Resume Parsing using AI/NLP (e.g., AWS Comprehend, Apache Tika)
- 🧠 AI Matching Integration via Kafka event messaging
- 📊 Observability with Spring Boot Actuator (Grafana ready)
- 🗂️ Resume Upload & Storage (local or cloud-compatible)
- 🔐 Secure APIs using OAuth2 & JWT
- 📃 Contract-First API Design via OpenAPI Generator
- 🔁 Kafka Integration to decouple matching and notification flows

---

### 📬 Kafka Integration

The system publishes events to Kafka so external microservices (like the AI Matcher) can consume them asynchronously
for:

- AI-powered candidate/job matching
- Intelligent notification processing
- Future extensibility (analytics, feedback loops, etc.)

#### 🔗 Topics

| Topic             | Description                                |
|-------------------|--------------------------------------------|
| `new-candidate`   | Fired when a candidate is created/updated  |
| `new-job`         | Fired when a job is created/updated        |
| `interview-event` | (Planned) Interview invitations, reminders |

You can configure topic names in application.yml.

#### Kafka Producer Example (Spring Boot):

kafkaTemplate.send("new-candidate", candidateDto);

#### Kafka Setup Required

```yaml
services:
  kafka:
    image: confluentinc/cp-kafka:7.3.0
```

---

### 🧠 Domain Model Overview

- `Candidate` – represents an applicant with skills, resume, and experience.
- `Job` – job post including required skills and experience.
- `Resume` – extracted text and storage path of the uploaded file.
- `MatchScore` – basic scoring structure for AI matching logic.
- `MatchResult` *(optional)* – enriched result combining job, candidate, and score.
- `KafkaMessage` - DTO used to publish candidate/job events to topics.

---

### 🧪 What You’ll Explore

- Integrating external AI/NLP APIs for resume understanding
- Designing REST APIs using OpenAPI Generator
- Implementing analytics and observability with Spring Boot Actuator
- Securing microservices with OAuth2 and JWT
- Managing file uploads and storage
- Creating asynchronous notifications via email or SMS
- Building scalable architecture using Spring Boot best practices

---

## 📚 Tech Stack

- **Java 21**
- **Spring Boot 3.5.0**
    - spring-boot-starter-web
    - spring-boot-starter-validation
    - spring-boot-starter-data-jpa
    - spring-boot-starter-actuator
- **PostgreSQL**
- **Hibernate Validator**
- **Apache Tika** (for content extraction)
- **OpenAPI Generator** (for code generation)
- **springdoc-openapi** (Swagger UI)
- **Jacoco** (code coverage)

---

## 🚀 Running the App

### Prerequisites

- Java 21
- Maven 3.8+
- PostgreSQL running locally (or configured in `application.yml`)

### Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

## Swagger UI

After the app is running, visit:

```bash
http://localhost:8080/swagger-ui.html
```

## ⚙️ Code Generation (OpenAPI)

This project auto-generates API interfaces and models from an OpenAPI YAML file located at:

```bash
src/main/resources/openapi/openapi.yaml
```

To trigger code generation manually:

```bash
mvn generate-sources
```

Generated code will be available under:

```bash
target/generated-sources/openapi
```

## 🧪 Testing & Coverage

Run all tests:

```bash
mvn test
```

### enerate code coverage report:

```bash
mvn verify
```

Then open:

```bash
target/site/jacoco/index.html
```

## 📂 Folder Structure

```bash
src/
├── main/
│   ├── java/
│   │   └── com/gabrieldears/talent_forge/
│   ├── resources/
│   │   └── openapi/
│   │       └── openapi.yaml
│   │   └── application.yml
└── test/
```

## 📦 Dependencies Highlights

| Dependency                       | Purpose                            |
|----------------------------------|------------------------------------|
| `spring-kafka`                   | Kafka integration (producer-ready) |
| `springdoc-openapi`              | OpenAPI/Swagger UI                 |
| `openapi-generator-maven-plugin` | Contract-first code gen            |
| `apache-tika`                    | Resume content extraction          |
| `jacoco-maven-plugin`            | Code coverage                      |

