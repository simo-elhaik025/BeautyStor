## Public Product DTOs

Les endpoints publics utilisent des DTO dédiés afin d'exposer uniquement les informations nécessaires aux clients.

Cette séparation permet :

- de protéger les données internes de l'application ;
- de limiter les données échangées ;
- d'optimiser les performances du catalogue ;
- de dissocier les besoins de l'administration de ceux du catalogue public.

### DTO

#### ProductSummaryResponse

Représentation simplifiée utilisée pour l'affichage de la liste des produits.

#### ProductDetailsResponse

Représentation complète utilisée pour la consultation d'un produit.


# Public Catalogue API

Les endpoints du catalogue public permettent aux visiteurs de consulter les produits disponibles.

Contrairement aux endpoints d'administration, ces endpoints sont accessibles sans authentification et retournent uniquement les informations destinées aux clients.

Les opérations de création, modification et suppression sont exposées séparément côté administration sur `/api/admin/products`.

## Endpoints

GET /api/products

Retourne une page des produits disponibles.

GET /api/products/{slug}

Retourne les informations détaillées d'un produit.


# Recherche et Filtres

Le catalogue public permet d'effectuer une recherche textuelle et d'appliquer plusieurs filtres simultanément.

Tous les paramètres sont optionnels.

## Paramètres disponibles

| Paramètre | Description |
|-----------|-------------|
| search | Recherche par nom du produit. |
| category | Filtre par catégorie. |
| brand | Filtre par marque. |
| available | Filtre selon la disponibilité en stock. |
| page | Index de page commençant à 0. |
| size | Nombre d'éléments par page. |
| sort | Tri Spring Pageable, ex: `name,asc` ou `basePrice,desc`. |

Les filtres peuvent être combinés afin d'obtenir des résultats plus précis.

### Exemple

GET /api/products?search=cream&category=2&brand=1&available=true

Exemple avec pagination et tri :

GET /api/products?page=0&size=12&sort=basePrice,desc

Les champs de tri disponibles dépendent du modèle `Product`. À ce stade, `createdAt` n'est pas exposé par l'entité produit.

# Pagination et Tri

Le catalogue public supporte la pagination et le tri afin d'améliorer les performances et l'expérience utilisateur.

## Pagination

| Paramètre | Description |
|-----------|-------------|
| page | Numéro de la page. |
| size | Nombre d'éléments par page. |

Exemple :

GET /api/products?page=0&size=12

## Tri

Le paramètre sort permet de définir le champ de tri ainsi que son ordre.

Exemples :

GET /api/products?sort=name,asc

GET /api/products?sort=basePrice,desc

GET /api/products?sort=createdAt,desc
