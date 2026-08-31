# 05 - API Design for Client

## Objectif

Ce document liste les routes utiles au client applicatif sans répéter le détail complet de l’API.

## Routes client

### Authentification

* `POST /api/auth/login`
* `POST /api/auth/register`
* `POST /api/auth/refresh`

### Catalogue public

* `GET /api/brands`
* `GET /api/categories`
* `GET /api/products`
* `GET /api/products/{slug}`
* `GET /api/product-images`
* `GET /api/product-variants`

### Panier et commandes

* `GET /api/cart`
* `POST /api/cart/items`
* `PUT /api/cart/items/{itemId}`
* `DELETE /api/cart/items/{itemId}`
* `POST /api/orders`
* `GET /api/orders`
* `GET /api/orders/{id}`

## Remarque

* Toutes ces routes utilisent l’enveloppe `ApiResponse` et les routes protégées attendent un JWT Bearer.
