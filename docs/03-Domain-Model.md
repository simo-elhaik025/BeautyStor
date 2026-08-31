# 03 - Domain Model

## Objectif

Ce document résume le modèle métier réel de BeautyStor et les relations principales utilisées par les entités JPA et les DTOs.

## Domaines

### Catalogue

* `Brand`
* `Category`
* `Product`
* `ProductVariant`
* `ProductImage`

### Utilisateurs

* `User`

### Vente

* `Cart`
* `CartItem`
* `Order`
* `OrderItem`

## Relations clés

* `Brand` et `Category` référencent les produits.
* `Category` est hiérarchique via `parent_id`.
* `Product` porte `brandId` et `categoryId`, avec des associations LAZY vers `Brand` et `Category`.
* `Product` possède plusieurs `ProductVariant` et `ProductImage`.
* `User` possède des `Cart` et des `Order`.
* `Cart` contient des `CartItem`.
* `Order` contient des `OrderItem`.
* `CartItem` et `OrderItem` référencent une variante produit.
* `OrderItem` conserve des snapshots du produit au moment de l’achat.

## Règles métier

* Les rôles réels sont `USER`, `ADMIN` et `DELIVERY_AGENT`.
* `OrderStatus` contient `PENDING`, `DELIVERED` et `CANCELLED`.
* `Order` stocke aussi l’adresse de livraison sous forme de snapshot JSON.
* `Cart.status` est une chaîne ; l’application utilise la valeur `ACTIVE` pour le panier courant.
