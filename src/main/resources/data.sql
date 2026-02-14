-- SEED DATA - ROLES, PERMISSIONS & SAMPLE DATA

-- 1. ROLES

INSERT INTO roles (name, description, created_at, updated_at) VALUES
                                                                  ('ADMIN',     'Full system access',        NOW(), NOW()),
                                                                  ('CUSTOMER',  'Regular customer access',   NOW(), NOW()),
                                                                  ('VENDOR',    'Product vendor access',     NOW(), NOW()),
                                                                  ('SUPPORT',   'Customer support access',   NOW(), NOW());

-- 2. PERMISSIONS

-- Fix: Increase module column size before inserting
ALTER TABLE permissions MODIFY COLUMN module VARCHAR(50);

INSERT INTO permissions (name, description, module, created_at, updated_at) VALUES
                                                                                -- PAYMENTS
                                                                                ('VIEW_PAYMENTS',       'View payments',                'PAYMENTS',      NOW(), NOW()),
                                                                                ('REFUND_PAYMENT',      'Refund payments',              'PAYMENTS',      NOW(), NOW()),

                                                                                -- CATEGORIES
                                                                                ('VIEW_CATEGORY',       'View product categories',      'CATEGORIES',    NOW(), NOW()),
                                                                                ('CREATE_CATEGORY',     'Create product category',      'CATEGORIES',    NOW(), NOW()),
                                                                                ('UPDATE_CATEGORY',     'Update product category',      'CATEGORIES',    NOW(), NOW()),
                                                                                ('DELETE_CATEGORY',     'Delete product category',      'CATEGORIES',    NOW(), NOW()),

                                                                                -- PRODUCTS
                                                                                ('VIEW_PRODUCT',        'View products',                'PRODUCTS',      NOW(), NOW()),
                                                                                ('CREATE_PRODUCT',      'Create new products',          'PRODUCTS',      NOW(), NOW()),
                                                                                ('UPDATE_PRODUCT',      'Edit existing products',       'PRODUCTS',      NOW(), NOW()),
                                                                                ('DELETE_PRODUCT',      'Delete products',              'PRODUCTS',      NOW(), NOW()),

                                                                                -- ORDERS
                                                                                ('VIEW_ORDERS',         'View all orders',              'ORDERS',        NOW(), NOW()),
                                                                                ('MANAGE_ORDERS',       'Manage order status',          'ORDERS',        NOW(), NOW()),
                                                                                ('CANCEL_ORDERS',       'Cancel orders',                'ORDERS',        NOW(), NOW()),

                                                                                -- USERS
                                                                                ('VIEW_USER',           'View user information',        'USERS',         NOW(), NOW()),
                                                                                ('UPDATE_USER',         'Update user information',      'USERS',         NOW(), NOW()),
                                                                                ('DELETE_USER',         'Delete user',                  'USERS',         NOW(), NOW()),

                                                                                -- INVENTORY
                                                                                ('VIEW_INVENTORY',      'View inventory',               'INVENTORY',     NOW(), NOW()),
                                                                                ('UPDATE_INVENTORY',    'Update inventory levels',      'INVENTORY',     NOW(), NOW()),
                                                                                ('ADJUST_STOCK',        'Adjust product stock levels',  'INVENTORY',     NOW(), NOW()),

                                                                                -- REVIEWS
                                                                                ('VIEW_REVIEWS',        'View product reviews',         'REVIEWS',       NOW(), NOW()),
                                                                                ('MANAGE_REVIEWS',      'Approve or delete reviews',    'REVIEWS',       NOW(), NOW()),

                                                                                -- COUPONS
                                                                                ('VIEW_COUPONS',        'View coupons',                 'COUPONS',       NOW(), NOW()),
                                                                                ('CREATE_COUPON',       'Create coupons',               'COUPONS',       NOW(), NOW()),
                                                                                ('UPDATE_COUPON',       'Update coupons',               'COUPONS',       NOW(), NOW()),
                                                                                ('DELETE_COUPON',       'Delete coupons',               'COUPONS',       NOW(), NOW()),

                                                                                -- SHIPMENTS
                                                                                ('VIEW_SHIPMENTS',      'View shipments',               'SHIPMENTS',     NOW(), NOW()),
                                                                                ('MANAGE_SHIPMENTS',    'Create and update shipments',  'SHIPMENTS',     NOW(), NOW()),

                                                                                -- ANALYTICS
                                                                                ('VIEW_ANALYTICS',      'View reports and analytics',   'ANALYTICS',     NOW(), NOW()),

                                                                                -- NOTIFICATIONS
                                                                                ('SEND_NOTIFICATIONS',  'Send notifications to users',  'NOTIFICATIONS', NOW(), NOW());

-- 3. ROLE → PERMISSION ASSIGNMENTS
TRUNCATE TABLE role_permission;
-- ADMIN: All permissions
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         CROSS JOIN permissions p
WHERE r.name = 'ADMIN';

-- CUSTOMER: Limited read + order permissions
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p ON p.name IN (
                                          'VIEW_PRODUCT',
                                          'VIEW_CATEGORY',
                                          'VIEW_ORDERS',
                                          'CANCEL_ORDERS',
                                          'VIEW_PAYMENTS',
                                          'VIEW_REVIEWS',
                                          'VIEW_SHIPMENTS'
    )
WHERE r.name = 'CUSTOMER';

-- VENDOR: Product and order management permissions
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p ON p.name IN (
                                          'VIEW_PRODUCT',
                                          'CREATE_PRODUCT',
                                          'UPDATE_PRODUCT',
                                          'DELETE_PRODUCT',
                                          'VIEW_CATEGORY',
                                          'VIEW_ORDERS',
                                          'MANAGE_ORDERS',
                                          'VIEW_INVENTORY',
                                          'UPDATE_INVENTORY',
                                          'ADJUST_STOCK',
                                          'VIEW_REVIEWS',
                                          'VIEW_ANALYTICS',
                                          'VIEW_PAYMENTS'
    )
WHERE r.name = 'VENDOR';

-- SUPPORT: Read-only + order/user management
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p ON p.name IN (
                                          'VIEW_PRODUCT',
                                          'VIEW_CATEGORY',
                                          'VIEW_ORDERS',
                                          'MANAGE_ORDERS',
                                          'CANCEL_ORDERS',
                                          'VIEW_USER',
                                          'UPDATE_USER',
                                          'VIEW_PAYMENTS',
                                          'VIEW_SHIPMENTS',
                                          'MANAGE_SHIPMENTS',
                                          'VIEW_REVIEWS'
    )
WHERE r.name = 'SUPPORT';

-- 4. CATEGORIES

INSERT INTO categories
(name, description, is_active, slug, created_at, updated_at, parent_id)
VALUES
    ('Electronics',      'Electronic items and gadgets',     TRUE, 'electronics',      NOW(), NOW(), NULL),
    ('Mobile Phones',    'Smartphones and accessories',      TRUE, 'mobile-phones',    NOW(), NOW(), 1),
    ('Laptops',          'Personal and gaming laptops',      TRUE, 'laptops',          NOW(), NOW(), 1),
    ('Headphones',       'Wired and wireless headphones',    TRUE, 'headphones',       NOW(), NOW(), 1),
    ('Home Appliances',  'Daily household appliances',       TRUE, 'home-appliances',  NOW(), NOW(), NULL),
    ('Washing Machines', 'Front load and top load machines', TRUE, 'washing-machines', NOW(), NOW(), 5),
    ('Refrigerators',    'Single and double door fridges',   TRUE, 'refrigerators',    NOW(), NOW(), 5),
    ('Clothing',         'Men and women fashion',            TRUE, 'clothing',         NOW(), NOW(), NULL),
    ('Books',            'Academic and fiction books',       TRUE, 'books',            NOW(), NOW(), NULL);

-- 5. PRODUCTS

INSERT INTO products
(name, sku, slug, description, price, discount_price, category_id, stock_quantity, is_active, created_at, updated_at)
VALUES
    -- Mobile Phones (category_id: 2)
    ('iPhone 15',
     'SKU-IP15',
     'iphone-15',
     'Apple iPhone 15 with A16 Bionic chip, 48MP camera, and Dynamic Island.',
     79999.00, 74999.00, 2, 50, TRUE, NOW(), NOW()),

    ('Samsung Galaxy S24',
     'SKU-S24',
     'samsung-galaxy-s24',
     'Samsung Galaxy S24 with Snapdragon 8 Gen 3, 50MP camera, 6.1" display.',
     69999.00, 64999.00, 2, 40, TRUE, NOW(), NOW()),

    ('OnePlus 12',
     'SKU-OP12',
     'oneplus-12',
     'OnePlus 12 with Snapdragon 8 Gen 3, 50MP Hasselblad camera, 100W fast charging.',
     64999.00, 59999.00, 2, 35, TRUE, NOW(), NOW()),

    -- Laptops (category_id: 3)
    ('Dell XPS 15',
     'SKU-DXPS15',
     'dell-xps-15',
     'Dell XPS 15 with Intel Core i7, 16GB RAM, 512GB SSD, OLED display.',
     149999.00, 139999.00, 3, 20, TRUE, NOW(), NOW()),

    ('MacBook Air M2',
     'SKU-MBA-M2',
     'macbook-air-m2',
     'Apple MacBook Air with M2 chip, 8GB RAM, 256GB SSD, 13.6" Liquid Retina.',
     114999.00, 109999.00, 3, 25, TRUE, NOW(), NOW()),

    -- Headphones (category_id: 4)
    ('Sony WH-1000XM5',
     'SKU-SONYXM5',
     'sony-wh-1000xm5',
     'Sony WH-1000XM5 wireless noise cancelling headphones with 30hr battery.',
     29999.00, 26999.00, 4, 60, TRUE, NOW(), NOW()),

    ('Bose QuietComfort 45',
     'SKU-BOSEQC45',
     'bose-quietcomfort-45',
     'Bose QC45 wireless headphones with world-class noise cancellation.',
     24999.00, 22999.00, 4, 45, TRUE, NOW(), NOW()),

    -- Home Appliances (category_id: 6)
    ('LG Front Load Washing Machine',
     'SKU-LGWM01',
     'lg-washing-machine',
     'LG 8KG Front Load Washing Machine with AI Direct Drive Motor.',
     45999.00, 41999.00, 6, 15, TRUE, NOW(), NOW()),

    ('Samsung Double Door Refrigerator',
     'SKU-SAMREF01',
     'samsung-double-door-refrigerator',
     'Samsung 253L Double Door Refrigerator with Digital Inverter Technology.',
     32999.00, 29999.00, 7, 10, TRUE, NOW(), NOW());

-- 6. PRODUCT IMAGES

INSERT INTO product_images (product_id, image_url,  created_at,updated_at) VALUES
                                                                    (1, 'https://cdn.example.com/products/iphone-15-front.jpg',    NOW(),NOW()),
                                                                    (1, 'https://cdn.example.com/products/iphone-15-back.jpg',     NOW(),NOW()),
                                                                    (2, 'https://cdn.example.com/products/galaxy-s24-front.jpg',   NOW(),NOW()),
                                                                    (3, 'https://cdn.example.com/products/oneplus-12-front.jpg',   NOW(),NOW()),
                                                                    (4, 'https://cdn.example.com/products/dell-xps-15.jpg',        NOW(),NOW()),
                                                                    (5, 'https://cdn.example.com/products/macbook-air-m2.jpg',     NOW(),NOW()),
                                                                    (6, 'https://cdn.example.com/products/sony-xm5-front.jpg',     NOW(),NOW()),
                                                                    (7, 'https://cdn.example.com/products/bose-qc45.jpg',          NOW(),NOW()),
                                                                    (8, 'https://cdn.example.com/products/lg-washing-machine.jpg', NOW(),NOW()),
                                                                    (9, 'https://cdn.example.com/products/samsung-fridge.jpg',     NOW(),NOW());

-- 7. COUPONS

-- INSERT INTO coupons
-- (code, description, discount_type, discount_value, min_order_value, max_discount_amount, usage_limit, is_active, valid_from, valid_until, created_at, updated_at)
-- VALUES
--     ('WELCOME10',  'Welcome coupon - 10% off for new users',  'percentage',   10, 500.00,   500.00,  50,   TRUE, NOW(), NOW() + INTERVAL '30 days',  NOW(), NOW()),
--     ('SAVE500',    'Flat Rs.500 off on orders above Rs.2000', 'fixed_amount', 500, 2000.00,  500.00,  100,  TRUE, NOW(), NOW() + INTERVAL '60 days',  NOW(), NOW()),
--     ('MOBILE20',   '20% off on Mobile Phones',                'percentage',   20, 5000.00,  3000.00, 30,   TRUE, NOW(), NOW() + INTERVAL '15 days',  NOW(), NOW()),
--     ('FREESHIP',   'Free shipping on all orders',             'fixed_amount', 0,  0.00,     NULL,    NULL, TRUE, NOW(), NOW() + INTERVAL '90 days',  NOW(), NOW());
--
-- 8. SHIPPING
--
-- INSERT INTO shipping_methods
-- (name, description, carrier, estimated_days, cost, free_shipping_threshold, is_active, created_at, updated_at)
-- VALUES
--     ('Standard Delivery',  'Delivery in 5-7 business days',  'Delhivery',  '5-7 days',  49.00,   999.00,  TRUE, NOW(), NOW()),
--     ('Express Delivery',   'Delivery in 2-3 business days',  'Blue Dart',  '2-3 days',  99.00,   1999.00, TRUE, NOW(), NOW()),
--     ('Same Day Delivery',  'Delivery within the same day',   'Dunzo',      '1 day',     149.00,  4999.00, TRUE, NOW(), NOW()),
--     ('Cash On Delivery',   'Pay when you receive the order', 'Delhivery',  '5-7 days',  0.00,    NULL,    TRUE, NOW(), NOW());