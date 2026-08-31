# 08 - Deployment

## Build Docker

Le projet utilise un Dockerfile multi-stage :

* image de build Maven 3.9.6 sur Java 21
* image runtime Temurin JRE 21
* artefact Spring Boot copié dans `/app/app.jar`
* port exposé : `8080`

## Configuration requise

Variables d’environnement attendues :

* `SPRING_DATASOURCE_URL`
* `SPRING_DATASOURCE_USERNAME`
* `SPRING_DATASOURCE_PASSWORD`
* `JWT_SECRET`

Variables optionnelles :

* `SPRING_JPA_SHOW_SQL`
* `SPRING_JPA_FORMAT_SQL`

## Remarques de déploiement

* L’application est stateless.
* MySQL doit être disponible avant le démarrage.
* Aucun `docker-compose` n’est fourni dans le dépôt.
