# 05 - API Design

# Objectif

Ce document décrit les API REST de BeautyStor.

Il présente les endpoints disponibles, les DTOs utilisés ainsi que les codes de réponse attendus.

Toutes les API suivent les conventions REST et utilisent le format JSON.

---

# API Conventions

## Base URL

```
/api
```

## Content Type

```
application/json
```

## HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 OK | Request successfully processed |
| 201 Created | Resource successfully created |
| 204 No Content | Resource successfully deleted |
| 400 Bad Request | Validation error |
| 404 Not Found | Resource not found |
| 409 Conflict | Resource already exists |
| 500 Internal Server Error | Unexpected server error |

---

# Category API

## Overview

The Category API provides endpoints to manage product categories.

Categories support hierarchical relationships through an optional parent category.

---

## Endpoints

### Create Category

- **HTTP Method:** `POST`
- **URL:** `/api/categories`
- **Request DTO:** `CreateCategoryRequest`
- **Response DTO:** `CategoryResponse`
- **Expected HTTP Status:** `201 Created`

---

### Get All Categories

- **HTTP Method:** `GET`
- **URL:** `/api/categories`
- **Request DTO:** N/A
- **Response DTO:** `List<CategoryResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Get Category by ID

- **HTTP Method:** `GET`
- **URL:** `/api/categories/{id}`
- **Request DTO:** N/A
- **Response DTO:** `CategoryResponse`
- **Expected HTTP Status:** `200 OK`

---

### Update Category

- **HTTP Method:** `PUT`
- **URL:** `/api/categories/{id}`
- **Request DTO:** `UpdateCategoryRequest`
- **Response DTO:** `CategoryResponse`
- **Expected HTTP Status:** `200 OK`

---

### Delete Category

- **HTTP Method:** `DELETE`
- **URL:** `/api/categories/{id}`
- **Request DTO:** N/A
- **Response DTO:** N/A
- **Expected HTTP Status:** `204 No Content`

---

## DTOs

### CreateCategoryRequest

```json
{
  "name": "string (required)",
  "slug": "string (required)",
  "parentId": "long (optional)",
  "isActive": true
}
```

### UpdateCategoryRequest

```json
{
  "name": "string (required)",
  "slug": "string (required)",
  "parentId": "long (optional)",
  "isActive": true
}
```

### CategoryResponse

```json
{
  "id": 1,
  "name": "Skincare",
  "slug": "skincare",
  "parentId": null,
  "isActive": true
}
```

---

# Brand API

## Overview

The Brand API provides endpoints to manage product brands.

Each brand can be associated with multiple products.

---

## Endpoints

### Create Brand

- **HTTP Method:** `POST`
- **URL:** `/api/brands`
- **Request DTO:** `CreateBrandRequest`
- **Response DTO:** `BrandResponse`
- **Expected HTTP Status:** `201 Created`

---

### Get All Brands

- **HTTP Method:** `GET`
- **URL:** `/api/brands`
- **Request DTO:** N/A
- **Response DTO:** `List<BrandResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Get Brand by ID

- **HTTP Method:** `GET`
- **URL:** `/api/brands/{id}`
- **Request DTO:** N/A
- **Response DTO:** `BrandResponse`
- **Expected HTTP Status:** `200 OK`

---

### Update Brand

- **HTTP Method:** `PUT`
- **URL:** `/api/brands/{id}`
- **Request DTO:** `UpdateBrandRequest`
- **Response DTO:** `BrandResponse`
- **Expected HTTP Status:** `200 OK`

---

### Delete Brand

- **HTTP Method:** `DELETE`
- **URL:** `/api/brands/{id}`
- **Request DTO:** N/A
- **Response DTO:** N/A
- **Expected HTTP Status:** `204 No Content`

---

## DTOs

### CreateBrandRequest

```json
{
  "name": "string (required)",
  "slug": "string (required)",
  "logoUrl": "string (optional)"
}
```

### UpdateBrandRequest

```json
{
  "name": "string (required)",
  "slug": "string (required)",
  "logoUrl": "string (optional)"
}
```

### BrandResponse

```json
{
  "id": 1,
  "name": "L'Oréal",
  "slug": "loreal",
  "logoUrl": "https://example.com/logo.png"
}
```

---

## Statut

### Sprint 1

- ✅ Category API
- ✅ Brand API
- ⏳ Product API
- ⏳ Product Variant API
- ⏳ Product Image API
- ⏳ Stock API
