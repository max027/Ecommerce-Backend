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

INSERT INTO categories
(name, description, is_active, slug, created_at, updated_at, category_id)
VALUES
    ('Electronics', 'Electronic items and gadgets', true, 'electronics', NOW(), NOW(), NULL),

    ('Mobile Phones', 'Smartphones and accessories', true, 'mobile-phones', NOW(), NOW(), 1),

    ('Laptops', 'Personal and gaming laptops', true, 'laptops', NOW(), NOW(), 1),

    ('Headphones', 'Wired and wireless headphones', true, 'headphones', NOW(), NOW(), 1),

    ('Home Appliances', 'Daily household appliances', true, 'home-appliances', NOW(), NOW(), NULL);


INSERT INTO products
(name, sku, slug, price, discount_price, category_id, stock_quantity, is_active, created_at, updated_at)
VALUES
    ('iPhone 15', 'SKU-IP15', 'iphone-15', 79999, 74999, 2, 50, true, NOW(), NOW()),

    ('Samsung Galaxy S24', 'SKU-S24', 'samsung-galaxy-s24', 69999, 64999, 2, 40, true, NOW(), NOW()),

    ('Dell XPS 15', 'SKU-DXPS15', 'dell-xps-15', 149999, 139999, 3, 20, true, NOW(), NOW()),

    ('Sony WH-1000XM5', 'SKU-SONYXM5', 'sony-wh-1000xm5', 29999, 26999, 4, 60, true, NOW(), NOW()),

    ('LG Washing Machine', 'SKU-LGWM01', 'lg-washing-machine', 45999, 41999, 5, 15, true, NOW(), NOW());
