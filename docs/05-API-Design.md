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

Product access is split into two contracts:

- public catalogue endpoints expose only customer-visible data
- administration endpoints expose the full product management view

Each product belongs to one category and one brand.

---

## Public Catalogue Endpoints

### Get Product List

- **HTTP Method:** `GET`
- **URL:** `/api/products`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<Page<ProductSummaryResponse>>`
- **Expected HTTP Status:** `200 OK`

This endpoint returns only available products, mapped with summary data for the client catalogue.

Optional query parameters:

- `search`: text search on the product name
- `category`: filter by category ID
- `brand`: filter by brand ID
- `available`: filter by availability
- `page`: zero-based page index
- `size`: page size
- `sort`: sort field and direction using Spring Pageable syntax

All filters are optional and can be combined.

Example:

`GET /api/products?search=cream&category=2&brand=1&available=true`

Pagination and sorting can be combined with the filters:

`GET /api/products?page=0&size=12&sort=basePrice,desc`

---

### Get Product Details

- **HTTP Method:** `GET`
- **URL:** `/api/products/{slug}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<ProductDetailsResponse>`
- **Expected HTTP Status:** `200 OK`

This endpoint returns one available product by slug, mapped with public detail data only.

---

## Administration Endpoints

### Create Product

- **HTTP Method:** `POST`
- **URL:** `/api/admin/products`
- **Request DTO:** `CreateProductRequest`
- **Response DTO:** `ApiResponse<ProductResponse>`
- **Expected HTTP Status:** `201 Created`

---

### Get All Products

- **HTTP Method:** `GET`
- **URL:** `/api/admin/products`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<List<ProductResponse>>`
- **Expected HTTP Status:** `200 OK`

---

### Get Product by ID

- **HTTP Method:** `GET`
- **URL:** `/api/admin/products/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<ProductResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Update Product

- **HTTP Method:** `PUT`
- **URL:** `/api/admin/products/{id}`
- **Request DTO:** `UpdateProductRequest`
- **Response DTO:** `ApiResponse<ProductResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Delete Product

- **HTTP Method:** `DELETE`
- **URL:** `/api/admin/products/{id}`
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
  "available": "boolean"
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
  "available": "boolean"
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
  "available": true
}
```

### ProductSummaryResponse

```json
{
  "id": 1,
  "name": "Hydrating Face Cream",
  "slug": "hydrating-face-cream",
  "brand": {
    "id": 1,
    "name": "BeautyStor",
    "slug": "beautystor",
    "logoUrl": "https://cdn.example.com/brands/beautystor.png"
  },
  "category": {
    "id": 2,
    "name": "Skincare",
    "slug": "skincare",
    "parentId": null,
    "active": true
  },
  "basePrice": 149.99,
  "primaryImage": {
    "id": 10,
    "productId": 1,
    "url": "https://cdn.example.com/products/hydrating-face-cream.jpg",
    "sortOrder": 1,
    "primary": true
  },
  "available": true
}
```

### ProductDetailsResponse

```json
{
  "id": 1,
  "name": "Hydrating Face Cream",
  "slug": "hydrating-face-cream",
  "description": "Daily moisturizing cream",
  "brand": {
    "id": 1,
    "name": "BeautyStor",
    "slug": "beautystor",
    "logoUrl": "https://cdn.example.com/brands/beautystor.png"
  },
  "category": {
    "id": 2,
    "name": "Skincare",
    "slug": "skincare",
    "parentId": null,
    "active": true
  },
  "basePrice": 149.99,
  "images": [
    {
      "id": 10,
      "productId": 1,
      "url": "https://cdn.example.com/products/hydrating-face-cream.jpg",
      "sortOrder": 1,
      "primary": true
    }
  ],
  "variants": [
    {
      "id": 20,
      "productId": 1,
      "sku": "HFC-50ML",
      "displayName": "50 ml",
      "price": 149.99,
      "stockQuantity": 12
    }
  ],
  "available": true
}
```
---




# Product Image API

## Overview

The Product Image API provides endpoints to manage images associated with products.

Each image belongs to a single product.

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
  "url": "string (required)",
  "sortOrder": "integer (required)",
  "primary": "boolean"
}
```

### UpdateProductImageRequest

```json
{
  "productId": "long (required)",
  "url": "string (required)",
  "sortOrder": "integer (required)",
  "primary": "boolean"
}
```

### ProductImageResponse

```json
{
  "id": 1,
  "productId": 1,
  "url": "https://example.com/images/product-1.jpg",
  "sortOrder": 1,
  "primary": true
}
```





# Product Variant API

## Overview

The Product Variant API provides endpoints to manage product variants.

Each variant belongs to a single product and represents a purchasable version of that product.

---

## Endpoints

### Create Product Variant

- **HTTP Method:** `POST`
- **URL:** `/api/product-variants`
- **Request DTO:** `CreateProductVariantRequest`
- **Response DTO:** `ApiResponse<ProductVariantResponse>`
- **Expected HTTP Status:** `201 Created`

---

### Get All Product Variants

- **HTTP Method:** `GET`
- **URL:** `/api/product-variants`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<List<ProductVariantResponse>>`
- **Expected HTTP Status:** `200 OK`

---

### Get Product Variant by ID

- **HTTP Method:** `GET`
- **URL:** `/api/product-variants/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<ProductVariantResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Update Product Variant

- **HTTP Method:** `PUT`
- **URL:** `/api/product-variants/{id}`
- **Request DTO:** `UpdateProductVariantRequest`
- **Response DTO:** `ApiResponse<ProductVariantResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Delete Product Variant

- **HTTP Method:** `DELETE`
- **URL:** `/api/product-variants/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<Void>`
- **Expected HTTP Status:** `200 OK`

---

## DTOs

### CreateProductVariantRequest

```json
{
  "productId": "long (required)",
  "sku": "string (required)",
  "displayName": "string (optional)",
  "price": "decimal (required)",
  "stockQuantity": "integer (required)"
}
```

### UpdateProductVariantRequest

```json
{
  "productId": "long (required)",
  "sku": "string (required)",
  "displayName": "string (optional)",
  "price": "decimal (required)",
  "stockQuantity": "integer (required)"
}
```

### ProductVariantResponse

```json
{
  "id": 1,
  "productId": 1,
  "sku": "SKU-001",
  "displayName": "100 ml",
  "price": 149.99,
  "stockQuantity": 50
}
```



# Cart API

## Overview

The Cart API allows an authenticated user to manage their own shopping cart.

The cart is created automatically when needed. The client never creates a cart manually.

Each line item is recalculated on the server.

Rules:

- only authenticated users can access these endpoints
- a user can only access their own cart
- the product variant must exist
- quantity must be strictly positive when adding
- quantity must be greater than or equal to 1 when updating
- quantity must not exceed available stock
- adding the same variant again increases the existing line quantity instead of creating a duplicate
- subtotal and total price are always computed by the server

---

## Endpoints

### Get Cart

- **HTTP Method:** `GET`
- **URL:** `/api/cart`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<CartResponse>`
- **Expected HTTP Status:** `200 OK`

Returns the authenticated user's current cart.

---

### Add Cart Item

- **HTTP Method:** `POST`
- **URL:** `/api/cart/items`
- **Request DTO:** `AddCartItemRequest`
- **Response DTO:** `ApiResponse<CartResponse>`
- **Expected HTTP Status:** `200 OK`

Creates the cart automatically if it does not exist yet.

---

### Update Cart Item Quantity

- **HTTP Method:** `PUT`
- **URL:** `/api/cart/items/{itemId}`
- **Request DTO:** `UpdateCartItemRequest`
- **Response DTO:** `ApiResponse<CartResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Delete Cart Item

- **HTTP Method:** `DELETE`
- **URL:** `/api/cart/items/{itemId}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<Void>`
- **Expected HTTP Status:** `200 OK`

---

## DTOs

### AddCartItemRequest

```json
{
  "productVariantId": "long (required)",
  "quantity": "integer (required, > 0)"
}
```

### UpdateCartItemRequest

```json
{
  "quantity": "integer (required, >= 1)"
}
```

### CartItemResponse

```json
{
  "itemId": 1,
  "productVariantId": 20,
  "productName": "Hydrating Face Cream",
  "sku": "HFC-50ML",
  "quantity": 2,
  "unitPrice": 149.99,
  "subtotal": 299.98
}
```

### CartResponse

```json
{
  "cartId": 5,
  "items": [
    {
      "itemId": 1,
      "productVariantId": 20,
      "productName": "Hydrating Face Cream",
      "sku": "HFC-50ML",
      "quantity": 2,
      "unitPrice": 149.99,
      "subtotal": 299.98
    }
  ],
  "totalItems": 2,
  "totalPrice": 299.98
}
```



# User API

## Overview

The User API provides endpoints to manage application users.

Each user can own multiple addresses, carts and orders.

---

## Endpoints

### Create User

- **HTTP Method:** `POST`
- **URL:** `/api/users`
- **Request DTO:** `CreateUserRequest`
- **Response DTO:** `ApiResponse<UserResponse>`
- **Expected HTTP Status:** `201 Created`

---

### Get All Users

- **HTTP Method:** `GET`
- **URL:** `/api/users`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<List<UserResponse>>`
- **Expected HTTP Status:** `200 OK`

---

### Get User by ID

- **HTTP Method:** `GET`
- **URL:** `/api/users/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<UserResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Update User

- **HTTP Method:** `PUT`
- **URL:** `/api/users/{id}`
- **Request DTO:** `UpdateUserRequest`
- **Response DTO:** `ApiResponse<UserResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Delete User

- **HTTP Method:** `DELETE`
- **URL:** `/api/users/{id}`
- **Request DTO:** N/A
- **Response DTO:** `ApiResponse<Void>`
- **Expected HTTP Status:** `200 OK`

---

## DTOs

### CreateUserRequest

```json
{
  "email": "john@example.com",
  "password": "string (required)",
  "role": "USER",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+212600000000"
}
```
### UpdateUserRequest


```json
{
  "email": "john@example.com",
  "role": "USER",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+212600000000",
  "isActive": true
}
```
### UserResponse
```
{
  "id": 1,
  "email": "john@example.com",
  "role": "USER",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+212600000000",
  "isActive": true
}
```





# Authentication API

## Overview

The Authentication API provides endpoints for user authentication using JWT.

It allows users to log in, obtain an Access Token and Refresh Token, and refresh an expired Access Token without re-entering their credentials.

---

## Endpoints

### Login

- **HTTP Method:** `POST`
- **URL:** `/api/auth/login`
- **Request DTO:** `LoginRequest`
- **Response DTO:** `ApiResponse<AuthenticationResponse>`
- **Expected HTTP Status:** `200 OK`

---

### Refresh Access Token

- **HTTP Method:** `POST`
- **URL:** `/api/auth/refresh`
- **Request DTO:** `RefreshTokenRequest`
- **Response DTO:** `ApiResponse<AuthenticationResponse>`
- **Expected HTTP Status:** `200 OK`

---

## DTOs

### LoginRequest

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

### RefreshTokenRequest

```json
{
  "refreshToken": "your-refresh-token"
}
```

### AuthenticationResponse

```json
{
  "accessToken": "jwt-access-token",
  "refreshToken": "jwt-refresh-token",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

---

## Security Responses

| HTTP Status | Description |
|-------------|-------------|
| 200 OK | Authentication successful |
| 400 Bad Request | Invalid request |
| 401 Unauthorized | Invalid credentials or invalid/expired JWT |
| 403 Forbidden | Access denied |
