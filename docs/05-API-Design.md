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

## Standard API Response

Unless otherwise specified, every endpoint returns a standard response object.

### Success Response

```json
{
  "data": {},
  "errors": null,
  "status": "success"
}
```

### Delete Response
```
{
  "data": null,
  "errors": null,
  "status": "success"
}
```

## HTTP Status Codes


| Code | Description |
|------|-------------|
| 200 OK | Request successfully processed |
| 201 Created | Resource successfully created |
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
- **Response DTO:** `ApiResponse<CategoryResponse>`
- **Expected HTTP Status:** `201 Created`

---

### Get All Categories

- **HTTP Method:** `GET`
- **URL:** `/api/categories`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<List<CategoryResponse>>`
- **Expected HTTP Status:** `200 OK`

---

### Get Category by ID

- **HTTP Method:** `GET`
- **URL:** `/api/categories/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<CategoryResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Update Category

- **HTTP Method:** `PUT`
- **URL:** `/api/categories/{id}`
- **Request DTO:** `UpdateCategoryRequest`
- **Response DTO:** `ApiResponse<CategoryResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Delete Category

- **HTTP Method:** `DELETE`
- **URL:** `/api/categories/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<Void>`
- **Expected HTTP Status:** `200 OK`

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
- **Response DTO:** `ApiResponse<BrandResponse>`
- **Expected HTTP Status:** `201 Created`

---

### Get All Brands

- **HTTP Method:** `GET`
- **URL:** `/api/brands`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<List<BrandResponse>>`
- **Expected HTTP Status:** `200 OK`

---

### Get Brand by ID

- **HTTP Method:** `GET`
- **URL:** `/api/brands/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<BrandResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Update Brand

- **HTTP Method:** `PUT`
- **URL:** `/api/brands/{id}`
- **Request DTO:** `UpdateBrandRequest`
- **Response DTO:** `ApiResponse<BrandResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Delete Brand

- **HTTP Method:** `DELETE`
- **URL:** `/api/brands/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<Void>`
- **Expected HTTP Status:** `200 OK`

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

---





# Product API

## Overview

The Product API provides endpoints to manage products in the BeautyStor catalog.

Each product belongs to one category and one brand.

---

## Endpoints

### Create Product

- **HTTP Method:** `POST`
- **URL:** `/api/products`
- **Request DTO:** `CreateProductRequest`
- **Response DTO:** `ApiResponse<ProductResponse>`
- **Expected HTTP Status:** `201 Created`

---

### Get All Products

- **HTTP Method:** `GET`
- **URL:** `/api/products`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<List<ProductResponse>>`
- **Expected HTTP Status:** `200 OK`

---

### Get Product by ID

- **HTTP Method:** `GET`
- **URL:** `/api/products/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<ProductResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Update Product

- **HTTP Method:** `PUT`
- **URL:** `/api/products/{id}`
- **Request DTO:** `UpdateProductRequest`
- **Response DTO:** `ApiResponse<ProductResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Delete Product

- **HTTP Method:** `DELETE`
- **URL:** `/api/products/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<Void>`
- **Expected HTTP Status:** `200 OK`

---

## DTOs

### CreateProductRequest

```json
{
  "name": "string (required)",
  "slug": "string (required)",
  "description": "string (optional)",
  "brandId": "long (required)",
  "categoryId": "long (required)",
  "basePrice": "decimal (required)",
  "isAvailable": "boolean"
}
```

### UpdateProductRequest

```json
{
  "name": "string (required)",
  "slug": "string (required)",
  "description": "string (optional)",
  "brandId": "long (required)",
  "categoryId": "long (required)",
  "basePrice": "decimal (required)",
  "isAvailable": "boolean"
}
```

### ProductResponse

```json
{
  "id": 1,
  "name": "Hydrating Face Cream",
  "slug": "hydrating-face-cream",
  "description": "Daily moisturizing cream",
  "brandId": 1,
  "categoryId": 2,
  "basePrice": 149.99,
  "isAvailable": true
}
```
---

# Product Image API

## Overview

The Product Image API provides endpoints to manage images associated with products.

Each image belongs to one product.

---

## Endpoints

### Create Product Image

- **HTTP Method:** `POST`
- **URL:** `/api/product-images`
- **Request DTO:** `CreateProductImageRequest`
- **Response DTO:** `ApiResponse<ProductImageResponse>`
- **Expected HTTP Status:** `201 Created`

---

### Get All Product Images

- **HTTP Method:** `GET`
- **URL:** `/api/product-images`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<List<ProductImageResponse>>`
- **Expected HTTP Status:** `200 OK`

---

### Get Product Image by ID

- **HTTP Method:** `GET`
- **URL:** `/api/product-images/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<ProductImageResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Update Product Image

- **HTTP Method:** `PUT`
- **URL:** `/api/product-images/{id}`
- **Request DTO:** `UpdateProductImageRequest`
- **Response DTO:** `ApiResponse<ProductImageResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Delete Product Image

- **HTTP Method:** `DELETE`
- **URL:** `/api/product-images/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<Void>`
- **Expected HTTP Status:** `200 OK`

---

## DTOs

### CreateProductImageRequest

```json
{
  "productId": "long (required)",
  "imageUrl": "string (required)",
  "altText": "string (optional)",
  "displayOrder": "integer (optional)",
  "isPrimary": "boolean"
}
```

### UpdateProductImageRequest

```json
{
  "productId": "long (required)",
  "imageUrl": "string (required)",
  "altText": "string (optional)",
  "displayOrder": "integer (optional)",
  "isPrimary": "boolean"
}
```

### ProductImageResponse

```json
{
  "id": 1,
  "productId": 1,
  "imageUrl": "https://example.com/images/product-1.jpg",
  "altText": "Front view",
  "displayOrder": 1,
  "isPrimary": true
}
```


## Statut

### Sprint 1

- ✅ Category API
- ✅ Brand API
- ✅ Product API
- ⏳ Product Variant API
- ⏳ Product Image API
- ⏳ Stock API
