# 05 - API Design

## Objectif

Ce document résume les conventions API et le périmètre global des routes exposées par BeautyStor.

## Conventions

* Base URL : `/api`
* Content type : `application/json`
* Authentification : `Authorization: Bearer <JWT>`
* Réponse standard : `ApiResponse<T>` avec `data`, `errors` et `status`
* Les mutations et les suppressions retournent aussi l’enveloppe standard

## Codes HTTP observés

* `200` : lecture ou mise à jour réussie
* `201` : création réussie
* `400` : validation, payload invalide ou règle métier côté client
* `401` : jeton absent ou invalide
* `403` : accès interdit par les rôles
* `404` : ressource introuvable
* `409` : contrainte de base de données
* `500` : erreur interne

## Surface API

### Publique

* `POST /api/auth/login`
* `POST /api/auth/register`
* `POST /api/auth/refresh`
* `GET /api/brands`
* `GET /api/categories`
* `GET /api/products`
* `GET /api/products/{slug}`
* `GET /api/product-images`
* `GET /api/product-variants`

### Client authentifié

* `GET /api/cart`
* `POST /api/cart/items`
* `PUT /api/cart/items/{itemId}`
* `DELETE /api/cart/items/{itemId}`
* `POST /api/orders`
* `GET /api/orders`
* `GET /api/orders/{id}`

### Administration et livraison

* `GET /api/admin/dashboard`
* `GET /api/admin/statistics`
* `GET /api/admin/products`
* `GET /api/admin/products/{id}`
* `POST /api/admin/products`
* `PUT /api/admin/products/{id}`
* `DELETE /api/admin/products/{id}`
* `GET /api/brands`
* `GET /api/brands/{id}`
* `POST /api/brands`
* `PUT /api/brands/{id}`
* `DELETE /api/brands/{id}`
* `GET /api/categories`
* `GET /api/categories/{id}`
* `POST /api/categories`
* `PUT /api/categories/{id}`
* `DELETE /api/categories/{id}`
* `GET /api/product-images`
* `GET /api/product-images/{id}`
* `POST /api/product-images`
* `PUT /api/product-images/{id}`
* `DELETE /api/product-images/{id}`
* `GET /api/product-variants`
* `GET /api/product-variants/{id}`
* `POST /api/product-variants`
* `PUT /api/product-variants/{id}`
* `DELETE /api/product-variants/{id}`
* `GET /api/admin/users`
* `GET /api/admin/users/{id}`
* `POST /api/admin/users`
* `PATCH /api/admin/users/{id}/status`
* `GET /api/admin/orders`
* `GET /api/admin/orders/{id}`
* `PATCH /api/admin/orders/{id}/status`
* `GET /api/delivery/orders`
* `GET /api/delivery/orders/{id}`
* `PATCH /api/delivery/orders/{id}/status`
