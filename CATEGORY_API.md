# Category API Documentation

## Overview
The Category API provides endpoints to manage product categories in the BeautyStor application. Categories can be hierarchical with parent-child relationships.

---

## Endpoints

### 1. Create Category
Create a new category.

- **HTTP Method:** `POST`
- **URL:** `/api/categories`
- **Request DTO:** `CreateCategoryRequest`
- **Response DTO:** `CategoryResponse`
- **Expected HTTP Status:** `201 Created`

### 2. Get All Categories
Retrieve a list of all categories.

- **HTTP Method:** `GET`
- **URL:** `/api/categories`
- **Request DTO:** N/A
- **Response DTO:** `List<CategoryResponse>`
- **Expected HTTP Status:** `200 OK`

### 3. Get Category by ID
Retrieve a specific category by its ID.

- **HTTP Method:** `GET`
- **URL:** `/api/categories/{id}`
- **Request DTO:** N/A
- **Response DTO:** `CategoryResponse`
- **Expected HTTP Status:** `200 OK`

### 4. Update Category
Update an existing category by ID.

- **HTTP Method:** `PUT`
- **URL:** `/api/categories/{id}`
- **Request DTO:** `UpdateCategoryRequest`
- **Response DTO:** `CategoryResponse`
- **Expected HTTP Status:** `200 OK`

### 5. Delete Category
Delete a category by ID.

- **HTTP Method:** `DELETE`
- **URL:** `/api/categories/{id}`
- **Request DTO:** N/A
- **Response DTO:** N/A
- **Expected HTTP Status:** `204 No Content`

---

## DTOs

### CreateCategoryRequest
```
{
  "name": "string (required)",
  "slug": "string (required)",
  "parentId": "long (optional, positive)",
  "isActive": "boolean"
}
```

### UpdateCategoryRequest
```
{
  "name": "string (required)",
  "slug": "string (required)",
  "parentId": "long (optional, positive)",
  "isActive": "boolean"
}
```

### CategoryResponse
```
{
  "id": "long",
  "name": "string",
  "slug": "string",
  "parentId": "long",
  "isActive": "boolean"
}
```
