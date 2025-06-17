# 🧠 Talent Forge – AI-Powered Job Matching Platform

**Talent Forge** is a modern recruitment backend platform built with **Spring Boot 3.5** and **Java 21**, designed to streamline hiring using **AI integration**. It enables companies to post jobs, manage candidate profiles, and leverage intelligent matching algorithms to rank candidates based on resume content and skills.

The platform offers resume parsing using external NLP services, analytics via Spring Actuator, secure API endpoints with OAuth2/JWT, and basic notification features for interview scheduling. It also supports file upload for resumes and provides an OpenAPI-driven contract-first approach for clean, scalable development.

---

### 🔍 Key Features

- ✅ **Job & Candidate CRUD**
- 📄 **Resume Parsing** with AI/NLP APIs (e.g., AWS Comprehend)
- 🧮 **AI-Powered Candidate Matching** with basic scoring logic
- 📊 **Admin Dashboard & Analytics** using Spring Boot Actuator + Grafana
- ✉️ **Notification System** (email/SMS for interview scheduling)
- 🗂️ **Resume Storage** (local file system or cloud bucket)
- 🔐 **Secure REST API** with OAuth2 / JWT Authentication
- 🧬 **OpenAPI-Driven Architecture** using code generation

---

### 🧠 Domain Model Overview

- `Candidate` – represents an applicant with skills, resume, and experience.
- `Job` – job post including required skills and experience.
- `Resume` – extracted text and storage path of the uploaded file.
- `MatchScore` – basic scoring structure for AI matching logic.
- `MatchResult` *(optional)* – enriched result combining job, candidate, and score.
- `Notification` – encapsulates information required to notify a candidate about relevant job matches or interview opportunities, including recipient details, message content, and delivery status tracking.

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

springdoc-openapi-starter-webmvc-ui – Swagger UI for Spring Boot 3.x

openapi-generator-maven-plugin – Generates interfaces from OpenAPI YAML

tika-core / tika-parsers-standard-package – For parsing text from files

jacoco-maven-plugin – For code coverage metrics
