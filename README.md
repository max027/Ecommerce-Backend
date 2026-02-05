# Ecommerce Backend

# Routes
## Auth
``text
POST    /api/auth/register                      
POST    /api/auth/login                       
POST    /api/auth/refresh                    
POST    /api/auth/logout                    
POST    /api/auth/forgot-password               
POST    /api/auth/reset-password               
POST    /api/auth/verify-email    
GET     /api/me
``
## Admin
``text
GET     /api/admin/vendors
GET     /api/admin/
POST    /api/admin/invite
POST    /api/admin/vendors/invite
POST    /api/admin/accept-invite
PUT     /api/admin/:id
PUT     /api/admin/vendors/:id
DELETE  /api/admin/:id
PUT     /api/admin/vendors/:id/suspend
``
## Roles Management
``text
POST    /api/admin/roles
PUT     /api/admin/roles/:id
DELETE  /api/admin/roles/:id
GET     /api/admin/permissions
``
## Users
``text
GET     /api/users/profile
PUT     /api/users/profile
POST    /api/users/address
PUT     /api/users/address
GET     /api/users/address/:id
PUT     /api/users/address/:id
DELETE  /api/users/address/:id
PUT     /api/users/address/:id/set-default
``

