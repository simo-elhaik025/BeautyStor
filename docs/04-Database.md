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

