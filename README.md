# Ecommerce Backend

# Routes
## Auth
| method | EndPoint                  |
|--------|---------------------------|
| POST   | /api/auth/register        |
| POST   | /api/auth/login           |
| POST   | /api/auth/refresh         |
| POST   | /api/auth/logout          |
| POST   | /api/auth/forget-password |
| POST   | /api/auth/reset-password  |
| POST   | /api/auth/verify-email    |
| GET    | /api/auth/me              |

## Admin

| method | EndPoint                             |
|--------|--------------------------------------|
| GET    | /api/admin/vendors?page=0&pageSize=5 |
| GET    | /api/admin/                          |
| POST   | /api/admin/invite                    |
| POST   | /api/admin/vendors/invite            |
| POST   | /api/admin/accept-invite             |
| PUT    | /api/admin/:id                       |
| PUT    | /api/admin/vendors/"id               |
| DELETE | /api/admin/:id                       |
| PUT    | /api/vendors/:id/suspend             |
| GET    | /api/admin/users?page=0&pageSize=5   |
| GET    | /api/admin/users/:id                 |
| DELETE | /api/admin/users/:id                 |
| PUT    | /api/admin/users/:id/suspend         |
| PUT    | /api/admin/users/:id/roles           |

## Roles Management
| method | EndPoint                 |
|--------|--------------------------|
| POST   | /api/admin/roles         |
| PUT    | /api/admin/roles/:id     |
| DELETE | /api/admin/roles/:id     |
| GET    | /api/admin/roles/:id     |

## Users
| method | EndPoint                           |
|--------|------------------------------------|
| GET    | /api/users/profile                 |
| PUT    | /api/users/profile/                |
| POST   | /api/users/address                 |
| PUT    | /api/users/address                 |
| GET    | /api/users/address/:id             |
| PUT    | /api/users/address/:id             |
| DELETE | /api/users/address/:id             |
| PUT    | /api/users/address/:id/set-default |

##  Product Routes (`/products`)
### Public Routes

| Method | Endpoint               | 
|--------|------------------------|
| GET    | /api/products          |
| GET    | /products/:id          | 
| GET    | /products/slug/:slug   | 
| GET    | /products/:id/reviews  |

### Admin Routes
| methos | endpoint                      |
|--------|-------------------------------|
| POST   | /products                     | 
| PUT    | /products/:id                 | 
| DELETE | /products/:id                 | 
| POST   | /products/:id/images          | 
| DELETE | /products/:id/images/:imageId | 
| PUT    | /products/:id/stock           | 

##  Category Routes (`/categories`)
### Public Routes

| Method | Endpoint                 | 
|--------|--------------------------|
| GET    | /categories              |
| GET    | /categories/:id          |
| GET    | /categories/slug/:slug   |
| GET    | /categories/:id/products | 

### Admin Routes

| Method | Endpoint         | 
|--------|------------------|
| POST   | /categories      |
| PUT    | /categories/:id  |
| DELETE |  /categories/:id |

##  Cart Routes (`/cart`)
### Protected Routes

| Method | Endpoint            | 
|--------|---------------------|
| GET    | /cart               | 
| POST   | /cart/items         |
| PUT    | /cart/items/:itemId |
| DELETE | /cart/items/:itemId | 
| DELETE | /cart               | 
| GET    | /cart/count         | 

### Public Routes (Guest Cart)

| Method | Endpoint                     | 
|--------|------------------------------|
| POST   | /cart/guest                  |
| GET    | /cart/guest/:sessionId       |
| POST   | /cart/guest/:sessionId/items |


##  Coupon Routes (`/coupons`)
### Public Routes

| Method | Endpoint         | 
|--------|------------------|
| POST   | coupons/validate |

### Admin Routes
| Method | Endpoint            | 
|--------|---------------------|
| GET    | /coupons            |
| GET    | /coupons/:id        |
| POST   | /coupons            | 
| PUT    | /coupons/:id        |
| DELETE | /coupons/:id        |
| PUT    | /coupons/:id/toggle | 

