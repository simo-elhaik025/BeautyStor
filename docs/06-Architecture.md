# 06 - Architecture

## Objectif

Le backend BeautyStor suit une architecture en couches simple et lisible.

## Structure

* `controller` : exposition HTTP et validation d’entrée
* `service` / `service.impl` : logique métier et transactions
* `repository` : accès JPA
* `entity` : modèle de persistance
* `dto` : contrats API
* `mapper` : conversion entités ↔ DTO
* `security` : JWT, filtres et gestion des accès
* `common` : enveloppe de réponse et gestion globale des erreurs
* `specification` : recherche produit filtrée

## Décisions actuelles

* Le flux reste `Controller -> Service -> Repository`.
* Les contrôleurs ne contiennent pas de logique métier.
* Les services portent les règles métier et les frontières transactionnelles.
* Les lectures complexes utilisent `@EntityGraph` ou des projections ciblées pour limiter les chargements inutiles.
* La sécurité est stateless, basée sur JWT et des rôles Spring Security.
* Les réponses sont normalisées par `ApiResponse`.
