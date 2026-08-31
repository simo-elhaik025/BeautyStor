# 04 - Database

## Objectif

Ce document décrit le schéma relationnel réel attendu par BeautyStor.

## Vue d’ensemble

* Le schéma est géré hors application.
* `spring.jpa.hibernate.ddl-auto=none`.
* Les entités JPA s’alignent sur les tables et colonnes existantes.

## Tables principales

* `Brand`
* `Category`
* `Product`
* `ProductVariant`
* `ProductImage`
* `User`
* `Cart`
* `CartItem`
* `orders`
* `OrderItem`

## Points clés du schéma

* `Category.parent_id` porte la hiérarchie des catégories.
* `Product.brand_id` et `Product.category_id` lient le produit à sa marque et sa catégorie.
* `ProductVariant.product_id` et `ProductImage.product_id` rattachent les dépendances au produit.
* `Cart.user_id` et `CartItem.cart_id` structurent le panier.
* `CartItem.product_variant_id` et `OrderItem.product_variant_id` référencent la variante vendue.
* `Order.user_id` référence l’utilisateur, et `OrderItem.order_id` référence la commande.
* `Order.shippingAddressSnapshot` est stocké en JSON.
* `OrderItem` conserve les snapshots nécessaires à l’historique de l’achat.
