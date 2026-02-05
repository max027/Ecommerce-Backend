INSERT INTO roles (name, description,created_at,updated_at) VALUES
                                                                ('ADMIN', 'Full system access',now(),now()),
                                                                ('CUSTOMER', 'Regular customer access',now(),now()),
                                                                ('VENDOR', 'Product vendor access',now(),now()),
                                                                ('SUPPORT', 'Customer support access',now(),now());

insert into permissions (name, description, module, created_at, updated_at) VALUES
-- payments
('VIEW_PAYMENTS','view payments ','PAYMENTS', now(), now()),
('REFUND_PAYMENT','refund payments','PAYMENTS',now(), now()),
-- category
('VIEW_CATEGORY','view category of product','CATEGORIES', now(), now()),
('CREATE_CATEGORY','create product category','CATEGORIES', now(), now()),
('UPDATE_CATEGORY','update product category','CATEGORIES', now(), now()),
('DELETE_CATEGORY','delete product category','CATEGORIES', now(), now());

INSERT INTO permissions (name, description, module, created_at, updated_at) VALUES
-- Product
('CREATE_PRODUCT', 'Create new products', 'PRODUCT', now(), now()),
('UPDATE_PRODUCT', 'Edit existing products', 'PRODUCT', now(), now()),
('DELETE_PRODUCT', 'Delete products', 'PRODUCT', now(), now()),
('VIEW_PRODUCT', 'View products', 'PRODUCT', now(), now()),

-- Orders
('VIEW_ORDERS', 'View all orders', 'ORDERS', now(), now()),
('CANCEL_ORDERS', 'Cancel orders', 'ORDERS', now(), now()),
('MANAGE_ORDERS', 'Manage order status', 'ORDERS', now(), now()),

-- Users
('VIEW_USER', 'View user information', 'USERS', now(), now()),
('UPDATE_USER', 'Update user information', 'USERS', now(), now()),
('DELETE_USER', 'Delete user', 'USERS', now(), now()),

-- Inventory
('VIEW_INVENTORY', 'View inventory', 'INVENTORY', now(), now()),
('UPDATE_INVENTORY', 'Update inventory levels', 'INVENTORY', now(), now());


-- Assign permissions to admin role
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id FROM roles r CROSS JOIN permissions p WHERE r.name = 'admin';

-- Assign permissions to customer role
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id FROM roles r JOIN permissions p ON p.name IN ('VIEW_PRODUCT', 'VIEW_CATEGORY','VIEW_ORDERS','VIEW_PAYMENTS','CANCEL_ORDERS')
WHERE r.name = 'customer';

-- assign permission to vendor
insert into role_permission (role_id,permission_id)
select r.id,p.id from roles r join permissions p on p.name in ("VIEW_PRODUCT","CREATE_PRODUCT","UPDATE_PRODUCT","DELETE_PRODUCT","VIEW_CATEGORY","UPDATE_INVENTORY","ADJUST_STOCK","VIEW_ORDERS","MANAGE_ORDERS")
where r.name="vendor";
