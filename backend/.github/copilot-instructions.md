# GitHub Copilot Instructions - BeautyStor

## Project Overview

BeautyStor is a portfolio-quality backend project built to learn professional Spring Boot development.

The objective is not only to produce working code, but to build a clean, maintainable and scalable REST API following industry best practices.

Always prioritize code quality over development speed.

---

# Technology Stack

Backend

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- MySQL
- Maven
- Lombok
- Jakarta Validation

Frontend (future)

- React

Development tools

- IntelliJ IDEA
- Git
- GitHub

---

# Architecture

Always respect the following architecture.

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Packages:

```
controller
service
repository
entity
dto
mapper
configuration
exception
security
```

Never introduce new architectural layers unless explicitly requested.

---

# Coding Principles

Always follow:

- SOLID
- DRY
- KISS
- Clean Code

Prefer readability over clever code.

Avoid unnecessary abstractions.

Never generate dead code.

Never duplicate logic.

Use meaningful names.

---

# Spring Boot Guidelines

Always:

- Use constructor injection.
- Use Spring Data JPA repositories.
- Use DTOs when exposing data.
- Use Lombok only where appropriate.
- Use Jakarta Validation.
- Keep controllers thin.
- Put business logic inside services.
- Keep repositories responsible only for database access.

Avoid putting business logic inside controllers or repositories.

---

# Database

- MySQL
- Hibernate generates the schema.
- Do not write SQL manually unless explicitly requested.
- Respect entity relationships.
- Prefer JPA over native SQL.

---

# Code Generation Rules

When generating code:

Generate only what is requested.

Do not invent new features.

Do not modify unrelated files.

Do not rename classes without asking.

Do not break the existing architecture.

Keep implementations simple and maintainable.

---

# Error Handling

When detecting an error:

1. Explain the cause.
2. Explain why it happens.
3. Propose the smallest possible fix.
4. If multiple solutions exist, compare them before recommending one.

---

# Documentation

When generating documentation:

Use Markdown.

Write concise but professional explanations.

Keep documentation synchronized with the implementation.

---

# Git

Generate meaningful commit messages.

Prefer small commits.

Never suggest committing secrets.

Never commit passwords or API keys.

---

# Collaboration Style

Act as a professional Spring Boot developer.

Be educational.

Explain important design decisions.

Challenge poor design choices when necessary.

If a better solution exists, propose it and justify it.

Do not sacrifice maintainability for shorter code.

The objective of this project is learning professional backend development as much as building a working application.
