# 04-Database

# Database

## Objectif

La base de données de BeautyStor est basée sur MySQL.

La structure de la base est générée automatiquement par Hibernate (Spring Data JPA).

Le schéma est donc maintenu directement par les entités Java.

---

## SGBD

MySQL 8+

---

## Base de données

Nom : beautystor_db


---

## Utilisateur

Username : defined in application.properties
Password : defined locally (not committed)


---

## Gestion du schéma

Le schéma est généré automatiquement grâce à Hibernate.

Configuration utilisée pendant le développement :

```properties
spring.jpa.hibernate.ddl-auto= update|create

En production :
spring.jpa.hibernate.ddl-auto=validate

```


# Conventions

## Primary Keys

All entities use the following convention for their primary key:

| Java Type | MySQL Type | Constraints |
|-----------|------------|-------------|
| `Long`    | `BIGINT`   | `PRIMARY KEY AUTO_INCREMENT` |

---

## Foreign Keys

Entity relationships are represented using **foreign keys** generated automatically by Hibernate.

The corresponding columns follow the naming convention:

```text
<entity_name>_id
```

Examples:

```text
category_id
brand_id
product_id
user_id
order_id
```

---

## Naming Conventions

### Tables

Table names are written in **snake_case** and use the singular form.

Examples:

```text
product
category
brand
product_variant
product_image
user
address
cart
cart_item
order
order_item
```

### Columns

Column names also follow the **snake_case** convention.

Examples:

```text
id
name
slug
description
base_price
stock
quantity
created_at
updated_at
is_active
category_id
brand_id
```

---

## Java ↔ MySQL Type Mapping

| Java Type              | MySQL Type     | Example |
|-----------             |------------    |---------|
| `Long`                 | `BIGINT`       | Entity identifier (`id`) |
| `Integer`              | `INT`          | Quantity, sort order |
| `String`               | `VARCHAR(255)` | Name, title, slug |
| `String` (long text)   | `TEXT`         | Description |
| `BigDecimal`           | `DECIMAL(10,2)`| Price |
| `Boolean`              | `BOOLEAN`      | Active, enabled |
| `LocalDate`            | `DATE`         | Birth date |
| `LocalDateTime`        | `TIMESTAMP`    | Created date |
| `Instant`              | `TIMESTAMP`    | Audit timestamp |
| `Enum`                 | `VARCHAR`      | Order status, user role |
| `UUID`                 | `CHAR(36)`     | Public identifier (if needed) |
| `byte[]`               | `BLOB`         | Binary data |
| `Map<String, Object>`  | `JSON`         | Dynamic attributes |
| `List<String>`         | `JSON`         | Simple collections (when appropriate) |

---

## Schema Generation

Database objects are **not created manually**.

Spring Boot and Hibernate generate automatically:

- Tables
- Columns
- Primary keys
- Foreign keys
- Constraints
- Relationships

The schema is generated directly from the JPA entity definitions.

Development configuration:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Production configuration:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

---

## Database Diagram

The complete database schema is available in:

```text
docs/diagrams/database.png
```
